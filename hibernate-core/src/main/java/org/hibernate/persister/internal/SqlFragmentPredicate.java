/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.persister.internal;

import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Predicate based on a SQL fragment
 */
public class SqlFragmentPredicate implements Predicate {
	private final String fragment;

	public SqlFragmentPredicate(String fragment) {
		this.fragment = fragment;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSqlFragment() {
		return fragment;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitSqlFragmentPredicate( this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMappingContainer getExpressionType() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isEmpty() {
		return false;
	}
}
