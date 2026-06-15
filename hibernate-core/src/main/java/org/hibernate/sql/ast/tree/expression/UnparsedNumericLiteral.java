/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.query.sqm.sql.internal.DomainResultProducer;
import org.hibernate.query.sqm.tree.expression.NumericTypeCategory;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.spi.SqlExpressionResolver;
import org.hibernate.sql.ast.spi.SqlSelection;
import org.hibernate.sql.exec.spi.ExecutionContext;
import org.hibernate.sql.exec.spi.JdbcParameterBindings;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.basic.BasicResult;
import org.hibernate.type.spi.TypeConfiguration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A numeric literal coming from an HQL query, which needs special handling
 *
 * @see org.hibernate.query.sqm.tree.expression.SqmHqlNumericLiteral
 *
 * @author Steve Ebersole
 */
public class UnparsedNumericLiteral<N extends Number> implements Literal, DomainResultProducer<N> {
	private final String literalValue;
	private final NumericTypeCategory typeCategory;
	private final JdbcMapping jdbcMapping;

	public UnparsedNumericLiteral(String literalValue, NumericTypeCategory typeCategory, JdbcMapping jdbcMapping) {
		this.literalValue = literalValue;
		this.typeCategory = typeCategory;
		this.jdbcMapping = jdbcMapping;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public N getLiteralValue() {
		return typeCategory.parseLiteralValue( literalValue );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void bindParameterValue(
			PreparedStatement statement,
			int startPosition,
			JdbcParameterBindings jdbcParameterBindings,
			ExecutionContext executionContext) throws SQLException {
		//noinspection unchecked
		jdbcMapping.getJdbcValueBinder().bind(
				statement,
				getLiteralValue(),
				startPosition,
				executionContext.getSession()
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getUnparsedLiteralValue() {
		return literalValue;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NumericTypeCategory getTypeCategory() {
		return typeCategory;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMapping getJdbcMapping() {
		return jdbcMapping;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMappingContainer getExpressionType() {
		return getJdbcMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DomainResult<N> createDomainResult(String resultVariable, DomainResultCreationState creationState) {
		final SqlExpressionResolver sqlExpressionResolver =
				creationState.getSqlAstCreationState().getSqlExpressionResolver();
		final TypeConfiguration typeConfiguration =
				creationState.getSqlAstCreationState().getCreationContext().getTypeConfiguration();

		final SqlSelection sqlSelection = sqlExpressionResolver.resolveSqlSelection(
				this,
				getJdbcMapping().getJdbcJavaType(),
				null,
				typeConfiguration
		);

		return new BasicResult<>(
				sqlSelection.getValuesArrayPosition(),
				resultVariable,
				jdbcMapping
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applySqlSelections(DomainResultCreationState creationState) {
		creationState.getSqlAstCreationState().getSqlExpressionResolver().resolveSqlSelection(
				this,
				jdbcMapping.getJdbcJavaType(),
				null,
				creationState.getSqlAstCreationState().getCreationContext().getMappingMetamodel().getTypeConfiguration()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitUnparsedNumericLiteral( this );
	}
}
