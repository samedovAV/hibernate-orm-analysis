/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import java.util.List;
import java.util.Set;

import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.BooleanExpression;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

import jakarta.annotation.Nullable;
import org.hibernate.query.common.FetchClauseType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models a {@code SELECT} query.  Used as a delegate in
 * implementing {@link jakarta.persistence.criteria.CriteriaQuery}
 * and {@link jakarta.persistence.criteria.Subquery}.
 *
 * @apiNote Internally (HQL and SQM) Hibernate supports ordering and limiting
 * for both root- and sub- criteria even though JPA only defines support for
 * them on a root.
 *
 * @see JpaCriteriaQuery
 * @see JpaSubQuery
 *
 * @author Steve Ebersole
 */
public interface JpaQueryStructure<T> extends JpaQueryPart<T> {

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Select clause

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isDistinct();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryStructure<T> setDistinct(boolean distinct);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSelection<T> getSelection();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryStructure<T> setSelection(JpaSelection<T> selection);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// From clause

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<? extends JpaRoot<?>> getRoots();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<? extends JpaRoot<?>> getRootList();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryStructure<T> addRoot(JpaRoot<?> root);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Where clause

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate getRestriction();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryStructure<T> setRestriction(@Nullable JpaPredicate restriction);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryStructure<T> setRestriction(@Nullable Expression<Boolean> restriction);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryStructure<T> setRestriction(BooleanExpression... restrictions);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryStructure<T> setRestriction(Predicate... restrictions);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryStructure<T> setRestriction(List<Predicate> restrictions);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Grouping (group-by / having) clause

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<? extends JpaExpression<?>> getGroupingExpressions();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryStructure<T> setGroupingExpressions(List<? extends Expression<?>> grouping);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryStructure<T> setGroupingExpressions(Expression<?>... grouping);

	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate getGroupRestriction();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryStructure<T> setGroupRestriction(@Nullable JpaPredicate restrictions);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryStructure<T> setGroupRestriction(@Nullable Expression<Boolean> restriction);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryStructure<T> setGroupRestriction(Predicate... restrictions);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryStructure<T> setGroupRestriction(BooleanExpression... restrictions);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryStructure<T> setGroupRestriction(List<Predicate> restrictions);

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Covariant overrides

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryStructure<T> setSortSpecifications(List<? extends JpaOrder> sortSpecifications);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryStructure<T> setOffset(@Nullable JpaExpression<? extends Number> offset);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryStructure<T> setFetch(@Nullable JpaExpression<? extends Number> fetch);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryStructure<T> setFetch(@Nullable JpaExpression<? extends Number> fetch, FetchClauseType fetchClauseType);
}
