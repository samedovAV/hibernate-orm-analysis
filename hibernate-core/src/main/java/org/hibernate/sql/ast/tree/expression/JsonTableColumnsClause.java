/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.SqlAstNode;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @since 7.0
 */
public class JsonTableColumnsClause implements SqlAstNode {

	private final List<JsonTableColumnDefinition> columnDefinitions;

	public JsonTableColumnsClause(List<JsonTableColumnDefinition> columnDefinitions) {
		this.columnDefinitions = columnDefinitions;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<JsonTableColumnDefinition> getColumnDefinitions() {
		return columnDefinitions;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		throw new UnsupportedOperationException("JsonPathPassingClause doesn't support walking");
	}

}
