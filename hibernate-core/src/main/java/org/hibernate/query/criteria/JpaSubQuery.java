/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.BooleanExpression;
import jakarta.persistence.criteria.CollectionJoin;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.ListJoin;
import jakarta.persistence.criteria.MapJoin;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import jakarta.persistence.criteria.SetJoin;
import jakarta.persistence.criteria.Subquery;
import org.hibernate.query.common.FetchClauseType;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JpaSubQuery<T> extends Subquery<T>, JpaSelectCriteria<T>, JpaCriteriaSelect<T>, JpaExpression<T>, JpaCteContainer {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSubQuery<T> multiselect(Selection<?>... selections);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSubQuery<T> multiselect(List<Selection<?>> selectionList);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, Y> JpaCrossJoin<X, Y> correlate(JpaCrossJoin<X, Y> parentCrossJoin);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaEntityJoin<T,X> correlate(JpaEntityJoin<T,X> parentEntityJoin);

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Limit/Offset/Fetch clause

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Number> getOffset();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSubQuery<T> offset(@Nullable JpaExpression<? extends Number> offset);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSubQuery<T> offset(@Nullable Number offset);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Number> getFetch();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSubQuery<T> fetch(@Nullable JpaExpression<? extends Number> fetch);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSubQuery<T> fetch(JpaExpression<? extends Number> fetch, FetchClauseType fetchClauseType);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSubQuery<T> fetch(@Nullable Number fetch);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSubQuery<T> fetch(Number fetch, FetchClauseType fetchClauseType);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FetchClauseType getFetchClauseType();

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Order by clause

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JpaOrder> getOrderList();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSubQuery<T> orderBy(Order... o);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSubQuery<T> orderBy(List<Order> o);

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Covariant overrides

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSubQuery<T> distinct(boolean distinct);

	@Override
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<T> getSelection();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSubQuery<T> select(@Nonnull Expression<T> expression);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSubQuery<T> where(@Nonnull Expression<Boolean> restriction);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSubQuery<T> where(@Nonnull BooleanExpression... restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSubQuery<T> where(@Nonnull List<? extends Expression<Boolean>> restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSubQuery<T> groupBy(@Nonnull Expression<?>... grouping);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSubQuery<T> groupBy(@Nonnull List<Expression<?>> grouping);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSubQuery<T> having(@Nonnull Expression<Boolean> restriction);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSubQuery<T> having(@Nonnull BooleanExpression... restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSubQuery<T> having(@Nonnull List<? extends Expression<Boolean>> restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaRoot<Y> correlate(@Nonnull Root<Y> parentRoot);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, Y> JpaJoin<X, Y> correlate(@Nonnull Join<X, Y> parentJoin);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, Y> JpaCollectionJoin<X, Y> correlate(@Nonnull CollectionJoin<X, Y> parentCollection);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, Y> JpaSetJoin<X, Y> correlate(@Nonnull SetJoin<X, Y> parentSet);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, Y> JpaListJoin<X, Y> correlate(@Nonnull ListJoin<X, Y> parentList);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, K, V> JpaMapJoin<X, K, V> correlate(@Nonnull MapJoin<X, K, V> parentMap);
}
