/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.BooleanExpression;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import jakarta.persistence.metamodel.EntityType;
import jakarta.annotation.Nullable;
import org.hibernate.Incubating;
import org.hibernate.query.common.FetchClauseType;

import java.util.List;
import java.util.Set;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Extension of the JPA {@link CriteriaQuery}
 *
 * @author Steve Ebersole
 */
public interface JpaCriteriaQuery<T> extends CriteriaQuery<T>, JpaQueryableCriteria<T>, JpaSelectCriteria<T>, JpaCriteriaSelect<T> {

	/**
	 * A query that returns the number of results of this query.
	 *
	 * @since 6.4
	 *
	 * @see org.hibernate.query.SelectionQuery#getResultCount()
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<Long> createCountQuery();

	/**
	 * A query that returns {@code true} if this query has any results.
	 *
	 * @since 7.1
	 */
	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<Boolean> createExistsQuery();

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Limit/Offset/Fetch clause

	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Number> getOffset();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<T> offset(@Nullable JpaExpression<? extends Number> offset);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<T> offset(@Nullable Number offset);

	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Number> getFetch();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<T> fetch(@Nullable JpaExpression<? extends Number> fetch);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<T> fetch(@Nullable JpaExpression<? extends Number> fetch, FetchClauseType fetchClauseType);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<T> fetch(@Nullable Number fetch);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<T> fetch(@Nullable Number fetch, FetchClauseType fetchClauseType);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FetchClauseType getFetchClauseType();

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Accessors

	/**
	 * Return the {@linkplain #getRoots() roots} as a list.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<Root<?>> getRootList();

	/**
	 * Get a {@linkplain Root query root} element at the given position
	 * with the given type.
	 *
	 * @param position the position of this root element
	 * @param type the type of the root entity
	 *
	 * @throws IllegalArgumentException if the root entity at the given
	 *         position is not of the given type, or if there are not
	 *         enough root entities in the query
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaRoot<? extends E> getRoot(int position, Class<E> type);

	/**
	 * Get a {@linkplain Root query root} element with the given alias
	 * and the given type.
	 *
	 * @param alias the identification variable of the root element
	 * @param type the type of the root entity
	 *
	 * @throws IllegalArgumentException if the root entity with the
	 *         given alias is not of the given type, or if there is
	 *         no root entities with the given alias
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaRoot<? extends E> getRoot(String alias, Class<E> type);

	/**
	 * {@inheritDoc}
	 *
	 * @apiNote Warning!  This actually walks the criteria tree looking
	 * for parameters nodes.
	 */
	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<ParameterExpression<?>> getParameters();


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Mutators

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaRoot<X> from(@Nonnull Class<X> entityClass);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaRoot<X> from(@Nonnull EntityType<X> entity);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<T> distinct(boolean distinct);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<T> select(@Nonnull Selection<? extends T> selection);

	@Nonnull
	@Override @Deprecated
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<T> multiselect(@Nonnull Selection<?>... selections);

	@Nonnull
	@Override @Deprecated
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<T> multiselect(@Nonnull List<Selection<?>> selectionList);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<T> where(@Nonnull Expression<Boolean> restriction);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<T> where(@Nonnull BooleanExpression... restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<T> where(@Nonnull List<? extends Expression<Boolean>> restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<T> groupBy(@Nonnull Expression<?>... grouping);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<T> groupBy(@Nonnull List<Expression<?>> grouping);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<T> having(@Nonnull Expression<Boolean> restriction);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<T> having(@Nonnull BooleanExpression... restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<T> having(@Nonnull List<? extends Expression<Boolean>> restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<T> orderBy(@Nonnull Order... o);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<T> orderBy(@Nonnull List<Order> o);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<U> JpaSubQuery<U> subquery(@Nonnull EntityType<U> type);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	HibernateCriteriaBuilder getCriteriaBuilder();
}
