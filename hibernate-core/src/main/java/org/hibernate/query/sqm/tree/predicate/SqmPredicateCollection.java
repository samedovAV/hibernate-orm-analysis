/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.predicate;

import jakarta.annotation.Nullable;

import java.util.Collection;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A grouping of predicates, such as a where-clause, join restriction, ...
 *
 * @author Steve Ebersole
 */
public interface SqmPredicateCollection {
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate getPredicate();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setPredicate(@Nullable SqmPredicate predicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void applyPredicate(SqmPredicate predicate);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void applyPredicates(SqmPredicate... predicates);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void applyPredicates(Collection<SqmPredicate> predicates);
}
