/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.action.queue.internal.exec;

import org.hibernate.action.queue.spi.plan.FlushOperation;

import org.hibernate.action.queue.spi.plan.FlushOperation;

import java.util.List;
import java.util.function.Consumer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// @author Steve Ebersole
public interface PlanStepExecutor {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void execute(
			List<FlushOperation> flushOperations,
			Consumer<Object> newlyManagedEntityConsumer,
			Consumer<FlushOperation> fixupOperationConsumer);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void finishUp();
}
