/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import java.util.List;

import jakarta.annotation.Nonnull;
import org.hibernate.Incubating;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A conflict clause for insert statements.
 *
 * @since 6.5
 */
@Incubating
public interface JpaConflictClause<T> {

	/**
	 * The excluded row/object which was not inserted.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaRoot<T> getExcludedRoot();

	/**
	 * The unique constraint name for which a constraint violation is allowed.
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getConstraintName();

	/**
	 * Sets the unique constraint name for which a constraint violation is allowed.
	 *
	 * @throws IllegalStateException when constraint paths have already been defined
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaConflictClause<T> conflictOnConstraint(@Nullable String constraintName);

	/**
	 * The paths which are part of a unique constraint, for which a constraint violation is allowed.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<? extends JpaPath<?>> getConstraintPaths();

	/**
	 * Shorthand for calling {@link #conflictOnConstraintPaths(List)} with paths resolved for the given attributes
	 * against the insert target.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaConflictClause<T> conflictOnConstraintAttributes(String... attributes);

	/**
	 * Shorthand for calling {@link #conflictOnConstraintPaths(List)} with paths resolved for the given attributes
	 * against the insert target.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaConflictClause<T> conflictOnConstraintAttributes(@Nonnull SingularAttribute<T, ?>... attributes);

	/**
	 * See {@link #conflictOnConstraintPaths(List)}.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaConflictClause<T> conflictOnConstraintPaths(@Nonnull Path<?>... paths);

	/**
	 * Sets the paths which are part of a unique constraint, for which a constraint violation is allowed.
	 *
	 * @throws IllegalStateException when a constraint name has already been defined
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaConflictClause<T> conflictOnConstraintPaths(@Nonnull List<? extends Path<?>> paths);

	/**
	 * The action to do when a conflict due to a unique constraint violation happens.
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaConflictUpdateAction<T> getConflictAction();

	/**
	 * Sets the action to do on a conflict. Setting {@code null} means to do nothing.
	 *
	 * @see #createConflictUpdateAction()
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaConflictClause<T> onConflictDo(@Nullable JpaConflictUpdateAction<T> action);

	/**
	 * Shorthand version for calling {@link #onConflictDo(JpaConflictUpdateAction)} with {@link #createConflictUpdateAction()}
	 * as argument and returning the update action.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default JpaConflictUpdateAction<T> onConflictDoUpdate() {
		final JpaConflictUpdateAction<T> conflictUpdateAction = createConflictUpdateAction();
		onConflictDo( conflictUpdateAction );
		return conflictUpdateAction;
	}

	/**
	 * Shorthand version for calling {@link #onConflictDo(JpaConflictUpdateAction)} with a {@code null} argument.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default JpaConflictClause<T> onConflictDoNothing() {
		return onConflictDo( null );
	}

	/**
	 * Create a new conflict update action for this insert statement.
	 *
	 * @return a new conflict update action
	 * @see #onConflictDo(JpaConflictUpdateAction)
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaConflictUpdateAction<T> createConflictUpdateAction();
}
