/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi.entry;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Structured CacheEntry format for persistent collections (other than Maps, see {@link StructuredMapCacheEntry}).
 *
 * @author Gavin King
 */
public class StructuredCollectionCacheEntry implements CacheEntryStructure {
	/**
	 * Access to the singleton reference.
	 */
	public static final StructuredCollectionCacheEntry INSTANCE = new StructuredCollectionCacheEntry();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object structure(Object item) {
		final var entry = (CollectionCacheEntry) item;
		return Arrays.asList( entry.getState() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object destructure(Object structured, SessionFactoryImplementor factory) {
		final var list = (List<?>) structured;
		return new CollectionCacheEntry( list.toArray( Serializable[]::new ) );
	}

	private StructuredCollectionCacheEntry() {
	}
}
