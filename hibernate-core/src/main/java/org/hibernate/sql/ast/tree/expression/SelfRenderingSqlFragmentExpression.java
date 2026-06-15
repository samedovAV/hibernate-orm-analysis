/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import jakarta.annotation.Nullable;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Represents a self rendering expression that renders a SQL fragment.
 *
 * @author Christian Beikov
 */
public class SelfRenderingSqlFragmentExpression implements SelfRenderingExpression {
	private final String expression;
	private final @Nullable JdbcMappingContainer expressionType;

	public SelfRenderingSqlFragmentExpression(String expression) {
		this( expression, null );
	}

	public SelfRenderingSqlFragmentExpression(String expression, @Nullable JdbcMappingContainer expressionType) {
		this.expression = expression;
		this.expressionType = expressionType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getExpression() {
		return expression;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMappingContainer getExpressionType() {
		return expressionType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void renderToSql(
			SqlAppender sqlAppender,
			SqlAstTranslator<?> walker,
			SessionFactoryImplementor sessionFactory) {
		sqlAppender.append( expression );
	}
}
