/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.spi;

import java.util.Collection;
import java.util.Map;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.hibernate.CustomEntityDirtinessStrategy;
import org.hibernate.Incubating;
import org.hibernate.Internal;
import org.hibernate.SessionFactory;
import org.hibernate.SessionFactoryObserver;
import org.hibernate.StatementObserver;
import org.hibernate.action.queue.spi.ActionQueueFactory;
import org.hibernate.action.queue.spi.PlanningOptions;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cache.spi.CacheImplementor;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.creation.spi.SessionBuilderImplementor;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EntityCopyObserverFactory;
import org.hibernate.event.spi.EventEngine;
import org.hibernate.graph.GraphParser;
import org.hibernate.graph.RootGraph;
import org.hibernate.graph.spi.RootGraphImplementor;
import org.hibernate.event.service.spi.EventListenerGroups;
import org.hibernate.metamodel.model.domain.JpaMetamodel;
import org.hibernate.metamodel.spi.MappingMetamodelImplementor;
import org.hibernate.metamodel.spi.RuntimeMetamodelsImplementor;
import org.hibernate.proxy.EntityNotFoundDelegate;
import org.hibernate.query.spi.QueryEngine;
import org.hibernate.query.sql.spi.SqlTranslationEngine;
import org.hibernate.resource.beans.spi.ManagedBeanRegistry;
import org.hibernate.temporal.spi.ChangesetCoordinator;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.hibernate.sql.ast.spi.ParameterMarkerStrategy;
import org.hibernate.sql.exec.internal.JdbcSelectWithActions;
import org.hibernate.sql.exec.spi.JdbcSelectWithActionsBuilder;
import org.hibernate.sql.results.jdbc.spi.JdbcValuesMappingProducerProvider;
import org.hibernate.stat.spi.StatisticsImplementor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.spi.TypeConfiguration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Defines the internal contract between the {@link SessionFactory} and the internal
 * implementation of Hibernate.
 *
 * @see SessionFactory
 * @see org.hibernate.internal.SessionFactoryImpl
 *
 * @author Gavin King
 * @author Steve Ebersole
 */
public interface SessionFactoryImplementor extends SessionFactory {
	/**
	 * The UUID assigned to this {@code SessionFactory}.
	 * <p>
	 * The value is generated as a {@link java.util.UUID}, but kept as a string.
	 *
	 * @see org.hibernate.internal.SessionFactoryRegistry#getSessionFactory
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getUuid();

	/**
	 * Overrides {@link SessionFactory#openSession()} to widen the return type:
	 * this is useful for internal code depending on {@link SessionFactoryImplementor}
	 * as it would otherwise need to frequently resort to casting to the internal contract.
	 *
	 * @return the opened {@code Session}.
	 */
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionImplementor openSession();

	/**
	 * Obtain a {@linkplain org.hibernate.SessionBuilder session builder}
	 * for creating new instances of {@link org.hibernate.Session} with
	 * certain customized options.
	 */
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor withOptions();

	/**
	 * Get a non-transactional "current" session.
	 *
	 * @apiNote This is used by {@code hibernate-envers}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionImplementor openTemporarySession();

	/**
	 * Obtain the {@link CacheImplementor}.
	 */
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CacheImplementor getCache();

	/**
	 * Obtain the {@link StatisticsImplementor}.
	 */
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	StatisticsImplementor getStatistics();

	/**
	 * Obtain the {@link TypeConfiguration}
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TypeConfiguration getTypeConfiguration();

	/**
	 * Obtain the {@link RuntimeMetamodelsImplementor}
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	RuntimeMetamodelsImplementor getRuntimeMetamodels();

	/**
	 * Obtain the {@link MappingMetamodelImplementor}
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default MappingMetamodelImplementor getMappingMetamodel() {
		return getRuntimeMetamodels().getMappingMetamodel();
	}

	/**
	 * Obtain the {@link JpaMetamodel}
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default JpaMetamodel getJpaMetamodel() {
		return getRuntimeMetamodels().getJpaMetamodel();
	}

	/**
	 * Obtain the {@link QueryEngine}
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryEngine getQueryEngine();

	/**
	 * Obtain the {@link SqlTranslationEngine}
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqlTranslationEngine getSqlTranslationEngine();

	/**
	 * Access to the {@code ServiceRegistry} for this {@code SessionFactory}.
	 *
	 * @return The factory's ServiceRegistry
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ServiceRegistryImplementor getServiceRegistry();

	/**
	 * Get the EventEngine associated with this SessionFactory
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EventEngine getEventEngine();

	/**
	 * Obtain the {@link EntityNotFoundDelegate}
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityNotFoundDelegate getEntityNotFoundDelegate();

	/**
	 * Register a {@link SessionFactoryObserver} of this factory.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addObserver(@Nonnull SessionFactoryObserver observer);

	/**
	 * Obtain the {@link CustomEntityDirtinessStrategy}
	 */
	//todo make a Service ?
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CustomEntityDirtinessStrategy getCustomEntityDirtinessStrategy();

