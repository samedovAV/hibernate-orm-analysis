/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.spi;

import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import jakarta.persistence.Timeout;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.LockOptions;
import org.hibernate.graph.spi.AppliedGraph;
import org.hibernate.query.ResultListTransformer;
import org.hibernate.query.TupleTransformer;

import java.util.List;
import java.util.Set;

import static java.util.Collections.emptyList;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public abstract class QueryOptionsAdapter implements QueryOptions {
	private final LockOptions lockOptions = new LockOptions();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Limit getLimit() {
		return Limit.NONE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getFetchSize() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getComment() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public LockOptions getLockOptions() {
		return lockOptions;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<String> getDatabaseHints() {
		return emptyList();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Timeout getTimeout() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FlushMode getFlushMode() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean isReadOnly() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CacheRetrieveMode getCacheRetrieveMode() {
		return CacheRetrieveMode.BYPASS;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CacheStoreMode getCacheStoreMode() {
		return CacheStoreMode.BYPASS;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CacheMode getCacheMode() {
		return CacheMode.IGNORE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean isResultCachingEnabled() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean getQueryPlanCachingEnabled() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getResultCacheRegionName() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AppliedGraph getAppliedGraph() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TupleTransformer<?> getTupleTransformer() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ResultListTransformer<?> getResultListTransformer() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<String> getEnabledFetchProfiles() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<String> getDisabledFetchProfiles() {
		return null;
	}
}
