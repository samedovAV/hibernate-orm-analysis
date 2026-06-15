/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.internal;

import org.hibernate.cache.spi.CacheImplementor;
import org.hibernate.cache.spi.TimestampsCache;
import org.hibernate.cache.spi.TimestampsCacheFactory;
import org.hibernate.cache.spi.TimestampsRegion;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Standard Hibernate implementation of the QueryCacheFactory interface.  Returns instances of
 * {@link QueryResultsCacheImpl}.
 */
public class StandardTimestampsCacheFactory implements TimestampsCacheFactory {
	/**
	 * Singleton access
	 */
	public static final StandardTimestampsCacheFactory INSTANCE = new StandardTimestampsCacheFactory();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TimestampsCache buildTimestampsCache(
			CacheImplementor cacheManager,
			TimestampsRegion timestampsRegion) {
		return new TimestampsCacheEnabledImpl( timestampsRegion );
	}
}
