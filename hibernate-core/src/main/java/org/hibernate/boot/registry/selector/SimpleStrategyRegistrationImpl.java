/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.registry.selector;

import java.util.Arrays;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A simple implementation of StrategyRegistration.
 *
 * @param <T> The strategy type.
 *
 * @author Steve Ebersole
 */
public class SimpleStrategyRegistrationImpl<T> implements StrategyRegistration<T> {
	private final Class<T> strategyRole;
	private final Class<? extends T> strategyImplementation;
	private final Iterable<String> selectorNames;

	/**
	 * Constructs a SimpleStrategyRegistrationImpl.
	 *
	 * @param strategyRole The strategy contract
	 * @param strategyImplementation The strategy implementation class
	 * @param selectorNames The selection/registration names for this implementation
	 */
	public SimpleStrategyRegistrationImpl(
			Class<T> strategyRole,
			Class<? extends T> strategyImplementation,
			Iterable<String> selectorNames) {
		this.strategyRole = strategyRole;
		this.strategyImplementation = strategyImplementation;
		this.selectorNames = selectorNames;
	}

	/**
	 * Constructs a SimpleStrategyRegistrationImpl.
	 *
	 * @param strategyRole The strategy contract
	 * @param strategyImplementation The strategy implementation class
	 * @param selectorNames The selection/registration names for this implementation
	 */
	public SimpleStrategyRegistrationImpl(
			Class<T> strategyRole,
			Class<? extends T> strategyImplementation,
			String... selectorNames) {
		this( strategyRole, strategyImplementation, Arrays.asList( selectorNames ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<T> getStrategyRole() {
		return strategyRole;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Iterable<String> getSelectorNames() {
		return selectorNames;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends T> getStrategyImplementation() {
		return strategyImplementation;
	}
}
