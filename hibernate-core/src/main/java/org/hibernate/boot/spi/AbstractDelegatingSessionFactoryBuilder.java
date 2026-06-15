/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.spi;

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
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Convenience base class for custom implementors of SessionFactoryBuilder, using delegation
 *
 * @author Steve Ebersole
 * @author Gunnar Morling
 * @author Guillaume Smet
 *
 * @param <T> The specific subclass; Allows subclasses to narrow the return type of the contract methods
 *            to a specialization of {@link MetadataBuilderImplementor}.
 */
public abstract class AbstractDelegatingSessionFactoryBuilder<T extends SessionFactoryBuilder> implements SessionFactoryBuilder {
	private final SessionFactoryBuilder delegate;

	public AbstractDelegatingSessionFactoryBuilder(SessionFactoryBuilder delegate) {
		this.delegate = delegate;
	}

	/**
	 * Returns a specific implementation. See the <a
	 * href="http://www.angelikalanger.com/GenericsFAQ/FAQSections/ProgrammingIdioms.html#FAQ206">What is the
	 * "getThis trick?"</a>.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract T getThis();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected SessionFactoryBuilder delegate() {
		return delegate;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyValidatorFactory(Object validatorFactory) {
		delegate.applyValidatorFactory( validatorFactory );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyBeanManager(Object beanManager) {
		delegate.applyBeanManager( beanManager );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyName(String sessionFactoryName) {
		delegate.applyName( sessionFactoryName );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyNameAsJndiName(boolean isJndiName) {
		delegate.applyNameAsJndiName( isJndiName );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyAutoClosing(boolean enabled) {
		delegate.applyAutoClosing( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyAutoFlushing(boolean enabled) {
		delegate.applyAutoFlushing( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyStatisticsSupport(boolean enabled) {
		delegate.applyStatisticsSupport( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyInterceptor(Interceptor interceptor) {
		delegate.applyInterceptor( interceptor );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyStatementInspector(StatementInspector statementInspector) {
		delegate.applyStatementInspector( statementInspector );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T addSessionFactoryObservers(SessionFactoryObserver... observers) {
		delegate.addSessionFactoryObservers( observers );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyCustomEntityDirtinessStrategy(CustomEntityDirtinessStrategy strategy) {
		delegate.applyCustomEntityDirtinessStrategy( strategy );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T addEntityNameResolver(EntityNameResolver... entityNameResolvers) {
		delegate.addEntityNameResolver( entityNameResolvers );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyEntityNotFoundDelegate(EntityNotFoundDelegate entityNotFoundDelegate) {
		delegate.applyEntityNotFoundDelegate( entityNotFoundDelegate );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyIdentifierRollbackSupport(boolean enabled) {
		delegate.applyIdentifierRollbackSupport( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyNullabilityChecking(boolean enabled) {
		delegate.applyNullabilityChecking( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyLazyInitializationOutsideTransaction(boolean enabled) {
		delegate.applyLazyInitializationOutsideTransaction( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyDefaultBatchFetchSize(int size) {
		delegate.applyDefaultBatchFetchSize( size );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyMaximumFetchDepth(int depth) {
		delegate.applyMaximumFetchDepth( depth );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applySubselectFetchEnabled(boolean enabled) {
		delegate.applySubselectFetchEnabled( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyDefaultNullPrecedence(Nulls nullPrecedence) {
		delegate.applyDefaultNullPrecedence( nullPrecedence );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyOrderingOfInserts(boolean enabled) {
		delegate.applyOrderingOfInserts( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyOrderingOfUpdates(boolean enabled) {
		delegate.applyOrderingOfUpdates( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyMultiTenancy(boolean enabled) {
		delegate.applyMultiTenancy(enabled);
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyCurrentTenantIdentifierResolver(CurrentTenantIdentifierResolver<?> resolver) {
		delegate.applyCurrentTenantIdentifierResolver( resolver );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyTenantSchemaMapper(TenantSchemaMapper<?> mapper) {
		delegate.applyTenantSchemaMapper( mapper );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyTenantCredentialsMapper(TenantCredentialsMapper<?> mapper) {
		delegate.applyTenantCredentialsMapper( mapper );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyJtaTrackingByThread(boolean enabled) {
		delegate.applyJtaTrackingByThread( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyPreferUserTransactions(boolean preferUserTransactions) {
		delegate.applyPreferUserTransactions( preferUserTransactions );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyNamedQueryCheckingOnStartup(boolean enabled) {
		delegate.applyNamedQueryCheckingOnStartup( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applySecondLevelCacheSupport(boolean enabled) {
		delegate.applySecondLevelCacheSupport( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyQueryCacheSupport(boolean enabled) {
		delegate.applyQueryCacheSupport( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyQueryCacheLayout(CacheLayout cacheLayout) {
		delegate.applyQueryCacheLayout( cacheLayout );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyTimestampsCacheFactory(TimestampsCacheFactory factory) {
		delegate.applyTimestampsCacheFactory( factory );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyCacheRegionPrefix(String prefix) {
		delegate.applyCacheRegionPrefix( prefix );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyMinimalPutsForCaching(boolean enabled) {
		delegate.applyMinimalPutsForCaching( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyStructuredCacheEntries(boolean enabled) {
		delegate.applyStructuredCacheEntries( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyDirectReferenceCaching(boolean enabled) {
		delegate.applyDirectReferenceCaching( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyAutomaticEvictionOfCollectionCaches(boolean enabled) {
		delegate.applyAutomaticEvictionOfCollectionCaches( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyJdbcBatchSize(int size) {
		delegate.applyJdbcBatchSize( size );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyScrollableResultsSupport(boolean enabled) {
		delegate.applyScrollableResultsSupport( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyGetGeneratedKeysSupport(boolean enabled) {
		delegate.applyGetGeneratedKeysSupport( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyJdbcFetchSize(int size) {
		delegate.applyJdbcFetchSize( size );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyConnectionProviderDisablesAutoCommit(boolean providerDisablesAutoCommit) {
		delegate.applyConnectionProviderDisablesAutoCommit( providerDisablesAutoCommit );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applySqlComments(boolean enabled) {
		delegate.applySqlComments( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applySqlFunction(
			String registrationName,
			SqmFunctionDescriptor sqlFunction) {
		delegate.applySqlFunction( registrationName, sqlFunction );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyCollectionsInDefaultFetchGroup(boolean enabled) {
		delegate.applyCollectionsInDefaultFetchGroup( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T allowOutOfTransactionUpdateOperations(boolean allow) {
		delegate.allowOutOfTransactionUpdateOperations( allow );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T enableJpaQueryCompliance(boolean enabled) {
		delegate.enableJpaQueryCompliance( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T enableJpaOrderByMappingCompliance(boolean enabled) {
		delegate.enableJpaOrderByMappingCompliance( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T enableJpaTransactionCompliance(boolean enabled) {
		delegate.enableJpaTransactionCompliance( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T enableJpaClosedCompliance(boolean enabled) {
		delegate.enableJpaClosedCompliance( enabled );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyStatelessInterceptor(Supplier<? extends Interceptor> statelessInterceptorSupplier) {
		delegate.applyStatelessInterceptor(statelessInterceptorSupplier);
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyStatementObserver(StatementObserver statementObserver) {
		delegate.applyStatementObserver( statementObserver );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyStatelessInterceptor(Class<? extends Interceptor> statelessInterceptorClass) {
		delegate.applyStatelessInterceptor(statelessInterceptorClass);
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyConnectionHandlingMode(PhysicalConnectionHandlingMode connectionHandlingMode) {
		delegate.applyConnectionHandlingMode( connectionHandlingMode );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyJsonFormatMapper(FormatMapper jsonFormatMapper) {
		delegate.applyJsonFormatMapper( jsonFormatMapper );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T applyXmlFormatMapper(FormatMapper xmlFormatMapper) {
		delegate.applyXmlFormatMapper( xmlFormatMapper );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyTemporalTableStrategy(TemporalTableStrategy strategy) {
		delegate.applyTemporalTableStrategy( strategy );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryBuilder applyAuditStrategy(AuditStrategy strategy) {
		delegate.applyAuditStrategy( strategy );
		return getThis();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactory build() {
		return delegate.build();
	}
}
