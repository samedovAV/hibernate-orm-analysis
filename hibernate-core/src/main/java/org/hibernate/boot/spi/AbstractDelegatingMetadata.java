/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.spi;

import org.hibernate.MappingException;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.model.IdentifierGeneratorDefinition;
import org.hibernate.boot.model.NamedEntityGraphDefinition;
import org.hibernate.boot.model.TypeDefinition;
import org.hibernate.boot.model.relational.Database;
import org.hibernate.boot.query.NamedHqlQueryDefinition;
import org.hibernate.boot.query.NamedNativeQueryDefinition;
import org.hibernate.boot.query.NamedProcedureCallDefinition;
import org.hibernate.boot.query.NamedResultSetMappingDescriptor;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.mapping.Component;
import org.hibernate.mapping.FetchProfile;
import org.hibernate.mapping.MappedSuperclass;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;
import org.hibernate.metamodel.mapping.DiscriminatorType;
import org.hibernate.query.named.NamedObjectRepository;
import org.hibernate.query.sqm.function.SqmFunctionDescriptor;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;
import org.hibernate.type.Type;
import org.hibernate.type.spi.TypeConfiguration;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Convenience base class for custom implementors of {@link MetadataImplementor} using delegation.
 *
 * @author Gunnar Morling
 *
 */
public abstract class AbstractDelegatingMetadata implements MetadataImplementor {

	private final MetadataImplementor delegate;

	public AbstractDelegatingMetadata(MetadataImplementor delegate) {
		this.delegate = delegate;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected MetadataImplementor delegate() {
		return delegate;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Type getIdentifierType(String className) throws MappingException {
		return delegate.getIdentifierType( className );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getIdentifierPropertyName(String className) throws MappingException {
		return delegate.getIdentifierPropertyName( className );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Type getReferencedPropertyType(String className, String propertyName) throws MappingException {
		return delegate.getReferencedPropertyType( className, propertyName );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder getSessionFactoryBuilder() {
		return delegate.getSessionFactoryBuilder();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryImplementor buildSessionFactory() {
		return delegate.buildSessionFactory();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public UUID getUUID() {
		return delegate.getUUID();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Database getDatabase() {
		return delegate.getDatabase();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Collection<PersistentClass> getEntityBindings() {
		return delegate.getEntityBindings();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public PersistentClass getEntityBinding(String entityName) {
		return delegate.getEntityBinding( entityName );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Collection<org.hibernate.mapping.Collection> getCollectionBindings() {
		return delegate.getCollectionBindings();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public org.hibernate.mapping.Collection getCollectionBinding(String role) {
		return delegate.getCollectionBinding( role );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Map<String, String> getImports() {
		return delegate.getImports();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public NamedHqlQueryDefinition<?> getNamedHqlQueryMapping(String name) {
		return delegate.getNamedHqlQueryMapping( name );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void visitNamedHqlQueryDefinitions(Consumer<NamedHqlQueryDefinition<?>> definitionConsumer) {
		delegate.visitNamedHqlQueryDefinitions( definitionConsumer );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public NamedNativeQueryDefinition<?> getNamedNativeQueryMapping(String name) {
		return delegate.getNamedNativeQueryMapping( name );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void visitNamedNativeQueryDefinitions(Consumer<NamedNativeQueryDefinition<?>> definitionConsumer) {
		delegate.visitNamedNativeQueryDefinitions( definitionConsumer );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public NamedProcedureCallDefinition getNamedProcedureCallMapping(String name) {
		return delegate.getNamedProcedureCallMapping( name );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void visitNamedProcedureCallDefinition(Consumer<NamedProcedureCallDefinition> definitionConsumer) {
		delegate.visitNamedProcedureCallDefinition( definitionConsumer );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public NamedResultSetMappingDescriptor getResultSetMapping(String name) {
		return delegate.getResultSetMapping( name );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void visitNamedResultSetMappingDefinition(Consumer<NamedResultSetMappingDescriptor> definitionConsumer) {
		delegate.visitNamedResultSetMappingDefinition( definitionConsumer );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TypeDefinition getTypeDefinition(String typeName) {
		return delegate.getTypeDefinition( typeName );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Map<String, FilterDefinition> getFilterDefinitions() {
		return delegate.getFilterDefinitions();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public FilterDefinition getFilterDefinition(String name) {
		return delegate.getFilterDefinition( name );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public FetchProfile getFetchProfile(String name) {
		return delegate.getFetchProfile( name );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Collection<FetchProfile> getFetchProfiles() {
		return delegate.getFetchProfiles();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public NamedEntityGraphDefinition getNamedEntityGraph(String name) {
		return delegate.getNamedEntityGraph( name );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Map<String, NamedEntityGraphDefinition> getNamedEntityGraphs() {
		return delegate.getNamedEntityGraphs();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public IdentifierGeneratorDefinition getIdentifierGenerator(String name) {
		return delegate.getIdentifierGenerator( name );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Collection<Table> collectTableMappings() {
		return delegate.collectTableMappings();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Map<String, SqmFunctionDescriptor> getSqlFunctionMap() {
		return delegate.getSqlFunctionMap();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public MetadataBuildingOptions getMetadataBuildingOptions() {
		return delegate.getMetadataBuildingOptions();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TypeConfiguration getTypeConfiguration() {
		return delegate.getTypeConfiguration();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmFunctionRegistry getFunctionRegistry() {
		return delegate.getFunctionRegistry();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void orderColumns(boolean forceOrdering) {
		delegate.orderColumns( false );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void validate() throws MappingException {
		delegate.validate();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Set<MappedSuperclass> getMappedSuperclassMappingsCopy() {
		return delegate.getMappedSuperclassMappingsCopy();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void initSessionFactory(SessionFactoryImplementor sessionFactory) {
		delegate.initSessionFactory( sessionFactory );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void visitRegisteredComponents(Consumer<Component> consumer) {
		delegate().visitRegisteredComponents( consumer );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Component getGenericComponent(Class<?> componentClass) {
		return delegate().getGenericComponent( componentClass );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public DiscriminatorType<?> resolveEmbeddableDiscriminatorType(
			Class<?> embeddableClass,
			Supplier<DiscriminatorType<?>> supplier) {
		return delegate().resolveEmbeddableDiscriminatorType( embeddableClass, supplier );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public NamedObjectRepository buildNamedQueryRepository() {
		return delegate().buildNamedQueryRepository();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Set<String> getContributors() {
		return delegate.getContributors();
	}
}
