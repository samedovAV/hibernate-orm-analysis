/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.predicate;

import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public abstract class AbstractPredicate implements Predicate {
	private final JdbcMappingContainer expressionType;
	private final boolean negated;

	public AbstractPredicate(JdbcMappingContainer expressionType) {
		this( expressionType, false );
	}

	public AbstractPredicate(JdbcMappingContainer expressionType, boolean negated) {
		this.expressionType = expressionType;
		this.negated = negated;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isNegated() {
		return negated;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isEmpty() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMappingContainer getExpressionType() {
		return expressionType;
	}
}
