/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.SqlAstWalker;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface SelfRenderingExpression extends Expression {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitSelfRenderingExpression( this );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void renderToSql(SqlAppender sqlAppender, SqlAstTranslator<?> walker, SessionFactoryImplementor sessionFactory);
}
