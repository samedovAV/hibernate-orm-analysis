/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.internal;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.expression.ColumnReference;
import org.hibernate.sql.model.MutationTarget;
import org.hibernate.sql.model.ast.AbstractTableInsert;
import org.hibernate.sql.model.ast.ColumnValueBinding;
import org.hibernate.sql.model.ast.ColumnValueParameter;
import org.hibernate.sql.model.ast.CustomSqlMutation;
import org.hibernate.sql.model.ast.MutatingTableReference;
import org.hibernate.sql.model.jdbc.JdbcInsertMutation;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Insertion defined using custom sql-insert
 *
 * @see org.hibernate.annotations.SQLInsert
 *
 * @author Steve Ebersole
 */
public class TableInsertCustomSql extends AbstractTableInsert implements CustomSqlMutation<JdbcInsertMutation> {

	public TableInsertCustomSql(
			MutatingTableReference mutatingTable,
			MutationTarget<?,?> mutationTarget,
			List<ColumnValueBinding> valueBindings,
			List<ColumnValueParameter> parameters) {
		super( mutatingTable, mutationTarget, parameters, valueBindings );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCustomSql() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getCustomSql() {
		return getMutatingTable().getTableMapping().getInsertDetails().getCustomSql();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isCallable() {
		return getMutatingTable().getTableMapping().getInsertDetails().isCallable();
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
		walker.visitCustomTableInsert( this );
	}
}
