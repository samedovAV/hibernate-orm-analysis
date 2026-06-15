/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.expression;

import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.Expression;
import org.hibernate.query.criteria.JpaTemporalExpression;

import java.time.temporal.Temporal;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface SqmTemporalExpression<T extends Temporal & Comparable<? super T>>
		extends SqmComparableExpression<T>, JpaTemporalExpression<T> {
	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmTemporalExpression<T> coalesce(@Nonnull Expression<? extends T> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmTemporalExpression<T> coalesce(T y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmTemporalExpression<T> nullif(@Nonnull Expression<? extends T> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmTemporalExpression<T> nullif(T y);
}
