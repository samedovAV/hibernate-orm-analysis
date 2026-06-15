/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.from;

import java.util.List;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.spi.NavigablePath;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractColumnReferenceQualifier implements ColumnReferenceQualifier {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract TableReference getPrimaryTableReference();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract List<TableReferenceJoin> getTableReferenceJoins();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract SessionFactoryImplementor getSessionFactory();

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// TableReference handling

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public TableReference getTableReference(
			NavigablePath navigablePath,
			String tableExpression,
			boolean resolve) {
		final TableReference primaryTableReference = getPrimaryTableReference().getTableReference(
				navigablePath,
				tableExpression,
				resolve
		);
		if ( primaryTableReference != null) {
			return primaryTableReference;
		}

		for ( TableReferenceJoin tableJoin : getTableReferenceJoins() ) {
			final TableReference tableReference = tableJoin.getJoinedTableReference().getTableReference(
					navigablePath,
					tableExpression,
					resolve
			);
			if ( tableReference != null) {
				return tableReference;
			}
		}

		return null;
	}

}
