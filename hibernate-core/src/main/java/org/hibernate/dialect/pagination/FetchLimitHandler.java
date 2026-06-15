/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.pagination;

import org.hibernate.sql.ast.spi.ParameterMarkerStrategy;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A {@link LimitHandler} for databases which support the ANSI
 * SQL standard syntax {@code FETCH FIRST m ROWS ONLY} but not
 * {@code OFFSET n ROWS}.
 *
 * @author Gavin King
 */
public class FetchLimitHandler extends AbstractNoOffsetLimitHandler {

	public static final FetchLimitHandler INSTANCE = new FetchLimitHandler(false);

	public FetchLimitHandler(boolean variableLimit) {
		super(variableLimit);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String limitClause() {
		return " fetch first ? rows only";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String limitClause(int jdbcParameterCount, ParameterMarkerStrategy parameterMarkerStrategy) {
		return " fetch first "
				+ parameterMarkerStrategy.createMarker( jdbcParameterCount + 1, null )
				+ " rows only";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String insert(String fetch, String sql) {
		return insertBeforeForUpdate( fetch, sql );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean bindLimitParametersFirst() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean processSqlMutatesState() {
		return false;
	}

}
