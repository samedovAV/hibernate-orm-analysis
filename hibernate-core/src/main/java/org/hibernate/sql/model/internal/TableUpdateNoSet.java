/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.internal;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.hibernate.jdbc.Expectation;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.expression.ColumnReference;
import org.hibernate.sql.exec.spi.JdbcParameterBinder;
import org.hibernate.sql.model.MutationOperation;
import org.hibernate.sql.model.MutationTarget;
import org.hibernate.sql.model.TableMapping;
import org.hibernate.sql.model.ast.AbstractRestrictedTableMutation;
import org.hibernate.sql.model.ast.ColumnValueBinding;
import org.hibernate.sql.model.ast.ColumnValueParameter;
import org.hibernate.sql.model.ast.MutatingTableReference;
import org.hibernate.sql.model.ast.TableUpdate;
import org.hibernate.sql.model.jdbc.JdbcMutationOperation;

import static java.util.Collections.emptyList;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A skipped update
 *
 * @author Steve Ebersole
 */
public class TableUpdateNoSet
		extends AbstractRestrictedTableMutation<MutationOperation>
		implements TableUpdate<MutationOperation> {
	public TableUpdateNoSet(MutatingTableReference mutatingTable, MutationTarget<?,?> mutationTarget) {
		super(
				mutatingTable,
				mutationTarget,
				"no-op",
				emptyList(),
				emptyList(),
				emptyList()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String getLoggableName() {
		return "TableUpdateNoSet";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCustomSql() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker walker) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void forEachValueBinding(BiConsumer<Integer, ColumnValueBinding> consumer) {
		// there are none
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected JdbcMutationOperation createMutationOperation(
			TableMapping tableDetails,
			String sql,
			List<JdbcParameterBinder> effectiveBinders) {
		// no operation
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expectation getExpectation() {
		return Expectation.None.INSTANCE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCallable() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<ColumnValueBinding> getValueBindings() {
		return emptyList();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void forEachParameter(Consumer<ColumnValueParameter> consumer) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<ColumnReference> getReturningColumns() {
		return emptyList();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void forEachReturningColumn(BiConsumer<Integer, ColumnReference> consumer) {
		// nothing to do
	}
}
