/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.exec.spi;

import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import jakarta.persistence.Timeout;
import org.hibernate.FlushMode;
import org.hibernate.Incubating;
import org.hibernate.LockOptions;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.graph.spi.AppliedGraph;
import org.hibernate.query.ResultListTransformer;
import org.hibernate.query.TupleTransformer;
import org.hibernate.query.spi.Limit;
import org.hibernate.query.spi.QueryOptions;
import org.hibernate.query.spi.QueryParameterBindings;
import org.hibernate.sql.exec.internal.BaseExecutionContext;
import org.hibernate.sql.exec.internal.StandardStatementCreator;
import org.hibernate.sql.results.spi.ListResultsConsumer;
import org.hibernate.sql.results.spi.ResultsConsumer;
import org.hibernate.sql.results.spi.RowTransformer;
import org.hibernate.sql.results.spi.ScrollableResultsConsumer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An executor for JdbcSelect operations.
 *
 * @author Steve Ebersole
 */
@Incubating
public interface JdbcSelectExecutor {

	/**
	 * @since 6.6
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T, R> T executeQuery(
			JdbcSelect jdbcSelect,
			JdbcParameterBindings jdbcParameterBindings,
			ExecutionContext executionContext,
			RowTransformer<R> rowTransformer,
			Class<R> domainResultType,
			StatementCreator statementCreator,
			ResultsConsumer<T, R> resultsConsumer);

	/**
	 * @since 6.6
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default <T, R> T executeQuery(
			JdbcSelect jdbcSelect,
			JdbcParameterBindings jdbcParameterBindings,
			ExecutionContext executionContext,
			RowTransformer<R> rowTransformer,
			Class<R> domainResultType,
			int resultCountEstimate,
			ResultsConsumer<T, R> resultsConsumer) {
		return executeQuery(
				jdbcSelect,
				jdbcParameterBindings,
				executionContext,
				rowTransformer,
				domainResultType,
				resultCountEstimate,
				StandardStatementCreator.getStatementCreator( null ),
				resultsConsumer
		);
	}

	/**
	 * @since 6.6
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default <T, R> T executeQuery(
			JdbcSelect jdbcSelect,
			JdbcParameterBindings jdbcParameterBindings,
			ExecutionContext executionContext,
			RowTransformer<R> rowTransformer,
			Class<R> domainResultType,
			int resultCountEstimate,
			StatementCreator statementCreator,
			ResultsConsumer<T, R> resultsConsumer) {
		return executeQuery(
				jdbcSelect,
				jdbcParameterBindings,
				executionContext,
				rowTransformer,
				domainResultType,
				statementCreator,
				resultsConsumer
		);
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default <R> List<R> list(
			JdbcSelect jdbcSelect,
			JdbcParameterBindings jdbcParameterBindings,
			ExecutionContext executionContext,
			RowTransformer<R> rowTransformer,
			ListResultsConsumer.UniqueSemantic uniqueSemantic) {
		return list( jdbcSelect, jdbcParameterBindings, executionContext, rowTransformer, null, uniqueSemantic );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default <R> List<R> list(
			JdbcSelect jdbcSelect,
			JdbcParameterBindings jdbcParameterBindings,
			ExecutionContext executionContext,
			RowTransformer<R> rowTransformer,
			Class<R> requestedJavaType,
			ListResultsConsumer.UniqueSemantic uniqueSemantic) {
		return list(
				jdbcSelect,
				jdbcParameterBindings,
				executionContext,
				rowTransformer,
				requestedJavaType,
				uniqueSemantic,
				-1
		);
	}

	/**
	 * @since 6.6
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <R> List<R> list(
			JdbcSelect jdbcSelect,
			JdbcParameterBindings jdbcParameterBindings,
			ExecutionContext executionContext,
			RowTransformer<R> rowTransformer,
			Class<R> requestedJavaType,
			ListResultsConsumer.UniqueSemantic uniqueSemantic,
			int resultCountEstimate) {
		// Only do auto flushing for top level queries
		return executeQuery(
				jdbcSelect,
				jdbcParameterBindings,
				executionContext,
				rowTransformer,
				requestedJavaType,
				resultCountEstimate,
				ListResultsConsumer.instance( uniqueSemantic )
		);
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default <R> ScrollableResults<R> scroll(
			JdbcSelect jdbcSelect,
			ScrollMode scrollMode,
			JdbcParameterBindings jdbcParameterBindings,
			ExecutionContext executionContext,
			RowTransformer<R> rowTransformer) {
		return scroll( jdbcSelect, scrollMode, jdbcParameterBindings, executionContext, rowTransformer, -1 );
	}

	/**
	 * @since 6.6
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <R> ScrollableResults<R> scroll(
			JdbcSelect jdbcSelect,
			ScrollMode scrollMode,
			JdbcParameterBindings jdbcParameterBindings,
			ExecutionContext executionContext,
			RowTransformer<R> rowTransformer,
			int resultCountEstimate) {
		return executeQuery(
				jdbcSelect,
				jdbcParameterBindings,
				getScrollContext( executionContext ),
				rowTransformer,
				null,
				resultCountEstimate,
				StandardStatementCreator.getStatementCreator( scrollMode ),
				ScrollableResultsConsumer.instance()
		);
	}

	/**
	 * @since 6.6
	 */
	@FunctionalInterface
	interface StatementCreator {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		PreparedStatement createStatement(ExecutionContext executionContext, String sql) throws SQLException;
	}

