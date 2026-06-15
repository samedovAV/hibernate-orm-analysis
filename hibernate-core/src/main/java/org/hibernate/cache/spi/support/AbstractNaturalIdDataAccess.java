/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi.support;

import org.hibernate.cache.cfg.spi.NaturalIdDataCachingConfig;
import org.hibernate.cache.spi.CacheKeysFactory;
import org.hibernate.cache.spi.DomainDataRegion;
import org.hibernate.cache.spi.access.NaturalIdDataAccess;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.persister.entity.EntityPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractNaturalIdDataAccess extends AbstractCachedDomainDataAccess implements NaturalIdDataAccess {
	private final CacheKeysFactory keysFactory;

	public AbstractNaturalIdDataAccess(
			DomainDataRegion region,
			CacheKeysFactory keysFactory,
			DomainDataStorageAccess storageAccess,
			NaturalIdDataCachingConfig config) {
		super( region, storageAccess );
		this.keysFactory = keysFactory;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object generateCacheKey(
			Object naturalIdValues,
			EntityPersister persister,
			SharedSessionContractImplementor session) {
		return keysFactory.createNaturalIdKey( naturalIdValues, persister, session );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object getNaturalIdValues(Object cacheKey) {
		return keysFactory.getNaturalIdValues( cacheKey );
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean insert(SharedSessionContractImplementor session, Object key, Object value) {
		getStorageAccess().putIntoCache( key, value, session );
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean afterInsert(SharedSessionContractImplementor session, Object key, Object value) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean update(SharedSessionContractImplementor session, Object key, Object value) {
		getStorageAccess().putIntoCache( key, value, session );
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean afterUpdate(SharedSessionContractImplementor session, Object key, Object value, SoftLock lock) {
		return false;
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
