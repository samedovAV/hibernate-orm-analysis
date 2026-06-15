/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.function.json;

import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.type.spi.TypeConfiguration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * H2 json_arrayagg function.
 */
public class H2JsonArrayAggFunction extends JsonArrayAggFunction {

	public H2JsonArrayAggFunction(TypeConfiguration typeConfiguration) {
		super( true, typeConfiguration );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void renderReturningClause(SqlAppender sqlAppender, Expression arg, SqlAstTranslator<?> translator) {
		// No returning clause supported or needed
	}
}
