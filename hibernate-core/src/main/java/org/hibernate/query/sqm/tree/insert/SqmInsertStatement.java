/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.insert;

import jakarta.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

import org.hibernate.query.criteria.JpaCriteriaInsert;
import org.hibernate.query.sqm.tree.SqmCopyContext;
import org.hibernate.query.sqm.tree.SqmDmlStatement;
import org.hibernate.query.sqm.tree.domain.SqmPath;

import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.Path;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * The general contract for INSERT statements.  At the moment only the INSERT-SELECT
 * forms is implemented/supported.
 *
 * @author Steve Ebersole
 */
public interface SqmInsertStatement<T> extends SqmDmlStatement<T>, JpaCriteriaInsert<T> {
	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<SqmPath<?>> getInsertionTargetPaths();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmInsertStatement<T> setInsertionTargetPaths(@Nonnull Path<?>... insertionTargetPaths);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmInsertStatement<T> setInsertionTargetPaths(@Nonnull List<? extends Path<?>> insertionTargetPaths);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmInsertStatement<T> copy(SqmCopyContext context);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitInsertionTargetPaths(Consumer<SqmPath<?>> consumer);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmConflictClause<T> getConflictClause();
}
