/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.xml.spi;

import java.util.EnumSet;

import org.hibernate.property.access.spi.PropertyAccessStrategy;

import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Aggregator of information from {@code entity-mappings/persistence-unit-metadata}
 * and {@code entity-mappings/persistence-unit-metadata/persistence-unit-defaults}
 * across all mapping XML files in the persistence-unit.
 *
 * @author Steve Ebersole
 */
public interface PersistenceUnitMetadata {
	/**
	 * Whether XML mappings are complete for the entire persistent unit.
	 * See {@code entity-mappings/persistence-unit-metadata/xml-mapping-metadata-complete}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean areXmlMappingsComplete();

	/**
	 * Default schema in effect for the entire persistent unit.
	 * See {@code entity-mappings/persistence-unit-metadata/persistence-unit-defaults/schema}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getDefaultSchema();

	/**
	 * Default catalog in effect for the entire persistent unit.
	 * See {@code entity-mappings/persistence-unit-metadata/persistence-unit-defaults/catalog}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getDefaultCatalog();

	/**
	 * Default AccessType in effect for the entire persistence unit.
	 * See {@code entity-mappings/persistence-unit-metadata/persistence-unit-defaults/access}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AccessType getAccessType();

	/**
	 * Name of the default {@link PropertyAccessStrategy} in effect for the entire persistence unit
	 * See {@code entity-mappings/persistence-unit-metadata/persistence-unit-defaults/default-access}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getDefaultAccessStrategyName();

	/**
	 * Cascades to apply by default for this persistence unit
	 * See {@code entity-mappings/persistence-unit-metadata/persistence-unit-defaults/default-cascade}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EnumSet<CascadeType> getDefaultCascadeTypes();

	/**
	 * Whether to quote all database identifiers in the persistence unit
	 * See {@code entity-mappings/persistence-unit-metadata/persistence-unit-defaults/delimited-identifiers}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean useQuotedIdentifiers();
}
