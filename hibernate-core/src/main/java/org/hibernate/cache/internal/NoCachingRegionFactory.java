/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.internal;

import java.util.Map;

import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.NoCacheRegionFactoryAvailableException;
import org.hibernate.cache.cfg.spi.DomainDataRegionBuildingContext;
import org.hibernate.cache.cfg.spi.DomainDataRegionConfig;
import org.hibernate.cache.spi.CacheTransactionSynchronization;
import org.hibernate.cache.spi.DomainDataRegion;
import org.hibernate.cache.spi.QueryResultsRegion;
import org.hibernate.cache.spi.RegionFactory;
import org.hibernate.cache.spi.TimestampsRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Factory used if no caching enabled in config...
 *
 * @author Steve Ebersole
 */
public class NoCachingRegionFactory implements RegionFactory {
	/**
	 * Singleton access
	 */
	public static final NoCachingRegionFactory INSTANCE = new NoCachingRegionFactory();

	/**
	 * Constructs a NoCachingRegionFactory.  Although access should generally use {@link #INSTANCE}
	 */
	public NoCachingRegionFactory() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void start(SessionFactoryOptions settings, Map<String,Object> configValues) throws CacheException {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void stop() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String qualify(String regionName) {
		return regionName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isMinimalPutsEnabledByDefault() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AccessType getDefaultAccessType() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public long nextTimestamp() {
		return System.currentTimeMillis();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CacheTransactionSynchronization createTransactionContext(SharedSessionContractImplementor session) {
		return NoCachingTransactionSynchronizationImpl.INSTANCE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DomainDataRegion buildDomainDataRegion(
			DomainDataRegionConfig regionConfig, DomainDataRegionBuildingContext buildingContext) {
		throw new NoCacheRegionFactoryAvailableException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QueryResultsRegion buildQueryResultsRegion(
			String regionName, SessionFactoryImplementor sessionFactory) {
		throw new NoCacheRegionFactoryAvailableException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TimestampsRegion buildTimestampsRegion(
			String regionName, SessionFactoryImplementor sessionFactory) {
		throw new NoCacheRegionFactoryAvailableException();
	}
}
