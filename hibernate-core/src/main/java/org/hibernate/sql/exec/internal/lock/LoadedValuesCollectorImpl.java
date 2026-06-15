/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.exec.internal.lock;

import org.hibernate.engine.spi.CollectionKey;
import org.hibernate.engine.spi.EntityKey;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.metamodel.mapping.PluralAttributeMapping;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.exec.spi.LoadedValuesCollector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class LoadedValuesCollectorImpl implements LoadedValuesCollector {
	private final Collection<NavigablePath> pathsToLock;

	private List<LoadedValuesCollector.LoadedEntityRegistration> entitiesToLock;
	private List<LoadedValuesCollector.LoadedCollectionRegistration> collectionsToLock;

	public LoadedValuesCollectorImpl(Collection<NavigablePath> pathsToLock) {
		this.pathsToLock = pathsToLock;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void registerEntity(NavigablePath navigablePath, EntityMappingType entityDescriptor, EntityKey entityKey) {
		if ( pathsToLock.contains( navigablePath ) ) {
			if ( entitiesToLock == null ) {
				entitiesToLock = new ArrayList<>();
			}
			entitiesToLock.add(
					new LoadedValuesCollector.LoadedEntityRegistration( navigablePath, entityDescriptor, entityKey ) );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void registerCollection(NavigablePath navigablePath, PluralAttributeMapping collectionDescriptor, CollectionKey collectionKey) {
		if ( collectionsToLock == null ) {
			collectionsToLock = new ArrayList<>();
		}
		collectionsToLock.add(
				new LoadedValuesCollector.LoadedCollectionRegistration( navigablePath, collectionDescriptor,
						collectionKey ) );
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<LoadedEntityRegistration> getCollectedEntities() {
		return entitiesToLock == null ? Collections.emptyList() : entitiesToLock;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<LoadedCollectionRegistration> getCollectedCollections() {
		return collectionsToLock == null ? Collections.emptyList() : collectionsToLock;
	}
}
