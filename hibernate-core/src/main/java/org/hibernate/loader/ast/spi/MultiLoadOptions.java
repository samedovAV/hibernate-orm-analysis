/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.loader.ast.spi;

import org.hibernate.FindMultipleOption;
import org.hibernate.LockOptions;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Encapsulation of the options for loading multiple entities (of a type)
/// by [key][org.hibernate.KeyType].
///
/// @see MultiIdLoadOptions
/// @see MultiNaturalIdLoadOptions
///
/// @author Steve Ebersole
public interface MultiLoadOptions {
	/**
	 * How should entities in removed status be handled.
	 *
	 * @since 7.2
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FindMultipleOption.RemovalsMode getRemovalsMode();

	/**
	 * Whether the result should be ordered relative to the order of
	 * identifiers to load.
	 *
	 * @since 7.2
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FindMultipleOption.OrderingMode getOrderingMode();

	/**
	 * Should we returned entities that are scheduled for deletion.
	 *
	 * @return entities that are scheduled for deletion are returned as well.
	 *
	 * @deprecated Use {@linkplain #getRemovalsMode()} instead.
	 */
	@Deprecated(since = "7.2", forRemoval = true)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isReturnOfDeletedEntitiesEnabled() {
		return getRemovalsMode() == FindMultipleOption.RemovalsMode.INCLUDE;
	}

	/**
	 * Should the entities be returned in the same order as their associated entity identifiers were provided.
	 *
	 * @return entities follow the provided identifier order
	 *
	 * @deprecated Use {@linkplain #getOrderingMode()} instead.
	 */
	@Deprecated(since = "7.2", forRemoval = true)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isOrderReturnEnabled() {
		return getOrderingMode() == FindMultipleOption.OrderingMode.ORDERED;
	}

	/**
	 * Specify the lock options applied during loading.
	 *
	 * @return lock options applied during loading.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LockOptions getLockOptions();

	/**
	 * Batch size to use when loading entities from the database.
	 *
	 * @return JDBC batch size
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getBatchSize();
}
