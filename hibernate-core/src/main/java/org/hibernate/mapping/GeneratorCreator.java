/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;

import org.hibernate.Internal;
import org.hibernate.generator.Assigned;
import org.hibernate.generator.Generator;
import org.hibernate.generator.GeneratorCreationContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Instantiates a {@link Generator}.
 *
 * @since 6.2
 *
 * @author Gavin King
 */
@Internal
@FunctionalInterface
public interface GeneratorCreator {
	/**
	 * Create the generator.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Generator createGenerator(GeneratorCreationContext context);

	/**
	 * Does this object create instances of {@link Assigned}?
	 *
	 * @since 7.0
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isAssigned() {
		return false;
	}
}
