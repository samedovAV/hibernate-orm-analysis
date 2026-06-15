/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.spi;

import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Provides access to the values for a managed type (currently just embeddables).
 *
 * @see EmbeddableInstantiator
 *
 * @author Christian Beikov
 */
@Incubating
public interface ValueAccess {
	/**
	 * The complete set of values.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object[] getValues();

	/**
	 * Access to an individual value.
	 *
	 * @apiNote It is important to remember that attributes are
	 * sorted alphabetically.  So the values here will be in alphabetically
	 * order according to the names of the corresponding attribute
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <T> T getValue(int i, Class<T> clazz) {
		return clazz.cast( getValues()[i] );
	}

	/**
	 * Access to the owner of the instance being instantiated
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Object getOwner() {
		return null;
	}
}
