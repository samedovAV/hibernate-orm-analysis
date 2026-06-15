/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.hibernate.AnnotationException;
import org.hibernate.boot.spi.AccessType;
import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.boot.spi.PropertyData;
import org.hibernate.mapping.Component;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;
import org.hibernate.models.spi.ClassDetails;
import org.hibernate.models.spi.MemberDetails;
import org.hibernate.models.spi.MethodDetails;
import jakarta.persistence.Access;
import jakarta.persistence.IdClass;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

import static jakarta.persistence.InheritanceType.SINGLE_TABLE;
import static jakarta.persistence.InheritanceType.TABLE_PER_CLASS;
import static org.hibernate.boot.model.internal.EntityBinder.isEntity;
import static org.hibernate.boot.model.internal.EntityBinder.isMappedSuperclass;
import static org.hibernate.boot.model.internal.PropertyBinder.addElementsOfClass;
import static org.hibernate.boot.model.internal.PropertyBinder.hasIdAnnotation;
import static org.hibernate.boot.model.internal.PropertyBinder.isEmbeddedId;
import static org.hibernate.boot.spi.AccessType.DEFAULT;
import static org.hibernate.boot.spi.AccessType.getAccessStrategy;
import static org.hibernate.internal.util.ReflectHelper.OBJECT_CLASS_NAME;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Some extra data to the inheritance position of a class.
 *
 * @author Emmanuel Bernard
 */
public class InheritanceState {
	private ClassDetails classDetails;

	/**
	 * Has sibling (either mappedsuperclass entity)
	 */
	private boolean hasSiblings = false;

	/**
	 * a mother entity is available
	 */
	private boolean hasParents = false;
	private InheritanceType type;
	private boolean isEmbeddableSuperclass = false;
	private final Map<ClassDetails, InheritanceState> inheritanceStatePerClass;
	private final List<ClassDetails> classesToProcessForMappedSuperclass = new ArrayList<>();
	private final MetadataBuildingContext buildingContext;
	private AccessType accessType;
	private ElementsToProcess elementsToProcess;
	private Boolean hasIdClassOrEmbeddedId;

