/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.stat;

import java.io.Serializable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Statistics relating to a particular query written in HQL or SQL.
 * <p>
 * Note that for a cached query, the number of cache misses is the
 * same as the number of queries actually executed against the
 * database.
 *
 * @author Gavin King
 * @author Steve Ebersole
 */
public interface QueryStatistics extends Serializable {
	/**
	 * How many times has this query been executed?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getExecutionCount();

	/**
	 * How many {@link java.sql.ResultSet} rows have been processed
	 * for this query
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getExecutionRowCount();

	/**
	 * What is the average amount time taken to execute this query?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getExecutionAvgTime();

	/**
	 * What is the max amount time taken to execute this query?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getExecutionMaxTime();

	/**
	 * What is the min amount time taken to execute this query?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getExecutionMinTime();

	/**
	 * How long, cumulatively, have all executions of this query
	 * taken?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getExecutionTotalTime();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	double getExecutionAvgTimeAsDouble();

	/**
	 * The number of cache hits for this query.
	 *
	 * @apiNote Note that a query can be saved into different
	 * regions at different times.  This value represents the
	 * sum total across all of those regions
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getCacheHitCount();

	/**
	 * The number of cache misses for this query
	 *
	 * @apiNote Note that a query can be saved into different
	 * regions at different times.  This value represents the
	 * sum total across all of those regions
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getCacheMissCount();

	/**
	 * The number of cache puts for this query
	 *
	 * @apiNote Note that a query can be saved into different
	 * regions at different times.  This value represents the
	 * sum total across all of those regions
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getCachePutCount();

	/**
	 * The number of query plans successfully fetched from the
	 * cache.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default long getPlanCacheHitCount() {
		//For backward compatibility
		return 0;
	}

	/**
	 * The number of query plans *not* fetched from the cache.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default long getPlanCacheMissCount(){
		//For backward compatibility
		return 0;
	}

	/**
	 * The overall time spent to compile the plan for this
	 * particular query.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default long getPlanCompilationTotalMicroseconds() {
		//For backward compatibility
		return 0;
	}
}
