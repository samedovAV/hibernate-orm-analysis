/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Specialized {@link Region} whose data is accessed directly,
 * without the need for key/item wrapping.
 *
 * Does not define a "remove" operation because Hibernate's
 * query and timestamps caches only ever "get" and "put".
 *
 * @author Steve Ebersole
 */
public interface DirectAccessRegion extends Region {
	/**
	 * Get value by key
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getFromCache(Object key, SharedSessionContractImplementor session);

	/**
	 * Put a value by key
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void putIntoCache(Object key, Object value, SharedSessionContractImplementor session);
}
