/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.internal;

import java.util.Map;

import jakarta.annotation.Nonnull;
import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.hibernate.sql.ast.spi.ParameterMarkerStrategy;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class ParameterMarkerStrategyInitiator implements StandardServiceInitiator<ParameterMarkerStrategy> {
	/**
	 * Singleton access
	 */
	public static final ParameterMarkerStrategyInitiator INSTANCE = new ParameterMarkerStrategyInitiator();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ParameterMarkerStrategy initiateService(@Nonnull Map<String, Object> configurationValues, @Nonnull ServiceRegistryImplementor registry) {
		final boolean useNativeMarkers = ConfigurationHelper.getBoolean(
				AvailableSettings.DIALECT_NATIVE_PARAM_MARKERS,
				configurationValues
		);

		if ( useNativeMarkers ) {
			final Dialect dialect = registry.requireService( JdbcServices.class ).getDialect();
			final ParameterMarkerStrategy nativeParameterMarkerStrategy = dialect.getNativeParameterMarkerStrategy();
			// the Dialect may return `null`, indicating falling-through to the standard strategy
			if ( nativeParameterMarkerStrategy != null ) {
				return nativeParameterMarkerStrategy;
			}
		}

		return ParameterMarkerStrategyStandard.INSTANCE;
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<ParameterMarkerStrategy> getServiceInitiated() {
		return ParameterMarkerStrategy.class;
	}
}
