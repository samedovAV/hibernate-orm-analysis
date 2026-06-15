/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import org.hibernate.internal.util.IndexedConsumer;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.mapping.SqlExpressible;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.SqlAstNode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Represents the format pattern for a date/time format expression
 *
 * @author Gavin King
 */
public class Format implements SqlExpressible, SqlAstNode {
	private final String format;

	public Format(String format) {
		this.format = format;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getFormat() {
		return format;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMapping getJdbcMapping() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker walker) {
		walker.visitFormat( this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int forEachJdbcType(int offset, IndexedConsumer<JdbcMapping> action) {
		return getJdbcTypeCount();
	}
}
