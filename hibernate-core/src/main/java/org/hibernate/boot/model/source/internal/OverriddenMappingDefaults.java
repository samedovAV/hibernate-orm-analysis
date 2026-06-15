/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal;

import java.util.EnumSet;

import org.hibernate.boot.spi.EffectiveMappingDefaults;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.metamodel.CollectionClassification;

import jakarta.persistence.CascadeType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Represents a "nested level" in the mapping defaults stack.
 *
 * @author Steve Ebersole
 */
public class OverriddenMappingDefaults implements EffectiveMappingDefaults {
	private final String implicitSchemaName;
	private final String implicitCatalogName;
	private final boolean implicitlyQuoteIdentifiers;
	private final String implicitIdColumnName;
	private final String implicitTenantIdColumnName;
	private final String implicitDiscriminatorColumnName;
	private final String implicitPackageName;
	private final boolean autoImportEnabled;
	private final jakarta.persistence.AccessType implicitPropertyAccessType;
	private final String implicitPropertyAccessorName;
	private final boolean entitiesImplicitlyLazy;
	private final boolean pluralAttributesImplicitlyLazy;
	private final AccessType implicitCacheAccessType;
	private final EnumSet<CascadeType> cascadeTypes;
	private final CollectionClassification implicitListClassification;

	public OverriddenMappingDefaults(
			String implicitSchemaName,
			String implicitCatalogName,
			boolean implicitlyQuoteIdentifiers,
			String implicitIdColumnName,
			String implicitTenantIdColumnName,
			String implicitDiscriminatorColumnName,
			String implicitPackageName,
			boolean autoImportEnabled,
			EnumSet<CascadeType> cascadeTypes,
			jakarta.persistence.AccessType implicitPropertyAccessType,
			String implicitPropertyAccessorName,
			boolean entitiesImplicitlyLazy,
			boolean pluralAttributesImplicitlyLazy,
			AccessType implicitCacheAccessType,
			CollectionClassification implicitListClassification) {
		this.implicitSchemaName = implicitSchemaName;
		this.implicitCatalogName = implicitCatalogName;
		this.implicitlyQuoteIdentifiers = implicitlyQuoteIdentifiers;
		this.implicitIdColumnName = implicitIdColumnName;
		this.implicitTenantIdColumnName = implicitTenantIdColumnName;
		this.implicitDiscriminatorColumnName = implicitDiscriminatorColumnName;
		this.implicitPackageName = implicitPackageName;
		this.autoImportEnabled = autoImportEnabled;
		this.cascadeTypes = cascadeTypes;
		this.implicitPropertyAccessType = implicitPropertyAccessType;
		this.implicitPropertyAccessorName = implicitPropertyAccessorName;
		this.entitiesImplicitlyLazy = entitiesImplicitlyLazy;
		this.pluralAttributesImplicitlyLazy = pluralAttributesImplicitlyLazy;
		this.implicitCacheAccessType = implicitCacheAccessType;
		this.implicitListClassification = implicitListClassification;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDefaultSchemaName() {
		return implicitSchemaName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDefaultCatalogName() {
		return implicitCatalogName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isDefaultQuoteIdentifiers() {
		return implicitlyQuoteIdentifiers;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDefaultIdColumnName() {
		return implicitIdColumnName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDefaultDiscriminatorColumnName() {
		return implicitDiscriminatorColumnName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDefaultTenantIdColumnName() {
		return implicitTenantIdColumnName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDefaultPackageName() {
		return implicitPackageName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isDefaultAutoImport() {
		return autoImportEnabled;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EnumSet<CascadeType> getDefaultCascadeTypes() {
		return cascadeTypes;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public jakarta.persistence.AccessType getDefaultPropertyAccessType() {
		return implicitPropertyAccessType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDefaultAccessStrategyName() {
		return implicitPropertyAccessorName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isDefaultEntityLaziness() {
		return entitiesImplicitlyLazy;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isDefaultCollectionLaziness() {
		return pluralAttributesImplicitlyLazy;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AccessType getDefaultCacheAccessType() {
		return implicitCacheAccessType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CollectionClassification getDefaultListClassification() {
		return implicitListClassification;
	}

	public static class Builder {
		private String implicitSchemaName;
		private String implicitCatalogName;
		private boolean implicitlyQuoteIdentifiers;
		private String implicitIdColumnName;
		private String implicitTenantIdColumnName;
		private String implicitDiscriminatorColumnName;
		private String implicitPackageName;
		private boolean autoImportEnabled;
		private final EnumSet<CascadeType> implicitCascadeTypes;
		private jakarta.persistence.AccessType implicitPropertyAccessType;
		private String implicitPropertyAccessorName;
		private boolean entitiesImplicitlyLazy;
		private boolean pluralAttributesImplicitlyLazy;
		private AccessType implicitCacheAccessType;
		private CollectionClassification implicitListClassification;

		public Builder(EffectiveMappingDefaults parentDefaults) {
			this.implicitSchemaName = parentDefaults.getDefaultSchemaName();
			this.implicitCatalogName = parentDefaults.getDefaultCatalogName();
			this.implicitlyQuoteIdentifiers = parentDefaults.isDefaultQuoteIdentifiers();
			this.implicitIdColumnName = parentDefaults.getDefaultIdColumnName();
			this.implicitTenantIdColumnName = parentDefaults.getDefaultTenantIdColumnName();
			this.implicitDiscriminatorColumnName = parentDefaults.getDefaultDiscriminatorColumnName();
			this.implicitPackageName = parentDefaults.getDefaultPackageName();
			this.autoImportEnabled = parentDefaults.isDefaultAutoImport();

			this.implicitCascadeTypes = parentDefaults.getDefaultCascadeTypes();
			this.implicitPropertyAccessType = parentDefaults.getDefaultPropertyAccessType();
			this.implicitPropertyAccessorName = parentDefaults.getDefaultAccessStrategyName();
			this.entitiesImplicitlyLazy = parentDefaults.isDefaultEntityLaziness();
			this.pluralAttributesImplicitlyLazy = parentDefaults.isDefaultCollectionLaziness();
			this.implicitCacheAccessType = parentDefaults.getDefaultCacheAccessType();
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Builder setImplicitSchemaName(String implicitSchemaName) {
			if ( StringHelper.isNotEmpty( implicitSchemaName ) ) {
				this.implicitSchemaName = implicitSchemaName;
			}
			return this;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Builder setImplicitCatalogName(String implicitCatalogName) {
			if ( StringHelper.isNotEmpty( implicitCatalogName ) ) {
				this.implicitCatalogName = implicitCatalogName;
			}
			return this;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Builder setImplicitlyQuoteIdentifiers(boolean implicitlyQuoteIdentifiers) {
			this.implicitlyQuoteIdentifiers = implicitlyQuoteIdentifiers;
			return this;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Builder setImplicitIdColumnName(String implicitIdColumnName) {
			if ( StringHelper.isNotEmpty( implicitIdColumnName ) ) {
				this.implicitIdColumnName = implicitIdColumnName;
			}
			return this;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Builder setImplicitTenantIdColumnName(String implicitTenantIdColumnName) {
			if ( StringHelper.isNotEmpty( implicitTenantIdColumnName ) ) {
				this.implicitTenantIdColumnName = implicitTenantIdColumnName;
			}
			return this;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Builder setImplicitDiscriminatorColumnName(String implicitDiscriminatorColumnName) {
			if ( StringHelper.isNotEmpty( implicitDiscriminatorColumnName ) ) {
				this.implicitDiscriminatorColumnName = implicitDiscriminatorColumnName;
			}
			return this;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Builder setImplicitPackageName(String implicitPackageName) {
			if ( StringHelper.isNotEmpty( implicitPackageName ) ) {
				this.implicitPackageName = implicitPackageName;
			}
			return this;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Builder setAutoImportEnabled(boolean autoImportEnabled) {
			this.autoImportEnabled = autoImportEnabled;
			return this;
		}

//		public Builder setImplicitCascadeStyleName(String implicitCascadeStyleName) {
//			if ( StringHelper.isNotEmpty( implicitCascadeStyleName ) ) {
//				this.implicitCascadeStyleName = implicitCascadeStyleName;
//			}
//			return this;
//		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Builder setImplicitPropertyAccessType(jakarta.persistence.AccessType accessType) {
			if ( accessType != null ) {
				this.implicitPropertyAccessType = accessType;
			}
			return this;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Builder setImplicitPropertyAccessorName(String implicitPropertyAccessorName) {
			if ( StringHelper.isNotEmpty( implicitPropertyAccessorName ) ) {
				this.implicitPropertyAccessorName = implicitPropertyAccessorName;
			}
			return this;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Builder setEntitiesImplicitlyLazy(boolean entitiesImplicitlyLazy) {
			this.entitiesImplicitlyLazy = entitiesImplicitlyLazy;
			return this;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Builder setPluralAttributesImplicitlyLazy(boolean pluralAttributesImplicitlyLazy) {
			this.pluralAttributesImplicitlyLazy = pluralAttributesImplicitlyLazy;
			return this;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Builder setImplicitCacheAccessType(AccessType implicitCacheAccessType) {
			if ( implicitCacheAccessType != null ) {
				this.implicitCacheAccessType = implicitCacheAccessType;
			}
			return this;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public CollectionClassification getImplicitListClassification() {
			return implicitListClassification;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Builder setImplicitListClassification(CollectionClassification implicitListClassification) {
			if ( implicitListClassification != null ) {
				this.implicitListClassification = implicitListClassification;
			}
			return this;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public OverriddenMappingDefaults build() {
			return new OverriddenMappingDefaults(
					implicitSchemaName,
					implicitCatalogName,
					implicitlyQuoteIdentifiers,
					implicitIdColumnName,
					implicitTenantIdColumnName,
					implicitDiscriminatorColumnName,
					implicitPackageName,
					autoImportEnabled,
					implicitCascadeTypes,
					implicitPropertyAccessType,
					implicitPropertyAccessorName,
					entitiesImplicitlyLazy,
					pluralAttributesImplicitlyLazy,
					implicitCacheAccessType,
					implicitListClassification
			);
		}
	}
}
