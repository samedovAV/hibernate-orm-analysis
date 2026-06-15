/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.util.collections;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Set;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Set implementation that use == instead of equals() as its comparison
 * mechanism.  This is achieved by internally using an IdentityHashMap.
 *
 * @author Emmanuel Bernard
 */
public class IdentitySet<E> implements Set<E> {
	private static final Object DUMP_VALUE = new Object();

	private final IdentityHashMap<E,Object> map;

	/**
	 * Create an IdentitySet with default sizing.
	 */
	public IdentitySet() {
		this.map = new IdentityHashMap<>();
	}

	/**
	 * Create an IdentitySet with the given sizing.
	 *
	 * @param sizing The sizing of the set to create.
	 */
	@SuppressWarnings("unused")
	public IdentitySet(int sizing) {
		this.map = new IdentityHashMap<>( sizing );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int size() {
		return map.size();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	@SuppressWarnings("SuspiciousMethodCalls")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean contains(Object o) {
		return map.get( o ) == DUMP_VALUE;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Iterator<E> iterator() {
		return map.keySet().iterator();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object[] toArray() {
		return map.keySet().toArray();
	}

	@SuppressWarnings("SuspiciousToArrayCall")
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public <T> T[] toArray(T[] a) {
		return map.keySet().toArray( a );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean add(E o) {
		return map.put( o, DUMP_VALUE ) == null;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean remove(Object o) {
		return map.remove( o ) == DUMP_VALUE;
	}

	@Override
	@SuppressWarnings("SuspiciousMethodCalls")
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean containsAll(Collection<?> checkValues) {
		for ( Object checkValue : checkValues ) {
			if ( ! map.containsKey( checkValue ) ) {
				return false;
			}
		}

		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean addAll(Collection<? extends E> additions) {
		boolean changed = false;

		for ( E addition : additions ) {
			changed = add( addition ) || changed;
		}

		return changed;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean retainAll(Collection<?> keepers) {
		//doable if needed
		throw new UnsupportedOperationException();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean removeAll(Collection<?> removals) {
		boolean changed = false;

		for ( Object removal : removals ) {
			changed = remove( removal ) || changed;
		}

		return changed;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void clear() {
		map.clear();
	}
}
