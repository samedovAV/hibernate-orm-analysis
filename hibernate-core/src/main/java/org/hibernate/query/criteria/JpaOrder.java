/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.Nulls;
import jakarta.persistence.criteria.Order;

import org.hibernate.query.SortDirection;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JpaOrder extends Order, JpaCriteriaNode {

	/**
	 * The direction, ascending or descending, in which to sort
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SortDirection getSortDirection();

	/**
	 * Set the precedence of nulls for this order element
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaOrder nullPrecedence(Nulls precedence);

	/**
	 * The precedence for nulls for this order element
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Nulls getNullPrecedence();


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Covariant returns

	/**
	 * Reverse the sorting direction
	 */
	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaOrder reverse();

	/**
	 * The expression to sort by
	 */
	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<?> getExpression();
}
