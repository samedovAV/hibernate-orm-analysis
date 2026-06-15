/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.predicate;

import java.util.function.Consumer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class PredicateCollector implements Consumer<Predicate> {
	private Predicate predicate;

	public PredicateCollector() {
	}

	public PredicateCollector(Predicate predicate) {
		this.predicate = predicate;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applyPredicate(Predicate incomingPredicate) {
		this.predicate = Predicate.combinePredicates( this.predicate, incomingPredicate );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(Predicate predicate) {
		applyPredicate( predicate );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Predicate getPredicate() {
		return predicate;
	}
}
