/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.persister.entity.mutation;

import org.hibernate.sql.model.MutationOperationGroup;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Coordinates the mutation operations of an entity.
 *
 * @see InsertCoordinator
 * @see DeleteCoordinator
 * @see UpdateCoordinator
 * @see MergeCoordinatorStandard
 *
 * @author Marco Belladelli
 */
public interface MutationCoordinator {
	/**
	 * The operation group used to perform the mutation unless some form
	 * of dynamic mutation is necessary.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationOperationGroup getStaticMutationOperationGroup();
}
