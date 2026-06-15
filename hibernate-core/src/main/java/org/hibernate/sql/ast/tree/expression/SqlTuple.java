/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.metamodel.mapping.MappingModelExpressible;
import org.hibernate.query.sqm.SqmExpressible;
import org.hibernate.query.sqm.sql.internal.DomainResultProducer;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.spi.SqlAstCreationState;
import org.hibernate.sql.ast.spi.SqlSelection;
import org.hibernate.sql.ast.tree.update.Assignable;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.tuple.TupleResult;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class SqlTuple implements Expression, SqlTupleContainer, DomainResultProducer, Assignable {
	private final List<? extends Expression> expressions;
	private final MappingModelExpressible<?> valueMapping;

	public SqlTuple(List<? extends Expression> expressions, MappingModelExpressible<?> valueMapping) {
		this.expressions = expressions;
		this.valueMapping = valueMapping;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MappingModelExpressible<?> getExpressionType() {
		return valueMapping;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<? extends Expression> getExpressions(){
		return expressions;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<ColumnReference> getColumnReferences() {
		// TODO: this operation is completely untypesafe
		//       since the List can totally contain
		//       Expressions which aren't ColumnReferences
		return expressions.stream()
				.map( expression -> (ColumnReference) expression ).toList();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitTuple( this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqlTuple getSqlTuple() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public DomainResult<?> createDomainResult(
			String resultVariable,
			DomainResultCreationState creationState) {
		final SqmExpressible<?> expressible = (SqmExpressible<?>) valueMapping;
		final int[] valuesArrayPositions = new int[expressions.size()];
		for ( int i = 0; i < expressions.size(); i++ ) {
			final Expression expression = expressions.get( i );
			valuesArrayPositions[i] =
					resolveSelection( creationState.getSqlAstCreationState(), expression )
							.getValuesArrayPosition();
		}
		return new TupleResult<>( valuesArrayPositions, resultVariable, expressible.getExpressibleJavaType() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static SqlSelection resolveSelection(SqlAstCreationState creationState, Expression expression) {
		return creationState.getSqlExpressionResolver()
				.resolveSqlSelection(
						expression,
						expression.getExpressionType().getSingleJdbcMapping().getJdbcJavaType(),
						null,
						creationState.getCreationContext().getMappingMetamodel().getTypeConfiguration()
				);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applySqlSelections(DomainResultCreationState creationState) {
		throw new UnsupportedOperationException();
	}

	public static class Builder {
		private final MappingModelExpressible<?> valueMapping;

		private List<Expression> expressions;

		public Builder(MappingModelExpressible<?> valueMapping) {
			this.valueMapping = valueMapping;
		}

		public Builder(MappingModelExpressible<?> valueMapping, int jdbcTypeCount) {
			this( valueMapping );
			expressions = new ArrayList<>( jdbcTypeCount );
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public void addSubExpression(Expression expression) {
			if ( expressions == null ) {
				expressions = new ArrayList<>();
			}

			expressions.add( expression );
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public SqlTuple buildTuple() {
			return new SqlTuple( expressions == null ? Collections.emptyList() : expressions, valueMapping );
		}
	}
}
