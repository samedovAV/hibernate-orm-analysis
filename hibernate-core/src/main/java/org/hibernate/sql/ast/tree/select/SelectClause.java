/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.select;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.sql.ast.spi.SqlSelection;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.query.sqm.sql.internal.DomainResultProducer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * The SELECT CLAUSE in the SQL AST.  Each selection here is a
 * {@link DomainResultProducer}
 *
 * @author Steve Ebersole
 */
public class SelectClause implements SqlAstNode {
	private boolean distinct;

	private final List<SqlSelection> sqlSelections;

	public SelectClause() {
		this.sqlSelections = new ArrayList<>();
	}

	public SelectClause(int estimateSelectionSize) {
		this.sqlSelections = new ArrayList<>( estimateSelectionSize );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void makeDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isDistinct() {
		return distinct;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addSqlSelection(SqlSelection sqlSelection) {
		sqlSelections.add( sqlSelection );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<SqlSelection> getSqlSelections() {
		return sqlSelections;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitSelectClause( this );
	}
}
