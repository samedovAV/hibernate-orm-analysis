/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type;

import java.util.Comparator;
import java.util.TreeMap;

import org.hibernate.collection.spi.PersistentSortedMap;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.metamodel.CollectionClassification;
import org.hibernate.persister.collection.CollectionPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


public class SortedMapType extends MapType {

	private final Comparator<?> comparator;

	public SortedMapType(String role, String propertyRef, Comparator<?> comparator) {
		super( role, propertyRef );
		this.comparator = comparator;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CollectionClassification getCollectionClassification() {
		return CollectionClassification.SORTED_MAP;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getReturnedClass() {
		return java.util.SortedMap.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistentCollection<?> instantiate(SharedSessionContractImplementor session, CollectionPersister persister, Object key) {
		return new PersistentSortedMap<>( session, comparator );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object instantiate(int anticipatedSize) {
		return new TreeMap<>(comparator);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistentCollection<?> wrap(SharedSessionContractImplementor session, Object collection) {
		return new PersistentSortedMap<>( session, (java.util.SortedMap<?,?>) collection );
	}
}
