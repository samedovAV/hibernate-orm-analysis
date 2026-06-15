/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi.support;

import org.hibernate.cache.cfg.spi.CollectionDataCachingConfig;
import org.hibernate.cache.cfg.spi.DomainDataRegionBuildingContext;
import org.hibernate.cache.cfg.spi.DomainDataRegionConfig;
import org.hibernate.cache.cfg.spi.EntityDataCachingConfig;
import org.hibernate.cache.cfg.spi.NaturalIdDataCachingConfig;
import org.hibernate.cache.spi.CacheKeysFactory;
import org.hibernate.cache.spi.access.CollectionDataAccess;
import org.hibernate.cache.spi.access.EntityDataAccess;
import org.hibernate.cache.spi.access.NaturalIdDataAccess;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class DomainDataRegionImpl extends DomainDataRegionTemplate {

	public DomainDataRegionImpl(
			DomainDataRegionConfig regionConfig,
			RegionFactoryTemplate regionFactory,
			DomainDataStorageAccess domainDataStorageAccess,
			CacheKeysFactory defaultKeysFactory,
			DomainDataRegionBuildingContext buildingContext) {
		super(
				regionConfig,
				regionFactory,
				domainDataStorageAccess,
				defaultKeysFactory,
				buildingContext
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected EntityDataAccess generateTransactionalEntityDataAccess(EntityDataCachingConfig entityAccessConfig) {
		return new EntityTransactionalAccess(
				this,
				getEffectiveKeysFactory(),
				getCacheStorageAccess(),
				entityAccessConfig
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected NaturalIdDataAccess generateTransactionalNaturalIdDataAccess(NaturalIdDataCachingConfig accessConfig) {
		return new NaturalIdTransactionalAccess(
				this,
				getEffectiveKeysFactory(),
				getCacheStorageAccess(),
				accessConfig
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected CollectionDataAccess generateTransactionalCollectionDataAccess(CollectionDataCachingConfig accessConfig) {
		return new CollectionTransactionAccess(
				this,
				getEffectiveKeysFactory(),
				getCacheStorageAccess(),
				accessConfig
		);
	}
}
