/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.internal.exec;

import java.sql.Connection;
import java.sql.SQLException;
import jakarta.persistence.PersistenceException;

import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;

import static org.hibernate.engine.jdbc.JdbcLogging.JDBC_LOGGER;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Implementation of JdbcConnectionAccess for cases where we are provided
 * a JDBC Connection to use.
 *
 * @author Steve Ebersole
 */
public class JdbcConnectionAccessProvidedConnectionImpl implements JdbcConnectionAccess {

	private final Connection jdbcConnection;
	private final boolean wasInitiallyAutoCommit;

	public JdbcConnectionAccessProvidedConnectionImpl(Connection jdbcConnection) {
		this.jdbcConnection = jdbcConnection;
		wasInitiallyAutoCommit = enableAutoCommit( jdbcConnection );
		JDBC_LOGGER.initialAutoCommit( wasInitiallyAutoCommit );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static boolean enableAutoCommit(Connection jdbcConnection) {
		try {
			final boolean wasInitiallyAutoCommit = jdbcConnection.getAutoCommit();
			if ( !wasInitiallyAutoCommit ) {
				try {
					jdbcConnection.setAutoCommit( true );
				}
				catch (SQLException exception) {
					throw new PersistenceException(
							String.format(
									"Could not set provided connection [%s] to auto-commit mode" +
											" (needed for schema generation)",
									jdbcConnection
							),
							exception
					);
				}
			}
			return wasInitiallyAutoCommit;
		}
		catch (SQLException ignore) {
			return false;
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Connection obtainConnection() throws SQLException {
		return jdbcConnection;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void releaseConnection(Connection connection) throws SQLException {
		// NOTE: reset auto-commit, but *do not* close the Connection.
		//       The application handed us this connection.
		if ( !wasInitiallyAutoCommit ) {
			try {
				if ( jdbcConnection.getAutoCommit() ) {
					jdbcConnection.setAutoCommit( false );
				}
			}
			catch (SQLException exception) {
				JDBC_LOGGER.unableToResetAutoCommitDisabled( exception );
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsAggressiveRelease() {
		return false;
	}
}
