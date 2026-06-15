/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import org.hibernate.engine.FetchStyle;
import org.hibernate.engine.FetchTiming;
import org.hibernate.sql.results.graph.FetchOptions;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Details about the discriminator for an entity hierarchy.
 *
 * @implNote All {@linkplain EntityMappingType entity-mappings} within the
 * hierarchy share the same EntityDiscriminatorMapping instance.
 *
 * @see jakarta.persistence.DiscriminatorColumn
 * @see jakarta.persistence.DiscriminatorValue
 *
 * @author Steve Ebersole
 */
public interface EntityDiscriminatorMapping extends DiscriminatorMapping, FetchOptions {

	String DISCRIMINATOR_ROLE_NAME = "{discriminator}";
	String LEGACY_DISCRIMINATOR_NAME = "class";

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static boolean matchesRoleName(String name) {
		return DISCRIMINATOR_ROLE_NAME.equals( name )
			|| LEGACY_DISCRIMINATOR_NAME.equalsIgnoreCase( name );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getPartName() {
		return DISCRIMINATOR_ROLE_NAME;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getFetchableName() {
		return getPartName();
	}

	/**
	 * Is the discriminator defined by a physical column?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasPhysicalColumn();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default int getFetchableKey() {
		return -2;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default FetchOptions getMappedFetchOptions() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default FetchStyle getStyle() {
		return FetchStyle.JOIN;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default FetchTiming getTiming() {
		return FetchTiming.IMMEDIATE;
	}

}
