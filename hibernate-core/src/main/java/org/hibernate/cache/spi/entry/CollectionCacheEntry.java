/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi.entry;

import java.io.Serializable;

import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.internal.util.collections.ArrayHelper;
import org.hibernate.persister.collection.CollectionPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Cacheable representation of persistent collections
 *
 * @author Gavin King
 */
public class CollectionCacheEntry implements Serializable {
	private final Object state;

	/**
	 * Constructs a CollectionCacheEntry
	 *
	 * @param collection The persistent collection instance
	 * @param persister The collection persister
	 */
	public CollectionCacheEntry(PersistentCollection<?> collection, CollectionPersister persister) {
		this.state = collection.disassemble( persister );
	}

	CollectionCacheEntry(Serializable state) {
		this.state = state;
	}

	/**
	 * Retrieve the cached collection state.
	 *
	 * @return The cached collection state.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Serializable[] getState() {
		//TODO: assumes all collections disassemble to an array!
		return (Serializable[]) state;
	}

	/**
	 * Assembles the collection from the cached state.
	 *
	 * @param collection The persistent collection instance being assembled
	 * @param persister The collection persister
	 * @param owner The collection owner instance
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void assemble(
			final PersistentCollection<?> collection,
			final CollectionPersister persister,
			final Object owner) {
		collection.initializeFromCache( persister, state, owner );
		collection.afterInitialize();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String toString() {
		return "CollectionCacheEntry" + ArrayHelper.toString( getState() );
	}

}
