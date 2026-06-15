/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi.entry;

import java.io.Serializable;

import org.hibernate.persister.entity.EntityPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Specialized CacheEntry for storing direct references to entity instances.
 *
 * @author Steve Ebersole
 */
public class ReferenceCacheEntryImpl implements CacheEntry {
	private final Object reference;
	// passing the persister avoids a costly persister lookup by class name at cache retrieval time
	private final EntityPersister subclassPersister;

	/**
	 * Constructs a ReferenceCacheEntryImpl
	 *
	 * @param reference The reference entity instance
	 * @param subclassPersister The specific subclass persister
	 */
	public ReferenceCacheEntryImpl(Object reference, EntityPersister subclassPersister) {
		this.reference = reference;
		this.subclassPersister = subclassPersister;
	}

	/**
	 * Provides access to the stored reference.
	 *
	 * @return The stored reference
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getReference() {
		return reference;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isReferenceEntry() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSubclass() {
		return subclassPersister.getEntityName();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityPersister getSubclassPersister() {
		return subclassPersister;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getVersion() {
		// reference data cannot be versioned
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Serializable[] getDisassembledState() {
		// reference data is not disassembled into the cache
		return null;
	}
}
