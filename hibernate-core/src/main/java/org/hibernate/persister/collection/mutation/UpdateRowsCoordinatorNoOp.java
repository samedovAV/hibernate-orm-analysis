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
 * @author Steve Ebersole
 */
public class UpdateRowsCoordinatorNoOp implements UpdateRowsCoordinator {
	private final CollectionMutationTarget mutationTarget;

	public UpdateRowsCoordinatorNoOp(CollectionMutationTarget mutationTarget) {
		this.mutationTarget = mutationTarget;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "UpdateRowsCoordinator(" + mutationTarget.getRolePath() + " (no-op))";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CollectionMutationTarget getMutationTarget() {
		return mutationTarget;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void updateRows(Object key, PersistentCollection<?> collection, SharedSessionContractImplementor session) {
		// nothing to do
	}

}
