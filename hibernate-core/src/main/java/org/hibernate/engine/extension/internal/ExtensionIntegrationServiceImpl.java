/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.extension.internal;

import jakarta.annotation.Nonnull;
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.hibernate.engine.extension.spi.ExtensionIntegration;
import org.hibernate.engine.extension.spi.ExtensionIntegrationService;
import org.jboss.logging.Logger;

import java.util.LinkedHashSet;
import java.util.Set;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class ExtensionIntegrationServiceImpl implements ExtensionIntegrationService {

	private static final Logger LOG = Logger.getLogger( ExtensionIntegrationServiceImpl.class );

	private final LinkedHashSet<ExtensionIntegration<?>> integrators = new LinkedHashSet<>();

	private ExtensionIntegrationServiceImpl() {
	}

	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static ExtensionIntegrationServiceImpl create(@Nonnull Set<ExtensionIntegration<?>> integrations, @Nonnull ClassLoaderService classLoaderService) {
		final var instance = new ExtensionIntegrationServiceImpl();
		// register provided integrators
		for ( var integration : integrations ) {
			instance.addExtensionIntegration( integration );
		}
		for ( var integration : classLoaderService.loadJavaServices( ExtensionIntegration.class ) ) {
			instance.addExtensionIntegration( integration );
		}
		return instance;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private void addExtensionIntegration(ExtensionIntegration<?> integration) {
		if ( LOG.isDebugEnabled() ) {
			LOG.debugf( "Adding extension integration for [%s]", integration.getExtensionType().getName() );
		}
		integrators.add( integration );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Iterable<ExtensionIntegration<?>> extensionIntegrations() {
		return integrators;
	}
}
