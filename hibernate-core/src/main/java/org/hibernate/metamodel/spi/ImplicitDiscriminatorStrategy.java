/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.spi;

import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.metamodel.model.domain.NavigableRole;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Used in cases where we have no explicit {@linkplain org.hibernate.annotations.AnyDiscriminatorValue}
 * mapping which matches.
 *
 * @author Steve Ebersole
 */
public interface ImplicitDiscriminatorStrategy {
	/**
	 * Determine the discriminator value to use for the given {@code entityMapping}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object toDiscriminatorValue(EntityMappingType entityMapping, NavigableRole discriminatorRole, MappingMetamodelImplementor mappingModel);

	/**
	 * Determine the entity-mapping which matches the given {@code discriminatorValue}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityMappingType toEntityMapping(Object discriminatorValue, NavigableRole discriminatorRole, MappingMetamodelImplementor mappingModel);
}
