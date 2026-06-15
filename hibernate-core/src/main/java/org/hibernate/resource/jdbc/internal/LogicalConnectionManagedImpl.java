/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.resource.jdbc.internal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.ResourceClosedException;
import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.resource.jdbc.LogicalConnection;
import org.hibernate.resource.jdbc.ResourceRegistry;
import org.hibernate.resource.jdbc.spi.JdbcSessionContext;
import org.hibernate.resource.jdbc.spi.JdbcSessionOwner;
import org.hibernate.resource.jdbc.spi.PhysicalConnectionHandlingMode;

import static org.hibernate.ConnectionAcquisitionMode.IMMEDIATELY;
import static org.hibernate.ConnectionReleaseMode.AFTER_STATEMENT;
import static org.hibernate.ConnectionReleaseMode.BEFORE_TRANSACTION_COMPLETION;
import static org.hibernate.ConnectionReleaseMode.ON_CLOSE;
import static org.hibernate.resource.jdbc.internal.LogicalConnectionLogging.CONNECTION_LOGGER;
import static org.hibernate.resource.jdbc.spi.PhysicalConnectionHandlingMode.DELAYED_ACQUISITION_AND_RELEASE_AFTER_TRANSACTION;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Represents a {@link LogicalConnection} where we manage obtaining and releasing the {@link Connection} as needed.
 * This implementation does not claim to be thread-safe and is not designed to be used by multiple
 * threads, yet we do apply a limited amount of care to be able to avoid obscure exceptions when
 * this class is used in the wrong way.
 *
 * @author Steve Ebersole
 */
public class LogicalConnectionManagedImpl extends AbstractLogicalConnectionImplementor {

	private final transient JdbcSessionOwner jdbcSessionOwner;
	private final transient PhysicalConnectionHandlingMode connectionHandlingMode;

	private transient Connection physicalConnection;
	private boolean closed;

