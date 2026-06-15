/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.SqlAstNode;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @since 7.0
 */
public class JsonValueErrorBehavior implements SqlAstNode {
	public static final JsonValueErrorBehavior NULL = new JsonValueErrorBehavior( null );
	public static final JsonValueErrorBehavior ERROR = new JsonValueErrorBehavior( null );

	private final @Nullable Expression defaultExpression;

	private JsonValueErrorBehavior(@Nullable Expression defaultExpression) {
		this.defaultExpression = defaultExpression;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static JsonValueErrorBehavior defaultOnError(Expression defaultExpression) {
		return new JsonValueErrorBehavior( defaultExpression );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Expression getDefaultExpression() {
		return defaultExpression;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		throw new UnsupportedOperationException("JsonValueErrorBehavior doesn't support walking");
	}

}
