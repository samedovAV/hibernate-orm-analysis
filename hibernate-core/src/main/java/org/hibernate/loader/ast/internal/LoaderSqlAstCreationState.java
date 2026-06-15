/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.loader.ast.internal;

import jakarta.persistence.Timeout;
import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.engine.spi.FetchOptions;
import org.hibernate.engine.spi.LoadQueryInfluencers;
import org.hibernate.graph.spi.AppliedGraph;
import org.hibernate.metamodel.mapping.AssociationKey;
import org.hibernate.metamodel.mapping.ForeignKeyDescriptor;
import org.hibernate.metamodel.mapping.ModelPart;
import org.hibernate.metamodel.mapping.ordering.OrderByFragment;
import org.hibernate.query.ResultListTransformer;
import org.hibernate.query.TupleTransformer;
import org.hibernate.query.spi.Limit;
import org.hibernate.query.spi.QueryOptions;
import org.hibernate.query.sqm.sql.internal.SqlAstQueryPartProcessingStateImpl;
import org.hibernate.query.sqm.tree.from.SqmFrom;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.Clause;
import org.hibernate.sql.ast.spi.FromClauseAccess;
import org.hibernate.sql.ast.spi.SqlAliasBaseGenerator;
import org.hibernate.sql.ast.spi.SqlAstCreationContext;
import org.hibernate.sql.ast.spi.SqlAstCreationState;
import org.hibernate.sql.ast.spi.SqlAstProcessingState;
import org.hibernate.sql.ast.spi.SqlAstQueryPartProcessingState;
import org.hibernate.sql.ast.spi.SqlExpressionResolver;
import org.hibernate.sql.ast.tree.from.FromClause;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import org.hibernate.sql.ast.tree.select.QueryPart;
import org.hibernate.sql.ast.tree.select.QuerySpec;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.FetchParent;
import org.hibernate.sql.results.graph.internal.ImmutableFetchList;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Helper used when generating the database-snapshot select query
 */
