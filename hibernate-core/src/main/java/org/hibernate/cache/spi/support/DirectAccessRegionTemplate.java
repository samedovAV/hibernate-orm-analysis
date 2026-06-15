/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi.support;

import org.hibernate.cache.spi.DirectAccessRegion;
import org.hibernate.cache.spi.RegionFactory;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Bridge between DirectAccessRegion and StorageAccess
 *
 * @author Steve Ebersole
 */
public abstract class DirectAccessRegionTemplate extends AbstractRegion implements DirectAccessRegion {
	private final StorageAccess storageAccess;

	/**
	 * Constructs a {@link DirectAccessRegionTemplate}.
	 *
	 * @param name - the unqualified region name
	 * @param regionFactory - the region factory
	 * @param storageAccess - the cache storage access strategy
	 */
	public DirectAccessRegionTemplate(String name, RegionFactory regionFactory, StorageAccess storageAccess) {
		super( name, regionFactory );
		this.storageAccess = storageAccess;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public StorageAccess getStorageAccess() {
		return storageAccess;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object getFromCache(Object key, SharedSessionContractImplementor session) {
		return getStorageAccess().getFromCache( key, session );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void putIntoCache(Object key, Object value, SharedSessionContractImplementor session) {
		getStorageAccess().putIntoCache( key, value, session );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void clear() {
		getStorageAccess().evictData();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void destroy() {
		getStorageAccess().release();
	}

}
