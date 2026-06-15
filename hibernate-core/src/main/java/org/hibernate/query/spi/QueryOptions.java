/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.spi;

import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import jakarta.persistence.Timeout;
import jakarta.annotation.Nullable;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.LockOptions;
import org.hibernate.graph.spi.AppliedGraph;
import org.hibernate.query.ResultListTransformer;
import org.hibernate.query.TupleTransformer;
import org.hibernate.sql.results.spi.ListResultsConsumer;

import java.sql.Statement;
import java.util.List;
import java.util.Set;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Encapsulates options for the execution of query.
 *
 * @apiNote Note that not all options are relevant for every type of query.
 *
 * @author Steve Ebersole
 */
public interface QueryOptions {

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Query options

	/**
	 * The timeout to apply to the query.  May also be defined at the transaction
	 * level using {@link org.hibernate.Transaction#getTimeout}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Timeout getTimeout();

	/**
	 * The flush mode to use for the query execution
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FlushMode getFlushMode();

	/**
	 * Should entities returned from the query be marked read-only.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isReadOnly();

	/**
	 * JPA {@link jakarta.persistence.EntityGraph} explicitly applied to the
	 * query.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AppliedGraph getAppliedGraph();

	/**
	 * Transformer applied to the query to transform the structure of each "row"
	 * in the results
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	TupleTransformer<?> getTupleTransformer();

	/**
	 * Transformer applied to the query to transform the structure of the
	 * overall results
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	ResultListTransformer<?> getResultListTransformer();

	/**
	 * Should results from the query be cached?
	 *
	 * @see #getCacheMode
	 * @see #getResultCacheRegionName
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isResultCachingEnabled();

	/**
	 * Controls whether query results are read from the cache.
	 * No effect unless {@link #isResultCachingEnabled} returns
	 * {@code true}
	 *
	 * @see CacheMode
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CacheRetrieveMode getCacheRetrieveMode();

	/**
	 * Controls whether query results are put into the cache.
	 * No effect unless {@link #isResultCachingEnabled} returns
	 * {@code true}
	 *
	 * @see CacheMode
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CacheStoreMode getCacheStoreMode();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default CacheMode getCacheMode() {
		return CacheMode.fromJpaModes( getCacheRetrieveMode(), getCacheStoreMode() );
	}

	/**
	 * The query cache region in which the results should be cached.  No
	 * effect unless {@link #isResultCachingEnabled} returns {@code true}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getResultCacheRegionName();

	/**
	 * Should the query plan of the query be cached?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean getQueryPlanCachingEnabled();

	/**
	 * Whether top-level HQL/criteria pagination should be applied in memory
	 * instead of by SQL.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Boolean isLimitInMemoryEnabled() {
		return null;
	}

	/**
	 * The explicitly enabled profiles for this query
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<String> getEnabledFetchProfiles();

	/**
	 * The explicitly disabled profiles for this query
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<String> getDisabledFetchProfiles();


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// JDBC / SQL options

	/**
	 * Describes the locking to apply to the query results
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LockOptions getLockOptions();

	/**
	 * The SQL comment to apply to the interpreted SQL query, for dialects which
	 * support SQL comments
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getComment();

	/**
	 * Hints to apply to the interpreted SQL query
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<String> getDatabaseHints();

	/**
	 * The fetch size to be applied to the JDBC query.
	 *
	 * @see Statement#getFetchSize
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getFetchSize();

	/**
	 * The limit to the query results.  May also be accessed via
	 * {@link #getFirstRow} and {@link #getMaxRows}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Limit getLimit();

	/**
	 * The original {@link Limit} as set by the application, before any wrapper
	 * (e.g. {@link org.hibernate.query.spi.SqlOmittingQueryOptions} or the
	 * scroll execution context) hid it from {@link #getLimit()}. Used by
	 * runtime parameter binders that the SQM-to-SQL converter pushed into the
	 * SQL AST so they can bind the original value even when {@link #getLimit()}
	 * has been intentionally suppressed for SQL rendering.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Limit peekOriginalLimit() {
		return getLimit();
	}

	/**
	 * The first row from the results to return
	 *
	 * @see #getLimit
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default Integer getFirstRow() {
		return getLimit().getFirstRow();
	}

	/**
	 * The maximum number of rows to return from the results
	 *
	 * @see #getLimit
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default Integer getMaxRows() {
		return getLimit().getMaxRows();
	}

	/**
	 * Determine the effective paging limit to apply to the
	 * query.  If the application did not explicitly specify paging
	 * limits, {@link Limit#NONE} is returned
	 *
	 * @see #getLimit
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Limit getEffectiveLimit() {
		final Limit explicit = getLimit();
		return explicit != null ? explicit : Limit.NONE;
	}

	/**
	 * Did the application explicitly request paging limits?
	 *
	 * @see #getLimit
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean hasLimit() {
		final Limit limit = getLimit();
		return limit != null
			&& (limit.getFirstRow() != null || limit.getMaxRows() != null);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default ListResultsConsumer.UniqueSemantic getUniqueSemantic(){
		return null;
	}

	/**
	 * Whether this execution goes through {@code scroll()} /
	 * {@code getResultStream()} and therefore needs SQL row ordering stable
	 * enough for scroll-style result grouping.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isScrollExecution() {
		return false;
	}

	/**
	 * Provide singleton access for frequently needed options:
	 */
	QueryOptions NONE = new QueryOptionsAdapter() {
	};

	QueryOptions READ_WRITE = new QueryOptionsAdapter() {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Boolean isReadOnly() {
			return Boolean.FALSE;
		}
	};

	QueryOptions READ_ONLY = new QueryOptionsAdapter() {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Boolean isReadOnly() {
			return Boolean.TRUE;
		}
	};

}
