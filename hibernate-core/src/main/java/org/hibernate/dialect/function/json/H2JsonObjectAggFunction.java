/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.function.json;

import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.type.spi.TypeConfiguration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Standard json_objectagg function that uses no returning clause.
 */
public class H2JsonObjectAggFunction extends JsonObjectAggFunction {

	public H2JsonObjectAggFunction(TypeConfiguration typeConfiguration) {
		super( ":", true, typeConfiguration );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void renderReturningClause(
			SqlAppender sqlAppender,
			JsonObjectAggArguments arguments,
			SqlAstTranslator<?> translator) {
		// No-op
	}
}
