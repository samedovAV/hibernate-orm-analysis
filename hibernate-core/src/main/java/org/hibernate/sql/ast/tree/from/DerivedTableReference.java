/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.from;

import java.util.List;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.mapping.ValuedModelPart;
import org.hibernate.spi.NavigablePath;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public abstract class DerivedTableReference extends AbstractTableReference {

	private final List<String> columnNames;
	private final boolean lateral;

	public DerivedTableReference(
			String identificationVariable,
			List<String> columnNames,
			boolean lateral,
			SessionFactoryImplementor sessionFactory) {
		super( identificationVariable, false );
		this.columnNames = columnNames;
		this.lateral = lateral;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTableId() {
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<String> getColumnNames() {
		return columnNames;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isLateral() {
		return lateral;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableReference resolveTableReference(
			NavigablePath navigablePath,
			String tableExpression) {
		throw new UnknownTableReferenceException(
				tableExpression,
				"TableReferences cannot be resolved relative to DerivedTableReferences - '" + tableExpression + "' : " + navigablePath
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableReference resolveTableReference(
			NavigablePath navigablePath,
			ValuedModelPart modelPart,
			String tableExpression) {
		throw new UnknownTableReferenceException(
				tableExpression,
				"TableReferences cannot be resolved relative to DerivedTableReferences - '" + tableExpression + "' : " + navigablePath
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableReference getTableReference(
			NavigablePath navigablePath,
			String tableExpression,
			boolean resolve) {
		return null;
	}

}
