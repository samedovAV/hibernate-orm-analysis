/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.internal;

import org.hibernate.query.spi.DomainQueryExecutionContext;
import org.hibernate.query.spi.QueryOptions;
import org.hibernate.query.spi.QueryParameterBindings;
import org.hibernate.sql.exec.internal.BaseExecutionContext;
import org.hibernate.sql.exec.spi.Callback;
import org.hibernate.sql.exec.spi.JdbcSelect;

import static org.hibernate.query.spi.SqlOmittingQueryOptions.omitSqlQueryOptions;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * ExecutionContext adapter delegating to a DomainQueryExecutionContext
 */
public class SqmJdbcExecutionContextAdapter extends BaseExecutionContext {
	/**
	 * Creates an adapter which drops any locking or paging details from the query options
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static SqmJdbcExecutionContextAdapter omittingLockingAndPaging(DomainQueryExecutionContext sqmExecutionContext) {
		return new SqmJdbcExecutionContextAdapter( sqmExecutionContext );
	}

	/**
	 * Creates an adapter which honors any locking or paging details specified in the query options
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static SqmJdbcExecutionContextAdapter usingLockingAndPaging(DomainQueryExecutionContext sqmExecutionContext) {
		return new SqmJdbcExecutionContextAdapter( sqmExecutionContext, sqmExecutionContext.getQueryOptions() );
	}

	private final DomainQueryExecutionContext sqmExecutionContext;
	private final QueryOptions queryOptions;

	private SqmJdbcExecutionContextAdapter(DomainQueryExecutionContext sqmExecutionContext) {
		this( sqmExecutionContext, omitSqlQueryOptions( sqmExecutionContext.getQueryOptions() ) );
	}

	private SqmJdbcExecutionContextAdapter(DomainQueryExecutionContext sqmExecutionContext, QueryOptions queryOptions) {
		super( sqmExecutionContext.getSession() );
		this.sqmExecutionContext = sqmExecutionContext;
		this.queryOptions = queryOptions;
	}

	public SqmJdbcExecutionContextAdapter(
			DomainQueryExecutionContext sqmExecutionContext,
			JdbcSelect jdbcSelect) {
		this( sqmExecutionContext, getQueryOptions( sqmExecutionContext, jdbcSelect ) );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private static QueryOptions getQueryOptions(DomainQueryExecutionContext sqmExecutionContext, JdbcSelect jdbcSelect) {
		return sqmExecutionContext.getQueryOptions().isLimitInMemoryEnabled() == Boolean.TRUE
				? omitSqlQueryOptions( sqmExecutionContext.getQueryOptions(), true, false )
				: omitSqlQueryOptions( sqmExecutionContext.getQueryOptions(), jdbcSelect );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QueryOptions getQueryOptions() {
		return queryOptions;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public QueryParameterBindings getQueryParameterBindings() {
		return sqmExecutionContext.getQueryParameterBindings();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Callback getCallback() {
		return sqmExecutionContext.getCallback();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean hasCallbackActions() {
		return sqmExecutionContext.hasCallbackActions();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasQueryExecutionToBeAddedToStatistics() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean upgradeLocks() {
		return true;
	}
}
