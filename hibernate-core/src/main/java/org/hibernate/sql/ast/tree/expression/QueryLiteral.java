/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hibernate.metamodel.mapping.BasicValuedMapping;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.metamodel.mapping.SqlExpressible;
import org.hibernate.query.sqm.sql.internal.DomainResultProducer;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.spi.SqlSelection;
import org.hibernate.sql.exec.spi.ExecutionContext;
import org.hibernate.sql.exec.spi.JdbcParameterBindings;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.basic.BasicResult;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Represents a literal in the SQL AST.  This form accepts a {@link BasicValuedMapping}
 * as its {@link org.hibernate.metamodel.mapping.MappingModelExpressible}.
 *
 * @author Steve Ebersole
 * @see JdbcLiteral
 */
public class QueryLiteral<T> implements Literal, DomainResultProducer<T> {
	private final T value;
	private final SqlExpressible expressible;

	public QueryLiteral(T value, SqlExpressible expressible) {
		assert value == null || expressible.getJdbcMapping().getJdbcJavaType().isInstance( value );
		this.value = value;
		this.expressible = expressible;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public T getLiteralValue() {
		return value;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JdbcMapping getJdbcMapping() {
		return expressible.getJdbcMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker walker) {
		walker.visitQueryLiteral( this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMappingContainer getExpressionType() {
		return expressible;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DomainResult<T> createDomainResult(
			String resultVariable,
			DomainResultCreationState creationState) {
		final SqlSelection sqlSelection = creationState.getSqlAstCreationState().getSqlExpressionResolver()
				.resolveSqlSelection(
						this,
						expressible.getJdbcMapping().getJdbcJavaType(),
						null,
						creationState.getSqlAstCreationState().getCreationContext().getTypeConfiguration()
				);

		return new BasicResult<>(
				sqlSelection.getValuesArrayPosition(),
				resultVariable,
				expressible.getJdbcMapping()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void bindParameterValue(
			PreparedStatement statement,
			int startPosition,
			JdbcParameterBindings jdbcParameterBindings,
			ExecutionContext executionContext) throws SQLException {
		//noinspection unchecked
		expressible.getJdbcMapping().getJdbcValueBinder().bind(
				statement,
				getLiteralValue(),
				startPosition,
				executionContext.getSession()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applySqlSelections(DomainResultCreationState creationState) {
		creationState.getSqlAstCreationState().getSqlExpressionResolver().resolveSqlSelection(
				this,
				expressible.getJdbcMapping().getJdbcJavaType(),
				null,
				creationState.getSqlAstCreationState().getCreationContext().getMappingMetamodel().getTypeConfiguration()
		);
	}
}
