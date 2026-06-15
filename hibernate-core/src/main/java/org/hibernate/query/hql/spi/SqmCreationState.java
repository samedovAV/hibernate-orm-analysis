/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.hql.spi;

import org.hibernate.Incubating;
import org.hibernate.internal.util.collections.Stack;
import org.hibernate.query.sqm.spi.SqmCreationContext;
import org.hibernate.query.sqm.tree.cte.SqmCteStatement;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models the state pertaining to the creation of a single SQM.
 *
 * @author Steve Ebersole
 */
@Incubating
public interface SqmCreationState {
	/**
	 * Access to the context of the creation
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmCreationContext getCreationContext();

	/**
	 * What options should be applied to the creation
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmCreationOptions getCreationOptions();

	/**
	 * Access to the stack of current creation processing state.
	 *
	 * New items are pushed to this stack as we cross certain
	 * boundaries while creating the SQM.  Generally these boundaries
	 * are specific to top-level statements and sub-queries.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Stack<SqmCreationProcessingState> getProcessingStateStack();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqmCreationProcessingState getCurrentProcessingState() {
		return getProcessingStateStack().getCurrent();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmCteStatement<?> findCteStatement(String name);
}
