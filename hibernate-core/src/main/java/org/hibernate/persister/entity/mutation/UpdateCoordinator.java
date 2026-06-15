/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.persister.entity.mutation;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.values.GeneratedValues;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Coordinates the updating of an entity.
 *
 * @author Steve Ebersole
 * @see #update
 */
public interface UpdateCoordinator extends MutationCoordinator {
	/**
	 * Update a persistent instance.
	 *
	 * @return The {@linkplain GeneratedValues generated values} if any, {@code null} otherwise.
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	GeneratedValues update(
			Object entity,
			Object id,
			Object rowId,
			Object[] values,
			Object oldVersion,
			Object[] incomingOldValues,
			int[] dirtyAttributeIndexes,
			boolean hasDirtyCollection,
			SharedSessionContractImplementor session);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void forceVersionIncrement(
			Object id,
			Object currentVersion,
			Object nextVersion,
			SharedSessionContractImplementor session);

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void forceVersionIncrement(
			Object id,
			Object currentVersion,
			Object nextVersion,
			boolean batching,
			SharedSessionContractImplementor session) {
		forceVersionIncrement( id, currentVersion, nextVersion, session );
	}
}