	public LogicalConnectionManagedImpl(JdbcSessionOwner sessionOwner, ResourceRegistry registry) {
		jdbcSessionOwner = sessionOwner;
		resourceRegistry = registry;

		connectionHandlingMode = determineConnectionHandlingMode( sessionOwner );
		if ( connectionHandlingMode.getAcquisitionMode() == IMMEDIATELY ) {
			//noinspection resource
			acquireConnectionIfNeeded();
		}

		if ( sessionOwner.getJdbcSessionContext().doesConnectionProviderDisableAutoCommit() ) {
			CONNECTION_LOGGER.connectionProviderDisablesAutoCommitEnabled();
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private PhysicalConnectionHandlingMode determineConnectionHandlingMode(JdbcSessionOwner sessionOwner) {
		final var connectionHandlingMode = sessionOwner.getJdbcSessionContext().getPhysicalConnectionHandlingMode();
		return connectionHandlingMode.getReleaseMode() == AFTER_STATEMENT
			&& !sessionOwner.getJdbcConnectionAccess().supportsAggressiveRelease()
				? DELAYED_ACQUISITION_AND_RELEASE_AFTER_TRANSACTION
				: connectionHandlingMode;
	}

	private LogicalConnectionManagedImpl(JdbcSessionOwner owner, boolean closed) {
		this( owner, new ResourceRegistryStandardImpl() );
		this.closed = closed;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private JdbcSessionContext getJdbcSessionContext() {
		return jdbcSessionOwner.getJdbcSessionContext();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private JdbcConnectionAccess getJdbcConnectionAccess() {
		return jdbcSessionOwner.getJdbcConnectionAccess();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private SqlExceptionHelper getExceptionHelper() {
		return jdbcSessionOwner.getSqlExceptionHelper();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private Connection acquireConnectionIfNeeded() {
		if ( physicalConnection == null ) {
			physicalConnection = acquire();
			afterAcquire();
		}
		return physicalConnection;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private void releaseConnectionIfNeeded() {
		final Connection connection = physicalConnection;
		if ( connection != null ) {
			beforeRelease();
			// Set the connection to null before releasing resources to prevent
			// recursion into this method. Recursion can happen when we release
			// resources and when batch statements are in progress: releasing
			// resources aborts the batch statement, which then triggers
			// logicalConnection.afterStatement(), which in some configurations
			// releases the connection.
			physicalConnection = null;
			release( connection );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isOpen() {
		return !closed;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PhysicalConnectionHandlingMode getConnectionHandlingMode() {
		return connectionHandlingMode;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isPhysicallyConnected() {
		return physicalConnection != null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Connection getPhysicalConnection() {
		errorIfClosed();
		return acquireConnectionIfNeeded();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void afterStatement() {
		super.afterStatement();
		if ( connectionHandlingMode.getReleaseMode() == AFTER_STATEMENT ) {
			if ( getResourceRegistry().hasRegisteredResources() ) {
				CONNECTION_LOGGER.skipConnectionReleaseAfterStatementDueToResources( hashCode() );
			}
			else {
				CONNECTION_LOGGER.initiatingConnectionReleaseAfterStatement( hashCode() );
				releaseConnectionIfNeeded();
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void beforeTransactionCompletion() {
		super.beforeTransactionCompletion();
		if ( connectionHandlingMode.getReleaseMode() == BEFORE_TRANSACTION_COMPLETION ) {
			CONNECTION_LOGGER.initiatingConnectionReleaseBeforeTransactionCompletion( hashCode() );
			releaseConnectionIfNeeded();
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void afterTransaction() {
		super.afterTransaction();
		if ( connectionHandlingMode.getReleaseMode() != ON_CLOSE ) {
			// NOTE: we check for !ON_CLOSE here (rather than AFTER_TRANSACTION) to also catch:
			// - AFTER_STATEMENT cases that were circumvented due to held resources
			// - BEFORE_TRANSACTION_COMPLETION cases that were circumvented because a rollback occurred
			//   (we don't get a beforeTransactionCompletion event on rollback).
			CONNECTION_LOGGER.initiatingConnectionReleaseAfterTransaction( hashCode() );
			releaseConnectionIfNeeded();
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Connection manualDisconnect() {
		if ( closed ) {
			throw new ResourceClosedException( "Logical connection is closed" );
		}
		final var connection = physicalConnection;
		releaseConnectionIfNeeded();
		return connection;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void manualReconnect(Connection suppliedConnection) {
		if ( closed ) {
			throw new ResourceClosedException( "Logical connection is closed" );
		}
		throw new IllegalStateException( "Cannot manually reconnect unless Connection was originally supplied by user" );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private Connection acquire() {
		final var eventHandler = getJdbcSessionContext().getEventHandler();
		eventHandler.jdbcConnectionAcquisitionStart();
		try {
			return getJdbcConnectionAccess().obtainConnection();
		}
		catch ( SQLException e ) {
			throw getExceptionHelper().convert( e, "Unable to acquire JDBC Connection" );
		}
		finally {
			eventHandler.jdbcConnectionAcquisitionEnd( physicalConnection );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private void release(Connection connection) {
		final var eventHandler = getJdbcSessionContext().getEventHandler();
		try {
			try {
				getResourceRegistry().releaseResources();
				if ( !connection.isClosed() ) {
					getExceptionHelper().logAndClearWarnings( connection );
				}
			}
			finally {
				eventHandler.jdbcConnectionReleaseStart();
				getJdbcConnectionAccess().releaseConnection( connection );
			}
		}
		catch (SQLException e) {
			throw getExceptionHelper().convert( e, "Unable to release JDBC Connection" );
		}
		finally {
			eventHandler.jdbcConnectionReleaseEnd();
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private void afterAcquire() {
		try {
			// give the session a chance to set the schema
			jdbcSessionOwner.afterObtainConnection( physicalConnection );
		}
		catch (SQLException e) {
			try {
				getJdbcConnectionAccess().releaseConnection( physicalConnection );
			}
			catch (SQLException re) {
				e.addSuppressed( re );
			}
			throw getExceptionHelper().convert( e, "Error after acquiring JDBC Connection" );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private void beforeRelease() {
		try {
			// give the session a chance to change the schema back to null
			jdbcSessionOwner.beforeReleaseConnection( physicalConnection );
		}
		catch (SQLException e) {
			CONNECTION_LOGGER.errorBeforeReleasingJdbcConnection( hashCode(), e );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void serialize(ObjectOutputStream oos) throws IOException {
		oos.writeBoolean( closed );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static LogicalConnectionManagedImpl deserialize(ObjectInputStream ois, JdbcSessionOwner owner)
			throws IOException {
		return new LogicalConnectionManagedImpl( owner, ois.readBoolean() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Connection close() {
		if ( !closed ) {
			try {
				getResourceRegistry().releaseResources();
			}
			finally {
				CONNECTION_LOGGER.closingLogicalConnection( hashCode() );
				try {
					releaseConnectionIfNeeded();
				}
				finally {
					// no matter what
					closed = true;
					CONNECTION_LOGGER.logicalConnectionClosed( hashCode() );
				}
			}
		}
		return null;
	}


	// PhysicalJdbcTransaction impl ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected Connection getConnectionForTransactionManagement() {
		return getPhysicalConnection();
	}

	boolean initiallyAutoCommit;

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void begin() {
		initiallyAutoCommit =
				!doConnectionsFromProviderHaveAutoCommitDisabled()
						&& determineInitialAutoCommitMode( getConnectionForTransactionManagement() );
		super.begin();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void afterCompletion() {
		resetConnection( initiallyAutoCommit );
		initiallyAutoCommit = false;
		afterTransaction();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected boolean doConnectionsFromProviderHaveAutoCommitDisabled() {
		return getJdbcSessionContext().doesConnectionProviderDisableAutoCommit();
	}
}
