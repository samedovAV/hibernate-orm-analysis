/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.pagination;

import org.hibernate.sql.ast.spi.ParameterMarkerStrategy;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A {@link LimitHandler} for databases like PostgreSQL, H2,
 * and HSQL that support the syntax {@code LIMIT n OFFSET m}.
 * Note that this syntax does not allow specification of an
 * offset without a limit.
 */
public class LimitOffsetLimitHandler extends AbstractSimpleLimitHandler {

	public static LimitOffsetLimitHandler INSTANCE = new LimitOffsetLimitHandler();
	public static LimitOffsetLimitHandler OFFSET_ONLY_INSTANCE = new LimitOffsetLimitHandler() {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		protected String offsetOnlyClause() {
			return " offset ?";
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		protected String offsetOnlyClause(int jdbcParameterCount, ParameterMarkerStrategy parameterMarkerStrategy) {
			return " offset " + parameterMarkerStrategy.createMarker( jdbcParameterCount + 1, null );
		}
	};

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String limitClause(boolean hasFirstRow) {
		return hasFirstRow ? " limit ? offset ?" : " limit ?";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String limitClause(boolean hasFirstRow, int jdbcParameterCount, ParameterMarkerStrategy parameterMarkerStrategy) {
		final String limit = " limit " + parameterMarkerStrategy.createMarker( jdbcParameterCount + 1, null );
		return hasFirstRow
				? limit + " offset " + parameterMarkerStrategy.createMarker( jdbcParameterCount + 2, null )
				: limit;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String offsetOnlyClause() {
		return " limit " + Integer.MAX_VALUE + " offset ?";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String offsetOnlyClause(int jdbcParameterCount, ParameterMarkerStrategy parameterMarkerStrategy) {
		return " limit "
				+ Integer.MAX_VALUE
				+ " offset "
				+ parameterMarkerStrategy.createMarker( jdbcParameterCount + 1, null );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final boolean bindLimitParametersInReverseOrder() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsOffset() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean processSqlMutatesState() {
		return false;
	}
}
