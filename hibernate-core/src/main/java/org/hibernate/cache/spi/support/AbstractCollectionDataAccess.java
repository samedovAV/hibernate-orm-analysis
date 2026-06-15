/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi.support;

import jakarta.annotation.Nonnull;
import org.hibernate.cache.cfg.spi.CollectionDataCachingConfig;
import org.hibernate.cache.spi.CacheKeysFactory;
import org.hibernate.cache.spi.DomainDataRegion;
import org.hibernate.cache.spi.access.CollectionDataAccess;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.persister.collection.CollectionPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractCollectionDataAccess
		extends AbstractCachedDomainDataAccess
		implements CollectionDataAccess {

	private final CacheKeysFactory keysFactory;

	public AbstractCollectionDataAccess(
			DomainDataRegion region,
			CacheKeysFactory keysFactory,
			DomainDataStorageAccess storageAccess,
			CollectionDataCachingConfig config) {
		super( region, storageAccess );
		this.keysFactory = keysFactory;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object generateCacheKey(Object id, CollectionPersister persister, SessionFactoryImplementor factory, String tenantIdentifier) {
		return keysFactory.createCollectionKey( id, persister, factory, tenantIdentifier );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getCacheKeyId(Object cacheKey) {
		return keysFactory.getCollectionId( cacheKey );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SoftLock lockItem(SharedSessionContractImplementor session, Object key, Object version) {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void unlockItem(SharedSessionContractImplementor session, Object key, SoftLock lock) {

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
}
