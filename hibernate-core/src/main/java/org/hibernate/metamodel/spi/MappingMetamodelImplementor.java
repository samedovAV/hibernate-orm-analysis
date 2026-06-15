/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.spi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.hibernate.EntityNameResolver;
import org.hibernate.metamodel.MappingMetamodel;
import org.hibernate.query.spi.QueryParameterBindingTypeResolver;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface MappingMetamodelImplementor extends MappingMetamodel, QueryParameterBindingTypeResolver {

	/**
	 * Retrieves a set of all the collection roles in which the given entity is a participant, as either an
	 * index or an element.
	 *
	 * @param entityName The entity name for which to get the collection roles.
	 *
	 * @return set of all the collection roles in which the given entityName participates.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<String> getCollectionRolesByEntityParticipant(String entityName);

	/**
	 * Access to the EntityNameResolver instance that Hibernate is configured to
	 * use for determining the entity descriptor from an instance of an entity
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Collection<EntityNameResolver> getEntityNameResolvers();

	/**
	 * Get the names of all entities known to this Metamodel
	 *
	 * @return All the entity names
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String[] getAllEntityNames() {
		final var entityNames = new ArrayList<String>();
		forEachEntityDescriptor( entityPersister -> entityNames.add( entityPersister.getEntityName() ) );
		return entityNames.toArray( String[]::new );
	}

	/**
	 * Get the names of all collections known to this Metamodel
	 *
	 * @return All the entity names
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String[] getAllCollectionRoles() {
		final var collectionRoles = new ArrayList<String>();
		forEachCollectionDescriptor( collectionPersister -> collectionRoles.add( collectionPersister.getRole() ) );
		return collectionRoles.toArray( String[]::new );
	}
}
