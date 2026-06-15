/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.util.collections;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


final class EmptyReadOnlyMap<K,V> implements ReadOnlyMap<K,V> {

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public V get(K key) {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void dispose() {
		//no-op
	}

}
