/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import jakarta.annotation.Nullable;
import org.hibernate.Incubating;
import org.hibernate.query.common.FrameKind;

import jakarta.persistence.criteria.Expression;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Common contract for a {@link JpaWindow} frame specification.
 *
 * @author Marco Belladelli
 */
@Incubating
public interface JpaWindowFrame {
	/**
	 * Get the {@link FrameKind} of this window frame.
	 *
	 * @return the window frame kind
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FrameKind getKind();

	/**
	 * Get the {@link Expression} of this window frame.
	 *
	 * @return the window frame expression
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	Expression<?> getExpression();
}
