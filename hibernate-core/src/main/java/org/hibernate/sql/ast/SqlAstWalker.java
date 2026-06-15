/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast;

import org.hibernate.Incubating;
import org.hibernate.persister.internal.SqlFragmentPredicate;
import org.hibernate.query.sqm.tree.expression.Conversion;
import org.hibernate.sql.ast.spi.SqlSelection;
import org.hibernate.sql.ast.tree.delete.DeleteStatement;
import org.hibernate.sql.ast.tree.expression.Any;
import org.hibernate.sql.ast.tree.expression.BinaryArithmeticExpression;
import org.hibernate.sql.ast.tree.expression.CaseSearchedExpression;
import org.hibernate.sql.ast.tree.expression.CaseSimpleExpression;
import org.hibernate.sql.ast.tree.expression.CastTarget;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.hibernate.sql.ast.tree.expression.ColumnReference;
import org.hibernate.sql.ast.tree.expression.AggregateColumnWriteExpression;
import org.hibernate.sql.ast.tree.expression.Distinct;
import org.hibernate.sql.ast.tree.expression.Duration;
import org.hibernate.sql.ast.tree.expression.DurationUnit;
import org.hibernate.sql.ast.tree.expression.EmbeddableTypeLiteral;
import org.hibernate.sql.ast.tree.expression.EntityTypeLiteral;
import org.hibernate.sql.ast.tree.expression.Every;
import org.hibernate.sql.ast.tree.expression.ExtractUnit;
import org.hibernate.sql.ast.tree.expression.Format;
import org.hibernate.sql.ast.tree.expression.JdbcLiteral;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.hibernate.sql.ast.tree.expression.ModifiedSubQueryExpression;
import org.hibernate.sql.ast.tree.expression.NestedColumnReference;
import org.hibernate.sql.ast.tree.expression.Over;
import org.hibernate.sql.ast.tree.expression.Overflow;
import org.hibernate.sql.ast.tree.expression.QueryLiteral;
import org.hibernate.sql.ast.tree.expression.SelfRenderingExpression;
import org.hibernate.sql.ast.tree.expression.SqlSelectionExpression;
import org.hibernate.sql.ast.tree.expression.SqlTuple;
import org.hibernate.sql.ast.tree.expression.Star;
import org.hibernate.sql.ast.tree.expression.Summarization;
import org.hibernate.sql.ast.tree.expression.TrimSpecification;
import org.hibernate.sql.ast.tree.expression.UnaryOperation;
import org.hibernate.sql.ast.tree.expression.UnparsedNumericLiteral;
import org.hibernate.sql.ast.tree.from.FromClause;
import org.hibernate.sql.ast.tree.from.FunctionTableReference;
import org.hibernate.sql.ast.tree.from.NamedTableReference;
import org.hibernate.sql.ast.tree.from.QueryPartTableReference;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.sql.ast.tree.from.TableGroupJoin;
import org.hibernate.sql.ast.tree.from.TableReferenceJoin;
import org.hibernate.sql.ast.tree.from.ValuesTableReference;
import org.hibernate.sql.ast.tree.insert.InsertSelectStatement;
import org.hibernate.sql.ast.tree.predicate.BetweenPredicate;
import org.hibernate.sql.ast.tree.predicate.BooleanExpressionPredicate;
import org.hibernate.sql.ast.tree.predicate.ComparisonPredicate;
import org.hibernate.sql.ast.tree.predicate.ExistsPredicate;
import org.hibernate.sql.ast.tree.predicate.FilterPredicate;
import org.hibernate.sql.ast.tree.predicate.GroupedPredicate;
import org.hibernate.sql.ast.tree.predicate.InArrayPredicate;
import org.hibernate.sql.ast.tree.predicate.InListPredicate;
import org.hibernate.sql.ast.tree.predicate.InSubQueryPredicate;
import org.hibernate.sql.ast.tree.predicate.Junction;
import org.hibernate.sql.ast.tree.predicate.LikePredicate;
import org.hibernate.sql.ast.tree.predicate.NegatedPredicate;
import org.hibernate.sql.ast.tree.predicate.NullnessPredicate;
import org.hibernate.sql.ast.tree.predicate.SelfRenderingPredicate;
import org.hibernate.sql.ast.tree.predicate.ThruthnessPredicate;
import org.hibernate.sql.ast.tree.select.QueryGroup;
import org.hibernate.sql.ast.tree.select.QueryPart;
import org.hibernate.sql.ast.tree.select.QuerySpec;
import org.hibernate.sql.ast.tree.select.SelectClause;
import org.hibernate.sql.ast.tree.select.SelectStatement;
import org.hibernate.sql.ast.tree.select.SortSpecification;
import org.hibernate.sql.ast.tree.update.Assignment;
import org.hibernate.sql.ast.tree.update.UpdateStatement;
import org.hibernate.sql.model.ast.ColumnWriteFragment;
import org.hibernate.sql.model.internal.OptionalTableUpdate;
import org.hibernate.sql.model.internal.TableDeleteCustomSql;
import org.hibernate.sql.model.internal.TableDeleteStandard;
import org.hibernate.sql.model.internal.TableInsertCustomSql;
import org.hibernate.sql.model.internal.TableInsertStandard;
import org.hibernate.sql.model.internal.TableUpdateCustomSql;
import org.hibernate.sql.model.internal.TableUpdateStandard;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 * @author Andrea Boriero
 */
