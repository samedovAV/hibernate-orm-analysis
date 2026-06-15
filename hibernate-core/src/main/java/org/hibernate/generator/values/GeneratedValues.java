/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.generator.values;

import java.util.List;

import org.hibernate.metamodel.mapping.ModelPart;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A container for {@linkplain org.hibernate.generator.OnExecutionGenerator database generated values}
 * retrieved by the mutation operation. The values are stored relative to the {@linkplain ModelPart property}
 * they represent.
 *
 * @author Marco Belladelli
 * @see org.hibernate.generator.OnExecutionGenerator
 */
public interface GeneratedValues {
	/**
	 * Register a generated value for the corresponding {@link ModelPart}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addGeneratedValue(ModelPart modelPart, Object value);

	/**
	 * Retrieve a generated value for the requested {@link ModelPart}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getGeneratedValue(ModelPart modelPart);

	/**
	 * Retrieves a list of generated values corresponding to the list of requested {@link ModelPart}s.
	 * Ensures the order of the values in the returned list corresponds to the input properties.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<Object> getGeneratedValues(List<? extends ModelPart> modelParts);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void apply(GeneratedValues generatedValues);
}
