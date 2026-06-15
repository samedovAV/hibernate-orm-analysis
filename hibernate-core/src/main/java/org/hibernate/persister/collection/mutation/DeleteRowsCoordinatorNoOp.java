/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.persister.collection.mutation;

import org.hibernate.action.queue.spi.decompose.collection.CollectionMutationTarget;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * DeleteRowsCoordinator implementation for cases where deletion is not enabled
 *
 * @author Steve Ebersole
 */
public class DeleteRowsCoordinatorNoOp implements DeleteRowsCoordinator {
	private final CollectionMutationTarget mutationTarget;

	public DeleteRowsCoordinatorNoOp(CollectionMutationTarget mutationTarget) {
		this.mutationTarget = mutationTarget;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "DeleteRowsCoordinator(" + mutationTarget.getRolePath() + " [DISABLED])";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CollectionMutationTarget getMutationTarget() {
		return mutationTarget;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void deleteRows(PersistentCollection<?> collection, Object key, SharedSessionContractImplementor session) {
		// nothing to do
	}
}
