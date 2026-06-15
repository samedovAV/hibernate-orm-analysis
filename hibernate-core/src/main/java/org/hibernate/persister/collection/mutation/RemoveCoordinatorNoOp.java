/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.persister.collection.mutation;

import org.hibernate.action.queue.spi.decompose.collection.CollectionMutationTarget;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class RemoveCoordinatorNoOp implements RemoveCoordinator {
	private final CollectionMutationTarget mutationTarget;

	public RemoveCoordinatorNoOp(CollectionMutationTarget mutationTarget) {
		this.mutationTarget = mutationTarget;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "RemoveCoordinator(" + mutationTarget.getRolePath() + " [DISABLED])";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CollectionMutationTarget getMutationTarget() {
		return mutationTarget;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSqlString() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void deleteAllRows(Object key, SharedSessionContractImplementor session) {
		// nothing to do
	}
}
