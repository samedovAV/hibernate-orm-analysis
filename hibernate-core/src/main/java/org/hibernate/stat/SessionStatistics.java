/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.stat;

import java.util.Set;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Information about the first-level (session) cache for a particular
 * instance of {@link org.hibernate.Session}.
 *
 * @author Gavin King
 */
public interface SessionStatistics {
	/**
	 * The number of entity instances associated with the session.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getEntityCount();
	/**
	 * The number of collection instances associated with the session.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getCollectionCount();

	/**
	 * The set of all {@link org.hibernate.engine.spi.EntityKey}s
	 * currently held within the persistence context.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<?> getEntityKeys();
	/**
	 * The set of all {@link org.hibernate.engine.spi.CollectionKey}s
	 * currently held within the persistence context.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<?> getCollectionKeys();

}
