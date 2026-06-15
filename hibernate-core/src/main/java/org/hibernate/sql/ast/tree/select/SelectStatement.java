/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.select;

import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.query.sqm.sql.internal.DomainResultProducer;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.spi.SqlExpressionResolver;
import org.hibernate.sql.ast.spi.SqlSelection;
import org.hibernate.sql.ast.tree.AbstractStatement;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.cte.CteContainer;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.basic.BasicResult;
import org.hibernate.type.spi.TypeConfiguration;

import java.util.Collections;
import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class SelectStatement extends AbstractStatement implements SqlAstNode, Expression, DomainResultProducer {
	private final QueryPart queryPart;
	private final List<DomainResult<?>> domainResults;

	public SelectStatement(QueryPart queryPart) {
		this( queryPart, Collections.emptyList() );
	}

	public SelectStatement(
			QueryPart queryPart,
			List<DomainResult<?>> domainResults) {
		this( null, queryPart, domainResults );
	}

	public SelectStatement(
			CteContainer cteContainer,
			QueryPart queryPart,
			List<DomainResult<?>> domainResults) {
		super( cteContainer );
		this.queryPart = queryPart;
		this.domainResults = domainResults;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isSelection() {
		return true;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QuerySpec getQuerySpec() {
		return queryPart.getFirstQuerySpec();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QueryPart getQueryPart() {
		return queryPart;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<DomainResult<?>> getDomainResultDescriptors() {
		return domainResults;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker walker) {
		walker.visitSelectStatement( this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DomainResult<?> createDomainResult(String resultVariable, DomainResultCreationState creationState) {
		final List<SqlSelection> sqlSelections =
				queryPart.getFirstQuerySpec().getSelectClause().getSqlSelections();
		if ( sqlSelections.size() == 1 ) {
			final SqlSelection first = sqlSelections.get( 0 );
			final JdbcMapping jdbcMapping = first.getExpressionType().getSingleJdbcMapping();
			final SqlSelection sqlSelection =
					creationState.getSqlAstCreationState().getSqlExpressionResolver()
							.resolveSqlSelection(
									this,
									jdbcMapping.getJdbcJavaType(),
									null,
									creationState.getSqlAstCreationState().getCreationContext()
											.getTypeConfiguration()
							);
			return new BasicResult<>(
					sqlSelection.getValuesArrayPosition(),
					resultVariable,
					jdbcMapping
			);
		}
		else {
			throw new UnsupportedOperationException("Domain result for non-scalar subquery shouldn't be created");
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void applySqlSelections(DomainResultCreationState creationState) {
		final TypeConfiguration typeConfiguration =
				creationState.getSqlAstCreationState().getCreationContext()
						.getTypeConfiguration();
		final SqlExpressionResolver expressionResolver =
				creationState.getSqlAstCreationState().getSqlExpressionResolver();
		for ( SqlSelection sqlSelection :
				queryPart.getFirstQuerySpec().getSelectClause().getSqlSelections() ) {
			sqlSelection.getExpressionType().forEachJdbcType(
					(index, jdbcMapping) -> {
						expressionResolver
								.resolveSqlSelection(
										this,
										jdbcMapping.getJdbcJavaType(),
										null,
										typeConfiguration
								);
					}
			);
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JdbcMappingContainer getExpressionType() {
		final SelectClause selectClause = queryPart.getFirstQuerySpec().getSelectClause();
		final List<SqlSelection> sqlSelections = selectClause.getSqlSelections();
		if ( sqlSelections.size() == 1 ) {
			return sqlSelections.get( 0 ).getExpressionType();
		}
		else {
			return null;
		}
	}
}
