/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.internal;

import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.expression.ColumnReference;
import org.hibernate.sql.model.MutationTarget;
import org.hibernate.sql.model.TableMapping;
import org.hibernate.sql.model.ast.AbstractTableUpdate;
import org.hibernate.sql.model.ast.ColumnValueBinding;
import org.hibernate.sql.model.ast.ColumnValueParameter;
import org.hibernate.sql.model.ast.CustomSqlMutation;
import org.hibernate.sql.model.ast.MutatingTableReference;
import org.hibernate.sql.model.jdbc.JdbcMutationOperation;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Update defined using custom sql-update
 *
 * @see org.hibernate.annotations.SQLUpdate
 *
 * @author Steve Ebersole
 */
public class TableUpdateCustomSql
		extends AbstractTableUpdate<JdbcMutationOperation>
		implements CustomSqlMutation<JdbcMutationOperation> {
	private TableMapping.MutationDetails mutationDetails;

	public TableUpdateCustomSql(
			MutatingTableReference mutatingTable,
			MutationTarget<?,?> mutationTarget,
			String sqlComment,
			List<ColumnValueBinding> valueBindings,
			List<ColumnValueBinding> keyRestrictionBindings,
			List<ColumnValueBinding> optLockRestrictionBindings) {
		super( mutatingTable, mutationTarget, sqlComment, valueBindings, keyRestrictionBindings, optLockRestrictionBindings );
		this.mutationDetails = mutationTarget.getIdentifierTableMapping().getUpdateDetails();
	}

	public TableUpdateCustomSql(
			MutatingTableReference mutatingTable,
			MutationTarget<?,?> mutationTarget,
			String sqlComment,
			List<ColumnValueBinding> valueBindings,
			List<ColumnValueBinding> keyRestrictionBindings,
			List<ColumnValueBinding> optLockRestrictionBindings,
			List<ColumnValueParameter> parameters) {
		super( mutatingTable, mutationTarget, sqlComment, valueBindings, keyRestrictionBindings, optLockRestrictionBindings, parameters );
		this.mutationDetails = mutationTarget.getIdentifierTableMapping().getUpdateDetails();
	}

	public TableUpdateCustomSql(
			MutatingTableReference mutatingTable,
			MutationTarget<?,?> mutationTarget,
			TableMapping.MutationDetails mutationDetails,
			String sqlComment,
			List<ColumnValueBinding> valueBindings,
			List<ColumnValueBinding> keyRestrictionBindings,
			List<ColumnValueBinding> optLockRestrictionBindings,
			List<ColumnValueParameter> parameters) {
		super( mutatingTable, mutationTarget, sqlComment, valueBindings, keyRestrictionBindings, optLockRestrictionBindings, parameters );
		this.mutationDetails = mutationDetails;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCustomSql() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getCustomSql() {
		return mutationDetails.getCustomSql();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isCallable() {
		return mutationDetails.isCallable();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<ColumnReference> getReturningColumns() {
		return Collections.emptyList();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void forEachReturningColumn(BiConsumer<Integer, ColumnReference> consumer) {
		// nothing to do
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker walker) {
		walker.visitCustomTableUpdate( this );
	}
}
