/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;

import jakarta.persistence.DiscriminatorType;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * JAXB binding interface for discriminated association based attributes (any and many-to-any)
 *
 * @author Steve Ebersole
 */
public interface JaxbAnyMapping extends JaxbPersistentAttribute {
	/**
	 * Details about the logical association foreign-key
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Key getKey();

	/**
	 * Details about the discriminator
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Discriminator getDiscriminator();

	/**
	 * The key of a {@link JaxbAnyMapping} - the (logical) foreign-key value
	 *
	 * @author Steve Ebersole
	 */
	interface Key {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		List<JaxbColumnImpl> getColumns();
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		String getType();
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		String getJavaClass();
	}

	/**
	 * JAXB binding interface for describing the discriminator of a discriminated association
	 *
	 * @author Steve Ebersole
	 */
	interface Discriminator {
		/**
		 * The column holding the discriminator value
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		JaxbColumnImpl getColumn();

		/**
		 * The type of discriminator
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		DiscriminatorType getType();

		/**
		 * Mapping of discriminator-values to the corresponding entity names
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		List<? extends JaxbDiscriminatorMapping> getValueMappings();
	}
}
