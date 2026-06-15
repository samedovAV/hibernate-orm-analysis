/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.jdbc.internal;

import java.util.Map;

import jakarta.annotation.Nonnull;
import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.hibernate.sql.results.jdbc.spi.JdbcValuesMappingProducerProvider;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Initiator for {@link JdbcValuesMappingProducerProviderStandard}
 *
 * @author Steve Ebersole
 */
public class JdbcValuesMappingProducerProviderInitiator
		implements StandardServiceInitiator<JdbcValuesMappingProducerProvider> {
	/**
	 * Singleton access
	 */
	public static final JdbcValuesMappingProducerProviderInitiator INSTANCE = new JdbcValuesMappingProducerProviderInitiator();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcValuesMappingProducerProvider initiateService(
			@Nonnull Map<String, Object> configurationValues,
			@Nonnull ServiceRegistryImplementor registry) {
		return JdbcValuesMappingProducerProviderStandard.INSTANCE;
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<JdbcValuesMappingProducerProvider> getServiceInitiated() {
		return JdbcValuesMappingProducerProvider.class;
	}
}
