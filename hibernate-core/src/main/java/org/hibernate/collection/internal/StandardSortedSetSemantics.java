/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.collection.internal;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.hibernate.collection.spi.AbstractSetSemantics;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.collection.spi.PersistentSortedSet;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.metamodel.CollectionClassification;
import org.hibernate.persister.collection.CollectionPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class StandardSortedSetSemantics<E> extends AbstractSetSemantics<SortedSet<E>,E> {
	/**
	 * Singleton access
	 */
	public static final StandardSortedSetSemantics<?> INSTANCE = new StandardSortedSetSemantics<>();

	private StandardSortedSetSemantics() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CollectionClassification getCollectionClassification() {
		return CollectionClassification.SORTED_SET;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<SortedSet> getCollectionJavaType() {
		return SortedSet.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SortedSet<E> instantiateRaw(
			int anticipatedSize,
			CollectionPersister collectionDescriptor) {
		return new TreeSet<E>(
				collectionDescriptor == null ? null : (Comparator) collectionDescriptor.getSortingComparator()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> SortedSet<X> instantiateWithElements(
			int anticipatedSize,
			CollectionPersister collectionDescriptor,
			Collection<? extends X> elements) {
		final SortedSet<X> set = new TreeSet<>(
				collectionDescriptor == null ? null : (Comparator) collectionDescriptor.getSortingComparator()
		);
		set.addAll( elements );
		return set;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistentCollection<E> instantiateWrapper(
			Object key,
			CollectionPersister collectionDescriptor,
			SharedSessionContractImplementor session) {
		//noinspection unchecked
		return new PersistentSortedSet<>( session, (Comparator<E>) collectionDescriptor.getSortingComparator() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistentCollection<E> wrap(
			SortedSet<E> rawCollection,
			CollectionPersister collectionDescriptor,
			SharedSessionContractImplementor session) {
		return new PersistentSortedSet<>( session, rawCollection );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Iterator<E> getElementIterator(SortedSet<E> rawCollection) {
		return rawCollection.iterator();
	}
}
