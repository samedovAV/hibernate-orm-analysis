/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.predicate;

import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.ast.tree.select.QueryPart;
import org.hibernate.sql.ast.tree.select.SelectStatement;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class InSubQueryPredicate extends AbstractPredicate {
	private final Expression testExpression;
	private final SelectStatement subQuery;

	public InSubQueryPredicate(Expression testExpression, QueryPart subQuery, boolean negated) {
		this( testExpression, new SelectStatement( subQuery ), negated, null );
	}

	public InSubQueryPredicate(Expression testExpression, SelectStatement subQuery, boolean negated, JdbcMappingContainer expressionType) {
		super( expressionType, negated );
		this.testExpression = testExpression;
		this.subQuery = subQuery;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getTestExpression() {
		return testExpression;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SelectStatement getSubQuery() {
		return subQuery;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitInSubQueryPredicate( this );
	}
}
