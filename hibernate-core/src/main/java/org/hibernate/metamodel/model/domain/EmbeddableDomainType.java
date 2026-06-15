/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain;

import java.util.Collection;

import jakarta.annotation.Nonnull;
import jakarta.persistence.metamodel.EmbeddableType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Hibernate extension to the JPA {@link EmbeddableType} contract.
 *
 * @apiNote Temporarily extends the deprecated EmbeddableType.  See the {@link EmbeddableType}
 * Javadocs for more information
 *
 * @author Steve Ebersole
 */
public interface EmbeddableDomainType<J>
		extends TreatableDomainType<J>, EmbeddableType<J> {

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Collection<? extends EmbeddableDomainType<? extends J>> getSubTypes();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isPolymorphic() {
		return getSuperType() != null || !getSubTypes().isEmpty();
	}
}
