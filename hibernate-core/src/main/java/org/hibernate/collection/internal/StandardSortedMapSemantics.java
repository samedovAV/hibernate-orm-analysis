/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.collection.internal;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.hibernate.collection.spi.AbstractMapSemantics;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.collection.spi.PersistentSortedMap;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.metamodel.CollectionClassification;
import org.hibernate.persister.collection.CollectionPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class StandardSortedMapSemantics<K,V> extends AbstractMapSemantics<SortedMap<K,V>,K,V> {
	/**
	 * Singleton access
	 */
	public static final StandardSortedMapSemantics<?,?> INSTANCE = new StandardSortedMapSemantics<>();

	private StandardSortedMapSemantics() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CollectionClassification getCollectionClassification() {
		return CollectionClassification.SORTED_MAP;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<SortedMap> getCollectionJavaType() {
		return SortedMap.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TreeMap<K,V> instantiateRaw(
			int anticipatedSize,
			CollectionPersister collectionDescriptor) {
		return new TreeMap<K,V>(
				collectionDescriptor == null ? null : (Comparator) collectionDescriptor.getSortingComparator()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <KK, VV> SortedMap<KK, VV> instantiateWithElements(
			int anticipatedSize,
			CollectionPersister collectionDescriptor,
			Map<? extends KK, ? extends VV> entries) {
		final SortedMap<KK, VV> map = new TreeMap<>(
				collectionDescriptor == null ? null : (Comparator) collectionDescriptor.getSortingComparator()
		);
		map.putAll( entries );
		return map;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistentCollection<V> instantiateWrapper(
			Object key,
			CollectionPersister collectionDescriptor,
			SharedSessionContractImplementor session) {
		//noinspection unchecked
		return new PersistentSortedMap<>( session, (Comparator<K>) collectionDescriptor.getSortingComparator() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistentCollection<V> wrap(
			SortedMap<K,V> rawCollection,
			CollectionPersister collectionDescriptor,
			SharedSessionContractImplementor session) {
		return new PersistentSortedMap<>( session, rawCollection );
	}
}
