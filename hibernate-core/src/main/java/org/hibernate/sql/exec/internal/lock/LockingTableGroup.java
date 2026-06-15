/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.exec.internal.lock;

import org.hibernate.metamodel.mapping.ModelPart;
import org.hibernate.metamodel.mapping.ModelPartContainer;
import org.hibernate.metamodel.mapping.SelectableMappings;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.sql.ast.tree.from.TableGroupJoin;
import org.hibernate.sql.ast.tree.from.TableReference;
import org.hibernate.sql.ast.tree.from.TableReferenceJoin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Collections.emptyList;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * TableGroup wrapping a {@linkplain TableLock table to be locked}.
 *
 * @author Steve Ebersole
 */
public class LockingTableGroup implements TableGroup {
	private final TableReference tableReference;
	private final String tableName;
	private final ModelPartContainer modelPart;
	private final NavigablePath navigablePath;
	private final SelectableMappings keyColumnMappings;

	private List<TableGroupJoin> tableGroupJoins;

	public LockingTableGroup(
			TableReference tableReference,
			String tableName,
			ModelPartContainer modelPart,
			SelectableMappings keyColumnMappings) {
		this.tableReference = tableReference;
		this.tableName = tableName;
		this.modelPart = modelPart;
		this.navigablePath = new NavigablePath( tableName );
		this.keyColumnMappings = keyColumnMappings;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SelectableMappings getKeyColumnMappings() {
		return keyColumnMappings;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NavigablePath getNavigablePath() {
		return navigablePath;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getGroupAlias() {
		return "";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ModelPartContainer getModelPart() {
		return modelPart;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSourceAlias() {
		return "";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<TableGroupJoin> getTableGroupJoins() {
		return tableGroupJoins == null
				? emptyList()
				: tableGroupJoins;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<TableGroupJoin> getNestedTableGroupJoins() {
		return List.of();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean canUseInnerJoins() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addTableGroupJoin(TableGroupJoin join) {
		if ( join.getJoinedGroup().isRealTableGroup() ) {
			throw new UnsupportedOperationException();
		}

		if ( tableGroupJoins == null ) {
			tableGroupJoins = new ArrayList<>();
		}
		tableGroupJoins.add( join );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void prependTableGroupJoin(NavigablePath navigablePath, TableGroupJoin join) {
		addTableGroupJoin( join );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addNestedTableGroupJoin(TableGroupJoin join) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void visitTableGroupJoins(Consumer<TableGroupJoin> consumer) {
		if ( tableGroupJoins != null ) {
			tableGroupJoins.forEach( consumer );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void visitNestedTableGroupJoins(Consumer<TableGroupJoin> consumer) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applyAffectedTableNames(Consumer<String> nameCollector) {
		nameCollector.accept( tableName );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableReference getPrimaryTableReference() {
		return tableReference;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<TableReferenceJoin> getTableReferenceJoins() {
		return List.of();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ModelPart getExpressionType() {
		return modelPart;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableReference getTableReference(NavigablePath navigablePath, String tableExpression, boolean resolve) {
		return tableName.equals( tableExpression ) ? tableReference : null;
	}
}
