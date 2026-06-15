/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.proxy.map;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Proxy for "dynamic-map" entity representations.
 *
 * @author Gavin King
 */
@SuppressWarnings("rawtypes")
public class MapProxy implements HibernateProxy, Map, Serializable {

	private final MapLazyInitializer lazyInitializer;

	private Object replacement;

	MapProxy(MapLazyInitializer lazyInitializer) {
		this.lazyInitializer = lazyInitializer;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public LazyInitializer getHibernateLazyInitializer() {
		return lazyInitializer;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int size() {
		return lazyInitializer.getMap().size();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void clear() {
		lazyInitializer.getMap().clear();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isEmpty() {
		return lazyInitializer.getMap().isEmpty();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean containsKey(Object key) {
		return lazyInitializer.getMap().containsKey(key);
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean containsValue(Object value) {
		return lazyInitializer.getMap().containsValue(value);
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Collection<?> values() {
		return lazyInitializer.getMap().values();
	}

	@Override @SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void putAll(Map map) {
		lazyInitializer.getMap().putAll(map);
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Set<?> entrySet() {
		return lazyInitializer.getMap().entrySet();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Set<?> keySet() {
		return lazyInitializer.getMap().keySet();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object get(Object key) {
		return lazyInitializer.getMap().get(key);
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object remove(Object key) {
		return lazyInitializer.getMap().remove(key);
	}

	@Override @SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object put(Object key, Object value) {
		return lazyInitializer.getMap().put(key, value);
	}

	@Serial
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object writeReplace() {
		/*
		 * If the target has already been loaded somewhere, just not set on the proxy,
		 * then use it to initialize the proxy so that we will serialize that instead of the proxy.
		 */
		lazyInitializer.initializeWithoutLoadIfPossible();

		if ( lazyInitializer.isUninitialized() ) {
			if ( replacement == null ) {
				lazyInitializer.prepareForPossibleLoadingOutsideTransaction();
				replacement = serializableProxy();
			}
			return replacement;
		}
		else {
			return lazyInitializer.getImplementation();
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private Object serializableProxy() {
		return new SerializableMapProxy(
				lazyInitializer.getEntityName(),
				lazyInitializer.getInternalIdentifier(),
				lazyInitializer.isReadOnlySettingAvailable()
						? Boolean.valueOf( lazyInitializer.isReadOnly() )
						: lazyInitializer.isReadOnlyBeforeAttachedToSession(),
				lazyInitializer.getSessionFactoryUuid(),
				lazyInitializer.getSessionFactoryName(),
				lazyInitializer.isAllowLoadOutsideTransaction()
		);
	}

}
