/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.transaction.jta.platform.internal;

import java.util.Map;

import jakarta.annotation.Nonnull;
import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.boot.registry.selector.spi.StrategySelector;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.transaction.jta.platform.spi.JtaPlatformResolver;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import org.jboss.logging.Logger;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class JtaPlatformResolverInitiator implements StandardServiceInitiator<JtaPlatformResolver> {
	public static final JtaPlatformResolverInitiator INSTANCE = new JtaPlatformResolverInitiator();

	private static final Logger LOG = Logger.getLogger( JtaPlatformResolverInitiator.class );

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JtaPlatformResolver initiateService(@Nonnull Map<String, Object> configurationValues, @Nonnull ServiceRegistryImplementor registry) {
		final Object setting = configurationValues.get( AvailableSettings.JTA_PLATFORM_RESOLVER );
		final JtaPlatformResolver resolver =
				registry.requireService( StrategySelector.class )
						.resolveStrategy( JtaPlatformResolver.class, setting );
		if ( resolver == null ) {
			LOG.tracef( "No JtaPlatformResolver was specified, using default [%s]", StandardJtaPlatformResolver.class.getName() );
			return StandardJtaPlatformResolver.INSTANCE;
		}
		return resolver;
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<JtaPlatformResolver> getServiceInitiated() {
		return JtaPlatformResolver.class;
	}
}
