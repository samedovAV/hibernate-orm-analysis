/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.cfgxml.internal;

import java.util.Map;

import jakarta.annotation.Nonnull;
import org.hibernate.boot.cfgxml.spi.CfgXmlAccessService;
import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class CfgXmlAccessServiceInitiator implements StandardServiceInitiator<CfgXmlAccessService> {
	/**
	 * Singleton access
	 */
	public static final CfgXmlAccessServiceInitiator INSTANCE = new CfgXmlAccessServiceInitiator();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CfgXmlAccessService initiateService(@Nonnull Map<String, Object> configurationValues, @Nonnull ServiceRegistryImplementor registry) {
		return new CfgXmlAccessServiceImpl( configurationValues );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<CfgXmlAccessService> getServiceInitiated() {
		return CfgXmlAccessService.class;
	}
}
