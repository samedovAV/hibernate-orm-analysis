/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.jpa.boot.internal;

import org.hibernate.SessionFactory;
import org.hibernate.SessionFactoryObserver;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

class ServiceRegistryCloser implements SessionFactoryObserver {
	/**
	 * Singleton access
	 */
	public static final ServiceRegistryCloser INSTANCE = new ServiceRegistryCloser();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void sessionFactoryCreated(SessionFactory sessionFactory) {
		// nothing to do
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void sessionFactoryClosed(SessionFactory sessionFactory) {
		final var factoryImplementor = (SessionFactoryImplementor) sessionFactory;
		final var serviceRegistry = factoryImplementor.getServiceRegistry();
		serviceRegistry.destroy();
		final var basicRegistry =
				(ServiceRegistryImplementor)
						serviceRegistry.getParentServiceRegistry();
		if ( basicRegistry != null ) {
			basicRegistry.destroy();
		}
	}
}
