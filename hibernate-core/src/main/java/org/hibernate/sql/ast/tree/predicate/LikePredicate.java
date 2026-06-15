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
public class LikePredicate extends AbstractPredicate {
	private final Expression matchExpression;
	private final Expression pattern;
	private final Expression escapeCharacter;
	private final boolean isCaseSensitive;

	public LikePredicate(Expression matchExpression, Expression pattern) {
		this( matchExpression, pattern, null );
	}

	public LikePredicate(
			Expression matchExpression,
			Expression pattern,
			Expression escapeCharacter) {
		this( matchExpression, pattern, escapeCharacter, false );
	}

	public LikePredicate(
			Expression matchExpression,
			Expression pattern,
			Expression escapeCharacter,
			boolean negated) {
		this( matchExpression, pattern, escapeCharacter, negated, true, null );
	}

	public LikePredicate(
			Expression matchExpression,
			Expression pattern,
			Expression escapeCharacter,
			boolean negated,
			boolean isCaseSensitive,
			JdbcMappingContainer expressionType) {
		super( expressionType, negated );
		this.matchExpression = matchExpression;
		this.pattern = pattern;
		this.escapeCharacter = escapeCharacter;
		this.isCaseSensitive = isCaseSensitive;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getMatchExpression() {
		return matchExpression;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getPattern() {
		return pattern;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getEscapeCharacter() {
		return escapeCharacter;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCaseSensitive() {
		return isCaseSensitive;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitLikePredicate( this );
	}
}
