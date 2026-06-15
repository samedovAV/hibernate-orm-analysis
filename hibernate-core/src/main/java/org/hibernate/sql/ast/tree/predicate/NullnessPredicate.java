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
 * @author Steve Ebersole
 */
public class NullnessPredicate extends AbstractPredicate {
	private final Expression expression;

	public NullnessPredicate(Expression expression) {
		this( expression, false, null );
	}

	public NullnessPredicate(Expression expression, boolean negated) {
		this( expression, negated, null );
	}

	public NullnessPredicate(Expression expression, boolean negated, JdbcMappingContainer expressionType) {
		super( expressionType, negated );
		this.expression = expression;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getExpression() {
		return expression;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitNullnessPredicate( this );
	}
}
