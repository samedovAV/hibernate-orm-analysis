/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.collection.spi;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.hibernate.HibernateException;
import org.hibernate.Incubating;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.persister.collection.BasicCollectionPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A persistent wrapper for a {@link java.util.SortedMap}. Underlying
 * collection is a {@code TreeMap}.
 *
 * @apiNote Incubating in terms of making this non-internal.
 *          These contracts will be getting cleaned up in following
 *          releases.
 *
 * @author Doug Currie
 */
@Incubating
public class PersistentSortedMap<K,E> extends PersistentMap<K,E> implements SortedMap<K,E> {
	protected Comparator<? super K> comparator;

	/**
	 * Constructs a PersistentSortedMap.  This form needed for SOAP libraries, etc
	 */
	@SuppressWarnings("unused")
	public PersistentSortedMap() {
	}

	/**
	 * Constructs a PersistentSortedMap.
	 *
	 * @param session The session
	 * @param comparator The sort comparator
	 */
	public PersistentSortedMap(SharedSessionContractImplementor session, Comparator<K> comparator) {
		super( session );
		this.comparator = comparator;
	}

	/**
	 * Constructs a PersistentSortedMap.
	 *
	 * @param session The session
	 * @param map The underlying map data
	 */
	public PersistentSortedMap(SharedSessionContractImplementor session, SortedMap<K,E> map) {
		super( session, map );
		comparator = map.comparator();
	}

	@SuppressWarnings("UnusedParameters")
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected Serializable snapshot(BasicCollectionPersister persister) throws HibernateException {
		final TreeMap<K,E> clonedMap = new TreeMap<>( comparator );
		for ( Entry<K,E> e : map.entrySet() ) {
			clonedMap.put( e.getKey(), (E) persister.getElementType().deepCopy( e.getValue(), persister.getFactory() ) );
		}
		return clonedMap;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setComparator(Comparator<? super K> comparator) {
		this.comparator = comparator;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Comparator<? super K> comparator() {
		return comparator;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SortedMap<K,E> subMap(K fromKey, K toKey) {
		read();
		final SortedMap<K,E> subMap = ( (SortedMap<K,E>) map ).subMap( fromKey, toKey );
		return new SortedSubMap( subMap );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SortedMap<K,E> headMap(K toKey) {
		read();
		final SortedMap<K,E> headMap = ( (SortedMap<K,E>) map ).headMap( toKey );
		return new SortedSubMap( headMap );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SortedMap<K,E> tailMap(K fromKey) {
		read();
		final SortedMap<K,E> tailMap = ( (SortedMap<K,E>) map ).tailMap( fromKey );
		return new SortedSubMap( tailMap );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public K firstKey() {
		read();
		return ( (SortedMap<K,E>) map ).firstKey();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public K lastKey() {
		read();
		return ( (SortedMap<K,E>) map ).lastKey();
	}

	class SortedSubMap implements SortedMap<K,E> {
		SortedMap<K,E> subMap;

		SortedSubMap(SortedMap<K,E> subMap) {
			this.subMap = subMap;
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public int size() {
			return subMap.size();
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public boolean isEmpty() {
			return subMap.isEmpty();
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public boolean containsKey(Object key) {
			return subMap.containsKey( key );
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public boolean containsValue(Object key) {
			return subMap.containsValue( key ) ;
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public E get(Object key) {
			return subMap.get( key );
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public E put(K key, E value) {
			write();
			return subMap.put( key,  value );
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public E remove(Object key) {
			write();
			return subMap.remove( key );
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public void putAll(Map<? extends K,? extends E> other) {
			write();
			subMap.putAll( other );
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public void clear() {
			write();
			subMap.clear();
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public Set<K> keySet() {
			return new SetProxy<>( subMap.keySet() );
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public Collection<E> values() {
			return new SetProxy<>( subMap.values() );
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public Set<Entry<K,E>> entrySet() {
			return new EntrySetProxy( subMap.entrySet() );
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public Comparator<? super K> comparator() {
			return subMap.comparator();
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public SortedMap<K,E> subMap(K fromKey, K toKey) {
			final SortedMap<K,E> subMap = this.subMap.subMap( fromKey, toKey );
			return new SortedSubMap( subMap );
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public SortedMap<K,E> headMap(K toKey) {
			final SortedMap<K,E> headMap = subMap.headMap( toKey );
			return new SortedSubMap( headMap );
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public SortedMap<K,E> tailMap(K fromKey) {
			final SortedMap<K,E> tailMap = subMap.tailMap( fromKey );
			return new SortedSubMap( tailMap );
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public K firstKey() {
			return subMap.firstKey();
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public K lastKey() {
			return subMap.lastKey();
		}
	}
}
