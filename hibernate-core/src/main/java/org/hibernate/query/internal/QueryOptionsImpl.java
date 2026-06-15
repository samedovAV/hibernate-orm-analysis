/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.internal;

import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import jakarta.persistence.Timeout;
import jakarta.annotation.Nullable;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.LockOptions;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.spi.AppliedGraph;
import org.hibernate.graph.spi.RootGraphImplementor;
import org.hibernate.query.ResultListTransformer;
import org.hibernate.query.TupleTransformer;
import org.hibernate.query.spi.Limit;
import org.hibernate.query.spi.MutableQueryOptions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.emptyList;
import static org.hibernate.query.QueryLogging.QUERY_LOGGER;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class QueryOptionsImpl implements MutableQueryOptions, AppliedGraph {
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Valid for all query types
	private FlushMode flushMode;
	private Timeout timeout;
	private String comment;
	private List<String> databaseHints;

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Valid for select query types
	private Integer fetchSize;
	private Boolean readOnlyEnabled;

	private Boolean resultCachingEnabled;
	private CacheRetrieveMode cacheRetrieveMode;
	private CacheStoreMode cacheStoreMode;
	private String resultCacheRegionName;
	private boolean refreshSession;

	private Boolean queryPlanCachingEnabled;
	private Boolean limitInMemoryEnabled;

	private final Limit limit;
	private final LockOptions lockOptions;

	private TupleTransformer<?> tupleTransformer;
	private ResultListTransformer<?> resultListTransformer;

	private RootGraphImplementor<?> rootGraph;
	private GraphSemantic graphSemantic;
	private Set<String> enabledFetchProfiles;
	private Set<String> disabledFetchProfiles;

	public QueryOptionsImpl() {
		this.limit = new Limit();
		this.lockOptions = new LockOptions();
	}

	/**
	 * Copy constructor.
	 * @see #makeCopy()
	 */
	public QueryOptionsImpl(QueryOptionsImpl original) {
		this.flushMode = original.flushMode;
		this.timeout = original.timeout;
		this.comment = original.comment;
		this.databaseHints = copy( original.databaseHints );
		this.fetchSize = original.fetchSize;
		this.readOnlyEnabled = original.readOnlyEnabled;
		this.resultCachingEnabled = original.resultCachingEnabled;
		this.cacheRetrieveMode = original.cacheRetrieveMode;
		this.cacheStoreMode = original.cacheStoreMode;
		this.resultCacheRegionName = original.resultCacheRegionName;
		this.refreshSession = original.refreshSession;
		this.queryPlanCachingEnabled = original.queryPlanCachingEnabled;
		this.limitInMemoryEnabled = original.limitInMemoryEnabled;
		this.limit = original.limit.makeCopy();
		this.lockOptions = original.lockOptions.makeCopy();
		this.tupleTransformer = original.tupleTransformer;
		this.resultListTransformer = original.resultListTransformer;
		this.rootGraph = original.rootGraph;
		this.graphSemantic = original.graphSemantic;
		this.enabledFetchProfiles = copy( original.enabledFetchProfiles );
		this.disabledFetchProfiles = copy( original.disabledFetchProfiles );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private <E> Set<E> copy(Set<E> original) {
		if ( original == null ) {
			return null;
		}
		return new HashSet<>( original );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private <E> List<E> copy(List<E> original) {
		return original == null ? null : new ArrayList<>( original );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Timeout getTimeout() {
		return timeout;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void setTimeout(int timeout) {
		setTimeout( Timeout.seconds( timeout ) );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void setTimeout(Integer timeout) {
		setTimeout( Timeout.seconds( timeout ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setTimeout(Timeout timeout) {
		this.timeout = timeout;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FlushMode getFlushMode() {
		return flushMode;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setFlushMode(FlushMode flushMode) {
		this.flushMode = flushMode;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getComment() {
		return comment;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<String> getDatabaseHints() {
		return databaseHints == null ? emptyList() : databaseHints;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addDatabaseHint(String hint) {
		if ( databaseHints == null ) {
			databaseHints = new ArrayList<>();
		}
		databaseHints.add( hint );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setTupleTransformer(TupleTransformer<?> transformer) {
		this.tupleTransformer = transformer;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setResultListTransformer(ResultListTransformer<?> transformer) {
		this.resultListTransformer = transformer;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Limit getLimit() {
		return limit;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public LockOptions getLockOptions() {
		return lockOptions;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getFetchSize() {
		return fetchSize;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setFetchSize(Integer fetchSize) {
		this.fetchSize = fetchSize;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CacheMode getCacheMode() {
		final var cacheMode = CacheMode.fromJpaModes( cacheRetrieveMode, cacheStoreMode );
		return refreshSession && cacheMode == CacheMode.REFRESH
				? CacheMode.REFRESH_SESSION
				: cacheMode;
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
	public void setCacheRetrieveMode(CacheRetrieveMode retrieveMode) {
		this.cacheRetrieveMode = retrieveMode;
		this.refreshSession = false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setCacheStoreMode(CacheStoreMode storeMode) {
		this.cacheStoreMode = storeMode;
		this.refreshSession = false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setCacheMode(CacheMode cacheMode) {
		if ( cacheMode == null ) {
			QUERY_LOGGER.debug( "Null CacheMode passed to #setCacheMode; falling back to 'NORMAL'" );
			cacheMode = CacheMode.NORMAL;
		}

		this.cacheRetrieveMode = cacheMode.getJpaRetrieveMode();
		this.cacheStoreMode = cacheMode.getJpaStoreMode();
		this.refreshSession = cacheMode == CacheMode.REFRESH_SESSION;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean isResultCachingEnabled() {
		return resultCachingEnabled;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setResultCachingEnabled(boolean resultCachingEnabled) {
		this.resultCachingEnabled = resultCachingEnabled;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getResultCacheRegionName() {
		return resultCacheRegionName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean getQueryPlanCachingEnabled() {
		return queryPlanCachingEnabled;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean isLimitInMemoryEnabled() {
		return limitInMemoryEnabled;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setQueryPlanCachingEnabled(Boolean queryPlanCachingEnabled) {
		this.queryPlanCachingEnabled = queryPlanCachingEnabled;
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
	public void setResultCacheRegionName(String resultCacheRegionName) {
		this.resultCacheRegionName = resultCacheRegionName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setLimitInMemory(boolean limitInMemory) {
		this.limitInMemoryEnabled = limitInMemory;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setReadOnly(boolean readOnly) {
		this.readOnlyEnabled = readOnly;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean isReadOnly() {
		return readOnlyEnabled;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applyGraph(RootGraphImplementor<?> rootGraph, GraphSemantic graphSemantic) {
		this.rootGraph = rootGraph;
		this.graphSemantic = graphSemantic;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void enableFetchProfile(String profileName) {
		if ( enabledFetchProfiles == null ) {
			enabledFetchProfiles = new HashSet<>();
		}
		enabledFetchProfiles.add( profileName );
		if ( disabledFetchProfiles != null ) {
			disabledFetchProfiles.remove( profileName );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void disableFetchProfile(String profileName) {
		if ( disabledFetchProfiles == null ) {
			disabledFetchProfiles = new HashSet<>();
		}
		disabledFetchProfiles.add( profileName );
		if ( enabledFetchProfiles != null ) {
			enabledFetchProfiles.remove( profileName );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<String> getEnabledFetchProfiles() {
		return enabledFetchProfiles;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<String> getDisabledFetchProfiles() {
		return disabledFetchProfiles;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AppliedGraph getAppliedGraph() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable RootGraphImplementor<?> getGraph() {
		return rootGraph;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable GraphSemantic getSemantic() {
		return graphSemantic;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MutableQueryOptions makeCopy() {
		return new QueryOptionsImpl( this );
	}
}
