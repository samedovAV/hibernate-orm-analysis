/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.internal;

import org.hibernate.SessionFactory;
import org.hibernate.SessionFactoryObserver;
import org.hibernate.engine.jndi.spi.JndiService;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.SessionFactoryRegistry;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Responsible for registering and de-registering the {@link SessionFactory}
 * with the {@link SessionFactoryRegistry}.
 *
 * @implNote This was added in order to clean up the constructor of
 *           {@link org.hibernate.internal.SessionFactoryImpl}, which
 *           was doing too many things.
 *
 * @author Gavin King
 */
class SessionFactoryObserverForRegistration implements SessionFactoryObserver {
	private JndiService jndiService;

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void sessionFactoryCreated(SessionFactory factory) {
		final var sessionFactory = (SessionFactoryImplementor) factory;
		jndiService = sessionFactory.getServiceRegistry().getService( JndiService.class );
		SessionFactoryRegistry.INSTANCE.addSessionFactory(
				sessionFactory.getUuid(),
				sessionFactory.getName(),
				sessionFactory.getJndiName(),
				sessionFactory,
				jndiService
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void sessionFactoryClosed(SessionFactory factory) {
		final var sessionFactory = (SessionFactoryImplementor) factory;
		SessionFactoryRegistry.INSTANCE.removeSessionFactory(
				sessionFactory.getUuid(),
				sessionFactory.getName(),
				sessionFactory.getJndiName(),
				jndiService
		);
	}
}
