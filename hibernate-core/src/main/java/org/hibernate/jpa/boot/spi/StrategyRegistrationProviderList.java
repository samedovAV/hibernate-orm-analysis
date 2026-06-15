/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.jpa.boot.spi;

import java.util.List;

import org.hibernate.boot.registry.selector.StrategyRegistrationProvider;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An object that provides a list of {@link StrategyRegistrationProvider}s to the JPA persistence provider.
 * <p>
 * An implementation may be registered with the JPA provider using the property
 * {@value org.hibernate.jpa.boot.spi.JpaSettings#STRATEGY_REGISTRATION_PROVIDERS}.
 *
 * @author Brett Meyer
 */
// TODO: Not a fan of this name or entry point into EMFBuilderImpl
public interface StrategyRegistrationProviderList {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<StrategyRegistrationProvider> getStrategyRegistrationProviders();
}
