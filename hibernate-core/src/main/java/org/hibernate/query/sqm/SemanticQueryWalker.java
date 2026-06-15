/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm;

import java.util.List;

import jakarta.annotation.Nullable;
import org.hibernate.metamodel.model.domain.internal.AnyDiscriminatorSqmPath;
import org.hibernate.query.sqm.tree.cte.SqmCteContainer;
import org.hibernate.query.sqm.tree.cte.SqmCteStatement;
import org.hibernate.query.sqm.tree.delete.SqmDeleteStatement;
import org.hibernate.query.sqm.tree.domain.NonAggregatedCompositeSimplePath;
import org.hibernate.query.sqm.tree.domain.SqmAnyValuedSimplePath;
import org.hibernate.query.sqm.tree.domain.SqmBagJoin;
import org.hibernate.query.sqm.tree.domain.SqmBasicValuedSimplePath;
import org.hibernate.query.sqm.tree.domain.SqmCorrelatedBagJoin;
import org.hibernate.query.sqm.tree.domain.SqmCorrelatedCrossJoin;
import org.hibernate.query.sqm.tree.domain.SqmCorrelatedCteJoin;
import org.hibernate.query.sqm.tree.domain.SqmCorrelatedDerivedJoin;
import org.hibernate.query.sqm.tree.domain.SqmCorrelatedEntityJoin;
import org.hibernate.query.sqm.tree.domain.SqmCorrelatedListJoin;
import org.hibernate.query.sqm.tree.domain.SqmCorrelatedMapJoin;
import org.hibernate.query.sqm.tree.domain.SqmCorrelatedPluralPartJoin;
import org.hibernate.query.sqm.tree.domain.SqmCorrelatedRoot;
import org.hibernate.query.sqm.tree.domain.SqmCorrelatedRootJoin;
import org.hibernate.query.sqm.tree.domain.SqmCorrelatedSetJoin;
import org.hibernate.query.sqm.tree.domain.SqmCorrelatedSingularJoin;
import org.hibernate.query.sqm.tree.domain.SqmCorrelation;
import org.hibernate.query.sqm.tree.domain.SqmCteRoot;
import org.hibernate.query.sqm.tree.domain.SqmDerivedRoot;
import org.hibernate.query.sqm.tree.domain.SqmEmbeddedValuedSimplePath;
import org.hibernate.query.sqm.tree.domain.SqmEntityValuedSimplePath;
import org.hibernate.query.sqm.tree.domain.SqmFkExpression;
import org.hibernate.query.sqm.tree.domain.SqmFunctionPath;
import org.hibernate.query.sqm.tree.domain.SqmFunctionRoot;
import org.hibernate.query.sqm.tree.domain.SqmIndexedCollectionAccessPath;
import org.hibernate.query.sqm.tree.domain.SqmListJoin;
import org.hibernate.query.sqm.tree.domain.SqmMapEntryReference;
import org.hibernate.query.sqm.tree.domain.SqmElementAggregateFunction;
import org.hibernate.query.sqm.tree.domain.SqmIndexAggregateFunction;
import org.hibernate.query.sqm.tree.domain.SqmMapJoin;
import org.hibernate.query.sqm.tree.domain.SqmPluralPartJoin;
import org.hibernate.query.sqm.tree.domain.SqmPluralValuedSimplePath;
import org.hibernate.query.sqm.tree.domain.SqmSetJoin;
import org.hibernate.query.sqm.tree.domain.SqmSingularJoin;
import org.hibernate.query.sqm.tree.domain.SqmTreatedPath;
import org.hibernate.query.sqm.tree.expression.AsWrapperSqmExpression;
import org.hibernate.query.sqm.tree.expression.JpaCriteriaParameter;
import org.hibernate.query.sqm.tree.expression.SqmAny;
import org.hibernate.query.sqm.tree.expression.SqmAnyDiscriminatorValue;
import org.hibernate.query.sqm.tree.expression.SqmBinaryArithmetic;
import org.hibernate.query.sqm.tree.expression.SqmByUnit;
import org.hibernate.query.sqm.tree.expression.SqmCaseSearched;
import org.hibernate.query.sqm.tree.expression.SqmCaseSimple;
import org.hibernate.query.sqm.tree.expression.SqmCastTarget;
import org.hibernate.query.sqm.tree.expression.SqmCoalesce;
import org.hibernate.query.sqm.tree.expression.SqmCollation;
import org.hibernate.query.sqm.tree.expression.SqmCollectionSize;
import org.hibernate.query.sqm.tree.expression.SqmDistinct;
import org.hibernate.query.sqm.tree.expression.SqmDurationUnit;
import org.hibernate.query.sqm.tree.expression.SqmEnumLiteral;
import org.hibernate.query.sqm.tree.expression.SqmEvery;
import org.hibernate.query.sqm.tree.expression.SqmExpression;
import org.hibernate.query.sqm.tree.expression.SqmExtractUnit;
import org.hibernate.query.sqm.tree.expression.SqmFieldLiteral;
import org.hibernate.query.sqm.tree.expression.SqmFormat;
import org.hibernate.query.sqm.tree.expression.SqmFunction;
import org.hibernate.query.sqm.tree.expression.SqmHqlNumericLiteral;
import org.hibernate.query.sqm.tree.expression.SqmLiteral;
import org.hibernate.query.sqm.tree.expression.SqmLiteralEmbeddableType;
import org.hibernate.query.sqm.tree.expression.SqmLiteralEntityType;
import org.hibernate.query.sqm.tree.expression.SqmModifiedSubQueryExpression;
import org.hibernate.query.sqm.tree.expression.SqmNamedExpression;
import org.hibernate.query.sqm.tree.expression.SqmNamedParameter;
import org.hibernate.query.sqm.tree.expression.SqmOver;
import org.hibernate.query.sqm.tree.expression.SqmOverflow;
import org.hibernate.query.sqm.tree.expression.SqmParameterizedEntityType;
import org.hibernate.query.sqm.tree.expression.SqmPositionalParameter;
import org.hibernate.query.sqm.tree.expression.SqmSetReturningFunction;
import org.hibernate.query.sqm.tree.expression.SqmStar;
import org.hibernate.query.sqm.tree.expression.SqmSummarization;
import org.hibernate.query.sqm.tree.expression.SqmToDuration;
import org.hibernate.query.sqm.tree.expression.SqmTrimSpecification;
import org.hibernate.query.sqm.tree.expression.SqmTuple;
import org.hibernate.query.sqm.tree.expression.SqmUnaryOperation;
import org.hibernate.query.sqm.tree.expression.SqmWindow;
import org.hibernate.query.sqm.tree.from.SqmAttributeJoin;
import org.hibernate.query.sqm.tree.from.SqmCrossJoin;
import org.hibernate.query.sqm.tree.from.SqmCteJoin;
import org.hibernate.query.sqm.tree.from.SqmDerivedJoin;
import org.hibernate.query.sqm.tree.from.SqmEntityJoin;
import org.hibernate.query.sqm.tree.from.SqmFromClause;
import org.hibernate.query.sqm.tree.from.SqmFunctionJoin;
import org.hibernate.query.sqm.tree.from.SqmRoot;
import org.hibernate.query.sqm.tree.insert.SqmConflictClause;
import org.hibernate.query.sqm.tree.insert.SqmInsertSelectStatement;
import org.hibernate.query.sqm.tree.insert.SqmInsertValuesStatement;
import org.hibernate.query.sqm.tree.insert.SqmValues;
import org.hibernate.query.sqm.tree.predicate.SqmBetweenPredicate;
import org.hibernate.query.sqm.tree.predicate.SqmBooleanExpressionPredicate;
import org.hibernate.query.sqm.tree.predicate.SqmComparisonPredicate;
import org.hibernate.query.sqm.tree.predicate.SqmEmptinessPredicate;
import org.hibernate.query.sqm.tree.predicate.SqmExistsPredicate;
import org.hibernate.query.sqm.tree.predicate.SqmGroupedPredicate;
import org.hibernate.query.sqm.tree.predicate.SqmInListPredicate;
import org.hibernate.query.sqm.tree.predicate.SqmInSubQueryPredicate;
import org.hibernate.query.sqm.tree.predicate.SqmJunctionPredicate;
import org.hibernate.query.sqm.tree.predicate.SqmLikePredicate;
import org.hibernate.query.sqm.tree.predicate.SqmMemberOfPredicate;
import org.hibernate.query.sqm.tree.predicate.SqmNegatedPredicate;
import org.hibernate.query.sqm.tree.predicate.SqmNullnessPredicate;
import org.hibernate.query.sqm.tree.predicate.SqmPredicate;
import org.hibernate.query.sqm.tree.predicate.SqmTruthnessPredicate;
import org.hibernate.query.sqm.tree.predicate.SqmWhereClause;
import org.hibernate.query.sqm.tree.select.SqmDynamicInstantiation;
import org.hibernate.query.sqm.tree.select.SqmJpaCompoundSelection;
import org.hibernate.query.sqm.tree.select.SqmOrderByClause;
import org.hibernate.query.sqm.tree.select.SqmQueryGroup;
import org.hibernate.query.sqm.tree.select.SqmQuerySpec;
import org.hibernate.query.sqm.tree.select.SqmSelectClause;
import org.hibernate.query.sqm.tree.select.SqmSelectStatement;
import org.hibernate.query.sqm.tree.select.SqmSelection;
import org.hibernate.query.sqm.tree.select.SqmSortSpecification;
import org.hibernate.query.sqm.tree.select.SqmSubQuery;
import org.hibernate.query.sqm.tree.update.SqmAssignment;
import org.hibernate.query.sqm.tree.update.SqmSetClause;
import org.hibernate.query.sqm.tree.update.SqmUpdateStatement;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Support for walking a Semantic Query Model (SQM) tree
 *
 * @author Steve Ebersole
 */
