/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.extract.internal;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.schema.extract.spi.ExtractionContext;

import static org.hibernate.engine.jdbc.JdbcLogging.JDBC_LOGGER;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class ExtractionContextImpl implements ExtractionContext {
	private final ServiceRegistry serviceRegistry;
	private final JdbcEnvironment jdbcEnvironment;
	private final SqlStringGenerationContext context;
	private final JdbcConnectionAccess jdbcConnectionAccess;
	private final DatabaseObjectAccess registeredTableAccess;

	private Connection jdbcConnection;
	private DatabaseMetaData jdbcDatabaseMetaData;

	public ExtractionContextImpl(
			ServiceRegistry serviceRegistry,
			JdbcEnvironment jdbcEnvironment,
			SqlStringGenerationContext context,
			JdbcConnectionAccess jdbcConnectionAccess,
			DatabaseObjectAccess registeredTableAccess) {
		this.serviceRegistry = serviceRegistry;
		this.jdbcEnvironment = jdbcEnvironment;
		this.context = context;
		this.jdbcConnectionAccess = jdbcConnectionAccess;
		this.registeredTableAccess = registeredTableAccess;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ServiceRegistry getServiceRegistry() {
		return serviceRegistry;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcEnvironment getJdbcEnvironment() {
		return jdbcEnvironment;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqlStringGenerationContext getSqlStringGenerationContext() {
		return context;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Connection getJdbcConnection() {
		if ( jdbcConnection == null ) {
			try {
				jdbcConnection = jdbcConnectionAccess.obtainConnection();
			}
			catch (SQLException e) {
				throw jdbcEnvironment.getSqlExceptionHelper()
						.convert( e, "Unable to obtain JDBC Connection" );
			}
		}
		return jdbcConnection;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DatabaseMetaData getJdbcDatabaseMetaData() {
		if ( jdbcDatabaseMetaData == null ) {
			try {
				jdbcDatabaseMetaData = getJdbcConnection().getMetaData();
			}
			catch (SQLException e) {
				throw jdbcEnvironment.getSqlExceptionHelper()
						.convert( e, "Unable to obtain JDBC DatabaseMetaData" );
			}
		}
		return jdbcDatabaseMetaData;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Identifier getDefaultCatalog() {
		return context.getDefaultCatalog();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Identifier getDefaultSchema() {
		return context.getDefaultSchema();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DatabaseObjectAccess getDatabaseObjectAccess() {
		return registeredTableAccess;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void cleanup() {
		if ( jdbcDatabaseMetaData != null ) {
			jdbcDatabaseMetaData = null;
		}

		if ( jdbcConnection != null ) {
			try {
				jdbcConnectionAccess.releaseConnection( jdbcConnection );
			}
			catch (SQLException exception) {
				JDBC_LOGGER.unableToReleaseConnection( exception );
			}
		}
	}
}
