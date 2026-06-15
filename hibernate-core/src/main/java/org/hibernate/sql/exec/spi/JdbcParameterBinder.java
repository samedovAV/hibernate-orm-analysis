/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.exec.spi;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Performs parameter value binding to a JDBC PreparedStatement.
 *
 * @author Steve Ebersole
 * @author John O'Hara
 */
public interface JdbcParameterBinder {

	JdbcParameterBinder NOOP = (statement, startPosition, jdbcParameterBindings, executionContext) -> {};

	/**
	 * Bind the appropriate value in the JDBC statement
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void bindParameterValue(
			PreparedStatement statement,
			int startPosition,
			JdbcParameterBindings jdbcParameterBindings,
			ExecutionContext executionContext) throws SQLException;
}
