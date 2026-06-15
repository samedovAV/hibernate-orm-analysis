/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JpaSearchedCase<T> extends JpaExpression<T>, CriteriaBuilder.Case<T> {
	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSearchedCase<T> when(@Nonnull Expression<Boolean> condition, T result);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSearchedCase<T> when(@Nonnull Expression<Boolean> condition, @Nonnull Expression<? extends T> result);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<T> otherwise(T result);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<T> otherwise(@Nonnull Expression<? extends T> result);
}
