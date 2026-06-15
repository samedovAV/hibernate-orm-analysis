/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.ast;

import java.util.List;

import org.hibernate.jdbc.Expectation;
import org.hibernate.sql.exec.spi.JdbcParameterBinder;
import org.hibernate.sql.model.MutationTarget;
import org.hibernate.sql.model.TableMapping;
import org.hibernate.sql.model.jdbc.JdbcDeleteMutation;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractTableDelete extends AbstractRestrictedTableMutation<JdbcDeleteMutation> implements TableDelete {

	public AbstractTableDelete(
			MutatingTableReference mutatingTable,
			MutationTarget<?,?> mutationTarget,
			List<ColumnValueBinding> keyRestrictionBindings,
			List<ColumnValueBinding> optLockRestrictionBindings,
			List<ColumnValueParameter> parameters) {
		this(
				mutatingTable,
				mutationTarget,
				"delete for " + mutationTarget.getRolePath(),
				keyRestrictionBindings,
				optLockRestrictionBindings,
				parameters
		);
	}

	public AbstractTableDelete(
			MutatingTableReference mutatingTable,
			MutationTarget<?,?> mutationTarget,
			String sqlComment,
			List<ColumnValueBinding> keyRestrictionBindings,
			List<ColumnValueBinding> optLockRestrictionBindings,
			List<ColumnValueParameter> parameters) {
		super( mutatingTable, mutationTarget, sqlComment, keyRestrictionBindings, optLockRestrictionBindings, parameters );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String getLoggableName() {
		return "TableDelete";
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Expectation getExpectation() {
		return getMutatingTable().getTableMapping().getDeleteDetails().getExpectation();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected JdbcDeleteMutation createMutationOperation(
			TableMapping tableDetails,
			String sql,
			List<JdbcParameterBinder> effectiveBinders) {
		return new JdbcDeleteMutation(
				tableDetails,
				getMutationTarget(),
				sql,
				isCallable(),
				getExpectation(),
				effectiveBinders
		);
	}
}
