/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.results.internal;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.hibernate.metamodel.mapping.ModelPartContainer;
import org.hibernate.query.results.spi.ResultSetMapping;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.tree.from.AbstractTableGroup;
import org.hibernate.sql.ast.tree.from.TableReference;
import org.hibernate.sql.ast.tree.from.TableReferenceJoin;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * TableGroup implementation used while building
 * {@linkplain ResultSetMapping} references.
 *
 * @author Steve Ebersole
 */
public class TableGroupImpl extends AbstractTableGroup {

	private final TableReference primaryTableReference;

	public TableGroupImpl(
			NavigablePath navigablePath,
			String alias,
			TableReference primaryTableReference,
			ModelPartContainer container) {
		super( false, navigablePath, container, alias, null, null );
		this.primaryTableReference = primaryTableReference;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getGroupAlias() {
		return getSourceAlias();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applyAffectedTableNames(Consumer<String> nameCollector) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableReference getPrimaryTableReference() {
		return primaryTableReference;
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
		return primaryTableReference.getTableReference( navigablePath, tableExpression, resolve ) == null
				? super.getTableReference( navigablePath, tableExpression, resolve )
				: primaryTableReference;
	}

}
