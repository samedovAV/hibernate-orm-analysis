/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.cte;

import org.hibernate.metamodel.mapping.JdbcMapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Information about a column in the CTE table
 *
 * @author Steve Ebersole
 */
public class CteColumn {
	private final String columnExpression;
	private final JdbcMapping jdbcMapping;

	public CteColumn(String columnExpression, JdbcMapping jdbcMapping) {
		this.columnExpression = columnExpression;
		this.jdbcMapping = jdbcMapping;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getColumnExpression() {
		return columnExpression;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMapping getJdbcMapping() {
		return jdbcMapping;
	}
}
