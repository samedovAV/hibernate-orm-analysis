/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.function.array;

import java.util.List;

import org.hibernate.metamodel.model.domain.ReturnableType;
import org.hibernate.query.sqm.function.AbstractSqmSelfRenderingFunctionDescriptor;
import org.hibernate.query.sqm.produce.function.StandardArgumentsValidators;
import org.hibernate.query.sqm.produce.function.StandardFunctionReturnTypeResolvers;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.type.spi.TypeConfiguration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class OracleArrayLengthFunction extends AbstractSqmSelfRenderingFunctionDescriptor {

	public OracleArrayLengthFunction(TypeConfiguration typeConfiguration) {
		super(
				"array_length",
				StandardArgumentsValidators.composite(
						StandardArgumentsValidators.exactly( 1 ),
						ArrayArgumentValidator.DEFAULT_INSTANCE
				),
				StandardFunctionReturnTypeResolvers.invariant( typeConfiguration.standardBasicTypeForJavaType( Integer.class ) ),
				null
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void render(
			SqlAppender sqlAppender,
			List<? extends SqlAstNode> sqlAstArguments,
			ReturnableType<?> returnType,
			SqlAstTranslator<?> walker) {
		final Expression arrayExpression = (Expression) sqlAstArguments.get( 0 );
		final String arrayTypeName = DdlTypeHelper.getTypeName(
				arrayExpression.getExpressionType(),
				walker.getSessionFactory().getTypeConfiguration()
		);
		sqlAppender.appendSql( arrayTypeName );
		sqlAppender.append( "_length(" );
		arrayExpression.accept( walker );
		sqlAppender.append( ')' );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getArgumentListSignature() {
		return "(ARRAY array)";
	}
}
