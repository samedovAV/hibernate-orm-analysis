/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import java.util.function.Consumer;

import org.hibernate.sql.ast.spi.SqlAstCreationState;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Things which can have {@link org.hibernate.annotations.SQLRestriction}
 * declarations - entities and collections
 *
 * @see FilterRestrictable
 */
public interface WhereRestrictable {

	/**
	 * Does this restrictable have a where restriction?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasWhereRestrictions();

	/**
	 * Apply the {@link org.hibernate.annotations.SQLRestriction} restrictions
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void applyWhereRestrictions(
			Consumer<Predicate> predicateConsumer,
			TableGroup tableGroup,
			boolean useQualifier,
			SqlAstCreationState creationState);
}
