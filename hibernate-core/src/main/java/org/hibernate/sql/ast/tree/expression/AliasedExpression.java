/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A wrapper for an expression that also renders an alias.
 *
 * @author Christian Beikov
 */
public class AliasedExpression implements SelfRenderingExpression {

	private final Expression expression;
	private final String alias;

	public AliasedExpression(Expression expression, String alias) {
		this.expression = expression;
		this.alias = alias;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void renderToSql(SqlAppender sqlAppender, SqlAstTranslator<?> walker, SessionFactoryImplementor sessionFactory) {
		expression.accept( walker );
		sqlAppender.appendSql( ' ' );
		sqlAppender.appendSql( alias );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JdbcMappingContainer getExpressionType() {
		return expression.getExpressionType();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getExpression() {
		return expression;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getAlias() {
		return alias;
	}
}
