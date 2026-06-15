/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.property.access.internal;

import java.util.Map;

import jakarta.annotation.Nonnull;
import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.property.access.spi.PropertyAccessStrategyResolver;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class PropertyAccessStrategyResolverInitiator implements StandardServiceInitiator<PropertyAccessStrategyResolver> {
	/**
	 * Singleton access
	 */
	public static final PropertyAccessStrategyResolverInitiator INSTANCE = new PropertyAccessStrategyResolverInitiator();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<PropertyAccessStrategyResolver> getServiceInitiated() {
		return PropertyAccessStrategyResolver.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PropertyAccessStrategyResolver initiateService(@Nonnull Map<String, Object> configurationValues, @Nonnull ServiceRegistryImplementor registry) {
		return new PropertyAccessStrategyResolverStandardImpl( registry );
	}
}
