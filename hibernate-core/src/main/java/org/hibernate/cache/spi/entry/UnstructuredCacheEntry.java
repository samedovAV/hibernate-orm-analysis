/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi.entry;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Unstructured CacheEntry format (used to store entities and collections).
 *
 * @author Gavin King
 * @author Steve Ebersole
 */
public class UnstructuredCacheEntry implements CacheEntryStructure {
	/**
	 * Access to the singleton instance.
	 */
	public static final UnstructuredCacheEntry INSTANCE = new UnstructuredCacheEntry();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object structure(Object item) {
		return item;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object destructure(Object structured, SessionFactoryImplementor factory) {
		return structured;
	}

	private UnstructuredCacheEntry() {
	}
}
