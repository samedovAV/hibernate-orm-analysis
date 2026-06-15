/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.function.xml;

import java.util.Map;

import org.hibernate.metamodel.model.domain.ReturnableType;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.type.spi.TypeConfiguration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * H2 xmlelement function.
 */
public class H2XmlElementFunction extends XmlElementFunction {

	public H2XmlElementFunction(TypeConfiguration typeConfiguration) {
		super( typeConfiguration );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected void render(
			SqlAppender sqlAppender,
			XmlElementArguments arguments,
			ReturnableType<?> returnType,
			SqlAstTranslator<?> walker) {
		sqlAppender.appendSql( "xmlnode(" );
		sqlAppender.appendSingleQuoteEscapedString( arguments.elementName() );
		if ( arguments.attributes() != null ) {
			String separator = ",";
			for ( Map.Entry<String, Expression> entry : arguments.attributes().getAttributes().entrySet() ) {
				sqlAppender.appendSql( separator );
				sqlAppender.appendSql( "xmlattr(" );
				sqlAppender.appendSingleQuoteEscapedString( entry.getKey() );
				sqlAppender.appendSql( ',' );
				entry.getValue().accept( walker );
				sqlAppender.appendSql( ')' );
				separator = "||";
			}
		}
		else {
			sqlAppender.appendSql( ",null" );
		}
		if ( !arguments.content().isEmpty() ) {
			String separator = ",";
			for ( Expression expression : arguments.content() ) {
				sqlAppender.appendSql( separator );
				expression.accept( walker );
				separator = "||";
			}
		}
		else {
			sqlAppender.appendSql( ",null" );
		}
		sqlAppender.appendSql( ",false)" );
	}
}
