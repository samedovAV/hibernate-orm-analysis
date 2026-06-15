/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.graph.spi;

import org.hibernate.graph.GraphSemantic;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contract for anything a fetch/load graph can be applied
 *
 * @author Steve Ebersole
 */
public interface AppliedGraph {
	/**
	 * The applied graph
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	RootGraphImplementor<?> getGraph();

	/**
	 * The semantic (fetch/load) under which the graph should be applied
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	GraphSemantic getSemantic();
}
