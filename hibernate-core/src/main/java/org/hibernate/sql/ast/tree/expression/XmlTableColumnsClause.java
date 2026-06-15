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
public class XmlTableColumnsClause implements SqlAstNode {

	private final List<XmlTableColumnDefinition> columnDefinitions;

	public XmlTableColumnsClause(List<XmlTableColumnDefinition> columnDefinitions) {
		this.columnDefinitions = columnDefinitions;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<XmlTableColumnDefinition> getColumnDefinitions() {
		return columnDefinitions;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		throw new UnsupportedOperationException("XmlTableColumnsClause doesn't support walking");
	}

}
