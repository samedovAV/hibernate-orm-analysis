/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.pagination;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hibernate.query.spi.Limit;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Handler not supporting query LIMIT clause. JDBC API is used to set maximum number of returned rows.
 *
 * @author Lukasz Antoniak
 */
public class NoopLimitHandler extends AbstractLimitHandler {

	public static final NoopLimitHandler INSTANCE = new NoopLimitHandler();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String processSql(String sql, Limit limit) {
		return sql;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int bindLimitParametersAtStartOfQuery(Limit limit, PreparedStatement statement, int index) {
		return 0;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int bindLimitParametersAtEndOfQuery(Limit limit, PreparedStatement statement, int index) {
		return 0;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void setMaxRows(Limit limit, PreparedStatement statement) throws SQLException {
		if ( hasMaxRows( limit ) ) {
			final Integer firstRow = limit.getFirstRow();
			final int convertedMaxRows =
					limit.getMaxRows()
					+ convertToFirstRowValue( firstRow == null ? 0 : firstRow );
			// Use Integer.MAX_VALUE on overflow
			statement.setMaxRows( convertedMaxRows < 0 ? Integer.MAX_VALUE : convertedMaxRows );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean processSqlMutatesState() {
		return false;
	}
}
