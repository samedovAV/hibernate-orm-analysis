/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.action.queue.internal.plan;

import org.hibernate.action.queue.spi.plan.FlushOperation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Output of [FlushPlanner] containing
///
/// @author Steve Ebersole
public class FlushPlan {
	private final List<PlanStep> steps;

	private final ArrayDeque<FlushOperation> fixups = new ArrayDeque<>();

	public FlushPlan(List<PlanStep> steps) {
		this.steps = List.copyOf(steps);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<PlanStep> steps() { return steps; }

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void enqueueFixup(FlushOperation fixup) {
		fixups.addLast(fixup);
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public List<FlushOperation> drainFixupsInOrder() {
		final ArrayList<FlushOperation> out = new ArrayList<>(fixups.size());
		while (!fixups.isEmpty()) {
			out.add(fixups.removeFirst());
		}
		return out;
	}

}
