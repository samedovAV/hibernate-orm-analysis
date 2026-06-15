/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.stat;

import java.io.Serializable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Entity-related statistics.
 *
 * @author Gavin King
 * @author Steve Ebersole
 */
public interface EntityStatistics extends CacheableDataStatistics, Serializable {
	/**
	 * Number of times (since last Statistics clearing) this entity
	 * has been deleted
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getDeleteCount();

	/**
	 * Number of times (since last Statistics clearing) this entity
	 * has been inserted
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getInsertCount();

	/**
	 * Number of times (since last Statistics clearing) this entity
	 * has been updated
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getUpdateCount();

	/**
	 * Number of times (since last Statistics clearing) this entity
	 * has been loaded
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getLoadCount();

	/**
	 * Number of times (since last Statistics clearing) this entity
	 * has been fetched
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getFetchCount();

	/**
	 * Number of times (since last Statistics clearing) this entity
	 * has experienced an optimistic lock failure.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getOptimisticFailureCount();
}
