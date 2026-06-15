/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import org.hibernate.SessionFactory;
import org.hibernate.boot.model.IdentifierGeneratorDefinition;
import org.hibernate.boot.model.NamedEntityGraphDefinition;
import org.hibernate.boot.model.TypeDefinition;
import org.hibernate.boot.model.relational.Database;
import org.hibernate.boot.query.NamedHqlQueryDefinition;
import org.hibernate.boot.query.NamedNativeQueryDefinition;
import org.hibernate.boot.query.NamedProcedureCallDefinition;
import org.hibernate.boot.query.NamedResultSetMappingDescriptor;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.FetchProfile;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;
import org.hibernate.query.sqm.function.SqmFunctionDescriptor;
import org.hibernate.type.MappingContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Represents the ORM model as determined by aggregating the provided mapping sources.
 * An instance may be obtained by calling {@link MetadataSources#buildMetadata()}.
 *
 * @author Steve Ebersole
 *
 * @since 5.0
 */
public interface Metadata extends MappingContext {
	/**
	 * Get the builder for {@link SessionFactory} instances based on this metamodel.
	 *
	 * @return The builder for {@link SessionFactory} instances.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionFactoryBuilder getSessionFactoryBuilder();

	/**
	 * Short-hand form of building a {@link SessionFactory} through the builder without any additional
	 * option overrides.
	 *
	 * @return THe built SessionFactory.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionFactory buildSessionFactory();

	/**
	 * Gets the {@link UUID} for this metamodel.
	 *
	 * @return the UUID.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	UUID getUUID();

	/**
	 * Retrieve the database model.
	 *
	 * @return The database model.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Database getDatabase();

	/**
	 * Retrieves the PersistentClass entity metadata representation for all known entities.
	 *
	 * Returned collection is immutable
	 *
	 * @return All PersistentClass representations.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	java.util.Collection<PersistentClass> getEntityBindings();

	/**
	 * Retrieves the PersistentClass entity mapping metadata representation for
	 * the given entity name.
	 *
	 * @param entityName The entity name for which to retrieve the metadata.
	 *
	 * @return The entity mapping metadata, or {@code null} if no matching entity found.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PersistentClass getEntityBinding(String entityName);

	/**
	 * Retrieves the Collection metadata representation for all known collections.
	 *
	 * Returned collection is immutable
	 *
	 * @return All Collection representations.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	java.util.Collection<Collection> getCollectionBindings();

	/**
	 * Retrieves the collection mapping metadata for the given collection role.
	 *
	 * @param role The collection role for which to retrieve the metadata.
	 *
	 * @return The collection mapping metadata, or {@code null} if no matching collection found.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Collection getCollectionBinding(String role);

	/**
	 * Retrieves all defined imports (class renames).
	 *
	 * @return All imports
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String,String> getImports();

	/**
	 * Retrieve named query metadata by name.
	 *
	 * @return The named query metadata, or {@code null}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NamedHqlQueryDefinition<?> getNamedHqlQueryMapping(String name);

	/**
	 * Visit all named HQL query definitions
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitNamedHqlQueryDefinitions(Consumer<NamedHqlQueryDefinition<?>> definitionConsumer);

	/**
	 * Retrieve named SQL query metadata.
	 *
	 * @return The named query metadata, or {@code null}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NamedNativeQueryDefinition<?> getNamedNativeQueryMapping(String name);

	/**
	 * Visit all named native query definitions
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitNamedNativeQueryDefinitions(Consumer<NamedNativeQueryDefinition<?>> definitionConsumer);

	/**
	 * Retrieve named procedure metadata.
	 *
	 * @return The named procedure metadata, or {@code null}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NamedProcedureCallDefinition getNamedProcedureCallMapping(String name);

	/**
	 * Visit all named callable query definitions
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitNamedProcedureCallDefinition(Consumer<NamedProcedureCallDefinition> definitionConsumer);

	/**
	 * Retrieve the metadata for a named SQL result set mapping.
	 *
	 * @param name The mapping name.
	 *
	 * @return The named result set mapping metadata, or {@code null} if none found.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NamedResultSetMappingDescriptor getResultSetMapping(String name);

	/**
	 * Visit all named SQL result set mapping definitions
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitNamedResultSetMappingDefinition(Consumer<NamedResultSetMappingDescriptor> definitionConsumer);

	/**
	 * Retrieve a type definition by name.
	 *
	 * @return The named type definition, or {@code null}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TypeDefinition getTypeDefinition(String typeName);

	/**
	 * Retrieves the complete map of filter definitions.
	 *
	 * Returned map is immutable
	 *
	 * @return The filter definition map.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String,FilterDefinition> getFilterDefinitions();

	/**
	 * Retrieves a filter definition by name.
	 *
	 * @param name The name of the filter definition to retrieve
	 * .
	 * @return The filter definition, or {@code null}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FilterDefinition getFilterDefinition(String name);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FetchProfile getFetchProfile(String name);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	java.util.Collection<FetchProfile> getFetchProfiles();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NamedEntityGraphDefinition getNamedEntityGraph(String name);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, NamedEntityGraphDefinition> getNamedEntityGraphs();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	IdentifierGeneratorDefinition getIdentifierGenerator(String name);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	java.util.Collection<Table> collectTableMappings();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, SqmFunctionDescriptor> getSqlFunctionMap();

	/**
	 * All of the known model contributors
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<String> getContributors();
}
