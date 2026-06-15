/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi.support;

import org.hibernate.cache.cfg.spi.NaturalIdDataCachingConfig;
import org.hibernate.cache.spi.CacheKeysFactory;
import org.hibernate.cache.spi.DomainDataRegion;
import org.hibernate.cache.spi.access.AccessType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class NaturalIdTransactionalAccess extends AbstractNaturalIdDataAccess {
	public NaturalIdTransactionalAccess(
			DomainDataRegion region,
			CacheKeysFactory keysFactory,
			DomainDataStorageAccess storageAccess,
			NaturalIdDataCachingConfig config) {
		super( region, keysFactory, storageAccess, config );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AccessType getAccessType() {
		return AccessType.TRANSACTIONAL;
	}
}
