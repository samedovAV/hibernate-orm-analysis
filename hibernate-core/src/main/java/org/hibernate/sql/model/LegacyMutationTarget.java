/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model;

import java.util.function.Consumer;

import org.hibernate.Incubating;
import org.hibernate.metamodel.mapping.ModelPartContainer;
import org.hibernate.metamodel.model.domain.NavigableRole;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Mutation target contract for the legacy (sequential) action queue.
 * <p>
 * Exposes table information as {@link TableMapping} instances, used by
 * legacy coordinators for executing mutations.
 *
 * @author Steve Ebersole
 */
@Incubating
public interface LegacyMutationTarget<T extends TableMapping> {
	/**
	 * The model role of this target
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NavigableRole getNavigableRole();

	/**
	 * The string representation of the role path
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getRolePath();

	/**
	 * The ModelPart describing the mutation target
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ModelPartContainer getTargetPart();

	/**
	 * Visit each mutable (non-inverse) table.
	 *
	 * @apiNote Inverse tables are excluded here - they are not mutable
	 * 		relative to this mapping
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void forEachMutableTable(Consumer<T> consumer);

	/**
	 * Same as {@link #forEachMutableTable} except that here the tables
	 * are visited in reverse order.
	 *
	 * @apiNote Inverse tables are excluded here - they are not mutable
	 * 		relative to this mapping
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void forEachMutableTableReverse(Consumer<T> consumer);

	/**
	 * The name of the table defining the identifier for this target
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getIdentifierTableName();

	/**
	 * The table mapping for the table containing the identifier
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T getIdentifierTableMapping();
}
