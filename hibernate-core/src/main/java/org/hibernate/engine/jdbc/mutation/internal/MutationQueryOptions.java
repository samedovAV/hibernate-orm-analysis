/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.mutation.internal;

import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import jakarta.persistence.Timeout;
import org.hibernate.FlushMode;
import org.hibernate.LockOptions;
import org.hibernate.graph.spi.AppliedGraph;
import org.hibernate.query.ResultListTransformer;
import org.hibernate.query.TupleTransformer;
import org.hibernate.query.spi.Limit;
import org.hibernate.query.spi.QueryOptions;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class MutationQueryOptions implements QueryOptions {
	public static final MutationQueryOptions INSTANCE = new MutationQueryOptions();

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
	public CacheRetrieveMode getCacheRetrieveMode() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CacheStoreMode getCacheStoreMode() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getResultCacheRegionName() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public LockOptions getLockOptions() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getComment() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<String> getDatabaseHints() {
		return Collections.emptyList();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getFetchSize() {
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

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Limit getLimit() {
		return LimitImpl.INSTANCE;
	}

	private static class LimitImpl extends Limit {
		public static final LimitImpl INSTANCE = new LimitImpl();

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public void setFirstRow(Integer firstRow) {
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public void setMaxRows(int maxRows) {
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public void setMaxRows(Integer maxRows) {
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Limit makeCopy() {
			return this;
		}
	}
}
