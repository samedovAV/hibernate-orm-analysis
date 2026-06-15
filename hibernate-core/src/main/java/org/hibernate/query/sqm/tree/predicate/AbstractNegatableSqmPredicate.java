/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.predicate;

import jakarta.annotation.Nullable;
import jakarta.annotation.Nonnull;
import org.hibernate.query.sqm.NodeBuilder;
import org.hibernate.query.sqm.SqmBindableType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 */
public abstract class AbstractNegatableSqmPredicate extends AbstractSqmPredicate implements SqmNegatablePredicate {
	private boolean negated;

	public AbstractNegatableSqmPredicate(NodeBuilder nodeBuilder) {
		this( false, nodeBuilder );
	}

	public AbstractNegatableSqmPredicate(boolean negated, NodeBuilder nodeBuilder) {
		this( nodeBuilder.getBooleanType(), negated, nodeBuilder );
	}

	public AbstractNegatableSqmPredicate(@Nullable SqmBindableType<Boolean> type, boolean negated, NodeBuilder nodeBuilder) {
		super( type, nodeBuilder );
		this.negated = negated;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isNegated() {
		return negated;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void negate() {
		negated = !negated;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract SqmNegatablePredicate createNegatedNode();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmNegatablePredicate not() {
		// in certain cases JPA required that this always return
		// a new instance.
		return createNegatedNode();
	}
}
