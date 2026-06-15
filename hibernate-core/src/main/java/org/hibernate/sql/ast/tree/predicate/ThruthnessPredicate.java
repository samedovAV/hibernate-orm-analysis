/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.predicate;

import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.expression.Expression;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Gavin King
 */
public class ThruthnessPredicate extends AbstractPredicate {
	private final Expression expression;
	private final boolean value;

	public ThruthnessPredicate(Expression expression, boolean value, boolean negated, JdbcMappingContainer expressionType) {
		super( expressionType, negated );
		this.expression = expression;
		this.value = value;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean getBooleanValue() {
		return value;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getExpression() {
		return expression;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitThruthnessPredicate( this );
	}
}
