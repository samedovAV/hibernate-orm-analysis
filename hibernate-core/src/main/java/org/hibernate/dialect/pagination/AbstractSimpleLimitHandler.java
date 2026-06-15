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
 * Superclass for simple {@link LimitHandler}s that don't
 * support specifying an offset without a limit.
 *
 * @author Gavin King
 */
public abstract class AbstractSimpleLimitHandler extends AbstractLimitHandler {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract String limitClause(boolean hasFirstRow);

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected String limitClause(boolean hasFirstRow, int jdbcParameterCount, ParameterMarkerStrategy parameterMarkerStrategy) {
		return limitClause( hasFirstRow );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String offsetOnlyClause() {
		return null;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected String offsetOnlyClause(int jdbcParameterCount, ParameterMarkerStrategy parameterMarkerStrategy) {
		return offsetOnlyClause();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String processSql(String sql, Limit limit) {
		return processSql( sql, -1, null, limit );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String processSql(String sql, int jdbcParameterCount, @Nullable ParameterMarkerStrategy parameterMarkerStrategy, QueryOptions queryOptions) {
		return processSql( sql, jdbcParameterCount, parameterMarkerStrategy, queryOptions.getLimit() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private String processSql(String sql, int jdbcParameterCount, @Nullable ParameterMarkerStrategy parameterMarkerStrategy, @Nullable Limit limit) {
		final boolean hasMaxRows = hasMaxRows( limit );
		final boolean hasFirstRow = hasFirstRow( limit );
		if ( hasMaxRows ) {
			final String limitClause =
					isStandardRenderer( parameterMarkerStrategy )
							? limitClause( hasFirstRow )
							: limitClause( hasFirstRow, jdbcParameterCount, parameterMarkerStrategy );
			return insert( limitClause, sql );
		}
		else if ( hasFirstRow ) {
			final String offsetOnlyClause =
					isStandardRenderer( parameterMarkerStrategy )
							? offsetOnlyClause()
							: offsetOnlyClause( jdbcParameterCount, parameterMarkerStrategy );
			return offsetOnlyClause != null
					? insert( offsetOnlyClause, sql )
					: sql;
		}
		else {
			return sql;
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String insert(String limitClause, String sql) {
		return insertBeforeForUpdate( limitClause, sql );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final boolean supportsLimit() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final boolean supportsVariableLimit() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean supportsOffset() {
		return super.supportsOffset();
	}
}
