/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.entity;

import java.util.BitSet;

import org.hibernate.sql.results.graph.Fetch;
import org.hibernate.sql.results.graph.FetchParent;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Specialization of Fetch for entity-valued fetches
 *
 * @author Steve Ebersole
 */
public interface EntityFetch extends EntityResultGraphNode, Fetch {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean containsAnyNonScalarResults() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default FetchParent asFetchParent() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void collectValueIndexesToCache(BitSet valueIndexes) {
		EntityResultGraphNode.super.collectValueIndexesToCache( valueIndexes );
	}
}
