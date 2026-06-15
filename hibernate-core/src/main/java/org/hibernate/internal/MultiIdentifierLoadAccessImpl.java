/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.PessimisticLockScope;
import jakarta.persistence.Timeout;
import org.hibernate.CacheMode;
import org.hibernate.FindMultipleOption;
import org.hibernate.FindMultipleOption.BatchSize;
import org.hibernate.KeyType;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.MultiIdentifierLoadAccess;
import org.hibernate.NaturalIdSynchronization;
import org.hibernate.ReadOnlyMode;
import org.hibernate.UnknownProfileException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.spi.RootGraphImplementor;
import org.hibernate.internal.find.StatefulFindMultipleByKeyOperation;
import org.hibernate.loader.ast.spi.MultiIdLoadOptions;
import org.hibernate.internal.find.StatefulLoadAccessContext;
import org.hibernate.persister.entity.EntityPersister;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.emptyList;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Implementation of MultiIdentifierLoadAccess.
///
/// @author Steve Ebersole
///
/// @deprecated Use [StatefulFindMultipleByKeyOperation] instead.
@Deprecated
class MultiIdentifierLoadAccessImpl<T> implements MultiIdentifierLoadAccess<T>, MultiIdLoadOptions {
	private final SharedSessionContractImplementor session;
	private final EntityPersister entityPersister;

	private LockOptions lockOptions;
	private CacheMode cacheMode;
	private Boolean readOnly;

	private RootGraphImplementor<T> rootGraph;
	private GraphSemantic graphSemantic;

	private BatchSize batchSize;
	private FindMultipleOption.SessionCheckMode sessionCheckMode = FindMultipleOption.SessionCheckMode.DISABLED;
	private FindMultipleOption.RemovalsMode removalsMode = FindMultipleOption.RemovalsMode.REPLACE;
	protected FindMultipleOption.OrderingMode orderingMode = FindMultipleOption.OrderingMode.ORDERED;

	private Set<String> enabledFetchProfiles;
	private Set<String> disabledFetchProfiles;

	MultiIdentifierLoadAccessImpl(SharedSessionContractImplementor session, EntityPersister entityPersister) {
		this.session = session;
		this.entityPersister = entityPersister;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MultiIdentifierLoadAccess<T> with(LockMode lockMode, PessimisticLockScope lockScope) {
		if ( lockOptions == null ) {
			lockOptions = new LockOptions();
		}
		lockOptions.setLockMode( lockMode );
		lockOptions.setLockScope( lockScope );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MultiIdentifierLoadAccess<T> with(Timeout timeout) {
		if ( lockOptions == null ) {
			lockOptions = new LockOptions();
		}
		lockOptions.setTimeOut( timeout.milliseconds() );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public LockOptions getLockOptions() {
		return lockOptions;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final MultiIdentifierLoadAccess<T> with(LockOptions lockOptions) {
		this.lockOptions = lockOptions;
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MultiIdentifierLoadAccess<T> with(CacheMode cacheMode) {
		this.cacheMode = cacheMode;
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MultiIdentifierLoadAccess<T> withReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MultiIdentifierLoadAccess<T> with(EntityGraph<T> graph, GraphSemantic semantic) {
		this.rootGraph = (RootGraphImplementor<T>) graph;
		this.graphSemantic = semantic;
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getBatchSize() {
		return batchSize.batchSize();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MultiIdentifierLoadAccess<T> withBatchSize(int batchSize) {
		this.batchSize = batchSize < 1 ? null : new BatchSize( batchSize );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FindMultipleOption.SessionCheckMode getSessionCheckMode() {
		return sessionCheckMode;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isSecondLevelCacheCheckingEnabled() {
		return cacheMode == CacheMode.NORMAL || cacheMode == CacheMode.GET;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MultiIdentifierLoadAccess<T> enableSessionCheck(boolean enabled) {
		this.sessionCheckMode = enabled ? FindMultipleOption.SessionCheckMode.ENABLED : FindMultipleOption.SessionCheckMode.DISABLED;
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FindMultipleOption.RemovalsMode getRemovalsMode() {
		return removalsMode;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MultiIdentifierLoadAccess<T> enableReturnOfDeletedEntities(boolean enabled) {
		this.removalsMode = enabled ? FindMultipleOption.RemovalsMode.INCLUDE : FindMultipleOption.RemovalsMode.REPLACE;
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FindMultipleOption.OrderingMode getOrderingMode() {
		return orderingMode;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MultiIdentifierLoadAccess<T> enableOrderedReturn(boolean enabled) {
		this.orderingMode = enabled ? FindMultipleOption.OrderingMode.ORDERED : FindMultipleOption.OrderingMode.UNORDERED;
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Boolean getReadOnly(SessionImplementor session) {
		return readOnly != null
				? readOnly
				: session.getLoadQueryInfluencers().getReadOnly();
	}

	@Override
	@SuppressWarnings( "unchecked" )
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <K> List<T> multiLoad(K... ids) {
		return buildOperation().performFind( List.of( ids ), graphSemantic, rootGraph );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private StatefulFindMultipleByKeyOperation<T> buildOperation() {
		return new StatefulFindMultipleByKeyOperation<T>(
				entityPersister,
				(StatefulLoadAccessContext) session,
				KeyType.IDENTIFIER,
				batchSize,
				sessionCheckMode,
				removalsMode,
				orderingMode,
				cacheMode,
				lockOptions,
				readOnly == Boolean.TRUE ? ReadOnlyMode.READ_ONLY : ReadOnlyMode.READ_WRITE,
				enabledFetchProfiles,
				disabledFetchProfiles,
				// irrelevant for load-by-id
				NaturalIdSynchronization.DISABLED
		);
	}

	@Override
	@SuppressWarnings( "unchecked" )
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <K> List<T> multiLoad(List<K> ids) {
		return ids.isEmpty()
				? emptyList()
				: buildOperation().performFind( (List<Object>)ids, graphSemantic, rootGraph );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MultiIdentifierLoadAccess<T> enableFetchProfile(String profileName) {
		if ( !session.getFactory().containsFetchProfileDefinition( profileName ) ) {
			throw new UnknownProfileException( profileName );
		}
		if ( enabledFetchProfiles == null ) {
			enabledFetchProfiles = new HashSet<>();
		}
		enabledFetchProfiles.add( profileName );
		if ( disabledFetchProfiles != null ) {
			disabledFetchProfiles.remove( profileName );
		}
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MultiIdentifierLoadAccess<T> disableFetchProfile(String profileName) {
		if ( disabledFetchProfiles == null ) {
			disabledFetchProfiles = new HashSet<>();
		}
		disabledFetchProfiles.add( profileName );
		if ( enabledFetchProfiles != null ) {
			enabledFetchProfiles.remove( profileName );
		}
		return this;
	}
}
