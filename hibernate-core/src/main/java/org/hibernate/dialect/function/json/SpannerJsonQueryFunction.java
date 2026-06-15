/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.function.json;

import org.hibernate.metamodel.model.domain.ReturnableType;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.expression.JsonQueryWrapMode;
import org.hibernate.type.spi.TypeConfiguration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class SpannerJsonQueryFunction extends JsonQueryFunction {

	public SpannerJsonQueryFunction(TypeConfiguration typeConfiguration) {
		super( typeConfiguration, false, false );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected void render(SqlAppender sqlAppender, JsonQueryArguments arguments, ReturnableType<?> returnType, SqlAstTranslator<?> walker) {
		if ( arguments.wrapMode() == JsonQueryWrapMode.WITH_WRAPPER ) {
			sqlAppender.appendSql( "parse_json(concat('[', to_json_string(" );
			JsonQueryArguments noWrapArgs = new JsonQueryArguments(
					arguments.jsonDocument(),
					arguments.jsonPath(),
					arguments.isJsonType(),
					arguments.passingClause(),
					null,
					arguments.errorBehavior(),
					arguments.emptyBehavior()
			);
			super.render( sqlAppender, noWrapArgs, returnType, walker );
			sqlAppender.appendSql( "), ']'))" );
		}
		else {
			super.render( sqlAppender, arguments, returnType, walker );
		}
	}
}
