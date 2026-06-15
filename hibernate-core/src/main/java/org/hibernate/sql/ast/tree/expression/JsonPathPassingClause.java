/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import java.util.Map;

import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.SqlAstNode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @since 7.0
 */
public class JsonPathPassingClause implements SqlAstNode {

	private final Map<String, Expression> passingExpressions;

	public JsonPathPassingClause(Map<String, Expression> passingExpressions) {
		this.passingExpressions = passingExpressions;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Map<String, Expression> getPassingExpressions() {
		return passingExpressions;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		throw new UnsupportedOperationException("JsonPathPassingClause doesn't support walking");
	}

}
