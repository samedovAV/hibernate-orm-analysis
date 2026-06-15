/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.function.array;

import java.util.List;

import org.hibernate.metamodel.model.domain.ReturnableType;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.type.spi.TypeConfiguration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Array intersects function that uses the PostgreSQL {@code &&} operator.
 */
public class ArrayIntersectsOperatorFunction extends ArrayIntersectsUnnestFunction {

	public ArrayIntersectsOperatorFunction(boolean nullable, TypeConfiguration typeConfiguration) {
		super( nullable, typeConfiguration );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void render(
			SqlAppender sqlAppender,
			List<? extends SqlAstNode> sqlAstArguments,
			ReturnableType<?> returnType, SqlAstTranslator<?> walker) {
		if ( nullable ) {
			super.render( sqlAppender, sqlAstArguments, returnType, walker );
		}
		else {
			final Expression haystackExpression = (Expression) sqlAstArguments.get( 0 );
			final Expression needleExpression = (Expression) sqlAstArguments.get( 1 );
			haystackExpression.accept( walker );
			sqlAppender.append( "&&" );
			needleExpression.accept( walker );
		}
	}
}
