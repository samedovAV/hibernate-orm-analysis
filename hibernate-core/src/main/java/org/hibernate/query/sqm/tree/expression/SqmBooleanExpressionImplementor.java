/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.expression;

import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.Expression;
import org.hibernate.query.sqm.internal.SqmCriteriaNodeBuilder;
import org.hibernate.query.sqm.tree.predicate.SqmPredicate;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface SqmBooleanExpressionImplementor
		extends SqmComparableExpressionImplementor<Boolean>, SqmBooleanExpression {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmCriteriaNodeBuilder nodeBuilder();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqmComparableExpression<Boolean> max() {
		throw new UnsupportedOperationException( "max() not supported" );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqmComparableExpression<Boolean> min() {
		throw new UnsupportedOperationException( "min() not supported" );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default SqmPredicate and(@Nonnull Expression<Boolean> y) {
		return nodeBuilder().and( this, y );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default SqmPredicate or(@Nonnull Expression<Boolean> y) {
		return nodeBuilder().or( this, y );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default SqmPredicate not() {
		return nodeBuilder().not( this );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default SqmBooleanExpression coalesce(@Nonnull Expression<? extends Boolean> y) {
		return new SqmBooleanExpressionWrapper( nodeBuilder().coalesce( this, y ) );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default SqmBooleanExpression coalesce(Boolean y) {
		return new SqmBooleanExpressionWrapper( nodeBuilder().coalesce( this, y ) );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default SqmBooleanExpression nullif(@Nonnull Expression<? extends Boolean> y) {
		return new SqmBooleanExpressionWrapper( nodeBuilder().nullif( this, y ) );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default SqmBooleanExpression nullif(Boolean y) {
		return new SqmBooleanExpressionWrapper( nodeBuilder().nullif( this, y ) );
	}
}
