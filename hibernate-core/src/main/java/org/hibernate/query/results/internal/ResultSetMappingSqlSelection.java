/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.results.internal;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.mapping.BasicValuedMapping;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.query.results.spi.ResultSetMapping;
import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.spi.SqlExpressionAccess;
import org.hibernate.sql.ast.spi.SqlSelection;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.results.jdbc.spi.JdbcValuesMetadata;
import org.hibernate.type.descriptor.ValueExtractor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * SqlSelection implementation used while building
 * {@linkplain ResultSetMapping} references.
 * Doubles as its own {@link Expression} as well.
 *
 * @author Steve Ebersole
 */
public class ResultSetMappingSqlSelection implements SqlSelection, Expression, SqlExpressionAccess {
	private final int valuesArrayPosition;
	private final JdbcMapping valueMapping;
	private final ValueExtractor<?> valueExtractor;

	public ResultSetMappingSqlSelection(int valuesArrayPosition, BasicValuedMapping valueMapping) {
		this ( valuesArrayPosition, valueMapping.getJdbcMapping() );
	}

	public ResultSetMappingSqlSelection(int valuesArrayPosition, JdbcMapping jdbcMapping) {
		this.valuesArrayPosition = valuesArrayPosition;
		this.valueMapping = jdbcMapping;
		this.valueExtractor = jdbcMapping.getJdbcValueExtractor();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ValueExtractor<?> getJdbcValueExtractor() {
		return valueExtractor;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqlSelection resolve(JdbcValuesMetadata jdbcResultsMetadata, SessionFactoryImplementor sessionFactory) {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getValuesArrayPosition() {
		return valuesArrayPosition;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getExpression() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMappingContainer getExpressionType() {
		return valueMapping;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isVirtual() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlAstWalker) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getSqlExpression() {
		return this;
	}
}
