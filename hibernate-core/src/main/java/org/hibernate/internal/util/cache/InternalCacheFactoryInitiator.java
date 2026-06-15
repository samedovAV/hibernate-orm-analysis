/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.util.cache;

import jakarta.annotation.Nonnull;
import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class InternalCacheFactoryInitiator implements StandardServiceInitiator<InternalCacheFactory> {

	/**
	 * Singleton access
	 */
	public static final InternalCacheFactoryInitiator INSTANCE = new InternalCacheFactoryInitiator();

	private InternalCacheFactoryInitiator() {}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public InternalCacheFactory initiateService(@Nonnull Map<String, Object> configurationValues, @Nonnull ServiceRegistryImplementor registry) {
		return new InternalCacheFactoryImpl();
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<InternalCacheFactory> getServiceInitiated() {
		return InternalCacheFactory.class;
	}
}
