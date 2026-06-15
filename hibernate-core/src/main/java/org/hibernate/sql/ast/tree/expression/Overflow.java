/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import org.hibernate.internal.util.IndexedConsumer;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.metamodel.mapping.SqlExpressible;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.SqlAstNode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class Overflow implements Expression, SqlExpressible, SqlAstNode {
	private final Expression separatorExpression;
	private final Expression fillerExpression;
	private final boolean withCount;

	public Overflow(Expression separatorExpression, Expression fillerExpression, boolean withCount) {
		this.separatorExpression = separatorExpression;
		this.fillerExpression = fillerExpression;
		this.withCount = withCount;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getSeparatorExpression() {
		return separatorExpression;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getFillerExpression() {
		return fillerExpression;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isWithCount() {
		return withCount;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JdbcMapping getJdbcMapping() {
		return ( (SqlExpressible) separatorExpression ).getJdbcMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JdbcMappingContainer getExpressionType() {
		return separatorExpression.getExpressionType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitOverflow( this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int forEachJdbcType(int offset, IndexedConsumer<JdbcMapping> action) {
		action.accept( offset, getJdbcMapping() );
		return getJdbcTypeCount();
	}
}
