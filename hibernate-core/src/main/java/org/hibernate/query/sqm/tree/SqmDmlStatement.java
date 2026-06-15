/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree;

import jakarta.annotation.Nonnull;
import org.hibernate.query.criteria.JpaManipulationCriteria;
import org.hibernate.query.criteria.JpaRoot;
import org.hibernate.query.sqm.tree.cte.SqmCteContainer;
import org.hibernate.query.sqm.tree.delete.SqmDeleteStatement;
import org.hibernate.query.sqm.tree.from.SqmRoot;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Common extension of SqmStatement for DML (delete, update and insert-select)
 * statements.  See {@link SqmDeleteStatement},
 * {@link org.hibernate.query.sqm.tree.update.SqmUpdateStatement} and
 * {@link org.hibernate.query.sqm.tree.insert.SqmInsertSelectStatement} respectively
 *
 * @author Steve Ebersole
 */
public interface SqmDmlStatement<E> extends SqmStatement<E>, SqmCteContainer, JpaManipulationCriteria<E> {
	/**
	 * Get the root path that is the target of the DML statement.
	 */
	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmRoot<E> getTarget();

	/**
	 * Set the root path
	 */
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setTarget(@Nonnull JpaRoot<E> root);
}
