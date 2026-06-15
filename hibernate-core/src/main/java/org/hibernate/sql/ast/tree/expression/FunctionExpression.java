/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import java.util.List;

import org.hibernate.sql.ast.tree.SqlAstNode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models a function expression at the SQL AST level.
 *
 * @author Christian Beikov
 */
public interface FunctionExpression extends Expression {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getFunctionName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<? extends SqlAstNode> getArguments();
}
