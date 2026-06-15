/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.internal;

import java.util.function.Supplier;

import org.hibernate.CustomEntityDirtinessStrategy;
import org.hibernate.EntityNameResolver;
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.SessionFactoryObserver;
import org.hibernate.StatementObserver;
import org.hibernate.annotations.CacheLayout;
import org.hibernate.audit.AuditStrategy;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.spi.BootstrapContext;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.boot.spi.SessionFactoryBuilderImplementor;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.bytecode.internal.SessionFactoryObserverForBytecodeEnhancer;
import org.hibernate.bytecode.spi.BytecodeProvider;
import org.hibernate.cache.spi.TimestampsCacheFactory;
import org.hibernate.temporal.TemporalTableStrategy;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.context.spi.TenantCredentialsMapper;
import org.hibernate.context.spi.TenantSchemaMapper;
import org.hibernate.proxy.EntityNotFoundDelegate;
import org.hibernate.query.sqm.function.SqmFunctionDescriptor;
import org.hibernate.resource.jdbc.spi.PhysicalConnectionHandlingMode;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.hibernate.type.format.FormatMapper;

import jakarta.persistence.criteria.Nulls;

import static org.hibernate.internal.SessionFactoryRegistry.instantiateSessionFactory;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Gail Badner
 * @author Steve Ebersole
 */
public class SessionFactoryBuilderImpl implements SessionFactoryBuilderImplementor {
	private final MetadataImplementor metadata;
	private final SessionFactoryOptionsBuilder optionsBuilder;
	private final BootstrapContext bootstrapContext;

	public SessionFactoryBuilderImpl(MetadataImplementor metadata, BootstrapContext bootstrapContext) {
		this(
				metadata,
				new SessionFactoryOptionsBuilder(
						metadata.getMetadataBuildingOptions().getServiceRegistry(),
						bootstrapContext
				),
				bootstrapContext
		);
	}

