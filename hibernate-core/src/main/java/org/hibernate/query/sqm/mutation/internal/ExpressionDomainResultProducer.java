/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.mutation.internal;

import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.query.sqm.sql.internal.DomainResultProducer;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.spi.SqlAstCreationState;
import org.hibernate.sql.ast.spi.SqlSelection;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.basic.BasicResult;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A wrapper around a basic {@link Expression} that produces a {@link BasicResult} as domain result.
 */
public class ExpressionDomainResultProducer implements DomainResultProducer<Object>, Expression {
	private final Expression expression;

	public ExpressionDomainResultProducer(Expression expression) {
		this.expression = expression;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DomainResult<Object> createDomainResult(String resultVariable, DomainResultCreationState creationState) {
		final SqlSelection sqlSelection = resolveSqlSelection( creationState );
		return new BasicResult<>(
				sqlSelection.getValuesArrayPosition(),
				resultVariable,
				expression.getExpressionType().getSingleJdbcMapping(),
				null,
				false,
				false
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applySqlSelections(DomainResultCreationState creationState) {
		resolveSqlSelection( creationState );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		expression.accept( sqlTreeWalker );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JdbcMappingContainer getExpressionType() {
		return expression.getExpressionType();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private SqlSelection resolveSqlSelection(DomainResultCreationState creationState) {
		final SqlAstCreationState sqlAstCreationState = creationState.getSqlAstCreationState();
		return sqlAstCreationState.getSqlExpressionResolver().resolveSqlSelection(
				expression,
				expression.getExpressionType().getSingleJdbcMapping().getJdbcJavaType(),
				null,
				sqlAstCreationState.getCreationContext().getTypeConfiguration()
		);
	}
}
