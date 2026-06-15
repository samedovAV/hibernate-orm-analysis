/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.spi;

import java.util.Map;
import java.util.TimeZone;
import java.util.function.Supplier;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import org.hibernate.CacheMode;
import org.hibernate.CustomEntityDirtinessStrategy;
import org.hibernate.EntityNameResolver;
import org.hibernate.FlushMode;
import org.hibernate.GraphParserMode;
import org.hibernate.Interceptor;
import org.hibernate.LockOptions;
import org.hibernate.SessionEventListener;
import org.hibernate.SessionFactoryObserver;
import org.hibernate.audit.AuditStrategy;
import org.hibernate.StatementObserver;
import org.hibernate.temporal.TemporalTableStrategy;
import org.hibernate.context.spi.TenantCredentialsMapper;
import org.hibernate.context.spi.TenantSchemaMapper;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.metamodel.spi.RuntimeModelCreationContext;
import org.hibernate.type.TimeZoneStorageStrategy;
import org.hibernate.annotations.CacheLayout;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.cache.spi.TimestampsCacheFactory;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.jpa.spi.JpaCompliance;
import org.hibernate.proxy.EntityNotFoundDelegate;
import org.hibernate.query.spi.ImmutableEntityUpdateQueryHandlingMode;
import org.hibernate.query.criteria.ValueHandlingMode;
import org.hibernate.query.hql.HqlTranslator;
import org.hibernate.query.sqm.function.SqmFunctionDescriptor;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;
import org.hibernate.query.sqm.mutation.spi.SqmMultiTableInsertStrategy;
import org.hibernate.query.sqm.mutation.spi.SqmMultiTableMutationStrategy;
import org.hibernate.query.sqm.sql.SqmTranslatorFactory;
import org.hibernate.resource.jdbc.spi.PhysicalConnectionHandlingMode;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.format.FormatMapper;

import jakarta.persistence.criteria.Nulls;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Convenience base class for custom implementations of {@link SessionFactoryOptions}
 * using delegation.
 *
 * @implNote Declared non-abstract to ensure that all {@link SessionFactoryOptions} methods
 *           have at least a default implementation.
 *
 * @author Steve Ebersole
 */
public class AbstractDelegatingSessionFactoryOptions implements SessionFactoryOptions {
	private final SessionFactoryOptions delegate;

