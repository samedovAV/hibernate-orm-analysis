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
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.spi.RootGraphImplementor;
import org.hibernate.query.ResultListTransformer;
import org.hibernate.query.TupleTransformer;

import static org.hibernate.query.QueryLogging.QUERY_LOGGER;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Extension to QueryOptions providing ability to mutate the values.
 * Generally used by the query instances to collect the options set
 * by the various API methods.
 *
 * @author Steve Ebersole
 */
public interface MutableQueryOptions extends QueryOptions {
	/**
	 * Corollary to {@link #getFlushMode()}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setFlushMode(FlushMode flushMode);

	/**
	 * Corollary to {@link #getCacheRetrieveMode}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setCacheRetrieveMode(CacheRetrieveMode retrieveMode);

	/**
	 * Corollary to {@link #getCacheStoreMode()}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setCacheStoreMode(CacheStoreMode storeMode);

	/**
	 * Corollary to {@link #getCacheMode()}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void setCacheMode(CacheMode cacheMode) {
		if ( cacheMode == null ) {
			QUERY_LOGGER.debug( "Null CacheMode passed to #setCacheMode; falling back to 'NORMAL'" );
			cacheMode = CacheMode.NORMAL;
		}

		setCacheRetrieveMode( cacheMode.getJpaRetrieveMode() );
		setCacheStoreMode( cacheMode.getJpaStoreMode() );
	}

	/**
	 * Corollary to {@link #isResultCachingEnabled()}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setResultCachingEnabled(boolean cacheable);

	/**
	 * Corollary to {@link #getResultCacheRegionName()}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setResultCacheRegionName(String cacheRegion);

	/**
	 * Corollary to {@link #getQueryPlanCachingEnabled()}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setQueryPlanCachingEnabled(Boolean queryPlanCachingEnabled);

	/**
	 * Corollary to {@link #isLimitInMemoryEnabled()}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setLimitInMemory(boolean limitInMemory);

	/**
	 * Corollary to {@link #getTimeout()}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setTimeout(Timeout timeout);

	/**
	 * Corollary to {@link #getTimeout()}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setTimeout(int timeout);

	/**
	 * Corollary to {@link #getFetchSize()}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setFetchSize(int fetchSize);

	/**
	 * Corollary to {@link #isReadOnly()}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setReadOnly(boolean readOnly);

	/**
	 * Corollary to {@link #getComment()}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setComment(String comment);

	/**
	 * Corollary to {@link #getDatabaseHints()}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addDatabaseHint(String hint);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setTupleTransformer(TupleTransformer<?> transformer);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setResultListTransformer(ResultListTransformer<?> transformer);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void applyGraph(RootGraphImplementor<?> rootGraph, GraphSemantic graphSemantic);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void enableFetchProfile(String profileName);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void disableFetchProfile(String profileName);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutableQueryOptions makeCopy();
}
