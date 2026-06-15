/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.stat;

import java.io.Serializable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Collection-related statistics.
 *
 * @author Gavin King
 * @author Steve Ebersole
 */
public interface CollectionStatistics extends CacheableDataStatistics, Serializable {
	/**
	 * Number of times (since last Statistics clearing) this collection
	 * has been loaded
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getLoadCount();

	/**
	 * Number of times (since last Statistics clearing) this collection
	 * has been fetched
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getFetchCount();

	/**
	 * Number of times (since last Statistics clearing) this collection
	 * has been recreated (rows potentially deleted and then rows (re-)inserted)
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getRecreateCount();

	/**
	 * Number of times (since last Statistics clearing) this collection
	 * has been removed
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getRemoveCount();

	/**
	 * Number of times (since last Statistics clearing) this collection
	 * has been updated
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getUpdateCount();
}
