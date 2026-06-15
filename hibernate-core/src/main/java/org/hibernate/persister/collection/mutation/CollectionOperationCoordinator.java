/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.persister.collection.mutation;

import org.hibernate.action.queue.spi.decompose.collection.CollectionMutationTarget;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Base contract for coordination of collection mutation operations
 *
 * @author Steve Ebersole
 */
public interface CollectionOperationCoordinator {
	/**
	 * The collection being mutated
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CollectionMutationTarget getMutationTarget();
}
