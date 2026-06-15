/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.util.cache;

import org.hibernate.service.Service;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Internal components can use this factory to create an efficient cache for internal purposes.
 * The implementation is pluggable, therefore the exact eviction and sizing semantics are unspecified
 * and responsibility of the implementation.
 *
 * @since 7.1
 */
public interface InternalCacheFactory extends Service {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<K,V> InternalCache<K,V> createInternalCache(int intendedApproximateSize);

}
