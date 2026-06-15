/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.spi;

import org.hibernate.engine.spi.LoadQueryInfluencers;
import org.hibernate.query.spi.QueryOptions;
import org.hibernate.query.sqm.tree.SqmStatement;

import java.util.function.BooleanSupplier;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

// Used by Hibernate Reactive
public interface CacheabilityInfluencers {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isQueryPlanCacheable();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getQueryString();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getQueryStringCacheKey();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int[] unnamedParameterIndices();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmStatement<?> getSqmStatement();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryOptions getQueryOptions();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LoadQueryInfluencers getLoadQueryInfluencers();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	BooleanSupplier hasMultiValuedParameterBindingsChecker();
}
