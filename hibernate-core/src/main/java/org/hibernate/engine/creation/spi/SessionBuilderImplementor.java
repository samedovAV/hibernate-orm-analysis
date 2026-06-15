/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.creation.spi;

import jakarta.annotation.Nullable;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;
import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import org.hibernate.CacheMode;
import org.hibernate.ConnectionAcquisitionMode;
import org.hibernate.ConnectionReleaseMode;
import org.hibernate.FlushMode;
import org.hibernate.Interceptor;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionEventListener;
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
 * Defines the internal contract between the {@link SessionBuilder} and
 * other parts of Hibernate.
 *
 * @see SessionBuilder
 *
 * @author Gail Badner
 */
public interface SessionBuilderImplementor extends SessionBuilder {
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionImplementor openSession();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SessionImplementor open() {
		return openSession();
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor interceptor(@Nullable Interceptor interceptor);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor noInterceptor();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor noSessionInterceptorCreation();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor statementInspector(@Nullable UnaryOperator<String> operator);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor statementInspector(@Nonnull StatementInspector statementInspector);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor connection(@Nonnull Connection connection);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor connectionHandling(@Nonnull ConnectionAcquisitionMode acquisitionMode, @Nonnull ConnectionReleaseMode releaseMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor connectionHandlingMode(@Nonnull PhysicalConnectionHandlingMode mode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor autoJoinTransactions(boolean autoJoinTransactions);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor autoClear(boolean autoClear);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor flushMode(@Nonnull FlushMode flushMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor tenantIdentifier(@Nullable Object tenantIdentifier);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor readOnly(boolean readOnly);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor initialCacheMode(@Nonnull CacheMode cacheMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor jdbcBatchSize(int batchSize);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor cacheStoreMode(@Nullable CacheStoreMode cacheStoreMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor cacheRetrieveMode(@Nullable CacheRetrieveMode cacheRetrieveMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor eventListeners(@Nonnull SessionEventListener... listeners);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor clearEventListeners();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor jdbcTimeZone(@Nullable TimeZone timeZone);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor autoClose(boolean autoClose);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor identifierRollback(boolean identifierRollback);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor noStatementInspector();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor defaultBatchFetchSize(int defaultBatchFetchSize);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor subselectFetchEnabled(boolean subselectFetchEnabled);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor asOf(@Nullable Instant instant);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor atChangeset(@Nullable Object changesetId);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionBuilderImplementor withOption(EntityManager.CreationOption option);
}
