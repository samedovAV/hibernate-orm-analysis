/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.spi;

import org.hibernate.boot.models.xml.spi.PersistenceUnitMetadata;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contract for things that need to be aware of JPA {@code orm.xml}-defined persistence-unit-defaults.
 * Only {@link MetadataBuildingOptions} are supported to implement this contract.
 *
 * @since 5.0
 *
 * @author Steve Ebersole
 */
public interface JpaOrmXmlPersistenceUnitDefaultAware {
	/**
	 * Represents the {@code persistence-unit-defaults} to be applied
	 */
	interface JpaOrmXmlPersistenceUnitDefaults {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		String getDefaultSchemaName();
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		String getDefaultCatalogName();
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		boolean shouldImplicitlyQuoteIdentifiers();
	}

	/**
	 * Apply the {@code orm.xml}-defined {@code persistence-unit-defaults} values.
	 *
	 * @param jpaOrmXmlPersistenceUnitDefaults The {@code persistence-unit-defaults} values
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void apply(JpaOrmXmlPersistenceUnitDefaults jpaOrmXmlPersistenceUnitDefaults);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void apply(PersistenceUnitMetadata persistenceUnitMetadata);
}
