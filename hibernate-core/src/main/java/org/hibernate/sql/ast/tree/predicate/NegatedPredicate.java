/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.predicate;

import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.sql.ast.SqlAstWalker;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class NegatedPredicate implements Predicate {
	private final Predicate predicate;

	public NegatedPredicate(Predicate predicate) {
		this.predicate = predicate;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Predicate getPredicate() {
		return predicate;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isEmpty() {
		return predicate.isEmpty();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitNegatedPredicate( this );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JdbcMappingContainer getExpressionType() {
		return predicate.getExpressionType();
	}
}
