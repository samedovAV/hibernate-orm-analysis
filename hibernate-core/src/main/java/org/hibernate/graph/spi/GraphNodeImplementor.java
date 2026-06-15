/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.graph.spi;

import jakarta.annotation.Nonnull;
import org.hibernate.graph.GraphNode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Integration version of the {@link GraphNode} contract
 *
 * @author Steve Ebersole
 * @author Strong Liu
 */
public interface GraphNodeImplementor<J> extends GraphNode<J> {
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	GraphNodeImplementor<J> makeCopy(boolean mutable);
}
