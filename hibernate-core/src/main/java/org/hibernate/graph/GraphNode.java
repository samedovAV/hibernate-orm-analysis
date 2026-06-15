/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.graph;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Common operations of {@link AttributeNode} and {@link Graph}.
 *
 * @author Steve Ebersole
 *
 * @see AttributeNode
 * @see Graph
 */
public interface GraphNode<J> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isMutable();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	GraphNode<J> makeCopy(boolean mutable);
}