public class LoaderSqlAstCreationState
		implements SqlAstQueryPartProcessingState, SqlAstCreationState, DomainResultCreationState, QueryOptions {
	public interface FetchProcessor {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		ImmutableFetchList visitFetches(FetchParent fetchParent, LoaderSqlAstCreationState creationState);
	}

	private final SqlAliasBaseGenerator sqlAliasBaseManager;
	private final boolean forceIdentifierSelection;
	private final LoadQueryInfluencers loadQueryInfluencers;
	private final SqlAstCreationContext sf;
	private final SqlAstQueryPartProcessingStateImpl processingState;
	private final FromClauseAccess fromClauseAccess;
	private final LockOptions lockOptions;
	private final FetchProcessor fetchProcessor;

	private boolean resolvingCircularFetch;
	private ForeignKeyDescriptor.Nature currentlyResolvingForeignKeySide;
	private final Set<AssociationKey> visitedAssociationKeys = new HashSet<>();
	private Map<NavigablePath, FetchOptions> fetchOptions;

	public LoaderSqlAstCreationState(
			QueryPart queryPart,
			SqlAliasBaseGenerator sqlAliasBaseManager,
			FromClauseAccess fromClauseAccess,
			LockOptions lockOptions,
			FetchProcessor fetchProcessor,
			boolean forceIdentifierSelection,
			LoadQueryInfluencers loadQueryInfluencers,
			SqlAstCreationContext sf) {
		this.sqlAliasBaseManager = sqlAliasBaseManager;
		this.fromClauseAccess = fromClauseAccess;
		this.lockOptions = lockOptions;
		this.fetchProcessor = fetchProcessor;
		this.forceIdentifierSelection = forceIdentifierSelection;
		this.loadQueryInfluencers = loadQueryInfluencers;
		this.sf = sf;
		this.processingState = new SqlAstQueryPartProcessingStateImpl(
				queryPart,
				null,
				this,
				() -> Clause.IRRELEVANT,
				true
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applyOrdering(TableGroup tableGroup, OrderByFragment orderByFragment) {
		final QuerySpec querySpec = getInflightQueryPart().getFirstQuerySpec();
		assert querySpec.isRoot() : "Illegal attempt to apply order-by fragment to a non-root query spec";
		orderByFragment.apply( querySpec, tableGroup, this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqlAstCreationContext getCreationContext() {
		return sf;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqlAstProcessingState getCurrentProcessingState() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public QueryPart getInflightQueryPart() {
		return processingState.getInflightQueryPart();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public FromClause getFromClause() {
		return processingState.getFromClause();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void applyPredicate(Predicate predicate) {
		processingState.applyPredicate( predicate );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void registerTreatedFrom(SqmFrom<?, ?> sqmFrom) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void registerFromUsage(SqmFrom<?, ?> sqmFrom, boolean downgradeTreatUses) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Map<SqmFrom<?, ?>, Boolean> getFromRegistrations() {
		return Collections.emptyMap();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqlExpressionResolver getSqlExpressionResolver() {
		return processingState;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FromClauseAccess getFromClauseAccess() {
		return fromClauseAccess;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqlAliasBaseGenerator getSqlAliasBaseGenerator() {
		return sqlAliasBaseManager;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public LoadQueryInfluencers getLoadQueryInfluencers() {
		return loadQueryInfluencers;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean applyOnlyLoadByKeyFilters() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void registerLockMode(String identificationVariable, LockMode explicitLockMode) {
		throw new UnsupportedOperationException( "Registering lock modes should only be done for result set mappings" );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public ImmutableFetchList visitFetches(FetchParent fetchParent) {
		return fetchProcessor.visitFetches( fetchParent, this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <R> R withNestedFetchParent(FetchParent fetchParent, Function<FetchParent, R> action) {
		final var nestingFetchParent = processingState.getNestingFetchParent();
		processingState.setNestingFetchParent( fetchParent );
		final R result = action.apply( fetchParent );
		processingState.setNestingFetchParent( nestingFetchParent );
		return result;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isResolvingCircularFetch() {
		return resolvingCircularFetch;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setResolvingCircularFetch(boolean resolvingCircularFetch) {
		this.resolvingCircularFetch = resolvingCircularFetch;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ForeignKeyDescriptor.Nature getCurrentlyResolvingForeignKeyPart() {
		return currentlyResolvingForeignKeySide;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setCurrentlyResolvingForeignKeyPart(ForeignKeyDescriptor.Nature currentlyResolvingForeignKeySide) {
		this.currentlyResolvingForeignKeySide = currentlyResolvingForeignKeySide;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void registerFetchOptions(NavigablePath fetchablePath, FetchOptions fetchOptions) {
		if ( fetchOptions.hasOptions() ) {
			if ( this.fetchOptions == null ) {
				this.fetchOptions = new HashMap<>();
			}
			this.fetchOptions.put( fetchablePath, fetchOptions );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchOptions getFetchOptions(NavigablePath fetchablePath) {
		return fetchOptions == null ? FetchOptions.NONE : fetchOptions.getOrDefault( fetchablePath, FetchOptions.NONE );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean forceIdentifierSelection() {
		return forceIdentifierSelection;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqlAstCreationState getSqlAstCreationState() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean registerVisitedAssociationKey(AssociationKey associationKey) {
		return visitedAssociationKeys.add( associationKey );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void removeVisitedAssociationKey(AssociationKey associationKey) {
		visitedAssociationKeys.remove( associationKey );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isAssociationKeyVisited(AssociationKey associationKey) {
		return visitedAssociationKeys.contains( associationKey );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isRegisteringVisitedAssociationKeys(){
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ModelPart resolveModelPart(NavigablePath navigablePath) {
		// for now, let's assume that the navigable-path refers to TableGroup
		return fromClauseAccess.findTableGroup( navigablePath ).getModelPart();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqlAstProcessingState getParentState() {
		return null;
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
	public AppliedGraph getAppliedGraph() {
		// todo (6.0) : use this from the "load settings" (Hibernate method args, map passed to JPA methods)
		//   the legacy approach is to temporarily set this on the Session's "load query influencers"
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
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean getQueryPlanCachingEnabled() {
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
	public String getResultCacheRegionName() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public LockOptions getLockOptions() {
		return lockOptions;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getComment() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<String> getDatabaseHints() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getFetchSize() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Limit getLimit() {
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
