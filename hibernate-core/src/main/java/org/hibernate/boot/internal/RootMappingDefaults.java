/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.internal;

import java.util.EnumSet;

import org.hibernate.boot.models.xml.spi.PersistenceUnitMetadata;
import org.hibernate.boot.spi.EffectiveMappingDefaults;
import org.hibernate.boot.spi.MappingDefaults;

import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;

import static org.hibernate.internal.util.NullnessHelper.coalesce;
import static org.hibernate.internal.util.NullnessHelper.coalesceSuppliedValues;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Base set of defaults for all mappings
 *
 * @author Steve Ebersole
 */
public class RootMappingDefaults implements EffectiveMappingDefaults {
	private final String catalog;
	private final String schema;

	private final boolean quoteIdentifiers;

	private final String packageName;
	private final boolean autoImport;

	private final EnumSet<CascadeType> cascadeTypes;

	private final AccessType propertyAccessType;
	private final String propertyAccessStrategyName;

	private final org.hibernate.cache.spi.access.AccessType cacheAccessType;

	private final boolean entityLaziness;
	private final boolean collectionLaziness;

	private final String idColumnName;
	private final String discriminatorColumnName;
	private final String tenantIdColumnName;

	public RootMappingDefaults(
			MappingDefaults mappingDefaults,
			PersistenceUnitMetadata persistenceUnitMetadata) {
		this.catalog = coalesceSuppliedValues(
				mappingDefaults::getImplicitCatalogName,
				persistenceUnitMetadata::getDefaultCatalog
		);
		this.schema = coalesceSuppliedValues(
				mappingDefaults::getImplicitSchemaName,
				persistenceUnitMetadata::getDefaultSchema
		);

		// both use primitive boolean, so true when one is true
		this.quoteIdentifiers = mappingDefaults.shouldImplicitlyQuoteIdentifiers()
				|| persistenceUnitMetadata.useQuotedIdentifiers();

		this.packageName = mappingDefaults.getImplicitPackageName();
		this.autoImport = mappingDefaults.isAutoImportEnabled();

		this.cascadeTypes = persistenceUnitMetadata.getDefaultCascadeTypes();

		this.propertyAccessType = persistenceUnitMetadata.getAccessType();
		this.propertyAccessStrategyName = coalesceSuppliedValues(
				mappingDefaults::getImplicitPropertyAccessorName,
				persistenceUnitMetadata::getDefaultAccessStrategyName
		);

		this.cacheAccessType = mappingDefaults.getImplicitCacheAccessType();

		this.entityLaziness = mappingDefaults.areEntitiesImplicitlyLazy();
		this.collectionLaziness = mappingDefaults.areCollectionsImplicitlyLazy();

		this.idColumnName = coalesce(
				mappingDefaults.getImplicitIdColumnName(),
				DEFAULT_IDENTIFIER_COLUMN_NAME
		);
		this.discriminatorColumnName = coalesce(
				mappingDefaults.getImplicitDiscriminatorColumnName(),
				DEFAULT_DISCRIMINATOR_COLUMN_NAME
		);
		this.tenantIdColumnName = coalesce(
				mappingDefaults.getImplicitTenantIdColumnName(),
				DEFAULT_TENANT_IDENTIFIER_COLUMN_NAME
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDefaultCatalogName() {
		return catalog;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDefaultSchemaName() {
		return schema;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isDefaultQuoteIdentifiers() {
		return quoteIdentifiers;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDefaultIdColumnName() {
		return idColumnName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDefaultDiscriminatorColumnName() {
		return discriminatorColumnName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDefaultTenantIdColumnName() {
		return tenantIdColumnName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDefaultPackageName() {
		return packageName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isDefaultAutoImport() {
		return autoImport;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EnumSet<CascadeType> getDefaultCascadeTypes() {
		return cascadeTypes;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AccessType getDefaultPropertyAccessType() {
		return propertyAccessType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDefaultAccessStrategyName() {
		return propertyAccessStrategyName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isDefaultEntityLaziness() {
		return entityLaziness;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isDefaultCollectionLaziness() {
		return collectionLaziness;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public org.hibernate.cache.spi.access.AccessType getDefaultCacheAccessType() {
		return cacheAccessType;
	}

}
