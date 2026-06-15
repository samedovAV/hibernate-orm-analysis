/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.creation.spi;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import org.hibernate.CacheMode;
import org.hibernate.ConnectionAcquisitionMode;
import org.hibernate.ConnectionReleaseMode;
import org.hibernate.FlushMode;
import org.hibernate.Interceptor;
import org.hibernate.SessionEventListener;
import org.hibernate.SharedSessionBuilder;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.resource.jdbc.spi.PhysicalConnectionHandlingMode;
import org.hibernate.resource.jdbc.spi.StatementInspector;

import java.sql.Connection;
import java.time.Instant;
import java.util.TimeZone;
import java.util.function.UnaryOperator;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 *
 * @since 7.2
 */
public interface SharedSessionBuilderImplementor extends SharedSessionBuilder, SessionBuilderImplementor {
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionImplementor open();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor interceptor(@Nullable Interceptor interceptor);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor noInterceptor();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor noSessionInterceptorCreation();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor statementInspector(@Nullable UnaryOperator<String> operator);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor statementInspector(@Nonnull StatementInspector statementInspector);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor tenantIdentifier(Object tenantIdentifier);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor readOnly(boolean readOnly);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor initialCacheMode(@Nonnull CacheMode cacheMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor jdbcBatchSize(int batchSize);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor cacheStoreMode(@Nullable CacheStoreMode cacheStoreMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor cacheRetrieveMode(@Nullable CacheRetrieveMode cacheRetrieveMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor connection();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor interceptor();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor connectionHandlingMode();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor autoJoinTransactions();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor flushMode();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor autoClose();

	@Override
	@Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor connectionHandlingMode(@Nonnull PhysicalConnectionHandlingMode mode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor connectionHandling(@Nonnull ConnectionAcquisitionMode acquisitionMode, @Nonnull ConnectionReleaseMode releaseMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor autoClear(boolean autoClear);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor flushMode(@Nonnull FlushMode flushMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor eventListeners(@Nonnull SessionEventListener... listeners);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor clearEventListeners();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor jdbcTimeZone(@Nullable TimeZone timeZone);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor connection(@Nonnull Connection connection);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor autoJoinTransactions(boolean autoJoinTransactions);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor autoClose(boolean autoClose);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor identifierRollback(boolean identifierRollback);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor statementInspector();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor noStatementInspector();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor defaultBatchFetchSize(int defaultBatchFetchSize);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor subselectFetchEnabled(boolean subselectFetchEnabled);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor asOf(@Nullable Instant instant);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionBuilderImplementor atChangeset(@Nullable Object changesetId);
}
