/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.function.json;

import org.hibernate.Internal;
import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.query.sqm.CastType;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@Internal
public class ExpressionTypeHelper {

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static boolean isBoolean(SqlAstNode node) {
		final Expression expression = (Expression) node;
		final JdbcMappingContainer expressionType = expression.getExpressionType();
		return expressionType.getJdbcTypeCount() == 1
				&& isBoolean( expressionType.getSingleJdbcMapping().getCastType() );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static boolean isNonNativeBoolean(SqlAstNode node) {
		final Expression expression = (Expression) node;
		final JdbcMappingContainer expressionType = expression.getExpressionType();
		return expressionType.getJdbcTypeCount() == 1
				&& isNonNativeBoolean( expressionType.getSingleJdbcMapping().getCastType() );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static boolean isJson(SqlAstNode node) {
		final Expression expression = (Expression) node;
		final JdbcMappingContainer expressionType = expression.getExpressionType();
		return expressionType.getJdbcTypeCount() == 1
				&& expressionType.getSingleJdbcMapping().getJdbcType().isJson();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static boolean isXml(SqlAstNode node) {
		final Expression expression = (Expression) node;
		final JdbcMappingContainer expressionType = expression.getExpressionType();
		return expressionType.getJdbcTypeCount() == 1
				&& expressionType.getSingleJdbcMapping().getJdbcType().isXml();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static JdbcType getSingleJdbcType(SqlAstNode node) {
		final Expression expression = (Expression) node;
		final JdbcMappingContainer expressionType = expression.getExpressionType();
		return expressionType.getSingleJdbcMapping().getJdbcType();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static boolean isBoolean(CastType castType) {
		switch ( castType ) {
			case BOOLEAN:
			case TF_BOOLEAN:
			case YN_BOOLEAN:
			case INTEGER_BOOLEAN:
				return true;
			default:
				return false;
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static boolean isNonNativeBoolean(CastType castType) {
		switch ( castType ) {
			case TF_BOOLEAN:
			case YN_BOOLEAN:
			case INTEGER_BOOLEAN:
				return true;
			default:
				return false;
		}
	}
}
