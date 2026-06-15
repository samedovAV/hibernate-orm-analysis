/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.internal;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

import org.hibernate.jdbc.Expectation;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.expression.ColumnReference;
import org.hibernate.sql.model.MutationTarget;
import org.hibernate.sql.model.ast.AbstractTableUpdate;
import org.hibernate.sql.model.ast.ColumnValueBinding;
import org.hibernate.sql.model.ast.ColumnValueParameter;
import org.hibernate.sql.model.ast.GeneratedMutation;
import org.hibernate.sql.model.ast.MutatingTableReference;
import org.hibernate.sql.model.jdbc.JdbcMutationOperation;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class TableUpdateStandard
		extends AbstractTableUpdate<JdbcMutationOperation>
		implements GeneratedMutation<JdbcMutationOperation> {
	private final String whereFragment;
	private final Expectation expectation;
	private final List<ColumnReference> returningColumns;

	public TableUpdateStandard(
			MutatingTableReference mutatingTable,
			MutationTarget<?,?> mutationTarget,
			String sqlComment,
			List<ColumnValueBinding> valueBindings,
			List<ColumnValueBinding> keyRestrictionBindings,
			List<ColumnValueBinding> optLockRestrictionBindings) {
		this(
				mutatingTable,
				mutationTarget,
				sqlComment,
				valueBindings,
				keyRestrictionBindings,
				optLockRestrictionBindings,
				null,
				null,
				Collections.emptyList()
		);
	}

	public TableUpdateStandard(
			MutatingTableReference mutatingTable,
			MutationTarget<?,?> mutationTarget,
			String sqlComment,
			List<ColumnValueBinding> valueBindings,
			List<ColumnValueBinding> keyRestrictionBindings,
			List<ColumnValueBinding> optLockRestrictionBindings,
			String whereFragment,
			Expectation expectation,
			List<ColumnReference> returningColumns) {
		super( mutatingTable, mutationTarget, sqlComment, valueBindings, keyRestrictionBindings, optLockRestrictionBindings );
		this.whereFragment = whereFragment;
		this.expectation = expectation;
		this.returningColumns = returningColumns;
	}

	public TableUpdateStandard(
			MutatingTableReference tableReference,
			MutationTarget<?,?> mutationTarget,
			String sqlComment,
			List<ColumnValueBinding> valueBindings,
			List<ColumnValueBinding> keyRestrictionBindings,
			List<ColumnValueBinding> optLockRestrictionBindings,
			List<ColumnValueParameter> parameters) {
		this(
				tableReference,
				mutationTarget,
				sqlComment,
				valueBindings,
				keyRestrictionBindings,
				optLockRestrictionBindings,
				parameters,
				null,
				null
		);
	}

	public TableUpdateStandard(
			MutatingTableReference tableReference,
			MutationTarget<?,?> mutationTarget,
			String sqlComment,
			List<ColumnValueBinding> valueBindings,
			List<ColumnValueBinding> keyRestrictionBindings,
			List<ColumnValueBinding> optLockRestrictionBindings,
			List<ColumnValueParameter> parameters,
			String whereFragment,
			Expectation expectation) {
		super( tableReference, mutationTarget, sqlComment, valueBindings, keyRestrictionBindings, optLockRestrictionBindings, parameters );
		this.whereFragment = whereFragment;
		this.expectation = expectation;
		this.returningColumns = Collections.emptyList();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCustomSql() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCallable() {
		return false;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getWhereFragment() {
		return whereFragment;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker walker) {
		walker.visitStandardTableUpdate( this );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Expectation getExpectation() {
		if ( expectation != null ) {
			return expectation;
		}
		return super.getExpectation();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<ColumnReference> getReturningColumns() {
		return returningColumns;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void forEachReturningColumn(BiConsumer<Integer,ColumnReference> consumer) {
		forEachThing( returningColumns, consumer );
	}
}