	public InheritanceState(
			ClassDetails classDetails,
			Map<ClassDetails, InheritanceState> inheritanceStatePerClass,
			MetadataBuildingContext buildingContext) {
		this.setClassDetails( classDetails );
		this.buildingContext = buildingContext;
		this.inheritanceStatePerClass = inheritanceStatePerClass;
		extractInheritanceType( classDetails );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private void extractInheritanceType(ClassDetails classDetails) {
		final boolean isMappedSuperclass = classDetails.hasDirectAnnotationUsage( MappedSuperclass.class );
		setEmbeddableSuperclass( isMappedSuperclass );
		final var defaultInheritanceType = isMappedSuperclass ? null : SINGLE_TABLE;
		final var inheritance = classDetails.getDirectAnnotationUsage( Inheritance.class );
		setType( inheritance != null ? inheritance.strategy() : defaultInheritanceType );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasTable() {
		return !hasParents() || SINGLE_TABLE != getType();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasDenormalizedTable() {
		return hasParents() && TABLE_PER_CLASS == getType();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static InheritanceState getInheritanceStateOfSuperEntity(
			ClassDetails classDetails,
			Map<ClassDetails, InheritanceState> states) {
		ClassDetails candidate = classDetails;
		do {
			candidate = candidate.getSuperClass();
			final var currentState = states.get( candidate );
			if ( currentState != null && !currentState.isEmbeddableSuperclass() ) {
				return currentState;
			}
		}
		while ( candidate != null && !isObjectClass( candidate ) );
		return null;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static InheritanceState getSuperclassInheritanceState(
			ClassDetails classDetails,
			Map<ClassDetails, InheritanceState> states) {
		ClassDetails superclass = classDetails;
		do {
			superclass = superclass.getSuperClass();
			final var currentState = states.get( superclass );
			if ( currentState != null ) {
				return currentState;
			}
		}
		while ( superclass != null && !isObjectClass( superclass ) );
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ClassDetails getClassDetails() {
		return classDetails;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setClassDetails(ClassDetails classDetails) {
		this.classDetails = classDetails;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasSiblings() {
		return hasSiblings;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setHasSiblings(boolean hasSiblings) {
		this.hasSiblings = hasSiblings;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasParents() {
		return hasParents;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setHasParents(boolean hasParents) {
		this.hasParents = hasParents;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public InheritanceType getType() {
		return type;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setType(InheritanceType type) {
		this.type = type;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isEmbeddableSuperclass() {
		return isEmbeddableSuperclass;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setEmbeddableSuperclass(boolean embeddableSuperclass) {
		isEmbeddableSuperclass = embeddableSuperclass;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ElementsToProcess postProcess(PersistentClass persistenceClass, EntityBinder entityBinder) {
		//make sure we run elements to process
		getElementsToProcess();
		addMappedSuperClassInMetadata( persistenceClass );
		entityBinder.setPropertyAccessType( accessType );
		return elementsToProcess;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void postProcess(Component component) {
		if ( classesToProcessForMappedSuperclass.isEmpty() ) {
			// Component classes might be processed more than once,
			// so only do this the first time we encounter them
			getMappedSuperclassesTillNextEntityOrdered();
		}
		addMappedSuperClassInMetadata( component );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public ClassDetails getClassWithIdClass(boolean evenIfSubclass) {
		if ( !evenIfSubclass && hasParents() ) {
			return null;
		}
		else if ( classDetails.hasDirectAnnotationUsage( IdClass.class ) ) {
			return classDetails;
		}
		else {
			final long count =
					Stream.concat( classDetails.getFields().stream(), classDetails.getMethods().stream() )
							.filter( PropertyBinder::isSimpleId )
							.count();
			if ( count > 1 ) {
				return classDetails;
			}
			else {
				final var state = getSuperclassInheritanceState( classDetails, inheritanceStatePerClass );
				return state == null ? null : state.getClassWithIdClass( true );
			}
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Boolean hasIdClassOrEmbeddedId() {
		if ( hasIdClassOrEmbeddedId == null ) {
			hasIdClassOrEmbeddedId = false;
			if ( getClassWithIdClass( true ) != null ) {
				hasIdClassOrEmbeddedId = true;
			}
			else {
				for ( var property : getElementsToProcess().getElements() ) {
					if ( isEmbeddedId( property.getAttributeMember() ) ) {
						hasIdClassOrEmbeddedId = true;
						break;
					}
				}
			}
		}
		return hasIdClassOrEmbeddedId;
	}

	/*
	 * Get the annotated elements and determine access type from hierarchy,
	 * guessing from @Id or @EmbeddedId presence if not specified.
	 * Change EntityBinder by side effect
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	ElementsToProcess getElementsToProcess() {
		if ( elementsToProcess == null ) {
			final var inheritanceState = inheritanceStatePerClass.get( classDetails );
			assert !inheritanceState.isEmbeddableSuperclass();

			getMappedSuperclassesTillNextEntityOrdered();

			final var defaultAccessType = determineDefaultAccessType();

			if ( classDetails.hasDirectAnnotationUsage( Access.class ) ) {
				accessType = getAccessStrategy( classDetails.getDirectAnnotationUsage( Access.class ).value() );
			}
			else {
				accessType = defaultAccessType;
			}

			final ArrayList<PropertyData> elements = new ArrayList<>();
			int idPropertyCount = 0;
			for ( var classToProcessForMappedSuperclass : classesToProcessForMappedSuperclass ) {
				final var container = new PropertyContainer( classToProcessForMappedSuperclass, classDetails, defaultAccessType );
				idPropertyCount = addElementsOfClass( elements, container, buildingContext, idPropertyCount );
			}
			if ( idPropertyCount == 0 && !inheritanceState.hasParents() ) {
				throw new AnnotationException( "Entity '" + classDetails.getName() + "' has no identifier"
						+ " (every '@Entity' class must declare or inherit at least one '@Id' or '@EmbeddedId' property)" );
			}
			elements.trimToSize();
			elementsToProcess = new ElementsToProcess( elements, idPropertyCount );
		}
		return elementsToProcess;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private AccessType determineDefaultAccessType() {

		// do not check for `@Access` to determine the default access type of the hierarchy

		final var idAccessType = determineAccessTypeFromId( classDetails );
		if ( idAccessType != null ) {
			return idAccessType;
		}

		// next consider the current class

		final var memberAccessType = determineAccessTypeFromMembers( classDetails );
		if ( memberAccessType != null ) {
			return memberAccessType;
		}

		// next consider the root class of the hierarchy

		final var rootEntity = getRootEntity();
		if ( rootEntity != null ) {
			final var rootMemberAccessType = determineAccessTypeFromMembers( rootEntity );
			if ( rootMemberAccessType != null ) {
				return rootMemberAccessType;
			}
		}

		// as an absolute last resort, fall back to looking at mapped superclasses

		for ( var candidate = classDetails.getSuperClass();
				candidate != null && !isObjectClass( candidate );
				candidate = candidate.getSuperClass() ) {
			if ( isMappedSuperclass( candidate ) ) {
				final var accessType = determineAccessTypeFromMembers( candidate );
				if ( accessType != null ) {
					return accessType;
				}
			}
		}

		return DEFAULT;
	}

	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	private static AccessType determineAccessTypeFromId(ClassDetails accessTypeSource) {
		for ( var candidate = accessTypeSource; !noSuperclass( candidate );
			candidate = candidate.getSuperClass() ) {
			if ( isEntityOrMappedSuperclass( candidate )
					&& !candidate.hasDirectAnnotationUsage( Access.class ) ) {
				for ( var method : candidate.getMethods() ) {
					if ( method.getMethodKind() == MethodDetails.MethodKind.GETTER
							&& hasIdAnnotation( method )
							&& canBeUsedToInferAccessType( method )	) {
						return AccessType.PROPERTY;
					}
				}
				for ( var field : candidate.getFields() ) {
					if ( hasIdAnnotation( field )
							&& canBeUsedToInferAccessType( field )	 ) {
						return AccessType.FIELD;
					}
				}
			}
		}
		return null;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private ClassDetails getRootEntity() {
		ClassDetails rootEntity = null;
		for ( var candidate = classDetails; candidate != null && !isObjectClass( candidate );
				candidate = candidate.getSuperClass() ) {
			if ( isEntity( candidate ) ) {
				rootEntity = candidate;
			}
		}
		return rootEntity;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private static AccessType determineAccessTypeFromMembers(ClassDetails classDetails) {
		for ( var field : classDetails.getFields() ) {
			if ( canBeUsedToInferAccessType( field )
					&& hasMappingAnnotation( field ) ) {
				return AccessType.FIELD;
			}
		}

		for ( var method : classDetails.getMethods() ) {
			if ( method.getMethodKind() == MethodDetails.MethodKind.GETTER
					&& canBeUsedToInferAccessType( method )
					&& hasMappingAnnotation( method ) ) {
				return AccessType.PROPERTY;
			}
		}

		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static boolean canBeUsedToInferAccessType(MemberDetails memberDetails) {
		return memberDetails.isPersistable()
			&& !memberDetails.hasDirectAnnotationUsage( Access.class )
			&& !memberDetails.hasDirectAnnotationUsage( Transient.class );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private static boolean hasMappingAnnotation(MemberDetails memberDetails) {
		final var annotations = memberDetails.getDirectAnnotationUsages();
		if ( annotations == null || annotations.isEmpty() ) {
			return false;
		}

		for ( var annotation : annotations ) {
			final var annotationType = annotation.annotationType();
			final var annotationName = annotationType.getName();
			if ( annotationName.startsWith( "jakarta.persistence." )
					&& !IGNORED_PERSISTENCE_ANNOTATIONS.contains( annotationName ) ) {
				return true;
			}
		}

		return false;
	}

	private static final Set<String> IGNORED_PERSISTENCE_ANNOTATIONS = Set.of(
			"jakarta.persistence.PostLoad",
			"jakarta.persistence.PostPersist",
			"jakarta.persistence.PostRemove",
			"jakarta.persistence.PostUpdate",
			"jakarta.persistence.PrePersist",
			"jakarta.persistence.PreRemove",
			"jakarta.persistence.PreUpdate",
			"jakarta.persistence.Transient",
			"jakarta.persistence.Access"
	);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static boolean noSuperclass(ClassDetails candidate) {
		return candidate == null || isObjectClass( candidate );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static boolean isEntityOrMappedSuperclass(ClassDetails candidate) {
		return isEntity( candidate ) || isMappedSuperclass( candidate );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static boolean isObjectClass(ClassDetails candidate) {
		return OBJECT_CLASS_NAME.equals( candidate.getName() );
	}

	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	private void getMappedSuperclassesTillNextEntityOrdered() {
		//ordered to allow proper messages on properties subclassing
		ClassDetails currentClassInHierarchy = classDetails;
		InheritanceState superclassState;
		do {
			classesToProcessForMappedSuperclass.add( 0, currentClassInHierarchy );
			ClassDetails superClass = currentClassInHierarchy;
			do {
				superClass = superClass.getSuperClass();
				superclassState = inheritanceStatePerClass.get( superClass );
			}
			while ( superClass != null
					&& !OBJECT_CLASS_NAME.equals( superClass.getClassName() )
					&& superclassState == null );
			currentClassInHierarchy = superClass;
		}
		while ( superclassState != null && superclassState.isEmbeddableSuperclass() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private void addMappedSuperClassInMetadata(Component component) {
		final var mappedSuperclass = processMappedSuperclass( component.getTable() );
		if ( mappedSuperclass != null ) {
			component.setMappedSuperclass( mappedSuperclass );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private void addMappedSuperClassInMetadata(PersistentClass persistentClass) {
		final var mappedSuperclass = processMappedSuperclass( persistentClass.getImplicitTable() );
		if ( mappedSuperclass != null ) {
			persistentClass.setSuperMappedSuperclass( mappedSuperclass );
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private org.hibernate.mapping.MappedSuperclass processMappedSuperclass(Table implicitTable) {
		//add @MappedSuperclass in the metadata
		// classes from 0 to n-1 are @MappedSuperclass and should be linked
		final var metadataCollector = buildingContext.getMetadataCollector();
		final var superEntityState = getInheritanceStateOfSuperEntity( classDetails, inheritanceStatePerClass );
		final var superEntity =
				superEntityState != null
						? metadataCollector.getEntityBinding( superEntityState.getClassDetails().getName() )
						: null;
		final int lastMappedSuperclass = isEmbeddableSuperclass
				? classesToProcessForMappedSuperclass.size()
				: classesToProcessForMappedSuperclass.size() - 1;
		org.hibernate.mapping.MappedSuperclass mappedSuperclass = null;
		for ( int index = 0; index < lastMappedSuperclass; index++ ) {
			final var parentSuperclass = mappedSuperclass;
			// todo (jpa32) : causes the mapped-superclass Class reference to be loaded...
			//		- but this is how it's always worked, so...
			final var mappedSuperclassDetails = classesToProcessForMappedSuperclass.get( index );
			final var mappedSuperclassJavaType = mappedSuperclassDetails.toJavaClass();
			//add MappedSuperclass if not already there
			mappedSuperclass = metadataCollector.getMappedSuperclass( mappedSuperclassJavaType );
			if ( mappedSuperclass == null ) {
				mappedSuperclass = new org.hibernate.mapping.MappedSuperclass( parentSuperclass, superEntity, implicitTable );
				mappedSuperclass.setMappedClass( mappedSuperclassJavaType );
				metadataCollector.addMappedSuperclass( mappedSuperclassJavaType, mappedSuperclass );
			}
		}
		return mappedSuperclass;
	}

	public static final class ElementsToProcess {
		private final List<PropertyData> properties;
		private final int idPropertyCount;

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public List<PropertyData> getElements() {
			return properties;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public int getIdPropertyCount() {
			return idPropertyCount;
		}

		private ElementsToProcess(List<PropertyData> properties, int idPropertyCount) {
			this.properties = properties;
			this.idPropertyCount = idPropertyCount;
		}
	}
}