@Incubating
public interface SqlAstWalker {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitSelectStatement(SelectStatement statement);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitDeleteStatement(DeleteStatement statement);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitUpdateStatement(UpdateStatement statement);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitInsertStatement(InsertSelectStatement statement);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitAssignment(Assignment assignment);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitQueryGroup(QueryGroup queryGroup);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitQuerySpec(QuerySpec querySpec);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitSortSpecification(SortSpecification sortSpecification);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitOffsetFetchClause(QueryPart querySpec);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitSelectClause(SelectClause selectClause);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitSqlSelection(SqlSelection sqlSelection);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitFromClause(FromClause fromClause);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitTableGroup(TableGroup tableGroup);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitTableGroupJoin(TableGroupJoin tableGroupJoin);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitNamedTableReference(NamedTableReference tableReference);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitValuesTableReference(ValuesTableReference tableReference);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitQueryPartTableReference(QueryPartTableReference tableReference);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitFunctionTableReference(FunctionTableReference tableReference);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitTableReferenceJoin(TableReferenceJoin tableReferenceJoin);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitColumnReference(ColumnReference columnReference);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitNestedColumnReference(NestedColumnReference nestedColumnReference);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitAggregateColumnWriteExpression(AggregateColumnWriteExpression aggregateColumnWriteExpression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitExtractUnit(ExtractUnit extractUnit);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitFormat(Format format);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitDistinct(Distinct distinct);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitOverflow(Overflow overflow);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitStar(Star star);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitTrimSpecification(TrimSpecification trimSpecification);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitCastTarget(CastTarget castTarget);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitBinaryArithmeticExpression(BinaryArithmeticExpression arithmeticExpression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitCaseSearchedExpression(CaseSearchedExpression caseSearchedExpression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitCaseSimpleExpression(CaseSimpleExpression caseSimpleExpression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitAny(Any any);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitEvery(Every every);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitSummarization(Summarization every);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitOver(Over<?> over);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitSelfRenderingExpression(SelfRenderingExpression expression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitSqlSelectionExpression(SqlSelectionExpression expression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitEntityTypeLiteral(EntityTypeLiteral expression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitEmbeddableTypeLiteral(EmbeddableTypeLiteral expression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitTuple(SqlTuple tuple);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitCollation(Collation collation);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitParameter(JdbcParameter jdbcParameter);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitJdbcLiteral(JdbcLiteral<?> jdbcLiteral);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitQueryLiteral(QueryLiteral<?> queryLiteral);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> void visitUnparsedNumericLiteral(UnparsedNumericLiteral<N> literal);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitUnaryOperationExpression(UnaryOperation unaryOperationExpression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitModifiedSubQueryExpression(ModifiedSubQueryExpression expression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitBooleanExpressionPredicate(BooleanExpressionPredicate booleanExpressionPredicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitBetweenPredicate(BetweenPredicate betweenPredicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitFilterPredicate(FilterPredicate filterPredicate);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitFilterFragmentPredicate(FilterPredicate.FilterFragmentPredicate fragmentPredicate);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitSqlFragmentPredicate(SqlFragmentPredicate predicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitGroupedPredicate(GroupedPredicate groupedPredicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitInListPredicate(InListPredicate inListPredicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitInSubQueryPredicate(InSubQueryPredicate inSubQueryPredicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitInArrayPredicate(InArrayPredicate inArrayPredicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitExistsPredicate(ExistsPredicate existsPredicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitJunction(Junction junction);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitLikePredicate(LikePredicate likePredicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitNegatedPredicate(NegatedPredicate negatedPredicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitNullnessPredicate(NullnessPredicate nullnessPredicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitThruthnessPredicate(ThruthnessPredicate predicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitRelationalPredicate(ComparisonPredicate comparisonPredicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitSelfRenderingPredicate(SelfRenderingPredicate selfRenderingPredicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitDurationUnit(DurationUnit durationUnit);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitDuration(Duration duration);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitConversion(Conversion conversion);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Model mutations

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitStandardTableInsert(TableInsertStandard tableInsert);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitCustomTableInsert(TableInsertCustomSql tableInsert);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitStandardTableDelete(TableDeleteStandard tableDelete);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitCustomTableDelete(TableDeleteCustomSql tableDelete);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitStandardTableUpdate(TableUpdateStandard tableUpdate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitOptionalTableUpdate(OptionalTableUpdate tableUpdate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitCustomTableUpdate(TableUpdateCustomSql tableUpdate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitColumnWriteFragment(ColumnWriteFragment columnWriteFragment);
}
