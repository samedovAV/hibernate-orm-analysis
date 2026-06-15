/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.predicate;

import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class InArrayPredicate extends AbstractPredicate {
	private final Expression testExpression;
	private final JdbcParameter arrayParameter;

	public InArrayPredicate(Expression testExpression, JdbcParameter arrayParameter, JdbcMappingContainer expressionType) {
		super( expressionType );
		this.testExpression = testExpression;
		this.arrayParameter = arrayParameter;
	}

	public InArrayPredicate(Expression testExpression, JdbcParameter arrayParameter) {
		this( testExpression, arrayParameter, null );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getTestExpression() {
		return testExpression;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcParameter getArrayParameter() {
		return arrayParameter;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitInArrayPredicate( this );
	}
}
