/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.stat;

import java.io.Serializable;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface CacheableDataStatistics extends Serializable {
	long NOT_CACHED_COUNT = Long.MIN_VALUE;

	/**
	 * The name of the region where this data is cached.
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCacheRegionName();

	/**
	 * The number of times this data has been into its configured cache region
	 * since the last Statistics clearing
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getCachePutCount();

	/**
	 * The number of successful cache look-ups for this data from its
	 * configured cache region since the last Statistics clearing
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getCacheHitCount();

	/**
	 * The number of unsuccessful cache look-ups for this data from its
	 * configured cache region since the last Statistics clearing
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getCacheMissCount();

	/**
	 * The number of evictions from the configured cache region since
	 * the last Statistics clearing
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getCacheRemoveCount();
}
