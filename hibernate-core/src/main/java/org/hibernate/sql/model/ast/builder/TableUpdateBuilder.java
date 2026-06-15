/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.ast.builder;

import org.hibernate.metamodel.mapping.SelectableConsumer;
import org.hibernate.metamodel.mapping.SelectableMapping;
import org.hibernate.sql.model.MutationOperation;
import org.hibernate.sql.model.ast.LogicalTableUpdate;
import org.hibernate.sql.model.ast.RestrictedTableMutation;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * {@link TableMutationBuilder} implementation for {@code update} statements.
 *
 * @author Steve Ebersole
 */
public interface TableUpdateBuilder<O extends MutationOperation>
		extends RestrictedTableMutationBuilder<O, RestrictedTableMutation<O>>,
		AssigningTableMutationBuilder<RestrictedTableMutation<O>>,
		SelectableConsumer {

	/**
	 * Allows using the update builder as selectable consumer.
	 * @see org.hibernate.metamodel.mapping.ValuedModelPart#forEachUpdatable(SelectableConsumer)
	 */
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void accept(int selectionIndex, SelectableMapping selectableMapping) {
		addColumnAssignment( selectableMapping );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setWhere(String fragment);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LogicalTableUpdate<O> buildMutation();
}
