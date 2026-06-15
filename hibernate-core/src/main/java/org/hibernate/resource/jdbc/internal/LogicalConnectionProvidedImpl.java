/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.resource.jdbc.internal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;

import static org.hibernate.resource.jdbc.internal.LogicalConnectionLogging.CONNECTION_LOGGER;

import org.hibernate.resource.jdbc.ResourceRegistry;
import org.hibernate.resource.jdbc.spi.PhysicalConnectionHandlingMode;

import static org.hibernate.resource.jdbc.spi.PhysicalConnectionHandlingMode.IMMEDIATE_ACQUISITION_AND_HOLD;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class LogicalConnectionProvidedImpl extends AbstractLogicalConnectionImplementor {

	private transient Connection providedConnection;
	private final boolean initiallyAutoCommit;
	private boolean closed;

	public LogicalConnectionProvidedImpl(Connection providedConnection, ResourceRegistry resourceRegistry) {
		this.resourceRegistry = resourceRegistry;
		if ( providedConnection == null ) {
			throw new IllegalArgumentException( "Provided Connection cannot be null" );
		}
		this.providedConnection = providedConnection;
		this.initiallyAutoCommit = determineInitialAutoCommitMode( providedConnection );
	}

	private LogicalConnectionProvidedImpl(boolean closed, boolean initiallyAutoCommit) {
		this.resourceRegistry = new ResourceRegistryStandardImpl();
		this.closed = closed;
		this.initiallyAutoCommit = initiallyAutoCommit;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PhysicalConnectionHandlingMode getConnectionHandlingMode() {
		return IMMEDIATE_ACQUISITION_AND_HOLD;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isOpen() {
		return !closed;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Connection close() {
		CONNECTION_LOGGER.closingLogicalConnection();
		getResourceRegistry().releaseResources();
		try {
			return providedConnection;
		}
		finally {
			providedConnection = null;
			closed = true;
			CONNECTION_LOGGER.logicalConnectionClosed();
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isPhysicallyConnected() {
		return providedConnection != null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Connection getPhysicalConnection() {
		errorIfClosed();
		return providedConnection;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void serialize(ObjectOutputStream oos) throws IOException {
		oos.writeBoolean( closed );
		oos.writeBoolean( initiallyAutoCommit );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static LogicalConnectionProvidedImpl deserialize(
			ObjectInputStream ois) throws IOException, ClassNotFoundException {
		final boolean isClosed = ois.readBoolean();
		final boolean initiallyAutoCommit = ois.readBoolean();
		return new LogicalConnectionProvidedImpl( isClosed, initiallyAutoCommit );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Connection manualDisconnect() {
		errorIfClosed();
		try {
			resourceRegistry.releaseResources();
			return providedConnection;
		}
		finally {
			providedConnection = null;
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void manualReconnect(Connection connection) {
		errorIfClosed();
		if ( connection == null ) {
			throw new IllegalArgumentException( "cannot reconnect using a null connection" );
		}
		else if ( connection == providedConnection ) {
			// likely an unmatched reconnect call (no matching disconnect call)
			CONNECTION_LOGGER.reconnectingSameConnectionAlreadyConnected();
		}
		else if ( providedConnection != null ) {
			throw new IllegalArgumentException(
					"Cannot reconnect to a new user-supplied connection because currently connected; must disconnect before reconnecting."
			);
		}
		providedConnection = connection;
		CONNECTION_LOGGER.manuallyReconnectedLogicalConnection();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected Connection getConnectionForTransactionManagement() {
		return providedConnection;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void afterCompletion() {
		afterTransaction();
		resetConnection( initiallyAutoCommit );
	}
}
