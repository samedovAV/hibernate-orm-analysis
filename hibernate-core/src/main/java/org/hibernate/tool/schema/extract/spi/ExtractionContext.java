/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.extract.spi;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.annotation.Nullable;
import org.hibernate.Incubating;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.relational.QualifiedSequenceName;
import org.hibernate.boot.model.relational.QualifiedTableName;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.service.ServiceRegistry;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Defines a context for performing extraction including providing access to information about ongoing extraction as
 * well as to delegates needed in performing extraction.
 *
 * @author Steve Ebersole
 * @author Gail Badner
 */
@Incubating
public interface ExtractionContext {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ServiceRegistry getServiceRegistry();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcEnvironment getJdbcEnvironment();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqlStringGenerationContext getSqlStringGenerationContext();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Connection getJdbcConnection();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DatabaseMetaData getJdbcDatabaseMetaData();

	@Incubating
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default <T> T getQueryResults(
			String queryString,
			Object[] positionalParameters,
			ResultSetProcessor<T> resultSetProcessor) throws SQLException {
		try ( var statement = getJdbcConnection().prepareStatement( queryString ) ) {
			if ( positionalParameters != null ) {
				for ( int i = 0 ; i < positionalParameters.length ; i++ ) {
					statement.setObject( i + 1, positionalParameters[i] );
				}
			}
			try (ResultSet resultSet = statement.executeQuery()) {
				return resultSetProcessor.process( resultSet );
			}
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Identifier getDefaultCatalog();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Identifier getDefaultSchema();

	@Incubating
	interface ResultSetProcessor<T> {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		T process(ResultSet resultSet) throws SQLException;
	}

	/**
	 * In conjunction with {@link #getDatabaseObjectAccess()} provides access to
	 * information about known database objects to the extractor.
	 */
	@Incubating
	interface DatabaseObjectAccess {
		@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
		TableInformation locateTableInformation(QualifiedTableName tableName);
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		SequenceInformation locateSequenceInformation(QualifiedSequenceName sequenceName);
		@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
		PrimaryKeyInformation locatePrimaryKeyInformation(QualifiedTableName tableName);
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		Iterable<ForeignKeyInformation> locateForeignKeyInformation(QualifiedTableName tableName);
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		Iterable<IndexInformation> locateIndexesInformation(QualifiedTableName tableName);
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		boolean isCaching();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DatabaseObjectAccess getDatabaseObjectAccess();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void cleanup();

	abstract class EmptyExtractionContext implements ExtractionContext {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ServiceRegistry getServiceRegistry() {
			return null;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public JdbcEnvironment getJdbcEnvironment() {
			return null;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public SqlStringGenerationContext getSqlStringGenerationContext() {
			return null;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Connection getJdbcConnection() {
			return null;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public DatabaseMetaData getJdbcDatabaseMetaData() {
			return null;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Identifier getDefaultCatalog() {
			return null;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Identifier getDefaultSchema() {
			return null;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public DatabaseObjectAccess getDatabaseObjectAccess() {
			return null;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public void cleanup() {

		}
	}
}
