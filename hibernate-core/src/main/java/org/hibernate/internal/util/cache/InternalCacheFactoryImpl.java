/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.util.cache;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


final class InternalCacheFactoryImpl implements InternalCacheFactory {

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <K, V> InternalCache<K, V> createInternalCache(int intendedApproximateSize) {
		return new LegacyInternalCacheImplementation<>( intendedApproximateSize );
	}
}
