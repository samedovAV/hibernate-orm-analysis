/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import org.hibernate.metamodel.mapping.SelectableMapping;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.from.EmbeddableFunctionTableReference;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Model a column which is relative to a base expression e.g. {@code array[1].columnName}.
 * This is needed to model column references within e.g. arrays.
 */
public class NestedColumnReference extends ColumnReference {
	private final Expression baseExpression;

	public NestedColumnReference(EmbeddableFunctionTableReference tableReference, SelectableMapping selectableMapping) {
		super( tableReference, selectableMapping );
		this.baseExpression = tableReference.getExpression();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getBaseExpression() {
		return baseExpression;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getReadExpression() {
		return super.getReadExpression();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitNestedColumnReference( this );
	}
}