	/*
		When `Query#scroll()` is call the query is not executed immediately, a new ExecutionContext with the values
		of the `persistenceContext.isDefaultReadOnly()` and of the `queryOptions.isReadOnly()` set at the moment of
		the Query#scroll() call is created in order to use it when the query will be executed.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private ExecutionContext getScrollContext(ExecutionContext context) {
		class ScrollableExecutionContext extends BaseExecutionContext implements QueryOptions {

			private final Timeout timeout;
			private final FlushMode flushMode;
			private final Boolean readOnly;
			private final AppliedGraph appliedGraph;
			private final TupleTransformer<?> tupleTransformer;
			private final ResultListTransformer<?> resultListTransformer;
			private final Boolean resultCachingEnabled;
			private final CacheRetrieveMode cacheRetrieveMode;
			private final CacheStoreMode cacheStoreMode;
			private final String resultCacheRegionName;
			private final LockOptions lockOptions;
			private final String comment;
			private final List<String> databaseHints;
			private final Integer fetchSize;
			private final Limit limit;
			private final Boolean limitInMemoryEnabled;
			// Preserved across the SqlOmittingQueryOptions wrap so that runtime
			// JdbcParameter binders that the converter injected (offset/limit)
			// can still see the application-set limit even though getLimit()
			// reports NONE for SQL rendering.
			private final Limit originalLimit;
			private final ExecutionContext context;

			public ScrollableExecutionContext(
					Timeout timeout,
					FlushMode flushMode,
					Boolean readOnly,
					AppliedGraph appliedGraph,
					TupleTransformer<?> tupleTransformer,
					ResultListTransformer<?> resultListTransformer,
					Boolean resultCachingEnabled,
					CacheRetrieveMode cacheRetrieveMode,
					CacheStoreMode cacheStoreMode,
					String resultCacheRegionName,
					LockOptions lockOptions,
					String comment,
					List<String> databaseHints,
					Integer fetchSize,
					Limit limit,
					Boolean limitInMemoryEnabled,
					Limit originalLimit,
					ExecutionContext context) {
				super( context.getSession() );
				this.timeout = timeout;
				this.flushMode = flushMode;
				this.readOnly = readOnly;
				this.appliedGraph = appliedGraph;
				this.tupleTransformer = tupleTransformer;
				this.resultListTransformer = resultListTransformer;
				this.resultCachingEnabled = resultCachingEnabled;
				this.cacheRetrieveMode = cacheRetrieveMode;
				this.cacheStoreMode = cacheStoreMode;
				this.resultCacheRegionName = resultCacheRegionName;
				this.lockOptions = lockOptions;
				this.comment = comment;
				this.databaseHints = databaseHints;
				this.fetchSize = fetchSize;
				this.limit = limit;
				this.limitInMemoryEnabled = limitInMemoryEnabled;
				this.originalLimit = originalLimit;
				this.context = context;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public boolean isScrollResult() {
				return true;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public QueryOptions getQueryOptions() {
				return this;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public Timeout getTimeout() {
				return timeout;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public FlushMode getFlushMode() {
				return flushMode;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public Boolean isReadOnly() {
				return readOnly;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public AppliedGraph getAppliedGraph() {
				return appliedGraph;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public TupleTransformer<?> getTupleTransformer() {
				return tupleTransformer;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public ResultListTransformer<?> getResultListTransformer() {
				return resultListTransformer;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public Boolean isResultCachingEnabled() {
				return resultCachingEnabled;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public Boolean getQueryPlanCachingEnabled() {
				return null;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public CacheRetrieveMode getCacheRetrieveMode() {
				return cacheRetrieveMode;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public CacheStoreMode getCacheStoreMode() {
				return cacheStoreMode;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public String getResultCacheRegionName() {
				return resultCacheRegionName;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public LockOptions getLockOptions() {
				return lockOptions;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public String getComment() {
				return comment;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public List<String> getDatabaseHints() {
				return databaseHints;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public Integer getFetchSize() {
				return fetchSize;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public Limit getLimit() {
				return limit;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public Boolean isLimitInMemoryEnabled() {
				return limitInMemoryEnabled;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public Limit peekOriginalLimit() {
				return originalLimit;
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public QueryParameterBindings getQueryParameterBindings() {
				return context.getQueryParameterBindings();
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public Callback getCallback() {
				return context.getCallback();
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public boolean hasCallbackActions() {
				return context.hasCallbackActions();
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

		final QueryOptions options = context.getQueryOptions();
		return new ScrollableExecutionContext(
				options.getTimeout(),
				options.getFlushMode(),
				options.isReadOnly() == null
					? context.getSession().getPersistenceContext().isDefaultReadOnly()
					: options.isReadOnly(),
				options.getAppliedGraph(),
				options.getTupleTransformer(),
				options.getResultListTransformer(),
				options.isResultCachingEnabled(),
				options.getCacheRetrieveMode(),
				options.getCacheStoreMode(),
				options.getResultCacheRegionName(),
				options.getLockOptions(),
				options.getComment(),
				options.getDatabaseHints(),
				options.getFetchSize(),
				options.getLimit(),
				options.isLimitInMemoryEnabled(),
				options.peekOriginalLimit(),
				context
		);
	}

}
