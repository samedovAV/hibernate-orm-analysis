/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.loader.ast.internal;

import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.engine.spi.LoadQueryInfluencers;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.loader.ast.spi.EntityBatchLoader;
import org.hibernate.metamodel.mapping.EntityMappingType;

import static org.hibernate.loader.ast.internal.MultiKeyLoadHelper.hasSingleId;
import static org.hibernate.loader.ast.internal.MultiKeyLoadLogging.MULTI_KEY_LOAD_LOGGER;
import static org.hibernate.pretty.MessageHelper.infoString;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public abstract class AbstractEntityBatchLoader<T>
		extends SingleIdEntityLoaderSupport<T>
		implements EntityBatchLoader<T> {

	private final SingleIdEntityLoaderStandardImpl<T> singleIdLoader;

	public AbstractEntityBatchLoader(EntityMappingType entityDescriptor, LoadQueryInfluencers influencers) {
		super( entityDescriptor, influencers.getSessionFactory() );
		singleIdLoader = new SingleIdEntityLoaderStandardImpl<>( entityDescriptor, influencers );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract void initializeEntities(
			Object[] idsToInitialize,
			Object pkValue,
			Object entityInstance,
			LockOptions lockOptions,
			Boolean readOnly,
			SharedSessionContractImplementor session);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract Object[] resolveIdsToInitialize(Object id, SharedSessionContractImplementor session);

@Override
@Prove(complexity = Complexity.O_N, n = "", count = {})
	public final T load(
			Object id,
			Object entityInstance,
			LockOptions lockOptions,
			Boolean readOnly,
			SharedSessionContractImplementor session) {
		if ( MULTI_KEY_LOAD_LOGGER.isTraceEnabled() ) {
			MULTI_KEY_LOAD_LOGGER.batchFetchingEntity( infoString( getLoadable(), id ) );
		}

		final var ids = resolveIdsToInitialize( id, session );
		return load( id, ids, hasSingleId( ids ), entityInstance, lockOptions, readOnly, session );
	}

@Override
@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T load(
			Object id,
			Object entityInstance,
			LockOptions lockOptions,
			SharedSessionContractImplementor session) {
		if ( MULTI_KEY_LOAD_LOGGER.isTraceEnabled() ) {
			MULTI_KEY_LOAD_LOGGER.batchFetchingEntity( infoString( getLoadable(), id ) );
		}

		final Object[] ids = resolveIdsToInitialize( id, session );
		final boolean hasSingleId = hasSingleId( ids );
		final T entity = load( id, ids, hasSingleId, entityInstance, lockOptions, null, session );
		if ( hasSingleId ) {
			return entity;
		}
		else if ( Hibernate.isInitialized( entity ) ) {
			return entity;
		}
		else {
			return null;
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private T load(
			Object id,
			Object[] ids,
			boolean hasSingleId,
			Object entityInstance,
			LockOptions lockOptions,
			Boolean readOnly,
			SharedSessionContractImplementor session) {
		// We disable batching if lockMode != NONE
		if ( hasSingleId || lockOptions.getLockMode() != LockMode.NONE ) {
			return singleIdLoader.load( id, entityInstance, lockOptions, readOnly, session );
		}
		else {
			initializeEntities( ids, id, entityInstance, lockOptions, readOnly, session );
			final var entityKey = session.generateEntityKey( id, getLoadable().getEntityPersister() );
			//noinspection unchecked
			return (T) session.getPersistenceContext().getEntity( entityKey );
		}
	}
}
