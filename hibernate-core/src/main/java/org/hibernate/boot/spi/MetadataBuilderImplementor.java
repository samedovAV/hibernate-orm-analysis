/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.spi;

import org.hibernate.boot.MetadataBuilder;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Internal API for {@link MetadataBuilder} exposing the building options being collected.
 *
 * @author Steve Ebersole
 */
public interface MetadataBuilderImplementor extends MetadataBuilder {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	BootstrapContext getBootstrapContext();

	/**
	 * Get the options being collected on this MetadataBuilder that will ultimately be used in
	 * building the Metadata.
	 *
	 * @return The current building options
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MetadataBuildingOptions getMetadataBuildingOptions();
}
