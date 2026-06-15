/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.predicate;

import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.query.sqm.ComparisonOperator;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.expression.Expression;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class ComparisonPredicate implements Predicate {
	private final Expression leftHandExpression;
	private final ComparisonOperator operator;
	private final Expression rightHandExpression;
	private final JdbcMappingContainer expressionType;

	public ComparisonPredicate(
			Expression leftHandExpression,
			ComparisonOperator operator,
			Expression rightHandExpression) {
		this( leftHandExpression, operator, rightHandExpression, null );
	}

	public ComparisonPredicate(
			Expression leftHandExpression,
			ComparisonOperator operator,
			Expression rightHandExpression,
			JdbcMappingContainer expressionType) {
		this.leftHandExpression = leftHandExpression;
		this.operator = operator;
		this.rightHandExpression = rightHandExpression;
		this.expressionType = expressionType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getLeftHandExpression() {
		return leftHandExpression;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getRightHandExpression() {
		return rightHandExpression;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ComparisonOperator getOperator() {
		return operator;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isEmpty() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitRelationalPredicate( this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMappingContainer getExpressionType() {
		return expressionType;
	}
}
