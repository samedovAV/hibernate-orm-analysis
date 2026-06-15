/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.internal;

import org.hibernate.metamodel.mapping.EntityMappingType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Defines the ability to perform post-creation processing for entity mappings.
 *
 * @author Steve Ebersole
 */
public interface InFlightEntityMappingType extends EntityMappingType {
	/**
	 * Link an entity type with its super-type, if one.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void linkWithSuperType(MappingModelCreationProcess creationProcess) {
		// by default do nothing - support for legacy impls
	}

	/**
	 * Called from {@link #linkWithSuperType}.  A callback from the entity-type to
	 * the super-type it resolved.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void linkWithSubType(EntityMappingType sub, MappingModelCreationProcess creationProcess) {
		// by default do nothing - support for legacy impls
	}

	/**
	 * After all hierarchy types have been linked, this method is called to allow the
	 * mapping model to be prepared which generally includes creating attribute mapping
	 * descriptors, identifier mapping descriptor, etc.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void prepareMappingModel(MappingModelCreationProcess creationProcess) {
		// by default do nothing - support for legacy impls
	}
}
