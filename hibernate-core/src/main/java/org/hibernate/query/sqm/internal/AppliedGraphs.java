/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.internal;

import jakarta.persistence.FetchType;
import org.hibernate.graph.spi.GraphImplementor;
import org.hibernate.query.spi.QueryOptions;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Réda Housni Alaoui
 */
public class AppliedGraphs {

	private AppliedGraphs() {
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static boolean containsCollectionFetches(QueryOptions queryOptions) {
		final var appliedGraph = queryOptions.getAppliedGraph();
		return appliedGraph != null
			&& appliedGraph.getGraph() != null
			&& containsCollectionFetches( appliedGraph.getGraph() );
	}

	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	private static boolean containsCollectionFetches(GraphImplementor<?> graph) {
		for ( var node : graph.getNodes().values() ) {
			if ( node.getFetchType() == FetchType.EAGER ) {
				if ( node.getAttributeDescriptor().isCollection() ) {
					return true;
				}
				for ( var subgraph : node.getSubGraphs().values() ) {
					if ( containsCollectionFetches( subgraph ) ) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
