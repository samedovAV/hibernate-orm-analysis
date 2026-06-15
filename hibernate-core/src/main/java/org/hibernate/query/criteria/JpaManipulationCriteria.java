/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import jakarta.annotation.Nonnull;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JpaManipulationCriteria<E> extends JpaQueryableCriteria<E> {
	/**
	 * Get the root path that is the target of the DML statement.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaRoot<E> getTarget();

	/**
	 * Set the root path
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setTarget(@Nonnull JpaRoot<E> root);
}
