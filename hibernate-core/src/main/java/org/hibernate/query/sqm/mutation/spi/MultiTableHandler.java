/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.mutation.spi;

import org.hibernate.query.spi.DomainQueryExecutionContext;
import org.hibernate.query.spi.QueryOptions;
import org.hibernate.sql.exec.spi.JdbcParameterBindings;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Simply as a matter of code structuring, it is often worthwhile to put all of the execution code into a separate
 * handler (executor) class.  This contract helps unify those helpers.
 *
 * Hiding this "behind the strategy" also allows mixing approaches based on the nature of specific
 * queries
 *
 * @author Steve Ebersole
 * @since 7.1
 */
public interface MultiTableHandler {

	/**
	 * Create the {@link JdbcParameterBindings} for this multi-table handler based on the execution context.
	 *
	 * @param executionContext Contextual information needed for execution
	 * @return The built parameter bindings
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcParameterBindings createJdbcParameterBindings(DomainQueryExecutionContext executionContext);

	/**
	 * Signals that the SQL depends on the parameter bindings e.g. due to the need for inlining
	 * of parameter values or multiValued parameters.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean dependsOnParameterBindings();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isCompatibleWith(JdbcParameterBindings jdbcParameterBindings, QueryOptions queryOptions);

	/**
	 * Execute the multi-table update or delete indicated by the SQM AST
	 * passed in when this Handler was created.
	 *
	 * @param jdbcParameterBindings The parameter bindings for JDBC parameters
	 * @param executionContext Contextual information needed for execution
	 * @return The "number of rows affected" count
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int execute(JdbcParameterBindings jdbcParameterBindings, DomainQueryExecutionContext executionContext);
}
