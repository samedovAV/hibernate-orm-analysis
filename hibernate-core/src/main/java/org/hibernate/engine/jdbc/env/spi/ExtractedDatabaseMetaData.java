/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.env.spi;

import java.util.List;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.tool.schema.extract.spi.SequenceInformation;

import static java.util.Collections.emptyList;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Information extracted from {@link java.sql.DatabaseMetaData} regarding what the JDBC driver reports as
 * being supported or not.  Obviously {@link java.sql.DatabaseMetaData} reports many things, these are a few in
 * which we have particular interest.
 *
 * @author Steve Ebersole
 */
public interface ExtractedDatabaseMetaData {
	/**
	 * Obtain the JDBC Environment from which this metadata came.
	 *
	 * @return The JDBC environment
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcEnvironment getJdbcEnvironment();

	/**
	 * The name of the database, according to the JDBC driver.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getDatabaseProductName();

	/**
	 * The version of the database, according to the JDBC driver.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getDatabaseProductVersion();

	/**
	 * Does this driver support named schemas in DML?
	 *
	 * @return {@code false} indicates the driver reported false;
	 * {@code true} indicates the driver reported true or that
	 * the driver could not be asked.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean supportsSchemas();

	/**
	 * Does this driver support named catalogs in DML?
	 *
	 * @return {@code false} indicates the driver reported false;
	 * {@code true} indicates the driver reported true or that
	 * the driver could not be asked.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean supportsCatalogs();

	/**
	 * Retrieve the name of the catalog in effect when we connected to the database.
	 *
	 * @return The catalog name
	 *
	 * @see AvailableSettings#DEFAULT_SCHEMA
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getConnectionCatalogName();

	/**
	 * Retrieve the name of the schema in effect when we connected to the database.
	 *
	 * @return The schema name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getConnectionSchemaName();

	/**
	 * Does the driver report supporting named parameters?
	 *
	 * @return {@code true} indicates the driver reported true; {@code false} indicates the driver reported false
	 * or that the driver could not be asked.
	 *
	 * @see AvailableSettings#CALLABLE_NAMED_PARAMS_ENABLED
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean supportsNamedParameters();

	/**
	 * Does the driver report supporting {@link java.sql.Types#REF_CURSOR}?
	 *
	 * @return {@code true} indicates the driver reported true;
	 * {@code false} indicates the driver reported false or that
	 * the driver could not be asked.
	 *
	 * @see java.sql.DatabaseMetaData#supportsRefCursors()
	 * @see org.hibernate.dialect.Dialect#supportsRefCursors
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean supportsRefCursors();

	/**
	 * Did the driver report to supporting scrollable result sets?
	 *
	 * @return True if the driver reported to support {@link java.sql.ResultSet#TYPE_SCROLL_INSENSITIVE}.
	 *
	 * @see java.sql.DatabaseMetaData#supportsResultSetType
	 * @see AvailableSettings#USE_SCROLLABLE_RESULTSET
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean supportsScrollableResults();

	/**
	 * Did the driver report to supporting retrieval of generated keys?
	 *
	 * @return True if the driver reported to support calls to {@link java.sql.Statement#getGeneratedKeys}
	 *
	 * @see java.sql.DatabaseMetaData#supportsGetGeneratedKeys
	 * @see AvailableSettings#USE_GET_GENERATED_KEYS
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean supportsGetGeneratedKeys();

	/**
	 * Did the driver report to supporting batched updates?
	 *
	 * @return True if the driver supports batched updates
	 *
	 * @see java.sql.DatabaseMetaData#supportsBatchUpdates
	 * @see org.hibernate.dialect.Dialect#supportsBatchUpdates
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean supportsBatchUpdates();

	/**
	 * Did the driver report to support performing DDL within transactions?
	 *
	 * @return True if the drivers supports DDL statements within transactions.
	 *
	 * @see java.sql.DatabaseMetaData#dataDefinitionIgnoredInTransactions
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean supportsDataDefinitionInTransaction();

	/**
	 * Did the driver report to DDL statements performed within a transaction performing an implicit commit of the
	 * transaction.
	 *
	 * @return True if the driver/database performs an implicit commit of transaction when DDL statement is
	 * performed
	 *
	 * @see java.sql.DatabaseMetaData#dataDefinitionCausesTransactionCommit()
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean doesDataDefinitionCauseTransactionCommit();

	/**
	 * Retrieve the type of codes the driver says it uses for {@code SQLState}.  They might follow either
	 * the X/Open standard or the SQL92 standard.
	 *
	 * @return The SQLState strategy reportedly used by this driver/database.
	 *
	 * @see java.sql.DatabaseMetaData#getSQLStateType()
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SQLStateType getSqlStateType();

	/**
	 * Retrieve the JDBC URL.
	 *
	 * @see java.sql.DatabaseMetaData#getURL()
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getUrl();

	/**
	 * Retrieve the JDBC driver name.
	 *
	 * @see java.sql.DatabaseMetaData#getDriverName()
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getDriver();

	/**
	 * Retrieve the transaction isolation level.
	 *
	 * @see java.sql.Connection#getTransactionIsolation()
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getTransactionIsolation();

	/**
	 * Retrieve the default transaction isolation level.
	 *
	 * @see java.sql.DatabaseMetaData#getDefaultTransactionIsolation()
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getDefaultTransactionIsolation();

	/**
	 * Retrieve the default JDBC {@linkplain java.sql.Statement#getFetchSize fetch size}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getDefaultFetchSize();

	/**
	 * Retrieve the list of {@code SequenceInformation} objects which describe the underlying database sequences.
	 *
	 * @return {@code SequenceInformation} objects.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default List<SequenceInformation> getSequenceInformationList() {
		return emptyList();
	}
}
