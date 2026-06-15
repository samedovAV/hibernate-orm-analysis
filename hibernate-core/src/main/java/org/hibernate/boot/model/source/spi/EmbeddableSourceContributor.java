/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Contract for things that can contain EmbeddableSource definitions.
 *
 * @author Steve Ebersole
 */
public interface EmbeddableSourceContributor {
	/**
	 * Gets the source information about the embeddable/composition.
	 *
	 * @return The EmbeddableSource
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EmbeddableSource getEmbeddableSource();
}
