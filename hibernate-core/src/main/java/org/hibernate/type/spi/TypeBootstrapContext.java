/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.spi;

import org.hibernate.service.ServiceRegistry;

import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Provide a way to customize the {@link org.hibernate.type.Type} instantiation process.
 * <p>
 * If a custom {@link org.hibernate.type.Type} defines a constructor which takes the
 * {@link TypeBootstrapContext} argument, Hibernate will use this instead of the
 * default constructor.
 *
 * @author Vlad Mihalcea
 *
 * @since 5.4
 */
public interface TypeBootstrapContext {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, Object> getConfigurationSettings();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ServiceRegistry getServiceRegistry();
}
