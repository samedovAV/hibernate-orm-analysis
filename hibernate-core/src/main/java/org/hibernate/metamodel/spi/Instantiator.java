/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.spi;

import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Strategy for instantiating a managed type
 *
 * @author Steve Ebersole
 */
@Incubating
public interface Instantiator {
	/**
	 * Performs and "instance of" check to see if the given object is an
	 * instance of managed structure
	 * @see Class#isInstance
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isInstance(Object object);

	/**
	 * @see Class#equals
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isSameClass(Object object);
}
