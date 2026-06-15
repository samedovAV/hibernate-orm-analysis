/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.from;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.hibernate.metamodel.mapping.EmbeddableMappingType;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.tree.expression.Expression;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A table group for functions that produce embeddable typed results.
 */
public class EmbeddableFunctionTableGroup extends AbstractTableGroup {

	private final EmbeddableFunctionTableReference tableReference;

	public EmbeddableFunctionTableGroup(
			NavigablePath navigablePath,
			EmbeddableMappingType embeddableMappingType,
			Expression expression) {
		super(
				false,
				navigablePath,
				embeddableMappingType,
				null,
				null,
				null
		);
		this.tableReference = new EmbeddableFunctionTableReference( navigablePath, embeddableMappingType, expression );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getGroupAlias() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void applyAffectedTableNames(Consumer<String> nameCollector) {
		tableReference.applyAffectedTableNames( nameCollector );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableReference getPrimaryTableReference() {
		return tableReference;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<TableReferenceJoin> getTableReferenceJoins() {
		return Collections.emptyList();
	}

}
