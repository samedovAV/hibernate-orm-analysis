/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.internal;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.persistence.EntityListener;
import org.hibernate.boot.jaxb.mapping.spi.JaxbEntityMappingsImpl;
import org.hibernate.boot.models.spi.GlobalRegistrations;
import org.hibernate.boot.models.xml.spi.XmlDocumentContext;
import org.hibernate.models.spi.ClassDetails;
import org.hibernate.models.spi.ModelsContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.hibernate.boot.model.internal.EmbeddableBinder.isEmbeddable;
import static org.hibernate.boot.model.internal.EntityBinder.isEntity;
import static org.hibernate.boot.model.internal.EntityBinder.isMappedSuperclass;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * In-flight holder for various things as we process metadata sources
 *
 * @author Steve Ebersole
 */
public class DomainModelCategorizationCollector {
	private final GlobalRegistrationsImpl globalRegistrations;
	private final ModelsContext modelsContext;

	private final Set<ClassDetails> rootEntities = new HashSet<>();
	private final Map<String,ClassDetails> mappedSuperclasses = new HashMap<>();
	private final Map<String,ClassDetails> embeddables = new HashMap<>();

	public DomainModelCategorizationCollector(
			GlobalRegistrations globalRegistrations,
			ModelsContext modelsContext) {
		this.globalRegistrations = (GlobalRegistrationsImpl) globalRegistrations;
		this.modelsContext = modelsContext;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public GlobalRegistrationsImpl getGlobalRegistrations() {
		return globalRegistrations;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<ClassDetails> getRootEntities() {
		return rootEntities;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Map<String, ClassDetails> getMappedSuperclasses() {
		return mappedSuperclasses;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Map<String, ClassDetails> getEmbeddables() {
		return embeddables;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void apply(JaxbEntityMappingsImpl jaxbRoot, XmlDocumentContext xmlDocumentContext) {
		globalRegistrations.collectJavaTypeRegistrations( jaxbRoot.getJavaTypeRegistrations() );
		globalRegistrations.collectJdbcTypeRegistrations( jaxbRoot.getJdbcTypeRegistrations() );
		globalRegistrations.collectConverterRegistrations( jaxbRoot.getConverterRegistrations() );
		globalRegistrations.collectConverters( jaxbRoot.getConverters() );
		globalRegistrations.collectUserTypeRegistrations( jaxbRoot.getUserTypeRegistrations() );
		globalRegistrations.collectCompositeUserTypeRegistrations( jaxbRoot.getCompositeUserTypeRegistrations() );
		globalRegistrations.collectCollectionTypeRegistrations( jaxbRoot.getCollectionUserTypeRegistrations() );
		globalRegistrations.collectEmbeddableInstantiatorRegistrations( jaxbRoot.getEmbeddableInstantiatorRegistrations() );
		globalRegistrations.collectFilterDefinitions( jaxbRoot.getFilterDefinitions() );

		final var persistenceUnitMetadata = jaxbRoot.getPersistenceUnitMetadata();
		if ( persistenceUnitMetadata != null ) {
			final var persistenceUnitDefaults = persistenceUnitMetadata.getPersistenceUnitDefaults();
			if ( persistenceUnitDefaults != null ) {
				final var listenerContainer = persistenceUnitDefaults.getEntityListenerContainer();
				if ( listenerContainer != null ) {
					getGlobalRegistrations()
							.collectEntityListenerRegistrations( listenerContainer.getEntityListeners(), modelsContext );
				}
			}
		}

		getGlobalRegistrations().collectFetchProfiles( jaxbRoot.getFetchProfiles() );

		getGlobalRegistrations().collectIdGenerators( jaxbRoot );

		getGlobalRegistrations().collectQueryReferences( jaxbRoot, xmlDocumentContext );

		getGlobalRegistrations().collectDataBaseObject( jaxbRoot.getDatabaseObjects() );
		// todo (7.0) : named graphs?
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void apply(ClassDetails classDetails) {
		globalRegistrations.collectJavaTypeRegistrations( classDetails );
		globalRegistrations.collectJdbcTypeRegistrations( classDetails );
		globalRegistrations.collectConverterRegistrations( classDetails );
		globalRegistrations.collectUserTypeRegistrations( classDetails );
		globalRegistrations.collectCompositeUserTypeRegistrations( classDetails );
		globalRegistrations.collectCollectionTypeRegistrations( classDetails );
		globalRegistrations.collectEmbeddableInstantiatorRegistrations( classDetails );
		globalRegistrations.collectFilterDefinitions( classDetails );

		globalRegistrations.collectIdGenerators( classDetails );

		globalRegistrations.collectImportRename( classDetails );

		// todo : named queries
		// todo : named graphs

		if ( isMappedSuperclass( classDetails ) ) {
			if ( classDetails.getClassName() != null ) {
				mappedSuperclasses.put( classDetails.getClassName(), classDetails );
			}
		}
		else if ( isEntity( classDetails ) ) {
			if ( isRootEntity( classDetails ) ) {
				rootEntities.add( classDetails );
			}
		}
		else if ( isEmbeddable( classDetails ) ) {
			if ( classDetails.getClassName() != null ) {
				embeddables.put( classDetails.getClassName(), classDetails );
			}
		}

		if ( isConverter( classDetails ) ) {
			globalRegistrations.collectConverter( classDetails );
		}
		if ( isLifecycleEventHandler( classDetails ) ) {
			globalRegistrations.addTargetedJpaEventListener( classDetails );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static boolean isConverter(ClassDetails classDetails) {
		return classDetails.getClassName() != null && classDetails.isImplementor( AttributeConverter.class )
			|| classDetails.hasDirectAnnotationUsage( Converter.class );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static boolean isLifecycleEventHandler(ClassDetails classDetails) {
		return classDetails.hasDirectAnnotationUsage( EntityListener.class );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static boolean isRootEntity(ClassDetails classInfo) {
		// perform a series of opt-out checks against the super-type hierarchy

		// an entity is considered a root of the hierarchy if:
		// 		1) it has no super-types
		//		2) its super types contain no entities (MappedSuperclasses are allowed)

		var current = classInfo.getSuperClass();
		while ( current != null ) {
			if ( isEntity( current ) && !current.isAbstract() ) {
				// a non-abstract super type has `@Entity` -> classInfo cannot be a root entity
				return false;
			}
			current = current.getSuperClass();
		}

		// if we hit no opt-outs we have a root
		return true;
	}
}
