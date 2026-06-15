/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.spi;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.annotation.Nullable;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.hibernate.sql.exec.spi.ExecutionContext;
import org.hibernate.sql.exec.spi.JdbcParameterBinder;
import org.hibernate.sql.exec.spi.JdbcParameterBindings;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Nathan Xu
 */
class FilterJdbcParameter implements JdbcParameter, JdbcParameterBinder {
	private final JdbcMapping jdbcMapping;
	private final Object jdbcParameterValue;

	FilterJdbcParameter(JdbcMapping jdbcMapping, Object jdbcParameterValue) {
		this.jdbcMapping = jdbcMapping;
		this.jdbcParameterValue = jdbcParameterValue;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcParameterBinder getParameterBinder() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void bindParameterValue(PreparedStatement statement, int startPosition, JdbcParameterBindings jdbcParameterBindings, ExecutionContext executionContext) throws SQLException {
		jdbcMapping.getJdbcValueBinder().bind(
				statement,
				jdbcMapping.convertToRelationalValue( jdbcParameterValue ),
				startPosition,
				executionContext.getSession()
		);

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMappingContainer getExpressionType() {
		return jdbcMapping;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Integer getParameterId() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		throw new IllegalStateException(  );
	}
}
