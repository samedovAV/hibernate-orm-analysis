/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.internal;

import jakarta.annotation.Nullable;
import org.hibernate.sql.ast.tree.expression.ColumnReference;
import org.hibernate.sql.model.MutationTarget;
import org.hibernate.sql.model.ast.ColumnValueBinding;
import org.hibernate.sql.model.ast.ColumnValueParameter;
import org.hibernate.sql.model.ast.MutatingTableReference;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class OptionalTableInsert extends TableInsertStandard {

	private final @Nullable String constraintName;
	private final List<String> constraintColumnNames;

	public OptionalTableInsert(
			MutatingTableReference mutatingTable,
			MutationTarget<?,?> mutationTarget,
			List<ColumnValueBinding> valueBindings,
			List<ColumnReference> returningColumns,
			List<ColumnValueParameter> parameters,
			@Nullable String constraintName,
			List<String> constraintColumnNames) {
		super( mutatingTable, mutationTarget, valueBindings, returningColumns, parameters );
		this.constraintName = constraintName;
		this.constraintColumnNames = constraintColumnNames;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable String getConstraintName() {
		return constraintName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<String> getConstraintColumnNames() {
		return constraintColumnNames;
	}
}
