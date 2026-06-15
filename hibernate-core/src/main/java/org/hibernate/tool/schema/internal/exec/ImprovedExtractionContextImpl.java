/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.internal.exec;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.resource.transaction.spi.DdlTransactionIsolator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.schema.extract.spi.ExtractionContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class ImprovedExtractionContextImpl implements ExtractionContext {
	private final ServiceRegistry serviceRegistry;
	private final JdbcEnvironment jdbcEnvironment;
	private final SqlStringGenerationContext context;
	private final DdlTransactionIsolator ddlTransactionIsolator;

	private final DatabaseObjectAccess databaseObjectAccess;

	private DatabaseMetaData jdbcDatabaseMetaData;

	public ImprovedExtractionContextImpl(
			ServiceRegistry serviceRegistry,
			JdbcEnvironment jdbcEnvironment,
			SqlStringGenerationContext context,
			DdlTransactionIsolator ddlTransactionIsolator,
			DatabaseObjectAccess databaseObjectAccess) {
		this.serviceRegistry = serviceRegistry;
		this.jdbcEnvironment = jdbcEnvironment;
		this.context = context;
		this.ddlTransactionIsolator = ddlTransactionIsolator;
		this.databaseObjectAccess = databaseObjectAccess;
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
		return ddlTransactionIsolator.getIsolatedConnection();
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
		return databaseObjectAccess;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void cleanup() {
		if ( jdbcDatabaseMetaData != null ) {
			jdbcDatabaseMetaData = null;
		}
	}
}
