/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.connections.spi;

import org.hibernate.Internal;
import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.Dialect;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contract used for logging "database information" on bootstrap
 *
 * @apiNote Most of the getters here may return {@code null} which indicates the value is not known
 *
 * @author Jan Schatteman
 */
public interface DatabaseConnectionInfo {
	/**
	 * The JDBC URL to be used for connections
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getJdbcUrl();

	/**
	 * The JDBC Driver to be used for connections
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getJdbcDriver();

	/**
	 * The database version.
	 *
	 * @see Dialect#getVersion()
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DatabaseVersion getDialectVersion();

	/**
	 * The default schema
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSchema();

	/**
	 * The default catalog
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCatalog();

	/**
	 * The transaction auto-commit mode in effect.
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getAutoCommitMode();

	/**
	 * The transaction isolation-level in effect.
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getIsolationLevel();

	/**
	 * The minimum connection pool size.
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getPoolMinSize();

	/**
	 * The maximum connection pool size.
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getPoolMaxSize();

	/**
	 * The default JDBC fetch size.
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getJdbcFetchSize();

	@Internal
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasSchema();

	@Internal
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasCatalog();

	/**
	 * Collects the information available here as a single String with the intent of using it in logging.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String toInfoString();
}
