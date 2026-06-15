/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.sql.internal;

import java.util.function.Function;
import java.util.function.Supplier;

import org.hibernate.sql.ast.Clause;
import org.hibernate.sql.ast.spi.SqlAstCreationState;
import org.hibernate.sql.ast.spi.SqlAstProcessingState;
import org.hibernate.sql.ast.spi.SqlExpressionResolver;
import org.hibernate.sql.ast.tree.from.FromClause;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class SqlAstQueryNodeProcessingStateImpl
		extends AbstractSqlAstQueryNodeProcessingStateImpl {

	private final FromClause fromClause;
	private Predicate predicate;

	public SqlAstQueryNodeProcessingStateImpl(
			FromClause fromClause,
			SqlAstProcessingState parent,
			SqlAstCreationState creationState,
			Supplier<Clause> currentClauseAccess) {
		super( parent, creationState, currentClauseAccess );
		this.fromClause = fromClause;
	}

	public SqlAstQueryNodeProcessingStateImpl(
			FromClause fromClause,
			SqlAstProcessingState parent,
			SqlAstCreationState creationState,
			Function<SqlExpressionResolver, SqlExpressionResolver> expressionResolverDecorator,
			Supplier<Clause> currentClauseAccess) {
		super( parent, creationState, expressionResolverDecorator, currentClauseAccess );
		this.fromClause = fromClause;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FromClause getFromClause() {
		return fromClause;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Predicate getPredicate() {
		return predicate;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applyPredicate(Predicate predicate) {
		this.predicate = Predicate.combinePredicates( this.predicate, predicate );
	}
}
