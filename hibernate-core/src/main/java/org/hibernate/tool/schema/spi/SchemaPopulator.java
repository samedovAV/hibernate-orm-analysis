/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.spi;

import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Service delegate for handling schema population.
 *
 * @author Gavin King
 *
 * @since 7.0
 */
@Incubating
public interface SchemaPopulator {
	/**
	 * Perform schema population to the indicated target(s).
	 *
	 * @param options Options for executing the creation
	 * @param targetDescriptor description of the target(s) for the creation commands
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void doPopulation(ExecutionOptions options, TargetDescriptor targetDescriptor);
}
