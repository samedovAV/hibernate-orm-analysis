/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.internal;

import java.util.Map;

import jakarta.annotation.Nonnull;
import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Standard initiator for the standard {@link JdbcServices} service
 *
 * TODO : should this maybe be a SessionFactory service?
 *
 * @author Steve Ebersole
 */
public class JdbcServicesInitiator implements StandardServiceInitiator<JdbcServices> {
	/**
	 * Singleton access
	 */
	public static final JdbcServicesInitiator INSTANCE = new JdbcServicesInitiator();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<JdbcServices> getServiceInitiated() {
		return JdbcServices.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcServices initiateService(@Nonnull Map<String, Object> configurationValues, @Nonnull ServiceRegistryImplementor registry) {
		return new JdbcServicesImpl();
	}
}
