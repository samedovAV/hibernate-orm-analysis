/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.collection.internal;

import java.util.Map;

import org.hibernate.collection.spi.AbstractMapSemantics;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.collection.spi.PersistentMap;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.internal.util.collections.CollectionHelper;
import org.hibernate.metamodel.CollectionClassification;
import org.hibernate.persister.collection.CollectionPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * CollectionSemantics for maps
 *
 * @author Steve Ebersole
 */
public class StandardMapSemantics<K,V> extends AbstractMapSemantics<Map<K,V>,K,V> {
	/**
	 * Singleton access
	 */
	public static final StandardMapSemantics<?,?> INSTANCE = new StandardMapSemantics<>();

	private StandardMapSemantics() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CollectionClassification getCollectionClassification() {
		return CollectionClassification.MAP;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Map<K,V> instantiateRaw(
			int anticipatedSize,
			CollectionPersister collectionDescriptor) {
		return CollectionHelper.mapOfSize( anticipatedSize );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <KK, VV> Map<KK, VV> instantiateWithElements(
			int anticipatedSize,
			CollectionPersister collectionDescriptor,
			Map<? extends KK, ? extends VV> entries) {
		final Map<KK, VV> map = CollectionHelper.mapOfSize( anticipatedSize );
		map.putAll( entries );
		return map;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistentCollection<V> instantiateWrapper(
			Object key,
			CollectionPersister collectionDescriptor,
			SharedSessionContractImplementor session) {
		return new PersistentMap<>( session );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistentCollection<V> wrap(
			Map<K,V> rawCollection,
			CollectionPersister collectionDescriptor,
			SharedSessionContractImplementor session) {
		return new PersistentMap<>( session, rawCollection );
	}
}
