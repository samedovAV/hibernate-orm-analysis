/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;


import org.hibernate.Incubating;
import org.hibernate.sql.ast.tree.expression.ColumnReference;
import org.hibernate.sql.model.ast.ColumnValueBinding;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Metadata about temporal columns for entities enabled for temporal history.
 *
 * @see org.hibernate.annotations.Temporal
 *
 * @author Gavin King
 *
 * @since 7.4
 */
@Incubating
public interface TemporalMapping extends AuxiliaryMapping {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectableMapping getStartingColumnMapping();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectableMapping getEndingColumnMapping();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ColumnValueBinding createStartingValueBinding(ColumnReference startingColumnReference);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ColumnValueBinding createEndingValueBinding(ColumnReference endingColumnReference);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ColumnValueBinding createNullEndingValueBinding(ColumnReference endingColumnReference);
}
