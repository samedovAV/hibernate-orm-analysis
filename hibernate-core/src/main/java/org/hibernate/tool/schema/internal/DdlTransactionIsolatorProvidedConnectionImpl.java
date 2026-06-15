/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.internal;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.resource.transaction.spi.DdlTransactionIsolator;
import org.hibernate.tool.schema.internal.exec.JdbcConnectionAccessProvidedConnectionImpl;
import org.hibernate.tool.schema.internal.exec.JdbcContext;
import org.hibernate.tool.schema.spi.SchemaManagementException;

import static org.hibernate.engine.jdbc.JdbcLogging.JDBC_LOGGER;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Specialized DdlTransactionIsolator for cases where we have a user provided Connection
 *
 * @author Steve Ebersole
 */
class DdlTransactionIsolatorProvidedConnectionImpl implements DdlTransactionIsolator {

	private final JdbcContext jdbcContext;

	public DdlTransactionIsolatorProvidedConnectionImpl(JdbcContext jdbcContext) {
		assert jdbcContext.getJdbcConnectionAccess() instanceof JdbcConnectionAccessProvidedConnectionImpl;
		this.jdbcContext = jdbcContext;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcContext getJdbcContext() {
		return jdbcContext;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Connection getIsolatedConnection() {
		return getIsolatedConnection(true);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Connection getIsolatedConnection(boolean autocommit) {
		try {
			Connection connection = jdbcContext.getJdbcConnectionAccess().obtainConnection();
			if ( connection.getAutoCommit() != autocommit ) {
				throw new SchemaManagementException( "User-provided Connection via JdbcConnectionAccessProvidedConnectionImpl has wrong auto-commit mode" );
			}
			return connection;
		}
		catch (SQLException e) {
			// should never happen
			throw new SchemaManagementException( "Error accessing user-provided Connection via JdbcConnectionAccessProvidedConnectionImpl", e );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void release() {
		final var connectionAccess = jdbcContext.getJdbcConnectionAccess();
		if( !( connectionAccess instanceof JdbcConnectionAccessProvidedConnectionImpl ) ) {
			throw new IllegalStateException(
				"DdlTransactionIsolatorProvidedConnectionImpl should always use a JdbcConnectionAccessProvidedConnectionImpl"
			);
		}
		try {
			// While passing the connection to the releaseConnection method might be suitable for other `JdbcConnectionAccess` implementations,
			// it has no meaning for JdbcConnectionAccessProvidedConnectionImpl because, in this case, the connection is wrapped
			// and we don't have access to it upon releasing via the DdlTransactionIsolatorProvidedConnectionImpl.
			connectionAccess.releaseConnection( null );
		}
		catch (SQLException exception) {
			JDBC_LOGGER.unableToReleaseIsolatedConnection( exception );
		}
	}
}
