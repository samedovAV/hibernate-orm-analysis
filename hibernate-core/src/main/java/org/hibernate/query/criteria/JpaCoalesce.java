/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JpaCoalesce<T> extends JpaExpression<T>, CriteriaBuilder.Coalesce<T> {
	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCoalesce<T> value(@Nullable T value);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCoalesce<T> value(@Nonnull Expression<? extends T> value);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCoalesce<T> value(@Nonnull JpaExpression<? extends T> value);

	@Nonnull
	@SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCoalesce<T> values(T... values);
}
