/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.expression;

import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Nulls;
import org.hibernate.query.criteria.JpaComparableExpression;
import org.hibernate.query.sqm.tree.predicate.SqmPredicate;
import org.hibernate.query.sqm.tree.select.SqmSortSpecification;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface SqmComparableExpression<C extends Comparable<? super C>>
		extends JpaComparableExpression<C>, SqmExpression<C> {
	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmComparableExpression<C> coalesce(C y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmComparableExpression<C> coalesce(@Nonnull Expression<? extends C> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmComparableExpression<C> nullif(C y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmComparableExpression<C> nullif(@Nonnull Expression<? extends C> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate greaterThan(@Nonnull Expression<? extends C> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate greaterThan(C y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate greaterThanOrEqualTo(@Nonnull Expression<? extends C> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate greaterThanOrEqualTo(C y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate lessThan(@Nonnull Expression<? extends C> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate lessThan(C y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate lessThanOrEqualTo(@Nonnull Expression<? extends C> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate lessThanOrEqualTo(C y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate between(@Nonnull Expression<? extends C> x, @Nonnull Expression<? extends C> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate between(C x, C y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmComparableExpression<C> max();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmComparableExpression<C> min();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmSortSpecification asc();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmSortSpecification asc(@Nonnull Nulls nullPrecedence);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmSortSpecification desc();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmSortSpecification desc(@Nonnull Nulls nullPrecedence);
}
