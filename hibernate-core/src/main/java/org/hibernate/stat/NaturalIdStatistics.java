/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.stat;

import java.io.Serializable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Statistics pertaining to the execution of queries which resolve a natural
 * id lookup against the database.
 *
 * @apiNote The natural-id resolution data is allowed to be stored in the
 * second-level cache, and if so stored will have available caching stats as
 * well available via {@link Statistics#getDomainDataRegionStatistics} using
 * the configured region name
 *
 * @author Eric Dalquist
 * @author Steve Ebersole
 */
public interface NaturalIdStatistics extends CacheableDataStatistics, Serializable {

	// todo (6.0) : consider a means to get the cache region statistics for:
	//              1) an entity by name
	//              2) a collection by role
	//              3) a natural-id by entity name

	/**
	 * Number of times (since last Statistics clearing) the "natural id
	 * resolution" query has been executed
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getExecutionCount();

	/**
	 * The average amount of time it takes (since last Statistics clearing) for
	 * the execution of this "natural id resolution" query
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getExecutionAvgTime();

	/**
	 * The maximum amount of time it takes (since last Statistics clearing) for
	 * the execution of this "natural id resolution" query
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getExecutionMaxTime();

	/**
	 * The minimum amount of time it takes (since last Statistics clearing) for
	 * the execution of this "natural id resolution" query
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getExecutionMinTime();

	/**
	 * The number of times (since last Statistics clearing) that natural-id value
	 * {@linkplain org.hibernate.metamodel.mapping.NaturalIdMapping#normalizeInput normalization}
	 * has been performed.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getNormalizationCount();
}