	public AbstractDelegatingSessionFactoryOptions(SessionFactoryOptions delegate) {
		this.delegate = delegate;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected SessionFactoryOptions delegate() {
		return delegate;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getUuid() {
		return delegate().getUuid();
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public StandardServiceRegistry getServiceRegistry() {
		return delegate.getServiceRegistry();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isJpaBootstrap() {
		return delegate.isJpaBootstrap();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isJtaTransactionAccessEnabled() {
		return delegate.isJtaTransactionAccessEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object getBeanManagerReference() {
		return delegate.getBeanManagerReference();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object getValidatorFactoryReference() {
		return delegate.getValidatorFactoryReference();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getSessionFactoryName() {
		return delegate.getSessionFactoryName();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Boolean isSessionFactoryNameAlsoJndiName() {
		return delegate.isSessionFactoryNameAlsoJndiName();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isFlushBeforeCompletionEnabled() {
		return delegate.isFlushBeforeCompletionEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isAutoCloseSessionEnabled() {
		return delegate.isAutoCloseSessionEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isStatisticsEnabled() {
		return delegate.isStatisticsEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Interceptor getInterceptor() {
		return delegate.getInterceptor();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public StatementObserver getStatementObserver() {
		return delegate().getStatementObserver();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmMultiTableMutationStrategy getCustomSqmMultiTableMutationStrategy() {
		return delegate.getCustomSqmMultiTableMutationStrategy();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmMultiTableInsertStrategy getCustomSqmMultiTableInsertStrategy() {
		return delegate.getCustomSqmMultiTableInsertStrategy();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmMultiTableMutationStrategy resolveCustomSqmMultiTableMutationStrategy(EntityMappingType rootEntityDescriptor, RuntimeModelCreationContext creationContext) {
		return delegate.resolveCustomSqmMultiTableMutationStrategy( rootEntityDescriptor, creationContext );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmMultiTableInsertStrategy resolveCustomSqmMultiTableInsertStrategy(EntityMappingType rootEntityDescriptor, RuntimeModelCreationContext creationContext) {
		return delegate.resolveCustomSqmMultiTableInsertStrategy( rootEntityDescriptor, creationContext );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public StatementInspector getStatementInspector() {
		return delegate.getStatementInspector();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryObserver[] getSessionFactoryObservers() {
		return delegate.getSessionFactoryObservers();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionEventListener[] buildSessionEventListeners() {
		return delegate.buildSessionEventListeners();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isIdentifierRollbackEnabled() {
		return delegate.isIdentifierRollbackEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isCheckNullability() {
		return delegate.isCheckNullability();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isInitializeLazyStateOutsideTransactionsEnabled() {
		return delegate.isInitializeLazyStateOutsideTransactionsEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isBidirectionalAssociationManagementEnabled() {
		return delegate.isBidirectionalAssociationManagementEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getDefaultBatchFetchSize() {
		return delegate.getDefaultBatchFetchSize();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Integer getMaximumFetchDepth() {
		return delegate.getMaximumFetchDepth();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isSubselectFetchEnabled() {
		return delegate.isSubselectFetchEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Nulls getDefaultNullPrecedence() {
		return delegate.getDefaultNullPrecedence();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isOrderUpdatesEnabled() {
		return delegate.isOrderUpdatesEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isOrderInsertsEnabled() {
		return delegate.isOrderInsertsEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isMultiTenancyEnabled() {
		return delegate.isMultiTenancyEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public CurrentTenantIdentifierResolver<Object> getCurrentTenantIdentifierResolver() {
		return delegate.getCurrentTenantIdentifierResolver();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TenantSchemaMapper<Object> getTenantSchemaMapper() {
		return delegate.getTenantSchemaMapper();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TenantCredentialsMapper<Object> getTenantCredentialsMapper() {
		return delegate.getTenantCredentialsMapper();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JavaType<Object> getDefaultTenantIdentifierJavaType() {
		return delegate.getDefaultTenantIdentifierJavaType();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isJtaTrackByThread() {
		return delegate.isJtaTrackByThread();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isNamedQueryStartupCheckingEnabled() {
		return delegate.isNamedQueryStartupCheckingEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isAllowOutOfTransactionUpdateOperations() {
		return delegate.isAllowOutOfTransactionUpdateOperations();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isSecondLevelCacheEnabled() {
		return delegate.isSecondLevelCacheEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isQueryCacheEnabled() {
		return delegate.isQueryCacheEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public CacheLayout getQueryCacheLayout() {
		return delegate.getQueryCacheLayout();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TimestampsCacheFactory getTimestampsCacheFactory() {
		return delegate.getTimestampsCacheFactory();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getCacheRegionPrefix() {
		return delegate.getCacheRegionPrefix();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isMinimalPutsEnabled() {
		return delegate.isMinimalPutsEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isStructuredCacheEntriesEnabled() {
		return delegate.isStructuredCacheEntriesEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isDirectReferenceCacheEntriesEnabled() {
		return delegate.isDirectReferenceCacheEntriesEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isAutoEvictCollectionCache() {
		return delegate.isAutoEvictCollectionCache();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getJdbcBatchSize() {
		return delegate.getJdbcBatchSize();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isScrollableResultSetsEnabled() {
		return delegate.isScrollableResultSetsEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isGetGeneratedKeysEnabled() {
		return delegate.isGetGeneratedKeysEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Integer getJdbcFetchSize() {
		return delegate.getJdbcFetchSize();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public PhysicalConnectionHandlingMode getPhysicalConnectionHandlingMode() {
		return delegate.getPhysicalConnectionHandlingMode();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean doesConnectionProviderDisableAutoCommit() {
		return delegate.doesConnectionProviderDisableAutoCommit();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isCommentsEnabled() {
		return delegate.isCommentsEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public CustomEntityDirtinessStrategy getCustomEntityDirtinessStrategy() {
		return delegate.getCustomEntityDirtinessStrategy();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public EntityNameResolver[] getEntityNameResolvers() {
		return delegate.getEntityNameResolvers();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public EntityNotFoundDelegate getEntityNotFoundDelegate() {
		return delegate.getEntityNotFoundDelegate();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Map<String, SqmFunctionDescriptor> getCustomSqlFunctionMap() {
		return delegate.getCustomSqlFunctionMap();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void setCheckNullability(boolean enabled) {
		delegate.setCheckNullability( enabled );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isPreferUserTransaction() {
		return delegate.isPreferUserTransaction();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Supplier<? extends Interceptor> getStatelessInterceptorImplementorSupplier() {
		return delegate.getStatelessInterceptorImplementorSupplier();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public HqlTranslator getCustomHqlTranslator() {
		return delegate.getCustomHqlTranslator();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmTranslatorFactory getCustomSqmTranslatorFactory() {
		return delegate.getCustomSqmTranslatorFactory();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TimeZone getJdbcTimeZone() {
		return delegate.getJdbcTimeZone();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TemporalTableStrategy getTemporalTableStrategy() {
		return delegate.getTemporalTableStrategy();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public AuditStrategy getAuditStrategy() {
		return delegate.getAuditStrategy();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public ValueHandlingMode getCriteriaValueHandlingMode() {
		return delegate.getCriteriaValueHandlingMode();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isCriteriaCopyTreeEnabled() {
		return delegate.isCriteriaCopyTreeEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isCriteriaPlanCacheEnabled() {
		return delegate.isCriteriaPlanCacheEnabled();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean getNativeJdbcParametersIgnored() {
		return delegate.getNativeJdbcParametersIgnored();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JpaCompliance getJpaCompliance() {
		return delegate.getJpaCompliance();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isFailOnPaginationOverCollectionFetchEnabled() {
		return delegate.isFailOnPaginationOverCollectionFetchEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public ImmutableEntityUpdateQueryHandlingMode getImmutableEntityUpdateQueryHandlingMode() {
		return delegate.getImmutableEntityUpdateQueryHandlingMode();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean allowImmutableEntityUpdate() {
		return delegate.allowImmutableEntityUpdate();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getDefaultCatalog() {
		return delegate.getDefaultCatalog();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getDefaultSchema() {
		return delegate.getDefaultSchema();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean inClauseParameterPaddingEnabled() {
		return delegate.inClauseParameterPaddingEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isJsonFunctionsEnabled() {
		return delegate.isJsonFunctionsEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isXmlFunctionsEnabled() {
		return delegate.isXmlFunctionsEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isPortableIntegerDivisionEnabled() {
		return delegate.isPortableIntegerDivisionEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getQueryStatisticsMaxSize() {
		return delegate.getQueryStatisticsMaxSize();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean areJPACallbacksEnabled() {
		return delegate.areJPACallbacksEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isCollectionsInDefaultFetchGroupEnabled() {
		return delegate.isCollectionsInDefaultFetchGroupEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isUnownedAssociationTransientCheck() {
		return delegate.isUnownedAssociationTransientCheck();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isUseOfJdbcNamedParametersEnabled() {
		return delegate().isUseOfJdbcNamedParametersEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmFunctionRegistry getCustomSqmFunctionRegistry() {
		return delegate().getCustomSqmFunctionRegistry();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getPreferredSqlTypeCodeForBoolean() {
		return delegate.getPreferredSqlTypeCodeForBoolean();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getPreferredSqlTypeCodeForDuration() {
		return delegate.getPreferredSqlTypeCodeForDuration();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getPreferredSqlTypeCodeForUuid() {
		return delegate.getPreferredSqlTypeCodeForUuid();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getPreferredSqlTypeCodeForInstant() {
		return delegate.getPreferredSqlTypeCodeForInstant();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getPreferredSqlTypeCodeForArray() {
		return delegate.getPreferredSqlTypeCodeForArray();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TimeZoneStorageStrategy getDefaultTimeZoneStorageStrategy() {
		return delegate.getDefaultTimeZoneStorageStrategy();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isPreferJavaTimeJdbcTypesEnabled() {
		return delegate.isPreferJavaTimeJdbcTypesEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isPreferNativeEnumTypesEnabled() {
		return delegate.isPreferNativeEnumTypesEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isPreferLocaleLanguageTagEnabled() {
		return delegate.isPreferLocaleLanguageTagEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public FormatMapper getJsonFormatMapper() {
		return delegate.getJsonFormatMapper();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public FormatMapper getXmlFormatMapper() {
		return delegate.getXmlFormatMapper();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isXmlFormatMapperLegacyFormatEnabled() {
		return delegate.isXmlFormatMapperLegacyFormatEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isPassProcedureParameterNames() {
		return delegate.isPassProcedureParameterNames();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isPreferJdbcDatetimeTypesInNativeQueriesEnabled() {
		return delegate.isPreferJdbcDatetimeTypesInNativeQueriesEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public CacheStoreMode getCacheStoreMode(Map<String, Object> properties) {
		return delegate.getCacheStoreMode( properties );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public CacheRetrieveMode getCacheRetrieveMode(Map<String, Object> properties) {
		return delegate.getCacheRetrieveMode( properties );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Map<String, Object> getDefaultSessionProperties() {
		return delegate.getDefaultSessionProperties();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public GraphParserMode getGraphParserMode() {
		return delegate.getGraphParserMode();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public CacheMode getInitialSessionCacheMode() {
		return delegate.getInitialSessionCacheMode();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public FlushMode getInitialSessionFlushMode() {
		return delegate.getInitialSessionFlushMode();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public LockOptions getDefaultLockOptions() {
		return delegate.getDefaultLockOptions();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isSafeModeEnabled() { return delegate.isSafeModeEnabled(); }
}
