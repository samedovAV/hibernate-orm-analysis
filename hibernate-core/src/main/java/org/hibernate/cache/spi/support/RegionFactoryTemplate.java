/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi.support;

import org.hibernate.cache.cfg.spi.DomainDataRegionBuildingContext;
import org.hibernate.cache.cfg.spi.DomainDataRegionConfig;
import org.hibernate.cache.internal.DefaultCacheKeysFactory;
import org.hibernate.cache.spi.AbstractRegionFactory;
import org.hibernate.cache.spi.CacheKeysFactory;
import org.hibernate.cache.spi.DomainDataRegion;
import org.hibernate.cache.spi.QueryResultsRegion;
import org.hibernate.cache.spi.TimestampsRegion;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class RegionFactoryTemplate extends AbstractRegionFactory {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DomainDataRegion buildDomainDataRegion(
			DomainDataRegionConfig regionConfig,
			DomainDataRegionBuildingContext buildingContext) {
		verifyStarted();
		return new DomainDataRegionTemplate(
				regionConfig,
				this,
				createDomainDataStorageAccess( regionConfig, buildingContext ),
				getImplicitCacheKeysFactory(),
				buildingContext
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected CacheKeysFactory getImplicitCacheKeysFactory() {
		return DefaultCacheKeysFactory.INSTANCE;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected DomainDataStorageAccess createDomainDataStorageAccess(
			DomainDataRegionConfig regionConfig,
			DomainDataRegionBuildingContext buildingContext) {
		throw new UnsupportedOperationException( "Not implemented by caching provider" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QueryResultsRegion buildQueryResultsRegion(
			String regionName,
			SessionFactoryImplementor sessionFactory) {
		verifyStarted();
		return new QueryResultsRegionTemplate(
				regionName,
				this,
				createQueryResultsRegionStorageAccess( regionName, sessionFactory )
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract StorageAccess createQueryResultsRegionStorageAccess(
			String regionName,
			SessionFactoryImplementor sessionFactory);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TimestampsRegion buildTimestampsRegion(
			String regionName, SessionFactoryImplementor sessionFactory) {
		verifyStarted();
		return new TimestampsRegionTemplate(
				regionName,
				this,
				createTimestampsRegionStorageAccess( regionName, sessionFactory )
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract StorageAccess createTimestampsRegionStorageAccess(
			String regionName,
			SessionFactoryImplementor sessionFactory);
}
