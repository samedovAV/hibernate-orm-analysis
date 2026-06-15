/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi.support;

import jakarta.annotation.Nonnull;
import org.hibernate.cache.spi.CacheKeysFactory;
import org.hibernate.cache.spi.DomainDataRegion;
import org.hibernate.cache.spi.access.EntityDataAccess;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.persister.entity.EntityPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractEntityDataAccess
		extends AbstractCachedDomainDataAccess
		implements EntityDataAccess {

	private final CacheKeysFactory cacheKeysFactory;

	public AbstractEntityDataAccess(
			DomainDataRegion region,
			CacheKeysFactory cacheKeysFactory,
			DomainDataStorageAccess storageAccess) {
		super( region, storageAccess );
		this.cacheKeysFactory = cacheKeysFactory;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object generateCacheKey(
			Object id,
			EntityPersister rootEntityDescriptor,
			SessionFactoryImplementor factory,
			String tenantIdentifier) {
		return cacheKeysFactory.createEntityKey(
				id,
				rootEntityDescriptor,
				factory,
				tenantIdentifier
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getCacheKeyId(Object cacheKey) {
		return cacheKeysFactory.getEntityId( cacheKey );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SoftLock lockRegion() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void unlockRegion(SoftLock lock) {
		clearCache();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SoftLock lockItem(
			SharedSessionContractImplementor session,
			Object key,
			Object version) {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void unlockItem(
			SharedSessionContractImplementor session,
			Object key,
			SoftLock lock) {
	}
}
