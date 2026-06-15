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
 * Coordinates the inserting of an entity.
 *
 * @author Marco Belladelli
 * @see #insert(Object, Object[], SharedSessionContractImplementor)
 * @see #insert(Object, Object, Object[], SharedSessionContractImplementor)
 */
public interface InsertCoordinator extends MutationCoordinator {
	/**
	 * Persist an entity instance with a generated identifier.
	 *
	 * @return The {@linkplain GeneratedValues generated values} if any, {@code null} otherwise.
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	GeneratedValues insert(Object entity, Object[] values, SharedSessionContractImplementor session);

	/**
	 * Persist an entity instance using the provided identifier.
	 *
	 * @return The {@linkplain GeneratedValues generated values} if any, {@code null} otherwise.
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	GeneratedValues insert(
			Object entity,
			Object id,
			Object[] values,
			SharedSessionContractImplementor session);
}
