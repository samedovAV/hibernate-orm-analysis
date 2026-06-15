/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.util.collections;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import org.hibernate.Internal;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Wraps a ConcurrentHashMap having all keys as Strings
 * and ensures all keys are lowercased.
 * It does assume keys and arguments are never null, preferring to throw a NPE
 * over adding unnecessary checks.
 * The public exposed methods are similar to the ones on Map, but
 * not all Map methods are exposed - only a selection we actually need; this
 * implies it doesn't implement Map; nothing stops us to make it implement Map
 * but at time of writing it seems unnecessary for our purposes.
 * @param <V> the type for the stored values.
 */
@Internal
public final class CaseInsensitiveDictionary<V> {

	private final Map<String, V> map = new ConcurrentHashMap<>();

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public V get(final String key) {
		return map.get( trueKey( key ) );
	}

	/**
	 * Contrary to traditional Map, we make the return unmodifiable.
	 * @return the map's keySet
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<String> unmodifiableKeySet() {
		return Collections.unmodifiableSet( map.keySet() );
	}

	/**
	 * Contrary to traditional Map, we make the return unmodifiable.
	 * @return the map's entrySet
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<Map.Entry<String, V>> unmodifiableEntrySet() {
		return Collections.unmodifiableSet( map.entrySet() );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public V put(final String key, V value) {
		return map.put( trueKey( key ), value );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public V remove(final String key) {
		return map.remove( trueKey( key ) );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean containsKey(final String key) {
		return map.containsKey( trueKey( key ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static String trueKey(final String key) {
		return key.toLowerCase( Locale.ROOT );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void clear() {
		map.clear();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void forEach(final BiConsumer<? super String, ? super V> action) {
		map.forEach( action );
	}

}
