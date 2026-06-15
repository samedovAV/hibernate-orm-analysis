/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.update;

import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.expression.Expression;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class Assignment implements SqlAstNode {
	private final Assignable assignable;
	private final Expression assignedValue;

	public Assignment(Assignable assignable, Expression assignedValue) {
		this.assignable = assignable;
		this.assignedValue = assignedValue;
	}

	/**
	 * The column being updated.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Assignable getAssignable() {
		return assignable;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getAssignedValue() {
		return assignedValue;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitAssignment( this );
	}
}
