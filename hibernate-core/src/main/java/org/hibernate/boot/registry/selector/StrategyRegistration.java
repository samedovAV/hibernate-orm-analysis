/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.registry.selector;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Describes the registration of a named strategy implementation.
 * <p>
 * A strategy + selector name should resolve to a single implementation.
 *
 * @param <T> The type of the strategy described by this implementation registration.
 *
 * @author Steve Ebersole
 */
public interface StrategyRegistration<T> {
	/**
	 * The strategy role. Best practice says this should be an interface.
	 *
	 * @return The strategy contract/role.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<T> getStrategyRole();

	/**
	 * Any registered names for this strategy registration.
	 *
	 * @return The registered selection names.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Iterable<String> getSelectorNames();

	/**
	 * The strategy implementation class.
	 *
	 * @return The strategy implementation.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<? extends T> getStrategyImplementation();
}
