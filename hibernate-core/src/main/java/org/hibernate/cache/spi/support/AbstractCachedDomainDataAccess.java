/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi.support;

import org.hibernate.Internal;
import org.hibernate.cache.spi.DomainDataRegion;
import org.hibernate.cache.spi.access.CachedDomainDataAccess;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.engine.spi.SharedSessionContractImplementor;


import static org.hibernate.cache.spi.SecondLevelCacheLogger.L2CACHE_LOGGER;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractCachedDomainDataAccess implements CachedDomainDataAccess, AbstractDomainDataRegion.Destructible {

	private final DomainDataRegion region;
	private final DomainDataStorageAccess storageAccess;

	protected AbstractCachedDomainDataAccess(
			DomainDataRegion region,
			DomainDataStorageAccess storageAccess) {
		this.region = region;
		this.storageAccess = storageAccess;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DomainDataRegion getRegion() {
		return region;
	}

	@Internal
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DomainDataStorageAccess getStorageAccess() {
		return storageAccess;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void clearCache() {
		L2CACHE_LOGGER.clearingCacheDataMap( region.getName() );
		getStorageAccess().evictData();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean contains(Object key) {
		return getStorageAccess().contains( key );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object get(SharedSessionContractImplementor session, Object key) {
		final boolean traceEnabled = L2CACHE_LOGGER.isTraceEnabled();
		if ( traceEnabled ) {
			L2CACHE_LOGGER.gettingCachedData( region.getName(), getAccessType(), key );
		}
		final Object item = getStorageAccess().getFromCache( key, session );
		if ( traceEnabled ) {
			if ( item == null ) {
				L2CACHE_LOGGER.cacheMiss( region.getName(), key );
			}
			else {
				L2CACHE_LOGGER.cacheHit( region.getName(), key );
			}
		}
		return item;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean putFromLoad(
			SharedSessionContractImplementor session,
			Object key,
			Object value,
			Object version) {
		if ( L2CACHE_LOGGER.isTraceEnabled() ) {
			L2CACHE_LOGGER.cachingDataFromLoad( region.getName(), getAccessType(), key, value );
		}
		getStorageAccess().putFromLoad( key, value, session );
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean putFromLoad(
			SharedSessionContractImplementor session,
			Object key,
			Object value,
			Object version,
			boolean minimalPutOverride) {
		if ( minimalPutOverride && getStorageAccess().contains( key ) ) {
			if ( L2CACHE_LOGGER.isTraceEnabled() ) {
				L2CACHE_LOGGER.cachePutFromLoadSkippedDueToMinimalPut( region.getName(), getAccessType(), key );
			}
			return false;
		}
		else {
			return putFromLoad( session, key, value, version );
		}
	}

	private static final SoftLock REGION_LOCK = new SoftLock() {
	};

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SoftLock lockRegion() {
		return REGION_LOCK;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void unlockRegion(SoftLock lock) {
		evictAll();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void remove(SharedSessionContractImplementor session, Object key) {
		getStorageAccess().removeFromCache( key, session );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void removeAll(SharedSessionContractImplementor session) {
		getStorageAccess().clearCache( session );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void evict(Object key) {
		getStorageAccess().evictData( key );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void evictAll() {
		getStorageAccess().evictData();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void destroy() {
		getStorageAccess().release();
	}
}
