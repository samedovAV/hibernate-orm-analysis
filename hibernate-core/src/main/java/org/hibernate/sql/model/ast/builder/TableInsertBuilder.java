/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.ast.builder;

import org.hibernate.metamodel.mapping.SelectableConsumer;
import org.hibernate.metamodel.mapping.SelectableMapping;
import org.hibernate.sql.model.ast.TableInsert;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * {@link TableMutationBuilder} implementation for {@code insert} statements.
 *
 * @author Steve Ebersole
 */
public interface TableInsertBuilder
		extends TableMutationBuilder<TableInsert>,
		AssigningTableMutationBuilder<TableInsert>,
		SelectableConsumer {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasColumnAssignment(SelectableMapping selectableMapping);

	/**
	 * Allows using the insert builder as selectable consumer.
	 * @see org.hibernate.metamodel.mapping.ValuedModelPart#forEachInsertable(SelectableConsumer)
	 */
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void accept(int selectionIndex, SelectableMapping selectableMapping) {
		addValueColumn( selectableMapping );
	}
}
