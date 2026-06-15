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
import org.hibernate.NaturalIdMultiLoadAccess;
import org.hibernate.NaturalIdSynchronization;
import org.hibernate.ReadOnlyMode;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.spi.RootGraphImplementor;
import org.hibernate.internal.find.StatefulFindMultipleByKeyOperation;
import org.hibernate.loader.ast.spi.MultiNaturalIdLoadOptions;
import org.hibernate.internal.find.StatefulLoadAccessContext;
import org.hibernate.persister.entity.EntityPersister;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Implementation of NaturalIdMultiLoadAccess.
///
/// @deprecated Use [StatefulFindMultipleByKeyOperation] instead.
///
/// @author Steve Ebersole
@Deprecated
public class NaturalIdMultiLoadAccessStandard<T> implements NaturalIdMultiLoadAccess<T>, MultiNaturalIdLoadOptions {
	private final EntityPersister entityDescriptor;
	private final SharedSessionContractImplementor session;

	private LockOptions lockOptions;
	private CacheMode cacheMode;

	private RootGraphImplementor<T> rootGraph;
	private GraphSemantic graphSemantic;

	private Integer batchSize;
	private FindMultipleOption.RemovalsMode removalsMode = FindMultipleOption.RemovalsMode.REPLACE;
	private FindMultipleOption.OrderingMode orderingMode = FindMultipleOption.OrderingMode.ORDERED;

	public NaturalIdMultiLoadAccessStandard(EntityPersister entityDescriptor, SharedSessionContractImplementor session) {
		this.entityDescriptor = entityDescriptor;
		this.session = session;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NaturalIdMultiLoadAccess<T> with(LockMode lockMode, PessimisticLockScope lockScope) {
		if ( lockOptions == null ) {
			lockOptions = new LockOptions();
		}
		lockOptions.setLockMode( lockMode );
		lockOptions.setLockScope( lockScope );
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void with(PessimisticLockScope scope) {
		if ( lockOptions == null ) {
			lockOptions = new LockOptions();
		}
		lockOptions.setScope( scope );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NaturalIdMultiLoadAccess<T> with(Timeout timeout) {
		if ( lockOptions == null ) {
			lockOptions = new LockOptions();
		}
		lockOptions.setTimeOut( timeout.milliseconds() );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NaturalIdMultiLoadAccess<T> with(LockOptions lockOptions) {
		this.lockOptions = lockOptions;
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NaturalIdMultiLoadAccess<T> with(CacheMode cacheMode) {
		this.cacheMode = cacheMode;
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NaturalIdMultiLoadAccess<T> with(EntityGraph<T> graph, GraphSemantic semantic) {
		this.rootGraph = (RootGraphImplementor<T>) graph;
		this.graphSemantic = semantic;
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NaturalIdMultiLoadAccess<T> withBatchSize(int batchSize) {
		this.batchSize = batchSize;
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NaturalIdMultiLoadAccess<T> enableReturnOfDeletedEntities(boolean enabled) {
		this.removalsMode = enabled ? FindMultipleOption.RemovalsMode.INCLUDE : FindMultipleOption.RemovalsMode.REPLACE;
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void with(FindMultipleOption.RemovalsMode removalsMode) {
		this.removalsMode = removalsMode;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NaturalIdMultiLoadAccess<T> enableOrderedReturn(boolean enabled) {
		this.orderingMode = enabled ? FindMultipleOption.OrderingMode.ORDERED : FindMultipleOption.OrderingMode.UNORDERED;
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void with(FindMultipleOption.OrderingMode orderingMode) {
		this.orderingMode = orderingMode;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<T> multiLoad(Object... ids) {
		return buildOperation()
				.performFind( List.of( ids ), graphSemantic, rootGraph );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<T> multiLoad(List<?> ids) {
		return buildOperation()
				.performFind( ids, graphSemantic, rootGraph );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private StatefulFindMultipleByKeyOperation<T> buildOperation() {
		return new StatefulFindMultipleByKeyOperation<T>(
				entityDescriptor,
				(StatefulLoadAccessContext) session,
				KeyType.NATURAL,
				batchSize == null ? null : new BatchSize( batchSize ),
				FindMultipleOption.SessionCheckMode.ENABLED,
				removalsMode,
				orderingMode,
				cacheMode,
				lockOptions,
				session.isDefaultReadOnly() ? ReadOnlyMode.READ_ONLY : ReadOnlyMode.READ_WRITE,
				null,
				null,
				NaturalIdSynchronization.ENABLED
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FindMultipleOption.RemovalsMode getRemovalsMode() {
		return removalsMode;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FindMultipleOption.OrderingMode getOrderingMode() {
		return orderingMode;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public LockOptions getLockOptions() {
		return lockOptions;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getBatchSize() {
		return batchSize;
	}
}
