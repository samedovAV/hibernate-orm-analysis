/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.spi;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;

import jakarta.persistence.Timeout;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.LockOptions;
import org.hibernate.graph.spi.AppliedGraph;
import org.hibernate.query.ResultListTransformer;
import org.hibernate.query.TupleTransformer;
import org.hibernate.sql.results.spi.ListResultsConsumer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class DelegatingQueryOptions implements QueryOptions {

	private final QueryOptions queryOptions;

	public DelegatingQueryOptions(QueryOptions queryOptions) {
		this.queryOptions = queryOptions;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Timeout getTimeout() {
		return queryOptions.getTimeout();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public FlushMode getFlushMode() {
		return queryOptions.getFlushMode();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Boolean isReadOnly() {
		return queryOptions.isReadOnly();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public AppliedGraph getAppliedGraph() {
		return queryOptions.getAppliedGraph();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TupleTransformer<?> getTupleTransformer() {
		return queryOptions.getTupleTransformer();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public ResultListTransformer<?> getResultListTransformer() {
		return queryOptions.getResultListTransformer();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Boolean isResultCachingEnabled() {
		return queryOptions.isResultCachingEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public CacheRetrieveMode getCacheRetrieveMode() {
		return queryOptions.getCacheRetrieveMode();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public CacheStoreMode getCacheStoreMode() {
		return queryOptions.getCacheStoreMode();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Boolean getQueryPlanCachingEnabled() {
		return queryOptions.getQueryPlanCachingEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Boolean isLimitInMemoryEnabled() {
		return queryOptions.isLimitInMemoryEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public CacheMode getCacheMode() {
		return queryOptions.getCacheMode();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getResultCacheRegionName() {
		return queryOptions.getResultCacheRegionName();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public LockOptions getLockOptions() {
		return queryOptions.getLockOptions();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getComment() {
		return queryOptions.getComment();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public List<String> getDatabaseHints() {
		return queryOptions.getDatabaseHints();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Integer getFetchSize() {
		return queryOptions.getFetchSize();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Set<String> getEnabledFetchProfiles() {
		return queryOptions.getEnabledFetchProfiles();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Set<String> getDisabledFetchProfiles() {
		return queryOptions.getDisabledFetchProfiles();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Limit getLimit() {
		return queryOptions.getLimit();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Limit peekOriginalLimit() {
		return queryOptions.peekOriginalLimit();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Integer getFirstRow() {
		return queryOptions.getFirstRow();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Integer getMaxRows() {
		return queryOptions.getMaxRows();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Limit getEffectiveLimit() {
		return queryOptions.getEffectiveLimit();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean hasLimit() {
		return queryOptions.hasLimit();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public ListResultsConsumer.UniqueSemantic getUniqueSemantic() {
		return queryOptions.getUniqueSemantic();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isScrollExecution() {
		return queryOptions.isScrollExecution();
	}
}
