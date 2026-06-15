/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.AbstractQuery;
import jakarta.persistence.criteria.BooleanExpression;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Subquery;
import jakarta.persistence.metamodel.EntityType;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Commonality between a JPA {@link JpaCriteriaQuery} and {@link JpaSubQuery},
 * mainly in the form of delegation to {@link JpaQueryStructure}
 *
 * @author Steve Ebersole
 */
public interface JpaSelectCriteria<T> extends AbstractQuery<T>, JpaCriteriaBase {
	/**
	 * The query structure.  See {@link JpaQueryStructure} for details
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryStructure<T> getQuerySpec();
	/**
	 * The query structure.  See {@link JpaQueryStructure} for details
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryPart<T> getQueryPart();

	/**
	 * Create and add a query root corresponding to the given subquery,
	 * forming a cartesian product with any existing roots.
	 *
	 * @param subquery the subquery
	 * @return query root corresponding to the given subquery
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaDerivedRoot<X> from(@Nonnull Subquery<X> subquery);

	/**
	 * Create and add a query root corresponding to the given cte,
	 * forming a cartesian product with any existing roots.
	 *
	 * @param cte the cte criteria
	 * @return query root corresponding to the given cte
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaRoot<X> from(@Nonnull JpaCteCriteria<X> cte);

	/**
	 * Create and add a query root corresponding to the given set-returning function,
	 * forming a cartesian product with any existing roots.
	 *
	 * @param function the set-returning function
	 * @return query root corresponding to the given function
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaFunctionRoot<X> from(@Nonnull JpaSetReturningFunction<X> function);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSelectCriteria<T> distinct(boolean distinct);

	@Override
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSelection<T> getSelection();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaRoot<X> from(@Nonnull Class<X> entityClass);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaRoot<X> from(@Nonnull EntityType<X> entity);

	@Override
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate getRestriction();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSelectCriteria<T> where(@Nonnull Expression<Boolean> restriction);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSelectCriteria<T> where(@Nonnull BooleanExpression... restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSelectCriteria<T> where(@Nonnull List<? extends Expression<Boolean>> restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSelectCriteria<T> groupBy(@Nonnull Expression<?>... grouping);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSelectCriteria<T> groupBy(@Nonnull List<Expression<?>> grouping);

	@Override
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate getGroupRestriction();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSelectCriteria<T> having(@Nonnull Expression<Boolean> restriction);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSelectCriteria<T> having(@Nonnull BooleanExpression... restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSelectCriteria<T> having(@Nonnull List<? extends Expression<Boolean>> restrictions);
}
