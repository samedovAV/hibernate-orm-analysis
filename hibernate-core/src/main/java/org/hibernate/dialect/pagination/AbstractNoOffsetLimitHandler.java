/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.pagination;

import jakarta.annotation.Nullable;
import org.hibernate.query.spi.Limit;
import org.hibernate.query.spi.QueryOptions;
import org.hibernate.sql.ast.spi.ParameterMarkerStrategy;

import static org.hibernate.sql.ast.internal.ParameterMarkerStrategyStandard.isStandardRenderer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Superclass for {@link LimitHandler}s that don't support
 * offsets at all.
 *
 * @author Gavin King
 */
public abstract class AbstractNoOffsetLimitHandler extends AbstractLimitHandler {

	private final boolean variableLimit;

	public AbstractNoOffsetLimitHandler(boolean variableLimit) {
		this.variableLimit = variableLimit;
	}

	/**
	 * The SQL fragment to insert, with a ? placeholder
	 * for the actual numerical limit.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract String limitClause();

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected String limitClause(int jdbcParameterCount, ParameterMarkerStrategy parameterMarkerStrategy) {
		return limitClause();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract String insert(String limitClause, String sql);

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String processSql(String sql, int jdbcParameterCount, @Nullable ParameterMarkerStrategy parameterMarkerStrategy, QueryOptions queryOptions) {
		return processSql( sql, jdbcParameterCount, parameterMarkerStrategy, queryOptions.getLimit() );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String processSql(String sql, Limit limit) {
		return processSql( sql, -1, null, limit );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private String processSql(String sql, int jdbcParameterCount, @Nullable ParameterMarkerStrategy parameterMarkerStrategy, @Nullable Limit limit) {
		if ( hasMaxRows( limit ) ) {
			final String limitClause;
			if ( supportsVariableLimit() ) {
				limitClause =
						isStandardRenderer( parameterMarkerStrategy )
								? limitClause()
								: limitClause( jdbcParameterCount, parameterMarkerStrategy );
			}
			else {
				limitClause =
						limitClause()
								.replace( "?",
										Integer.toString( getMaxOrLimit( limit ) ) );
			}
			return insert( limitClause, sql );
		}
		else {
			return sql;
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final boolean supportsLimit() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final boolean supportsLimitOffset() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final boolean supportsVariableLimit() {
		return variableLimit;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public abstract boolean bindLimitParametersFirst();

}
