/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;



/**
 * Contract for instantiating entity values
 */
public interface EntityInstantiator extends Instantiator {
	/**
	 * Create an instance of managed entity
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object instantiate();

	/**
	 * Can this entity be instantiated?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean canBeInstantiated() {
		return true;
	}
}
