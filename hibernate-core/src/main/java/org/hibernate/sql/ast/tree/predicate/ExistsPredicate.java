/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.predicate;

import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.select.QueryPart;
import org.hibernate.sql.ast.tree.select.SelectStatement;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Gavin King
 */
public class ExistsPredicate implements Predicate {

	private final boolean negated;
	private final SelectStatement expression;
	private final JdbcMappingContainer expressionType;

	public ExistsPredicate(QueryPart expression, boolean negated, JdbcMappingContainer expressionType) {
		this( new SelectStatement( expression ), negated, expressionType );
	}

	public ExistsPredicate(SelectStatement expression, boolean negated, JdbcMappingContainer expressionType) {
		this.negated = negated;
		this.expression = expression;
		this.expressionType = expressionType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SelectStatement getExpression() {
		return expression;
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
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitExistsPredicate( this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMappingContainer getExpressionType() {
		return expressionType;
	}
}
