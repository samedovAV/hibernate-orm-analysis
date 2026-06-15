/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel;

import java.util.function.Consumer;
import java.util.function.Function;

import org.hibernate.Incubating;
import org.hibernate.Internal;
import org.hibernate.action.queue.internal.constraint.ConstraintModel;
import org.hibernate.metamodel.mapping.EmbeddableValuedModelPart;
import org.hibernate.metamodel.mapping.MappingModelExpressible;
import org.hibernate.metamodel.model.domain.NavigableRole;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.spi.NavigablePath;
import org.hibernate.query.sqm.SqmExpressible;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.type.spi.TypeConfiguration;

import jakarta.persistence.metamodel.Metamodel;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Access to information about the runtime relational O/R mapping model.
 *
 * @apiNote This is an incubating SPI. Its name and package may change.
 *
 * @author Steve Ebersole
 */
@Incubating
public interface MappingMetamodel extends Metamodel {
	/**
	 * The {@link TypeConfiguration} this metamodel is associated with
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TypeConfiguration getTypeConfiguration();

	/**
	 * Access to constraint details from the domain model.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ConstraintModel getConstraintModel();

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Entity descriptors

	/**
	 * Visit all entity mapping descriptors defined in the model
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void forEachEntityDescriptor(Consumer<EntityPersister> action);

	/**
	 * Get an entity mapping descriptor based on its Hibernate entity-name
	 *
	 * @throws IllegalArgumentException if the name does not refer to an entity
	 *
	 * @see #findEntityDescriptor
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityPersister getEntityDescriptor(String entityName);

	/**
	 * Get an entity mapping descriptor based on its NavigableRole.
	 *
	 * @throws IllegalArgumentException if the name does not refer to an entity
	 *
	 * @see #findEntityDescriptor
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityPersister getEntityDescriptor(NavigableRole name);

	/**
	 * Get an EmbeddableMappingType based on its NavigableRole.
	 *
	 * @throws IllegalArgumentException if the role does not refer to an entity
	 *
	 * @see #findEntityDescriptor
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EmbeddableValuedModelPart getEmbeddableValuedModelPart(NavigableRole role);


	/**
	 * Get an entity mapping descriptor based on its Class.
	 *
	 * @throws IllegalArgumentException if the class is not an entity class
	 *
	 * @see #findEntityDescriptor
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityPersister getEntityDescriptor(Class<?> entityJavaType);

	/**
	 * Find an entity mapping descriptor based on its Hibernate entity name.
	 *
	 * @apiNote Returns {@code null} rather than throwing exception
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityPersister findEntityDescriptor(String entityName);

	/**
	 * Find an entity mapping descriptor based on its Class.
	 *
	 * @apiNote Returns {@code null} rather than throwing exception
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityPersister findEntityDescriptor(Class<?> entityJavaType);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isEntityClass(Class<?> entityJavaType);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getImportedName(String name);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Collection descriptors

	/**
	 * Visit the mapping descriptors for all collections defined in the model
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void forEachCollectionDescriptor(Consumer<CollectionPersister> action);

	/**
	 * Get a collection mapping descriptor based on its role
	 *
	 * @throws IllegalArgumentException if the role does not refer to a collection
	 *
	 * @see #findCollectionDescriptor
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CollectionPersister getCollectionDescriptor(String role);

	/**
	 * Get a collection mapping descriptor based on its role
	 *
	 * @throws IllegalArgumentException if the role does not refer to a collection
	 *
	 * @see #findCollectionDescriptor
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CollectionPersister getCollectionDescriptor(NavigableRole role);

	/**
	 * Find a collection mapping descriptor based on its role.  Returns
	 * {@code null} if the role does not refer to a collection
	 *
	 * @see #findCollectionDescriptor
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CollectionPersister findCollectionDescriptor(NavigableRole role);

	/**
	 * Find a collection mapping descriptor based on its role.  Returns
	 * {@code null} if the role does not refer to a collection
	 *
	 * @see #findCollectionDescriptor
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CollectionPersister findCollectionDescriptor(String role);

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// SQM model -> Mapping model

	// TODO Layer breaker used in SQM to SQL translation.
	//      Consider moving to QueryEngine or collaborators.
	@Internal
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MappingModelExpressible<?> resolveMappingExpressible(
			SqmExpressible<?> sqmExpressible,
			Function<NavigablePath, TableGroup> tableGroupLocator);
}
