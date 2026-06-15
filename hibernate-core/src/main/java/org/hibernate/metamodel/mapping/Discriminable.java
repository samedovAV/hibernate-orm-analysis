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
 * Anything that has a discriminator associated with it.
 */
public interface Discriminable {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiscriminatorMapping getDiscriminatorMapping();

	/**
	 * Apply the discriminator as a predicate via the {@code predicateConsumer}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void applyDiscriminator(
			Consumer<Predicate> predicateConsumer,
			String alias,
			TableGroup tableGroup,
			SqlAstCreationState creationState);
}