	/**
	 * Obtain the {@link CurrentTenantIdentifierResolver}
	 */
	//todo make a Service ?
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CurrentTenantIdentifierResolver<Object> getCurrentTenantIdentifierResolver();

	/**
	 * Object the current tenant identifier using the
	 * {@linkplain #getCurrentTenantIdentifierResolver() resolver}.
	 *
	 * @since 7.2
	 */
	@Incubating
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object resolveTenantIdentifier();

	/**
	 * The {@link JavaType} to use for a tenant identifier.
	 *
	 * @since 6.4
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JavaType<Object> getTenantIdentifierJavaType();

	/**
	 * Access to the {@linkplain EventListenerGroups event listener groups}.
	 *
	 * @since 7.0
	 */
	@Internal @Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EventListenerGroups getEventListenerGroups();

	/**
	 * Obtain the {@link ParameterMarkerStrategy} service.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ParameterMarkerStrategy getParameterMarkerStrategy();

	/**
	 * Obtain the {@link JdbcServices} service.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcValuesMappingProducerProvider getJdbcValuesMappingProducerProvider();

	/**
	 * Obtain the {@link EntityCopyObserverFactory} service.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityCopyObserverFactory getEntityCopyObserver();

	/**
	 * Obtain the {@link ClassLoaderService}.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ClassLoaderService getClassLoaderService();

	/**
	 * Obtain the {@link ManagedBeanRegistry} service.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ManagedBeanRegistry getManagedBeanRegistry();

	/**
	 * Obtain the {@link EventListenerRegistry} service.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EventListenerRegistry getEventListenerRegistry();

	/**
	 * Efficient access to the {@link ChangesetCoordinator}.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ChangesetCoordinator getChangesetCoordinator();

	/**
	 * Configuration for graph planning as part of the ActionQueue.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PlanningOptions getGraphPlanningOptions();

	/**
	 * Return an instance of {@link WrapperOptions} which is not backed by a session,
	 * and whose functionality is therefore incomplete.
	 *
	 * @apiNote Avoid using this operation.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	WrapperOptions getWrapperOptions();

	/**
	 * Get the {@linkplain SessionFactoryOptions options} used to build this factory.
	 */
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionFactoryOptions getSessionFactoryOptions();

	/**
	 * Access to the StatementObserver associated with this factory.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	StatementObserver getStatementObserver();

	/**
	 * Obtain the {@linkplain FilterDefinition definition of a filter} by name.
	 *
	 * @param filterName The name of a declared filter
	 */
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FilterDefinition getFilterDefinition(@Nonnull String filterName);

	/**
	 * Obtain a collection of {@link FilterDefinition}s representing all the
	 * {@linkplain org.hibernate.annotations.FilterDef#autoEnabled auto-enabled}
	 * filters.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Collection<FilterDefinition> getAutoEnabledFilters();

	/**
	 * Obtain the {@link JdbcServices} service.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcServices getJdbcServices();

	/**
	 * Obtain the {@link SqlStringGenerationContext}.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqlStringGenerationContext getSqlStringGenerationContext();

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// map these to Metamodel

	@Override
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	RootGraphImplementor<?> findEntityGraphByName(@Nonnull String name);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default <T> RootGraphImplementor<T> createEntityGraph(@Nonnull Class<T> entityType) {
		return (RootGraphImplementor<T>) SessionFactory.super.createEntityGraph( entityType );
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	RootGraphImplementor<Map<String, ?>> createGraphForDynamicEntity(@Nonnull String entityName);

	/**
	 * The best guess entity name for an entity not in an association
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String bestGuessEntityName(@Nonnull Object object);

	@Incubating
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default JdbcSelectWithActionsBuilder getJdbcSelectWithActionsBuilder(){
		return new JdbcSelectWithActions.Builder();
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <T> RootGraph<T> parseEntityGraph(@Nonnull Class<T> rootEntityClass, @Nonnull CharSequence graphText) {
		return GraphParser.parse( rootEntityClass, graphText.toString(), unwrap( SessionFactoryImplementor.class ) );
	}

	@Override @Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <T> RootGraph<T> parseEntityGraph(@Nonnull String rootEntityName, @Nonnull CharSequence graphText) {
		return GraphParser.parse( rootEntityName, graphText.toString(), unwrap( SessionFactoryImplementor.class ) );
	}

	@Override @Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <T> RootGraph<T> parseEntityGraph(@Nonnull CharSequence graphText) {
		return GraphParser.parse( graphText.toString(), unwrap( SessionFactoryImplementor.class ) );
	}

	/**
	 * Access to the factory for ActionQueue instances configured for this factory.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ActionQueueFactory getActionQueueFactory();
}
