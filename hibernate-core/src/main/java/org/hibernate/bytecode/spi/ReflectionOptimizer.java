/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Represents reflection optimization for a particular class.
 */
public interface ReflectionOptimizer {
	/**
	 * Retrieve the optimizer for calling an entity's constructor via reflection.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	InstantiationOptimizer getInstantiationOptimizer();

	/**
	 * Retrieve the optimizer for accessing the entity's persistent state.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AccessOptimizer getAccessOptimizer();

	/**
	 * Represents optimized entity instantiation.
	 */
	interface InstantiationOptimizer {
		/**
		 * Perform instantiation of an instance of the underlying class.
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		Object newInstance();
	}

	/**
	 * Represents optimized entity property access.
	 */
	interface AccessOptimizer {
		/**
		 * Get the name of all properties.
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		String[] getPropertyNames();

		/**
		 * Get the value of all properties from the given entity
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		Object[] getPropertyValues(Object object);

		/**
		 * Set all property values into an entity instance.
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void setPropertyValues(Object object, Object[] values);
	}
}
