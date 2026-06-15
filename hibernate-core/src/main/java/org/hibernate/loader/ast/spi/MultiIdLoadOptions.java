/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.loader.ast.spi;

import org.hibernate.FindMultipleOption;
import org.hibernate.engine.spi.SessionImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/// Encapsulation of the options for loading multiple entities (of a type)
/// by [id][org.hibernate.KeyType#IDENTIFIER].
///
/// @see org.hibernate.Session#findMultiple
/// @see MultiIdEntityLoader
///
/// @author Steve Ebersole
public interface MultiIdLoadOptions extends MultiLoadOptions {
	/**
	 * Controls whether to check the current status of each identified entity
	 * within the persistence context.
	 *
	 * @since 7.2
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FindMultipleOption.SessionCheckMode getSessionCheckMode();

	/**
	 * Check the first-level cache first, and only if the entity is not found in the cache
	 * should Hibernate hit the database.
	 *
	 * @return the session cache is checked first
	 * @deprecated Use {@linkplain #getSessionCheckMode()} instead.
	 */
	@Deprecated(since = "7.2", forRemoval = true)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isSessionCheckingEnabled() {
		return getSessionCheckMode() == FindMultipleOption.SessionCheckMode.ENABLED;
	}

	/**
	 * Check the second-level cache first, and only if the entity is not found in the cache
	 * should Hibernate hit the database.
	 *
	 * @return the session factory cache is checked first
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isSecondLevelCacheCheckingEnabled();

	/**
	 * Should the entities be loaded in read-only mode?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean getReadOnly(SessionImplementor session);
}
