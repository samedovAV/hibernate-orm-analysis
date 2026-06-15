/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.from;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.hibernate.metamodel.mapping.ModelPart;
import org.hibernate.metamodel.mapping.ModelPartContainer;
import org.hibernate.spi.NavigablePath;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Acts as a TableGroup for DML query operations.  It is used to simply
 * wrap the TableReference of the "mutating table"
 *
 * @author Steve Ebersole
 */
public class MutatingTableReferenceGroupWrapper implements TableGroup {
	private final NavigablePath navigablePath;
	private final ModelPartContainer modelPart;
	private final NamedTableReference mutatingTableReference;

	public MutatingTableReferenceGroupWrapper(
			NavigablePath navigablePath,
			ModelPartContainer modelPart,
			NamedTableReference mutatingTableReference) {
		this.navigablePath = navigablePath;
		this.modelPart = modelPart;
		this.mutatingTableReference = mutatingTableReference;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NavigablePath getNavigablePath() {
		return navigablePath;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ModelPart getExpressionType() {
		return getModelPart();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getGroupAlias() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ModelPartContainer getModelPart() {
		return modelPart;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableReference getPrimaryTableReference() {
		return mutatingTableReference;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TableReference getTableReference(
			NavigablePath navigablePath,
			String tableExpression,
			boolean resolve) {
		return mutatingTableReference.getTableReference( tableExpression );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void applyAffectedTableNames(Consumer<String> nameCollector) {
		mutatingTableReference.applyAffectedTableNames( nameCollector);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSourceAlias() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<TableGroupJoin> getTableGroupJoins() {
		return Collections.emptyList();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<TableGroupJoin> getNestedTableGroupJoins() {
		return Collections.emptyList();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean canUseInnerJoins() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addTableGroupJoin(TableGroupJoin join) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void prependTableGroupJoin(NavigablePath navigablePath, TableGroupJoin join) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addNestedTableGroupJoin(TableGroupJoin join) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void visitTableGroupJoins(Consumer<TableGroupJoin> consumer) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void visitNestedTableGroupJoins(Consumer<TableGroupJoin> consumer) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<TableReferenceJoin> getTableReferenceJoins() {
		return Collections.emptyList();
	}

}
