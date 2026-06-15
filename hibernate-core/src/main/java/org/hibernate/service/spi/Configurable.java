/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.service.spi;

import jakarta.annotation.Nonnull;

import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Allows the service to request access to the configuration properties for configuring itself.
 *
 * @author Steve Ebersole
 */
public interface Configurable {
	/**
	 * Configure the service.
	 *
	 * @param configurationValues The configuration properties.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void configure(@Nonnull Map<String, Object> configurationValues);
}
