/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.spi;

import jakarta.annotation.Nonnull;
import org.hibernate.cache.internal.DisabledCaching;
import org.hibernate.cache.internal.EnabledCaching;
import org.hibernate.cache.internal.NoCachingRegionFactory;
import org.hibernate.cache.spi.CacheImplementor;
import org.hibernate.cache.spi.RegionFactory;
import org.hibernate.service.spi.SessionFactoryServiceInitiator;
import org.hibernate.service.spi.SessionFactoryServiceInitiatorContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Initiator for second level cache support
 *
 * @author Steve Ebersole
 * @author Strong Liu
 */
public class CacheInitiator implements SessionFactoryServiceInitiator<CacheImplementor> {
	public static final CacheInitiator INSTANCE = new CacheInitiator();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CacheImplementor initiateService(@Nonnull SessionFactoryServiceInitiatorContext context) {
		final var regionFactory = context.getServiceRegistry().requireService( RegionFactory.class );
		return regionFactory instanceof NoCachingRegionFactory
				? new DisabledCaching( context.getSessionFactory() )
				: new EnabledCaching( context.getSessionFactory() );
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<CacheImplementor> getServiceInitiated() {
		return CacheImplementor.class;
	}
}
