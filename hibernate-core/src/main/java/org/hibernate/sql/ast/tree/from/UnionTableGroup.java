/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.from;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.hibernate.persister.entity.UnionSubclassEntityPersister;
import org.hibernate.spi.NavigablePath;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Andrea Boriero
 */
public class UnionTableGroup extends AbstractTableGroup {
	private final UnionTableReference tableReference;

	public UnionTableGroup(
			boolean canUseInnerJoins,
			NavigablePath navigablePath,
			UnionTableReference tableReference,
			UnionSubclassEntityPersister modelPart,
			String sourceAlias) {
		super( canUseInnerJoins, navigablePath, modelPart, sourceAlias, null, null );
		this.tableReference = tableReference;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applyAffectedTableNames(Consumer<String> nameCollector) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public UnionTableReference getPrimaryTableReference() {
		return tableReference;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<TableReferenceJoin> getTableReferenceJoins() {
		return Collections.emptyList();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TableReference getTableReference(
			NavigablePath navigablePath,
			String tableExpression,
			boolean resolve) {
		if ( tableReference.getTableReference( navigablePath, tableExpression, resolve ) != null ) {
			return tableReference;
		}
		return super.getTableReference( navigablePath, tableExpression, resolve );
	}
}
