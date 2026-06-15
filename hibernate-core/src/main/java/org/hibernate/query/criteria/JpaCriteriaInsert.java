/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import java.util.List;

import jakarta.annotation.Nonnull;
import org.hibernate.Incubating;

import jakarta.persistence.criteria.Path;
import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * The commonalities between insert-select and insert-values.
 *
 * @since 6.5
 */
@Incubating
public interface JpaCriteriaInsert<T> extends JpaManipulationCriteria<T> {

	/**
	 * Returns the insertion target paths.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<? extends JpaPath<?>> getInsertionTargetPaths();

	/**
	 * Sets the insertion target paths.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaInsert<T> setInsertionTargetPaths(@Nonnull Path<?>... insertionTargetPaths);

	/**
	 * Sets the insertion target paths.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaInsert<T> setInsertionTargetPaths(@Nonnull List<? extends Path<?>> insertionTargetPaths);

	/**
	 * Sets the conflict clause that defines what happens when an insert violates a unique constraint.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaConflictClause<T> onConflict();

	/**
	 * Sets the conflict clause that defines what happens when an insert violates a unique constraint.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaInsert<T> onConflict(@Nullable JpaConflictClause<T> conflictClause);

	/**
	 * Returns the conflict clause that defines what happens when an insert violates a unique constraint,
	 * or {@code null} if there is none.
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaConflictClause<T> getConflictClause();

	/**
	 * Create a new conflict clause for this insert statement.
	 *
	 * @return a new conflict clause
	 * @see JpaCriteriaInsert#onConflict(JpaConflictClause)
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaConflictClause<T> createConflictClause();
}
