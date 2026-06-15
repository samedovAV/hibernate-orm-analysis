/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.pagination;

import org.hibernate.query.spi.Limit;
import org.hibernate.sql.ast.spi.ParameterMarkerStrategy;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A {@link LimitHandler} for Transact SQL and similar
 * databases which support the syntax {@code SELECT TOP n}.
 * Note that this syntax does not allow specification of
 * an offset.
 *
 * @author Brett Meyer
 */
public class TopLimitHandler extends AbstractNoOffsetLimitHandler {

	public static TopLimitHandler INSTANCE = new TopLimitHandler(true);

	public TopLimitHandler(boolean variableLimit) {
		super(variableLimit);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String limitClause() {
		return " top ? ";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String limitClause(int jdbcParameterCount, ParameterMarkerStrategy parameterMarkerStrategy) {
		return " top " + parameterMarkerStrategy.createMarker( 1, null ) + " rows only";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String insert(String limitClause, String sql) {
		return insertAfterDistinct( limitClause, sql );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean bindLimitParametersFirst() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean processSqlMutatesState() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getParameterPositionStart(Limit limit) {
		return hasMaxRows( limit ) && supportsVariableLimit() ? 2 : 1;
	}
}
