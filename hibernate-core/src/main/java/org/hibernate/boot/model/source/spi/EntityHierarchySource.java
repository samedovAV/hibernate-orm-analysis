/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import org.hibernate.engine.OptimisticLockStyle;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models the source-agnostic view of an entity hierarchy.
 *
 * @author Steve Ebersole
 */
public interface EntityHierarchySource {
	/**
	 * Obtain the hierarchy's root type source.
	 *
	 * @return The root type source.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntitySource getRoot();

	/**
	 * The inheritance type/strategy for the hierarchy.
	 * <p>
	 * The entire hierarchy must have with the same inheritance strategy.
	 *
	 * @return The inheritance type.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	InheritanceType getHierarchyInheritanceType();

	/**
	 * Obtain source information about this entity's identifier.
	 *
	 * @return Identifier source information.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	IdentifierSource getIdentifierSource();

	/**
	 * Obtain the source information about the attribute used for optimistic locking.
	 *
	 * @return the source information about the attribute used for optimistic locking
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	VersionAttributeSource getVersionAttributeSource();

	/**
	 * Obtain the source information about the discriminator attribute for single table inheritance
	 *
	 * @return the source information about the discriminator attribute for single table inheritance
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiscriminatorSource getDiscriminatorSource();

	/**
	 * Obtain the source information about the multi-tenancy discriminator for this entity
	 *
	 * @return the source information about the multi-tenancy discriminator for this entity
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MultiTenancySource getMultiTenancySource();

	/**
	 * Is this root entity mutable?
	 *
	 * @return {@code true} indicates mutable; {@code false} non-mutable.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isMutable();

	/**
	 * Should explicit polymorphism (querying) be applied to this entity?
	 *
	 * @return {@code true} indicates explicit polymorphism; {@code false} implicit.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isExplicitPolymorphism();

	/**
	 * Obtain the specified extra where condition to be applied to this entity.
	 *
	 * @return The extra where condition
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getWhere();

	/**
	 * Obtain the row-id name for this entity
	 *
	 * @return The row-id name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getRowId();

	/**
	 * Obtain the optimistic locking style for this entity.
	 *
	 * @return The optimistic locking style.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	OptimisticLockStyle getOptimisticLockStyle();

	/**
	 * Obtain the caching configuration for this entity.
	 *
	 * @return The caching configuration.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Caching getCaching();

	/**
	 * Obtain the natural id caching configuration for this entity.
	 *
	 * @return The natural id caching configuration.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Caching getNaturalIdCaching();
}
