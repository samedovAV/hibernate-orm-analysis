/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model;

import org.hibernate.generator.values.GeneratedValuesMutationDelegate;
import org.hibernate.persister.entity.mutation.EntityMutationTarget;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public interface EntityMutationOperationGroup extends MutationOperationGroup {

	/**
	 * The model-part being mutated.
	 * N.B. it returns a widened type compared to the same method in the super interface.
	 */
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityMutationTarget getMutationTarget();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default EntityMutationOperationGroup asEntityMutationOperationGroup() {
		return this;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default GeneratedValuesMutationDelegate getMutationDelegate() {
		return getMutationTarget().getMutationDelegate( getMutationType() );
	}
}
