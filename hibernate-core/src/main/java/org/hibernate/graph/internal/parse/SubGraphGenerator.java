/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.graph.internal.parse;

import org.hibernate.graph.spi.AttributeNodeImplementor;
import org.hibernate.graph.spi.GraphParserEntityNameResolver;
import org.hibernate.graph.spi.SubGraphImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
@FunctionalInterface
public interface SubGraphGenerator {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SubGraphImplementor<?> createSubGraph(
			AttributeNodeImplementor<?,?,?> attributeNode,
			String subTypeName,
			GraphParserEntityNameResolver entityNameResolver);
}
