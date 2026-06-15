/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import org.hibernate.Incubating;
import org.hibernate.query.common.FrameExclusion;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Common contract for window parts used in window and aggregate functions.
 *
 * @author Marco Belladelli
 */
@Incubating
public interface JpaWindow {
	/**
	 * Add partition by expressions to the window.
	 *
	 * @param expressions the partition by expressions
	 *
	 * @return the modified window
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaWindow partitionBy(Expression<?>... expressions);

	/**
	 * Add order by expressions to the window.
	 *
	 * @param expressions the order by expressions
	 *
	 * @return the modified window
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaWindow orderBy(Order... expressions);

	/**
	 * Add a {@code ROWS} frame clause to the window and define
	 * start and end {@link JpaWindowFrame} specifications.
	 *
	 * @param startFrame the start frame
	 * @param endFrame the optional end frame
	 *
	 * @return the modified window
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaWindow frameRows(JpaWindowFrame startFrame, JpaWindowFrame endFrame);

	/**
	 * Add a {@code RANGE} frame clause to the window and define
	 * start and end {@link JpaWindowFrame} specifications.
	 *
	 * @param startFrame the start frame
	 * @param endFrame the optional end frame
	 *
	 * @return the modified window
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaWindow frameRange(JpaWindowFrame startFrame, JpaWindowFrame endFrame);

	/**
	 * Add a {@code GROUPS} frame clause to the window and define
	 * start and end {@link JpaWindowFrame} specifications.
	 *
	 * @param startFrame the start frame
	 * @param endFrame the optional end frame
	 *
	 * @return the modified window
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaWindow frameGroups(JpaWindowFrame startFrame, JpaWindowFrame endFrame);

	/**
	 * Set a {@link FrameExclusion} for this window's frame.
	 *
	 * @param frameExclusion the frame exclusion
	 *
	 * @return the modified window
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaWindow frameExclude(FrameExclusion frameExclusion);
}
