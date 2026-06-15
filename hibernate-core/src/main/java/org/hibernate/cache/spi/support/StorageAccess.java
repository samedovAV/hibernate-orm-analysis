/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi.support;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A general read/write abstraction over the specific "cache" object from the caching provider.
 *
 * @apiNote Similar to {@link org.hibernate.cache.spi.access.CachedDomainDataAccess},
 *          some methods handle "transactional" access (access in the scope of a session),
 *          and some are non-"transactional" (for cache management outside a session).
 *
 * @author Steve Ebersole
 */
public interface StorageAccess {
	/**
	 * Get an item from the cache.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getFromCache(Object key, SharedSessionContractImplementor session);

	/**
	 * Put an item into the cache
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void putIntoCache(Object key, Object value, SharedSessionContractImplementor session);

	/**
	 * Remove an item from the cache by key
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void removeFromCache(Object key, SharedSessionContractImplementor session) {
		evictData( key );
	}

	/**
	 * Clear data from the cache
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void clearCache(SharedSessionContractImplementor session) {
		evictData();
	}

	/**
	 * Does the cache contain this key?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean contains(Object key);

	/**
	 * Clear all data regardless of transaction/locking
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void evictData();

	/**
	 * Remove the entry regardless of transaction/locking
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void evictData(Object key);

	/**
	 * Release any resources.  Called during cache shutdown
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void release();
}
