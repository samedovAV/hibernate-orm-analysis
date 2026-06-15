/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.ast;

import org.hibernate.engine.jdbc.mutation.ParameterUsage;
import org.hibernate.sql.ast.tree.expression.ColumnReference;
import org.hibernate.sql.exec.internal.AbstractJdbcParameter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Parameter descriptor specific to mutations.
/// It exposes metadata about the parameter.
///
/// [!NOTE]
/// > Especially note the [usage][#getUsage] - we track parameters separately for
/// > [assignments][ParameterUsage#SET] and [restrictions][ParameterUsage#RESTRICT]
/// > to allow different values in each clause.  E.g.
/// > ````
/// update ...
/// set col = newValue
/// where col = oldValue
/// ````
///
/// @author Steve Ebersole
public class ColumnValueParameter extends AbstractJdbcParameter {
	private final ColumnReference columnReference;
	private final ParameterUsage usage;

	public ColumnValueParameter(ColumnReference columnReference, ParameterUsage usage) {
		super( columnReference.getJdbcMapping() );
		this.columnReference = columnReference;
		this.usage = usage;
	}

	public ColumnValueParameter(ColumnReference columnReference) {
		this( columnReference, ParameterUsage.SET );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ColumnReference getColumnReference() {
		return columnReference;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ParameterUsage getUsage() {
		return usage;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "ColumnValueParameter(" + columnReference.getColumnExpression() + ')';
	}
}
