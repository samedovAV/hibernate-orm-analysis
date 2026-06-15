/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.from;

import org.hibernate.sql.ast.SqlAstJoinType;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.spi.SqlAstTreeHelper;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import org.hibernate.sql.ast.tree.predicate.PredicateContainer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Represents a join to a {@link TableReference}; roughly equivalent to a SQL join.
 *
 * @author Steve Ebersole
 */
public class TableReferenceJoin implements TableJoin, PredicateContainer {
	private final boolean innerJoin;
	private final NamedTableReference joinedTableBinding;
	private Predicate predicate;

	public TableReferenceJoin(boolean innerJoin, NamedTableReference joinedTableBinding, Predicate predicate) {
		this.innerJoin = innerJoin;
		this.joinedTableBinding = joinedTableBinding;
		this.predicate = predicate;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqlAstJoinType getJoinType() {
		return innerJoin ? SqlAstJoinType.INNER : SqlAstJoinType.LEFT;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NamedTableReference getJoinedTableReference() {
		return joinedTableBinding;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqlAstNode getJoinedNode() {
		return joinedTableBinding;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Predicate getPredicate() {
		return predicate;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitTableReferenceJoin( this );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String toString() {
		return getJoinType().getText() + "join " + getJoinedTableReference().toString();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isInitialized() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applyPredicate(Predicate newPredicate) {
		predicate = SqlAstTreeHelper.combinePredicates( predicate, newPredicate);
	}
}
