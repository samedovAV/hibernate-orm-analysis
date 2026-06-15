/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Responsible for building the {@link TimestampsCache} to use for
 * managing query results with respect to staleness of the underlying
 * tables (sometimes called "query spaces" or "table spaces").
 * <p>
 * An implementation may be selected using the configuration property
 * {@link org.hibernate.cfg.AvailableSettings#QUERY_CACHE_FACTORY}.
 *
 * @author Steve Ebersole
 */
public interface TimestampsCacheFactory {
	/**
	 * Build the {@link TimestampsCache}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TimestampsCache buildTimestampsCache(CacheImplementor cacheManager, TimestampsRegion timestampsRegion);
}
