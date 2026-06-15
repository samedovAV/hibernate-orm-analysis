/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.pagination;

import org.hibernate.sql.ast.spi.ParameterMarkerStrategy;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Limit handler for MySQL and CUBRID which support the syntax
 * {@code LIMIT n} and {@code LIMIT m, n}. Note that this
 * syntax does not allow specification of an offset without
 * a limit.
 *
 * @author Esen Sagynov (kadishmal at gmail dot com)
 */
public class LimitLimitHandler extends AbstractSimpleLimitHandler {

	public static final LimitLimitHandler INSTANCE = new LimitLimitHandler();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String limitClause(boolean hasFirstRow) {
		return hasFirstRow ? " limit ?,?" : " limit ?";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String limitClause(boolean hasFirstRow, int jdbcParameterCount, ParameterMarkerStrategy parameterMarkerStrategy) {
		final String limit = " limit " + parameterMarkerStrategy.createMarker( jdbcParameterCount + 1, null );
		return hasFirstRow
				? limit + "," + parameterMarkerStrategy.createMarker( jdbcParameterCount + 2, null )
				: limit;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String offsetOnlyClause() {
		return " limit ?," + Integer.MAX_VALUE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String offsetOnlyClause(int jdbcParameterCount, ParameterMarkerStrategy parameterMarkerStrategy) {
		return " limit "
				+ parameterMarkerStrategy.createMarker( jdbcParameterCount + 1, null )
				+ ","
				+ Integer.MAX_VALUE;
	}

	private static final Pattern FOR_UPDATE_PATTERN =
			compile("\\s+for\\s+update\\b|\\s+lock\\s+in\\s+shared\\s+mode\\b|\\s*;?\\s*$", CASE_INSENSITIVE);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected Pattern getForUpdatePattern() {
		return FOR_UPDATE_PATTERN;
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
