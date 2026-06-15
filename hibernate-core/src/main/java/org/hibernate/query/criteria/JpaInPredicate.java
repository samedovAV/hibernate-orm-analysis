/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.CriteriaBuilder;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JpaInPredicate<T> extends JpaPredicate, CriteriaBuilder.In<T>  {
	/**
	 * Return the expression to be tested against the
	 * list of values.
	 * @return expression
	 */
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<T> getExpression();

	/**
	 *  Add to list of values to be tested against.
	 *  @param value value
	 *  @return in predicate
	 */
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaInPredicate<T> value(T value);

	/**
	 *  Add to list of values to be tested against.
	 *  @param value expression
	 *  @return in predicate
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaInPredicate<T> value(JpaExpression<? extends T> value);
}
