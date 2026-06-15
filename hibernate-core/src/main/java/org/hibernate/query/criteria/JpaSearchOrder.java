/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import jakarta.persistence.criteria.Nulls;
import org.hibernate.Incubating;
import org.hibernate.query.SortDirection;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Represents the search order for a recursive CTE (common table expression).
 *
 * @see JpaCteCriteria
 */
@Incubating
public interface JpaSearchOrder extends JpaCriteriaNode {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SortDirection getSortOrder();

	/**
	 * Set the precedence of nulls for this search order element
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSearchOrder nullPrecedence(Nulls precedence);

	/**
	 * The precedence for nulls for this search order element
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Nulls getNullPrecedence();

	/**
	 * Whether ascending ordering is in effect.
	 * @return boolean indicating whether ordering is ascending
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isAscending();

	/**
	 * Switch the ordering.
	 * @return a new <code>Order</code> instance with the reversed ordering
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSearchOrder reverse();

	/**
	 * Return the CTE attribute that is used for ordering.
	 * @return CTE attribute used for ordering
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCteCriteriaAttribute getAttribute();
}
