/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A mapping model object representing a {@linkplain jakarta.persistence.MappedSuperclass mapped superclass}
 * of an entity class. A mapped superclass is not itself an entity, but it may declare persistent
 * attributes which are inherited by entity subclasses.
 *
 * @author Emmanuel Bernard
 */
public class MappedSuperclass implements IdentifiableTypeClass {
	private final MappedSuperclass superMappedSuperclass;
	private final PersistentClass superPersistentClass;
	private final List<Property> declaredProperties;
	private final Table implicitTable;
	private Class<?> mappedClass;
	private Property identifierProperty;
	private Property version;
	private Component identifierMapper;

	public MappedSuperclass(
			MappedSuperclass superMappedSuperclass,
			PersistentClass superPersistentClass,
			Table implicitTable) {
		this.superMappedSuperclass = superMappedSuperclass;
		this.superPersistentClass = superPersistentClass;
		this.implicitTable = implicitTable;
		this.declaredProperties = new ArrayList<>();
	}

	/**
	 * Returns the first superclass marked as @MappedSuperclass or null if:
	 *  - none exists
	 *  - or the first persistent superclass found is an @Entity
	 *
	 * @return the super MappedSuperclass
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MappedSuperclass getSuperMappedSuperclass() {
		return superMappedSuperclass;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasIdentifierProperty() {
		return getIdentifierProperty() != null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isVersioned() {
		return getVersion() != null;
	}

	/**
	 * Returns the PersistentClass of the first superclass marked as @Entity
	 * or null if none exists
	 *
	 * @return the PersistentClass of the superclass
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistentClass getSuperPersistentClass() {
		return superPersistentClass;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<Property> getDeclaredProperties() {
		return declaredProperties;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void addDeclaredProperty(Property property) {
		//Do not add duplicate properties
		final String name = property.getName();
		for ( var declaredProperty : declaredProperties ) {
			if ( name.equals( declaredProperty.getName() ) ) {
				return;
			}
		}
		declaredProperties.add( property );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getMappedClass() {
		return mappedClass;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setMappedClass(Class<?> mappedClass) {
		this.mappedClass = mappedClass;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Property getIdentifierProperty() {
		//get direct identifierMapper or the one from the super mappedSuperclass
		// or the one from the super persistentClass
		Property propagatedIdentifierProp = identifierProperty;
		if ( propagatedIdentifierProp == null ) {
			if ( superMappedSuperclass != null ) {
				propagatedIdentifierProp = superMappedSuperclass.getIdentifierProperty();
			}
			if (propagatedIdentifierProp == null && superPersistentClass != null){
				propagatedIdentifierProp = superPersistentClass.getIdentifierProperty();
			}
		}
		return propagatedIdentifierProp;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Property getDeclaredIdentifierProperty() {
		return identifierProperty;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setDeclaredIdentifierProperty(Property prop) {
		this.identifierProperty = prop;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Property getVersion() {
		//get direct version or the one from the super mappedSuperclass
		// or the one from the super persistentClass
		Property propagatedVersion = version;
		if (propagatedVersion == null) {
			if ( superMappedSuperclass != null ) {
				propagatedVersion = superMappedSuperclass.getVersion();
			}
			if (propagatedVersion == null && superPersistentClass != null){
				propagatedVersion = superPersistentClass.getVersion();
			}
		}
		return propagatedVersion;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Property getDeclaredVersion() {
		return version;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setDeclaredVersion(Property prop) {
		this.version = prop;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Component getIdentifierMapper() {
		//get direct identifierMapper or the one from the super mappedSuperclass
		// or the one from the super persistentClass
		Component propagatedMapper = identifierMapper;
		if ( propagatedMapper == null ) {
			if ( superMappedSuperclass != null ) {
				propagatedMapper = superMappedSuperclass.getIdentifierMapper();
			}
			if ( propagatedMapper == null && superPersistentClass != null ) {
				propagatedMapper = superPersistentClass.getIdentifierMapper();
			}
		}
		return propagatedMapper;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Component getDeclaredIdentifierMapper() {
		return identifierMapper;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setDeclaredIdentifierMapper(Component identifierMapper) {
		this.identifierMapper = identifierMapper;
	}

	/**
	 * Check to see if this MappedSuperclass defines a property with the given name.
	 *
	 * @param name The property name to check
	 *
	 * @return {@code true} if a property with that name exists; {@code false} if not
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean hasProperty(String name) {
		for ( var property : getDeclaredProperties() ) {
			if ( property.getName().equals( name ) ) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Check to see if a property with the given name exists in this MappedSuperclass
	 * or in any of its super hierarchy.
	 *
	 * @param name The property name to check
	 *
	 * @return {@code true} if a property with that name exists; {@code false} if not
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isPropertyDefinedInHierarchy(String name) {
		return hasProperty( name )
			|| superMappedSuperclass != null && superMappedSuperclass.isPropertyDefinedInHierarchy( name )
			|| superPersistentClass != null && superPersistentClass.isPropertyDefinedInHierarchy( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void prepareForMappingModel() {
		declaredProperties.sort( Comparator.comparing( Property::getName ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Table findTable(String name) {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Table getTable(String name) {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Join findSecondaryTable(String name) {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Join getSecondaryTable(String name) {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public IdentifiableTypeClass getSuperType() {
		return superPersistentClass != null
				? superPersistentClass
				: superMappedSuperclass;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<IdentifiableTypeClass> getSubTypes() {
		throw new UnsupportedOperationException( "Not implemented yet" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Table getImplicitTable() {
		return implicitTable;
	}
}
