/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.exec.spi;

import jakarta.annotation.Nullable;
import org.hibernate.Incubating;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.hibernate.sql.exec.internal.lock.LoadedValuesCollectorFactory;
import org.hibernate.sql.results.jdbc.spi.JdbcValuesMappingProducer;

import java.sql.Connection;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Primary operation which is a {@code SELECT} performed via JDBC.
 *
 * @author Steve Ebersole
 */
@Incubating
public interface JdbcSelect extends PrimaryOperation, CacheableJdbcOperation {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcValuesMappingProducer getJdbcValuesMappingProducer();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcLockStrategy getLockStrategy();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean usesLimitParameters();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcParameter getLimitParameter();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getRowsToSkip();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getMaxRows();

	/**
	 * Returns a Factory used to create a collector of values loaded to be applied during the
	 * processing of the selection's results.
	 * May be {@code null}.
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LoadedValuesCollectorFactory getLoadedValuesCollectorFactory();

	/**
	 * Perform any pre-actions.
	 * <p>
	 * Generally the pre-actions should use the passed {@code jdbcStatementAccess} to interact with the
	 * database, although the {@code jdbcConnection} can be used to create specialized statements,
	 * access the {@linkplain java.sql.DatabaseMetaData database metadata}, etc.
	 *
	 * @param jdbcStatementAccess Access to a JDBC Statement object which may be used to perform the action.
	 * @param jdbcConnection The JDBC Connection.
	 * @param executionContext Access to contextual information useful while executing.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void performPreActions(StatementAccess jdbcStatementAccess, Connection jdbcConnection, ExecutionContext executionContext);	/**
	 * Perform any post-actions.
	 * <p>
	 * Generally the post-actions should use the passed {@code jdbcStatementAccess} to interact with the
	 * database, although the {@code jdbcConnection} can be used to create specialized statements,
	 * access the {@linkplain java.sql.DatabaseMetaData database metadata}, etc.
	 *
	 * @param succeeded Whether the primary operation succeeded.
	 * @param jdbcStatementAccess Access to a JDBC Statement object which may be used to perform the action.
	 * @param jdbcConnection The JDBC Connection.
	 * @param executionContext Access to contextual information useful while executing.
	 * @param loadedValuesCollector Access to the collector of values loaded as part of the primary operation.  This is useful for post-actions that need to know what was loaded in order to perform their work.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void performPostActions(
			boolean succeeded,
			StatementAccess jdbcStatementAccess,
			Connection jdbcConnection,
			ExecutionContext executionContext,
			LoadedValuesCollector loadedValuesCollector);

}
