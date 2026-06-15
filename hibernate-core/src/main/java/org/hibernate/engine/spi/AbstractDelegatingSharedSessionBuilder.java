/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.spi;

import java.sql.Connection;
import java.time.Instant;
import java.util.TimeZone;
import java.util.function.UnaryOperator;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import org.hibernate.CacheMode;
import org.hibernate.ConnectionAcquisitionMode;
import org.hibernate.ConnectionReleaseMode;
import org.hibernate.FlushMode;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.SessionEventListener;
import org.hibernate.SharedSessionBuilder;
import org.hibernate.resource.jdbc.spi.PhysicalConnectionHandlingMode;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Base class for {@link SharedSessionBuilder} implementations that wish to implement only parts of that contract
 * themselves while forwarding other method invocations to a delegate instance.
 *
 * @author Gunnar Morling
 * @author Guillaume Smet
 */
public abstract class AbstractDelegatingSharedSessionBuilder implements SharedSessionBuilder {
	private final SharedSessionBuilder delegate;

	public AbstractDelegatingSharedSessionBuilder(SharedSessionBuilder delegate) {
		this.delegate = delegate;
	}

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected SharedSessionBuilder getThis() {
		return this;
	}

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SharedSessionBuilder delegate() {
		return delegate;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Session openSession() {
		return delegate.openSession();
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Session open() {
		return delegate.open();
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder interceptor() {
		delegate.interceptor();
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder connection() {
		delegate.connection();
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder connectionHandlingMode() {
		delegate.connectionHandlingMode();
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder autoJoinTransactions() {
		delegate.autoJoinTransactions();
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder autoClose() {
		delegate.autoClose();
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder interceptor(@Nullable Interceptor interceptor) {
		delegate.interceptor( interceptor );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder noInterceptor() {
		delegate.noInterceptor();
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder noSessionInterceptorCreation() {
		delegate.noSessionInterceptorCreation();
		return this;
	}

	@Override
	@Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder statementInspector(@Nonnull StatementInspector statementInspector) {
		delegate.statementInspector( statementInspector );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder statementInspector(@Nullable UnaryOperator<String> operator) {
		delegate.statementInspector( operator );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder statementInspector() {
		delegate.statementInspector();
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder noStatementInspector() {
		delegate.noStatementInspector();
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder connection(@Nonnull Connection connection) {
		delegate.connection( connection );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder autoJoinTransactions(boolean autoJoinTransactions) {
		delegate.autoJoinTransactions( autoJoinTransactions );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder autoClose(boolean autoClose) {
		delegate.autoClose( autoClose );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder tenantIdentifier(Object tenantIdentifier) {
		delegate.tenantIdentifier( tenantIdentifier );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder readOnly(boolean readOnly) {
		delegate.readOnly( readOnly );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder initialCacheMode(@Nonnull CacheMode cacheMode) {
		delegate.initialCacheMode( cacheMode );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder jdbcBatchSize(int batchSize) {
		delegate.jdbcBatchSize( batchSize );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder cacheStoreMode(@Nullable CacheStoreMode cacheStoreMode) {
		delegate.cacheStoreMode( cacheStoreMode );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder cacheRetrieveMode(@Nullable CacheRetrieveMode cacheRetrieveMode) {
		delegate.cacheRetrieveMode( cacheRetrieveMode );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder eventListeners(@Nonnull SessionEventListener... listeners) {
		delegate.eventListeners( listeners );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder clearEventListeners() {
		delegate.clearEventListeners();
		return this;
	}

	@Override
	@Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder connectionHandlingMode(PhysicalConnectionHandlingMode mode) {
		delegate.connectionHandlingMode( mode );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder connectionHandling(@Nonnull ConnectionAcquisitionMode acquisitionMode, @Nonnull ConnectionReleaseMode releaseMode) {
		delegate.connectionHandling( acquisitionMode, releaseMode );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder autoClear(boolean autoClear) {
		delegate.autoClear( autoClear );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder flushMode(@Nonnull FlushMode flushMode) {
		delegate.flushMode( flushMode );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder flushMode() {
		delegate.flushMode();
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder jdbcTimeZone(@Nullable TimeZone timeZone) {
		delegate.jdbcTimeZone( timeZone );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder identifierRollback(boolean identifierRollback) {
		delegate.identifierRollback( identifierRollback );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder defaultBatchFetchSize(int defaultBatchFetchSize) {
		delegate.defaultBatchFetchSize( defaultBatchFetchSize );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder subselectFetchEnabled(boolean subselectFetchEnabled) {
		delegate.subselectFetchEnabled( subselectFetchEnabled );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder asOf(@Nullable Instant instant) {
		delegate.asOf( instant );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionBuilder atChangeset(@Nullable Object changesetId) {
		delegate.atChangeset( changesetId );
		return this;
	}
}
