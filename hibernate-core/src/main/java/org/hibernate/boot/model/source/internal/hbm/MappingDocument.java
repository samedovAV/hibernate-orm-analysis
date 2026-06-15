/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.Set;

import org.hibernate.boot.jaxb.Origin;
import org.hibernate.boot.jaxb.hbm.spi.EntityInfo;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmHibernateMapping;
import org.hibernate.boot.model.TypeDefinitionRegistry;
import org.hibernate.boot.internal.TypeDefinitionRegistryStandardImpl;
import org.hibernate.boot.model.naming.ObjectNameNormalizer;
import org.hibernate.boot.model.source.internal.OverriddenMappingDefaults;
import org.hibernate.boot.model.source.spi.MetadataSourceProcessor;
import org.hibernate.boot.model.source.spi.ToolingHintContext;
import org.hibernate.boot.query.HbmResultSetMappingDescriptor;
import org.hibernate.boot.spi.BootstrapContext;
import org.hibernate.boot.spi.EffectiveMappingDefaults;
import org.hibernate.boot.spi.InFlightMetadataCollector;
import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.boot.spi.MetadataBuildingOptions;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.mapping.PersistentClass;


import static org.hibernate.boot.BootLogging.BOOT_LOGGER;
import static org.hibernate.boot.model.source.internal.hbm.Helper.collectToolingHints;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Aggregates together information about a mapping document.
 *
 * @author Steve Ebersole
 */
public class MappingDocument implements HbmLocalMetadataBuildingContext, MetadataSourceProcessor {

	private final JaxbHbmHibernateMapping documentRoot;
	private final Origin origin;
	private final MetadataBuildingContext rootBuildingContext;
	private final EffectiveMappingDefaults mappingDefaults;

	private final ToolingHintContext toolingHintContext;

	private final TypeDefinitionRegistry typeDefinitionRegistry;

	private final String contributor;

	public MappingDocument(
			String contributor,
			JaxbHbmHibernateMapping documentRoot,
			Origin origin,
			MetadataBuildingContext rootBuildingContext) {
		this.contributor = contributor;
		this.documentRoot = documentRoot;
		this.origin = origin;
		this.rootBuildingContext = rootBuildingContext;

		// todo : allow for a split in default-lazy for singular/plural

		mappingDefaults =
				new OverriddenMappingDefaults.Builder( rootBuildingContext.getEffectiveDefaults() )
						.setImplicitSchemaName( documentRoot.getSchema() )
						.setImplicitCatalogName( documentRoot.getCatalog() )
						.setImplicitPackageName( documentRoot.getPackage() )
						.setImplicitPropertyAccessorName( documentRoot.getDefaultAccess() )
//						.setImplicitCascadeStyleName( documentRoot.getDefaultCascade() )
						.setEntitiesImplicitlyLazy( documentRoot.isDefaultLazy() )
						.setAutoImportEnabled( documentRoot.isAutoImport() )
						.setPluralAttributesImplicitlyLazy( documentRoot.isDefaultLazy() )
						.build();

		toolingHintContext = collectToolingHints( null, documentRoot );

		typeDefinitionRegistry =
				new TypeDefinitionRegistryStandardImpl( rootBuildingContext.getTypeDefinitionRegistry() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JaxbHbmHibernateMapping getDocumentRoot() {
		return documentRoot;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ToolingHintContext getToolingHintContext() {
		return toolingHintContext;
	}


	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String determineEntityName(EntityInfo entityElement) {
		return determineEntityName( entityElement.getEntityName(), entityElement.getName() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static String qualifyIfNeeded(String name, String implicitPackageName) {
		if ( name == null ) {
			return null;
		}
		if ( name.indexOf( '.' ) < 0 && implicitPackageName != null ) {
			return implicitPackageName + '.' + name;
		}
		else {
			return name;
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String determineEntityName(String entityName, String clazz) {
		return entityName != null
				? entityName
				: qualifyIfNeeded( clazz, mappingDefaults.getDefaultPackageName() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String qualifyClassName(String name) {
		return qualifyIfNeeded( name, mappingDefaults.getDefaultPackageName() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistentClass findEntityBinding(String entityName, String clazz) {
		return getMetadataCollector().getEntityBinding( determineEntityName( entityName, clazz ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Origin getOrigin() {
		return origin;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public BootstrapContext getBootstrapContext() {
		return rootBuildingContext.getBootstrapContext();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public MetadataBuildingOptions getBuildingOptions() {
		return rootBuildingContext.getBuildingOptions();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EffectiveMappingDefaults getEffectiveDefaults() {
		return mappingDefaults;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public InFlightMetadataCollector getMetadataCollector() {
		return rootBuildingContext.getMetadataCollector();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public ObjectNameNormalizer getObjectNameNormalizer() {
		return rootBuildingContext.getObjectNameNormalizer();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TypeDefinitionRegistry getTypeDefinitionRegistry() {
		return typeDefinitionRegistry;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getCurrentContributorName() {
		return contributor;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void prepare() {
		// nothing to do here
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void processTypeDefinitions() {
		for ( var typeDef : documentRoot.getTypedef() ) {
			TypeDefinitionBinder.processTypeDefinition( this, typeDef );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void processQueryRenames() {
		for ( var renameBinding : documentRoot.getImport() ) {
			final String name = qualifyClassName( renameBinding.getClazz() );
			final String rename = renameBinding.getRename() == null
					? StringHelper.unqualify( name )
					: renameBinding.getRename();
			getMetadataCollector().addImport( rename, name );
			BOOT_LOGGER.importEntry( rename, name );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void processFilterDefinitions() {
		for ( var filterDefinitionBinding : documentRoot.getFilterDef() ) {
			FilterDefinitionBinder.processFilterDefinition( this, filterDefinitionBinding );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void processFetchProfiles() {
		for ( var fetchProfileBinding : documentRoot.getFetchProfile() ) {
			FetchProfileBinder.processFetchProfile( this, fetchProfileBinding );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void processAuxiliaryDatabaseObjectDefinitions() {
		for ( var auxDbObjectBinding : documentRoot.getDatabaseObject() ) {
			AuxiliaryDatabaseObjectBinder.processAuxiliaryDatabaseObject( this, auxDbObjectBinding );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void processNamedQueries() {
		for ( var namedQuery : documentRoot.getQuery() ) {
			NamedQueryBinder.processNamedQuery( this, namedQuery );
		}
		for ( var namedQuery : documentRoot.getSqlQuery() ) {
			NamedQueryBinder.processNamedNativeQuery( this, namedQuery );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void processIdentifierGenerators() {
		for ( var identifierGenerator : documentRoot.getIdentifierGenerator() ) {
			IdentifierGeneratorDefinitionBinder.processIdentifierGeneratorDefinition( this, identifierGenerator );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void prepareForEntityHierarchyProcessing() {
		// should *not* be called
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void processEntityHierarchies(Set<String> processedEntityNames) {
		// should *not* be called
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void postProcessEntityHierarchies() {
		// should *not* be called
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void processResultSetMappings() {
		documentRoot.getResultset()
				.forEach( hbmResultSetMapping ->
						getMetadataCollector().addResultSetMapping(
								new HbmResultSetMappingDescriptor( hbmResultSetMapping, rootBuildingContext ) ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void finishUp() {
		// nothing to do
	}

}
