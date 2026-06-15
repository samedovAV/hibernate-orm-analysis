/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Additional contract describing the source of an identifier mapping whose {@linkplain #getNature() nature} is
 * {@link org.hibernate.id.EntityIdentifierNature#NON_AGGREGATED_COMPOSITE}.
 * <p>
 * Think {@link jakarta.persistence.IdClass}
 *
 * @author Steve Ebersole
 */
public interface IdentifierSourceNonAggregatedComposite extends CompositeIdentifierSource {
	/**
	 * Obtain the source descriptor for the identifier attribute.
	 *
	 * @return The identifier attribute source.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<SingularAttributeSource> getAttributeSourcesMakingUpIdentifier();

	/**
	 * Retrieve the source information for the {@link jakarta.persistence.IdClass} definition
	 *
	 * @return The IdClass source information, or {@code null} if none.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EmbeddableSource getIdClassSource();
}