public interface SemanticQueryWalker<T> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitUpdateStatement(SqmUpdateStatement<?> statement);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitSetClause(SqmSetClause setClause);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitAssignment(SqmAssignment<?> assignment);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitInsertSelectStatement(SqmInsertSelectStatement<?> statement);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitInsertValuesStatement(SqmInsertValuesStatement<?> statement);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitConflictClause(SqmConflictClause<?> sqmConflictClause);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitDeleteStatement(SqmDeleteStatement<?> statement);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitSelectStatement(SqmSelectStatement<?> statement);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitCteStatement(SqmCteStatement<?> sqmCteStatement);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitCteContainer(SqmCteContainer consumer);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// from-clause / domain paths

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitFromClause(SqmFromClause fromClause);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitRootPath(SqmRoot<?> sqmRoot);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitRootDerived(SqmDerivedRoot<?> sqmRoot);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitRootFunction(SqmFunctionRoot<?> sqmRoot);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitRootCte(SqmCteRoot<?> sqmRoot);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitCrossJoin(SqmCrossJoin<?, ?> joinedFromElement);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitPluralPartJoin(SqmPluralPartJoin<?, ?> joinedFromElement);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitQualifiedEntityJoin(SqmEntityJoin<?,?> joinedFromElement);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitQualifiedAttributeJoin(SqmAttributeJoin<?, ?> joinedFromElement);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default T visitCorrelatedCteJoin(SqmCorrelatedCteJoin<?> join){
		return visitQualifiedCteJoin( join );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default T visitCorrelatedDerivedJoin(SqmCorrelatedDerivedJoin<?> join){
		return visitQualifiedDerivedJoin( join );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default T visitCorrelatedCrossJoin(SqmCorrelatedCrossJoin<?, ?> join) {
		return visitCrossJoin( join );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default T visitCorrelatedEntityJoin(SqmCorrelatedEntityJoin<?,?> join) {
		return visitQualifiedEntityJoin( join );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default T visitCorrelatedPluralPartJoin(SqmCorrelatedPluralPartJoin<?, ?> join) {
		return visitPluralPartJoin( join );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default T visitBagJoin(SqmBagJoin<?,?> join){
		return visitQualifiedAttributeJoin( join );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default T visitCorrelatedBagJoin(SqmCorrelatedBagJoin<?, ?> join) {
		return visitQualifiedAttributeJoin( join );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default T visitCorrelatedListJoin(SqmCorrelatedListJoin<?, ?> join) {
		return visitQualifiedAttributeJoin( join );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default T visitCorrelatedMapJoin(SqmCorrelatedMapJoin<?, ?, ?> join) {
		return visitQualifiedAttributeJoin( join );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default T visitCorrelatedSetJoin(SqmCorrelatedSetJoin<?, ?> join) {
		return visitQualifiedAttributeJoin( join );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default T visitCorrelatedSingularJoin(SqmCorrelatedSingularJoin<?, ?> join) {
		return visitQualifiedAttributeJoin( join );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default T visitListJoin(SqmListJoin<?, ?> join) {
		return visitQualifiedAttributeJoin( join );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default T visitMapJoin(SqmMapJoin<?, ?, ?> join) {
		return visitQualifiedAttributeJoin( join );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default T visitSetJoin(SqmSetJoin<?, ?> join) {
		return visitQualifiedAttributeJoin( join );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default T visitSingularJoin(SqmSingularJoin<?, ?> join) {
		return visitQualifiedAttributeJoin( join );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitQualifiedDerivedJoin(SqmDerivedJoin<?> joinedFromElement);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitQualifiedFunctionJoin(SqmFunctionJoin<?> joinedFromElement);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitQualifiedCteJoin(SqmCteJoin<?> joinedFromElement);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitBasicValuedPath(SqmBasicValuedSimplePath<?> path);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitEmbeddableValuedPath(SqmEmbeddedValuedSimplePath<?> path);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitAnyValuedValuedPath(SqmAnyValuedSimplePath<?> path);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitNonAggregatedCompositeValuedPath(NonAggregatedCompositeSimplePath<?> path);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitEntityValuedPath(SqmEntityValuedSimplePath<?> path);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitPluralValuedPath(SqmPluralValuedSimplePath<?> path);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitFkExpression(SqmFkExpression<?> fkExpression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitDiscriminatorPath(DiscriminatorSqmPath<?> sqmPath);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitIndexedPluralAccessPath(SqmIndexedCollectionAccessPath<?> path);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitElementAggregateFunction(SqmElementAggregateFunction<?> path);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitIndexAggregateFunction(SqmIndexAggregateFunction<?> path);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitFunctionPath(SqmFunctionPath<?> functionPath);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitTreatedPath(SqmTreatedPath<?, ?> sqmTreatedPath);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitCorrelation(SqmCorrelation<?, ?> correlation);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default T visitCorrelatedRootJoin(SqmCorrelatedRootJoin<?> correlatedRootJoin){
		return visitCorrelation( correlatedRootJoin );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default T visitCorrelatedRoot(SqmCorrelatedRoot<?> correlatedRoot){
		return visitCorrelation( correlatedRoot );
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Query spec

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitQueryGroup(SqmQueryGroup<?> queryGroup);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitQuerySpec(SqmQuerySpec<?> querySpec);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitSelectClause(SqmSelectClause selectClause);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitSelection(SqmSelection<?> selection);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitValues(SqmValues values);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitGroupByClause(List<SqmExpression<?>> groupByClauseExpressions);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitHavingClause(SqmPredicate clause);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitDynamicInstantiation(SqmDynamicInstantiation<?> sqmDynamicInstantiation);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitJpaCompoundSelection(SqmJpaCompoundSelection<?> selection);

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// expressions - general

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitLiteral(SqmLiteral<?> literal);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitEnumLiteral(SqmEnumLiteral<?> sqmEnumLiteral);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitFieldLiteral(SqmFieldLiteral<?> sqmFieldLiteral);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> T visitHqlNumericLiteral(SqmHqlNumericLiteral<N> numericLiteral);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitTuple(SqmTuple<?> sqmTuple);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitCollation(SqmCollation sqmCollate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitBinaryArithmeticExpression(SqmBinaryArithmetic<?> expression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitSubQueryExpression(SqmSubQuery<?> expression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitModifiedSubQueryExpression(SqmModifiedSubQueryExpression<?> expression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitSimpleCaseExpression(SqmCaseSimple<?, ?> expression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitSearchedCaseExpression(SqmCaseSearched<?> expression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitAny(SqmAny<?> sqmAny);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitEvery(SqmEvery<?> sqmEvery);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitSummarization(SqmSummarization<?> sqmSummarization);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitPositionalParameterExpression(SqmPositionalParameter<?> expression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitNamedParameterExpression(SqmNamedParameter<?> expression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitJpaCriteriaParameter(JpaCriteriaParameter<?> expression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitEntityTypeLiteralExpression(SqmLiteralEntityType<?> expression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitEmbeddableTypeLiteralExpression(SqmLiteralEmbeddableType<?> expression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitAnyDiscriminatorTypeExpression(AnyDiscriminatorSqmPath<?> expression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitAnyDiscriminatorTypeValueExpression(SqmAnyDiscriminatorValue<?> expression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitParameterizedEntityTypeExpression(SqmParameterizedEntityType<?> expression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitUnaryOperationExpression(SqmUnaryOperation<?> expression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitFunction(SqmFunction<?> tSqmFunction);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitSetReturningFunction(SqmSetReturningFunction<?> tSqmFunction);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitExtractUnit(SqmExtractUnit<?> extractUnit);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitFormat(SqmFormat sqmFormat);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitCastTarget(SqmCastTarget<?> sqmCastTarget);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitTrimSpecification(SqmTrimSpecification trimSpecification);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitDistinct(SqmDistinct<?> distinct);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitStar(SqmStar sqmStar);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitOver(SqmOver<?> over);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitWindow(SqmWindow widow);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitOverflow(SqmOverflow<?> sqmOverflow);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitCoalesce(SqmCoalesce<?> sqmCoalesce);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitToDuration(SqmToDuration<?> toDuration);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitByUnit(SqmByUnit sqmByUnit);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitDurationUnit(SqmDurationUnit<?> durationUnit);

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// predicates

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitWhereClause(@Nullable SqmWhereClause whereClause);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitGroupedPredicate(SqmGroupedPredicate predicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitJunctionPredicate(SqmJunctionPredicate predicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitComparisonPredicate(SqmComparisonPredicate predicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitIsEmptyPredicate(SqmEmptinessPredicate predicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitIsNullPredicate(SqmNullnessPredicate predicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitIsTruePredicate(SqmTruthnessPredicate predicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitBetweenPredicate(SqmBetweenPredicate predicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitLikePredicate(SqmLikePredicate predicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitMemberOfPredicate(SqmMemberOfPredicate predicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitNegatedPredicate(SqmNegatedPredicate predicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitInListPredicate(SqmInListPredicate<?> predicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitInSubQueryPredicate(SqmInSubQueryPredicate<?> predicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitBooleanExpressionPredicate(SqmBooleanExpressionPredicate predicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitExistsPredicate(SqmExistsPredicate sqmExistsPredicate);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// sorting

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitOrderByClause(SqmOrderByClause orderByClause);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitSortSpecification(SqmSortSpecification sortSpecification);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// paging

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitOffsetExpression(SqmExpression<?> expression);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitFetchExpression(SqmExpression<?> expression);



	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// misc

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitPluralAttributeSizeFunction(SqmCollectionSize function);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitMapEntryFunction(SqmMapEntryReference<?, ?> function);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitFullyQualifiedClass(Class<?> namedClass);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitAsWrapperExpression(AsWrapperSqmExpression<?> expression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T visitNamedExpression(SqmNamedExpression<?> expression);
}