	public SessionFactoryBuilderImpl(MetadataImplementor metadata, SessionFactoryOptionsBuilder optionsBuilder, BootstrapContext context) {
		this.metadata = metadata;
		this.optionsBuilder = optionsBuilder;
		this.bootstrapContext = context;

		if ( metadata.getSqlFunctionMap() != null ) {
			for ( var sqlFunctionEntry : metadata.getSqlFunctionMap().entrySet() ) {
				applySqlFunction( sqlFunctionEntry.getKey(), sqlFunctionEntry.getValue() );
			}
		}

		final var bytecodeProvider =
				metadata.getMetadataBuildingOptions().getServiceRegistry()
						.getService( BytecodeProvider.class );
		addSessionFactoryObservers( new SessionFactoryObserverForBytecodeEnhancer( bytecodeProvider ) );
		addSessionFactoryObservers( new SessionFactoryObserverForNamedQueryValidation( metadata ) );
		addSessionFactoryObservers( new SessionFactoryObserverForSchemaExport( metadata ) );
		addSessionFactoryObservers( new SessionFactoryObserverForRegistration() );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyBeanManager(Object beanManager) {
		optionsBuilder.applyBeanManager(  beanManager );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyValidatorFactory(Object validatorFactory) {
		optionsBuilder.applyValidatorFactory( validatorFactory );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applyName(String sessionFactoryName) {
		optionsBuilder.applySessionFactoryName( sessionFactoryName );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applyNameAsJndiName(boolean isJndiName) {
		optionsBuilder.enableSessionFactoryNameAsJndiName( isJndiName );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applyAutoClosing(boolean enabled) {
		optionsBuilder.enableSessionAutoClosing( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applyAutoFlushing(boolean enabled) {
		optionsBuilder.enableSessionAutoFlushing( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applyJtaTrackingByThread(boolean enabled) {
		optionsBuilder.enableJtaTrackingByThread( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applyPreferUserTransactions(boolean preferUserTransactions) {
		optionsBuilder.enablePreferUserTransaction( preferUserTransactions );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applyStatisticsSupport(boolean enabled) {
		optionsBuilder.enableStatisticsSupport( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder addSessionFactoryObservers(SessionFactoryObserver... observers) {
		optionsBuilder.addSessionFactoryObservers( observers );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyInterceptor(Interceptor interceptor) {
		optionsBuilder.applyInterceptor( interceptor );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyStatelessInterceptor(Class<? extends Interceptor> statelessInterceptorClass) {
		optionsBuilder.applyStatelessInterceptor( statelessInterceptorClass );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applyStatelessInterceptor(Supplier<? extends Interceptor> statelessInterceptorSupplier) {
		optionsBuilder.applyStatelessInterceptorSupplier( statelessInterceptorSupplier );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyStatementObserver(StatementObserver statementObserver) {
		optionsBuilder.applyStatementObserver( statementObserver );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyStatementInspector(StatementInspector statementInspector) {
		optionsBuilder.applyStatementInspector( statementInspector );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyCustomEntityDirtinessStrategy(CustomEntityDirtinessStrategy strategy) {
		optionsBuilder.applyCustomEntityDirtinessStrategy( strategy );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder addEntityNameResolver(EntityNameResolver... entityNameResolvers) {
		optionsBuilder.addEntityNameResolvers( entityNameResolvers );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyEntityNotFoundDelegate(EntityNotFoundDelegate entityNotFoundDelegate) {
		optionsBuilder.applyEntityNotFoundDelegate( entityNotFoundDelegate );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applyIdentifierRollbackSupport(boolean enabled) {
		optionsBuilder.enableIdentifierRollbackSupport( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applyNullabilityChecking(boolean enabled) {
		optionsBuilder.enableNullabilityChecking( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applyLazyInitializationOutsideTransaction(boolean enabled) {
		optionsBuilder.allowLazyInitializationOutsideTransaction( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyDefaultBatchFetchSize(int size) {
		optionsBuilder.applyDefaultBatchFetchSize( size );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyMaximumFetchDepth(int depth) {
		optionsBuilder.applyMaximumFetchDepth( depth );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applySubselectFetchEnabled(boolean enabled) {
		optionsBuilder.applySubselectFetchEnabled( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyDefaultNullPrecedence(Nulls nullPrecedence) {
		optionsBuilder.applyDefaultNullPrecedence( nullPrecedence );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applyOrderingOfInserts(boolean enabled) {
		optionsBuilder.enableOrderingOfInserts( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applyOrderingOfUpdates(boolean enabled) {
		optionsBuilder.enableOrderingOfUpdates( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyMultiTenancy(boolean enabled) {
		optionsBuilder.applyMultiTenancy(enabled);
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyCurrentTenantIdentifierResolver(CurrentTenantIdentifierResolver<?> resolver) {
		optionsBuilder.applyCurrentTenantIdentifierResolver( resolver );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyTenantSchemaMapper(TenantSchemaMapper<?> mapper) {
		optionsBuilder.applyTenantSchemaMapper( mapper );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyTenantCredentialsMapper(TenantCredentialsMapper<?> mapper) {
		optionsBuilder.applyTenantCredentialsMapper( mapper );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applyNamedQueryCheckingOnStartup(boolean enabled) {
		optionsBuilder.enableNamedQueryCheckingOnStartup( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applySecondLevelCacheSupport(boolean enabled) {
		optionsBuilder.enableSecondLevelCacheSupport( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applyQueryCacheSupport(boolean enabled) {
		optionsBuilder.enableQueryCacheSupport( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyQueryCacheLayout(CacheLayout cacheLayout) {
		optionsBuilder.applyQueryCacheLayout( cacheLayout );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyTimestampsCacheFactory(TimestampsCacheFactory factory) {
		optionsBuilder.applyTimestampsCacheFactory( factory );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyCacheRegionPrefix(String prefix) {
		optionsBuilder.applyCacheRegionPrefix( prefix );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applyMinimalPutsForCaching(boolean enabled) {
		optionsBuilder.enableMinimalPuts( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applyStructuredCacheEntries(boolean enabled) {
		optionsBuilder.enabledStructuredCacheEntries( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applyDirectReferenceCaching(boolean enabled) {
		optionsBuilder.allowDirectReferenceCacheEntries( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applyAutomaticEvictionOfCollectionCaches(boolean enabled) {
		optionsBuilder.enableAutoEvictCollectionCaches( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyJdbcBatchSize(int size) {
		optionsBuilder.applyJdbcBatchSize( size );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applyScrollableResultsSupport(boolean enabled) {
		optionsBuilder.enableScrollableResultSupport( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applyGetGeneratedKeysSupport(boolean enabled) {
		optionsBuilder.enableGeneratedKeysSupport( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyJdbcFetchSize(int size) {
		optionsBuilder.applyJdbcFetchSize( size );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyConnectionHandlingMode(PhysicalConnectionHandlingMode connectionHandlingMode) {
		optionsBuilder.applyConnectionHandlingMode( connectionHandlingMode );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyConnectionProviderDisablesAutoCommit(boolean providerDisablesAutoCommit) {
		optionsBuilder.applyConnectionProviderDisablesAutoCommit( providerDisablesAutoCommit );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applySqlComments(boolean enabled) {
		optionsBuilder.enableCommentsSupport( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applySqlFunction(String registrationName, SqmFunctionDescriptor functionDescriptor) {
		optionsBuilder.applySqlFunction( registrationName, functionDescriptor );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryBuilder applyCollectionsInDefaultFetchGroup(boolean enabled) {
		optionsBuilder.enableCollectionInDefaultFetchGroup( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyTemporalTableStrategy(TemporalTableStrategy strategy) {
		optionsBuilder.applyTemporalTableStrategy( strategy );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyAuditStrategy(AuditStrategy strategy) {
		optionsBuilder.applyAuditStrategy( strategy );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder allowOutOfTransactionUpdateOperations(boolean allow) {
		optionsBuilder.allowOutOfTransactionUpdateOperations( allow );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder enableJpaQueryCompliance(boolean enabled) {
		optionsBuilder.enableJpaQueryCompliance( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder enableJpaOrderByMappingCompliance(boolean enabled) {
		optionsBuilder.enableJpaOrderByMappingCompliance( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder enableJpaTransactionCompliance(boolean enabled) {
		optionsBuilder.enableJpaTransactionCompliance( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder enableJpaClosedCompliance(boolean enabled) {
		optionsBuilder.enableJpaClosedCompliance( enabled );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyJsonFormatMapper(FormatMapper jsonFormatMapper) {
		optionsBuilder.applyJsonFormatMapper( jsonFormatMapper );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyXmlFormatMapper(FormatMapper xmlFormatMapper) {
		optionsBuilder.applyXmlFormatMapper( xmlFormatMapper );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void disableJtaTransactionAccess() {
		optionsBuilder.disableJtaTransactionAccess();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactory build() {
		return instantiateSessionFactory( metadata, buildSessionFactoryOptions(), bootstrapContext );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryOptions buildSessionFactoryOptions() {
		return optionsBuilder.buildOptions();
	}
}
