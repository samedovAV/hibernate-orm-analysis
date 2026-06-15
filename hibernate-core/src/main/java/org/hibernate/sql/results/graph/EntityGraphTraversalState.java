/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph;

import org.hibernate.Incubating;
import org.hibernate.engine.FetchTiming;
import org.hibernate.graph.spi.GraphImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * State used as part of applying entity graphs to
 * Hibernate {@link DomainResult} / {@link Fetch} load graphs.
 *
 * @author Nathan Xu
 */
@Incubating
public interface EntityGraphTraversalState {

	/**
	 * Details of a particular traversal within the entity graph
	 */
	class TraversalResult {
		private final GraphImplementor<?> previousContext;
		private final FetchStrategy fetchStrategy;
		private final org.hibernate.engine.spi.FetchOptions fetchOptions;

		public TraversalResult(GraphImplementor<?> previousContext, FetchStrategy fetchStrategy) {
			this( previousContext, fetchStrategy, org.hibernate.engine.spi.FetchOptions.NONE );
		}

		public TraversalResult(
				GraphImplementor<?> previousContext,
				FetchStrategy fetchStrategy,
				org.hibernate.engine.spi.FetchOptions fetchOptions) {
			this.previousContext = previousContext;
			this.fetchStrategy = fetchStrategy;
			this.fetchOptions = fetchOptions;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public GraphImplementor<?> getGraph() {
			return previousContext;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public FetchStrategy getFetchStrategy() {
			return fetchStrategy;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public org.hibernate.engine.spi.FetchOptions getFetchOptions() {
			return fetchOptions;
		}
	}

	class FetchStrategy {
		private final FetchTiming fetchTiming;
		private final boolean joined;

		public FetchStrategy(FetchTiming fetchTiming, boolean joined) {
			assert fetchTiming != null;
			this.fetchTiming = fetchTiming;
			this.joined = joined;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public FetchTiming getFetchTiming() {
			return fetchTiming;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean isJoined() {
			return joined;
		}
	}

	/**
	 * Traverses to the next part of the Jakarta Persistence entity graph relating to
	 * the given {@link Fetchable}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TraversalResult traverse(FetchParent parent, Fetchable fetchable, boolean exploreKeySubgraph);

	/**
	 * Backtrack to previous entity graph status before last traversal.
	 * Mainly reset the current context entity graph node to the passed method parameter.
	 *
	 * @param previousContext The previous entity graph context node; should not be null
	 * @see #traverse(FetchParent, Fetchable, boolean)
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void backtrack(TraversalResult previousContext);
}
