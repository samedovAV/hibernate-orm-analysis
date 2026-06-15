/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * A container for multiple selectable (column, formula) mappings.
 *
 * @author Christian Beikov
 */
public interface SelectableMappings {
	/**
	 * The number of selectables
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getJdbcTypeCount();

	/**
	 * Get the selectable at the given position
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectableMapping getSelectable(int columnIndex);

	/**
	 * Visit each contained selectable mapping.
	 *
	 * As the selectables are iterated, we call `SelectionConsumer`
	 * passing along `offset` + our current iteration index.
	 *
	 * The return is the number of selectables we directly contain
	 *
	 * @see SelectableConsumer#accept(int, SelectableMapping)
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int forEachSelectable(int offset, SelectableConsumer consumer);

	/**
	 * Same as {@link #forEachSelectable(int, SelectableConsumer)}, with
	 * an implicit offset of `0`
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default int forEachSelectable(SelectableConsumer consumer) {
		return forEachSelectable( 0, consumer );
	}

}
