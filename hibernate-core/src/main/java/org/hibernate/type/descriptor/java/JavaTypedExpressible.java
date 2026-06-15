/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.java;

import org.hibernate.cache.MutableCacheKeyBuilder;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contract for something that has an associated JavaType
 */
public interface JavaTypedExpressible<T> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JavaType<T> getExpressibleJavaType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addToCacheKey(MutableCacheKeyBuilder cacheKey, Object value, SharedSessionContractImplementor session);

}
