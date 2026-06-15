/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.sql;

import java.util.List;
import java.util.function.Supplier;

import org.hibernate.LockMode;
import org.hibernate.engine.spi.LoadQueryInfluencers;
import org.hibernate.internal.util.collections.Stack;
import org.hibernate.metamodel.mapping.MappingModelExpressible;
import org.hibernate.query.sqm.spi.BaseSemanticQueryWalker;
import org.hibernate.query.sqm.tree.SqmVisitableNode;
import org.hibernate.query.sqm.tree.expression.SqmExpression;
import org.hibernate.query.sqm.tree.expression.SqmParameter;
import org.hibernate.query.sqm.tree.predicate.SqmPredicate;
import org.hibernate.query.sqm.tree.select.SqmQueryPart;
import org.hibernate.sql.ast.Clause;
import org.hibernate.sql.ast.SqlAstJoinType;
import org.hibernate.sql.ast.spi.FromClauseAccess;
import org.hibernate.sql.ast.spi.SqlAliasBaseGenerator;
import org.hibernate.sql.ast.spi.SqlAstCreationContext;
import org.hibernate.sql.ast.spi.SqlAstCreationState;
import org.hibernate.sql.ast.spi.SqlAstProcessingState;
import org.hibernate.sql.ast.spi.SqlExpressionResolver;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.ast.tree.expression.QueryTransformer;
import org.hibernate.sql.ast.tree.predicate.Predicate;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 *
 */
public class FakeSqmToSqlAstConverter extends BaseSemanticQueryWalker implements SqmToSqlAstConverter {

	private final SqlAstCreationState creationState;

	public FakeSqmToSqlAstConverter(SqlAstCreationState creationState) {
		this.creationState = creationState;
	}
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// SqlAstCreationState

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqlAstCreationContext getCreationContext() {
		return creationState.getCreationContext();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqlAstProcessingState getCurrentProcessingState() {
		return creationState.getCurrentProcessingState();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqlExpressionResolver getSqlExpressionResolver() {
		return creationState.getSqlExpressionResolver();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqlAliasBaseGenerator getSqlAliasBaseGenerator() {
		return creationState.getSqlAliasBaseGenerator();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public LoadQueryInfluencers getLoadQueryInfluencers() {
		return new LoadQueryInfluencers( getCreationContext().getSessionFactory() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean applyOnlyLoadByKeyFilters() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void registerLockMode(String identificationVariable, LockMode explicitLockMode) {
		creationState.registerLockMode( identificationVariable, explicitLockMode );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public FromClauseAccess getFromClauseAccess() {
		return creationState.getFromClauseAccess();
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// SqmToSqlAstConverter

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Stack<Clause> getCurrentClauseStack() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Stack<SqmQueryPart<?>> getSqmQueryPartStack() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmQueryPart<?> getCurrentSqmQueryPart() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void registerQueryTransformer(QueryTransformer transformer) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable SqlAstJoinType getCurrentlyProcessingJoinType() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isInTypeInference() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MappingModelExpressible<?> resolveFunctionImpliedReturnType() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MappingModelExpressible<?> determineValueMapping(SqmExpression<?> sqmExpression) {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object visitWithInferredType(
			SqmVisitableNode node,
			Supplier<MappingModelExpressible<?>> inferredTypeAccess) {
		return node.accept( this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<Expression> expandSelfRenderingFunctionMultiValueParameter(SqmParameter<?> sqmParameter) {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Predicate visitNestedTopLevelPredicate(SqmPredicate predicate) {
		return (Predicate) predicate.accept( this );
	}
}
