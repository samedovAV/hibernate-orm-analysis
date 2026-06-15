/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.predicate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.internal.util.collections.ArrayHelper;
import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.expression.Expression;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class InListPredicate extends AbstractPredicate {
	private final Expression testExpression;
	private final List<Expression> listExpressions;

	public InListPredicate(Expression testExpression) {
		this( testExpression, new ArrayList<>() );
	}

	public InListPredicate(Expression testExpression, boolean negated, JdbcMappingContainer expressionType) {
		this( testExpression, new ArrayList<>(), negated, expressionType );
	}

	public InListPredicate(Expression testExpression, Expression... listExpressions) {
		this( testExpression, ArrayHelper.toExpandableList( listExpressions ) );
	}

	public InListPredicate(
			Expression testExpression,
			List<Expression> listExpressions) {
		this( testExpression, listExpressions, false, null );
	}

	public InListPredicate(
			Expression testExpression,
			List<Expression> listExpressions,
			boolean negated,
			JdbcMappingContainer expressionType) {
		super( expressionType, negated );
		this.testExpression = testExpression;
		this.listExpressions = listExpressions;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getTestExpression() {
		return testExpression;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<Expression> getListExpressions() {
		return listExpressions;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addExpression(Expression expression) {
		listExpressions.add( expression );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitInListPredicate( this );
	}
}
