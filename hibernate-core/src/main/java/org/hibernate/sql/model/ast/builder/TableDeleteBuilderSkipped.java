/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.ast.builder;

import org.hibernate.metamodel.mapping.SelectableMapping;
import org.hibernate.sql.model.TableMapping;
import org.hibernate.sql.model.ast.ColumnValueBinding;
import org.hibernate.sql.model.ast.ColumnValueBindingList;
import org.hibernate.sql.model.ast.MutatingTableReference;
import org.hibernate.sql.model.ast.TableDelete;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class TableDeleteBuilderSkipped implements TableDeleteBuilder {
	private final MutatingTableReference tableReference;

	public TableDeleteBuilderSkipped(TableMapping tableMapping) {
		tableReference = new MutatingTableReference( tableMapping );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addNonKeyRestriction(ColumnValueBinding valueBinding) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addNonKeyRestriction(SelectableMapping restrictableMapping, String restrictionExpression) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addKeyRestrictionBinding(SelectableMapping selectableMapping) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addNullOptimisticLockRestriction(SelectableMapping column) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addOptimisticLockRestriction(SelectableMapping selectableMapping) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ColumnValueBindingList getKeyRestrictionBindings() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ColumnValueBindingList getOptimisticLockBindings() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setWhere(String fragment) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addWhereFragment(String fragment) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MutatingTableReference getMutatingTable() {
		return tableReference;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableDelete buildMutation() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasValueBindings() {
		return false;
	}
}
