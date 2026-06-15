/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;


import org.hibernate.engine.spi.IdentifierValue;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Mapping for a composite identifier
 *
 * @author Andrea Boriero
 */
public interface CompositeIdentifierMapping extends EntityIdentifierMapping, EmbeddableValuedModelPart {

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default int getFetchableKey() {
		return -1;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default IdentifierValue getUnsavedStrategy() {
		return IdentifierValue.UNDEFINED;
	}

	/**
	 * Does the identifier have a corresponding EmbeddableId or IdClass?
	 *
	 * @return false if there is not an IdCass or an EmbeddableId
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasContainingClass();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EmbeddableMappingType getPartMappingType();

	/**
	 * Returns the embeddable type descriptor of the id-class, if there is one,
	 * otherwise the one of the virtual embeddable mapping type.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EmbeddableMappingType getMappedIdEmbeddableTypeDescriptor();
}
