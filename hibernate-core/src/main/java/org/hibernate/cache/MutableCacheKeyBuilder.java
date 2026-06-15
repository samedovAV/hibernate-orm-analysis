/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache;

import java.io.Serializable;

import org.hibernate.cache.spi.QueryResultsCache;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A builder that generates a Serializable Object to be used as a key into the {@linkplain QueryResultsCache
 * query results cache}.
 */

public interface MutableCacheKeyBuilder extends Serializable {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addValue(Object value);


	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addHashCode(int hashCode);

	/**
	 *  creates an Object to be used as a key into the {@linkplain QueryResultsCache
	 *  query results cache}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Serializable build();

}
