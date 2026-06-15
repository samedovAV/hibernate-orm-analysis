/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.action.queue.internal.plan;


import org.hibernate.action.queue.internal.constraint.DeferrableConstraintMode;
import org.hibernate.action.queue.internal.graph.Graph;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Creates an executable plan from the dependency graph.
/// Applies topological sort with cycle breaking.
/// The resulting FlushPlan defines ordered steps and cycle-break fixups.
///
/// See [CycleBreaker].
/// See [TopographicalSorter].
///
/// @author Steve Ebersole
public interface FlushPlanner {
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default FlushPlan plan(Graph graph) {
		return plan( graph, DeferrableConstraintMode.DEFAULT );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FlushPlan plan(Graph graph, DeferrableConstraintMode deferrableConstraintMode);
}
