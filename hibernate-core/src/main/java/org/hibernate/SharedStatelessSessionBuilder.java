/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import org.hibernate.engine.creation.CommonSharedBuilder;

import java.sql.Connection;
import java.time.Instant;
import java.util.TimeZone;
import java.util.function.UnaryOperator;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Allows creation of a child {@link StatelessSession} which shares some options
 * with another pre-existing parent session.
 * <p>
 * A child stateless session does not, by default, share the parent's JDBC
 * connection, transaction coordinator, interceptor, or SQL statement inspector.
 * Instead, the child stateless session starts from the normal
 * {@link SessionFactory} defaults, with the parent's tenant identifier and JDBC
 * time zone inherited as the baseline. The parent's interceptor, SQL statement
 * inspector, connection, and transaction context are inherited only when
 * explicitly requested by calling {@link #interceptor()},
 * {@link #statementInspector()}, or {@link #connection()}.
 * <p>
 * When {@linkplain Transaction resource-local} transaction management is used:
 * <ul>
 * <li>by default, each session executes with its own dedicated JDBC connection
 *     and therefore has its own isolated transaction, but
 * <li>calling the {@link #connection()} method specifies that the connection,
 *     and therefore also the JDBC transaction, should be shared from parent
 *     to child.
 * </ul>
 * A child stateless session with a shared transaction context also receives
 * parent flush and close notifications, so its JDBC batch is executed with the
 * parent, and the child is automatically closed when the parent is closed.
 * <pre>{@code
 * try (var statelessSession
 *          = session.statelessWithOptions()
 *                  .connection() // share the JDBC connection
 *                  .cacheMode(CacheMode.IGNORE)
 *                  .openStatelessSession()) {
 *     ...
 * }
 * }</pre>
 *
 * On the other hand, when JTA transaction management is used, all sessions
 * execute within the same transaction. Typically, connection sharing is
 * handled automatically by the JTA-enabled {@link javax.sql.DataSource}.
 *
 * @see Session#statelessWithOptions()
 * @see StatelessSession#statelessWithOptions()
 * @see SharedSessionBuilder
 *
 * @since 7.2
 *
 * @author Steve Ebersole
 */
@Incubating
public interface SharedStatelessSessionBuilder extends StatelessSessionBuilder, CommonSharedBuilder {
	/**
	 * Open the stateless session.
	 */
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	StatelessSession open();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedStatelessSessionBuilder connection();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedStatelessSessionBuilder interceptor();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedStatelessSessionBuilder interceptor(@Nullable Interceptor interceptor);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedStatelessSessionBuilder noInterceptor();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedStatelessSessionBuilder noSessionInterceptorCreation();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedStatelessSessionBuilder statementInspector(@Nullable UnaryOperator<String> operator);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedStatelessSessionBuilder statementInspector();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedStatelessSessionBuilder noStatementInspector();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedStatelessSessionBuilder tenantIdentifier(Object tenantIdentifier);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedStatelessSessionBuilder readOnly(boolean readOnly);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedStatelessSessionBuilder jdbcBatchSize(int batchSize);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedStatelessSessionBuilder initialCacheMode(@Nonnull CacheMode cacheMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedStatelessSessionBuilder cacheStoreMode(@Nullable CacheStoreMode cacheStoreMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedStatelessSessionBuilder cacheRetrieveMode(@Nullable CacheRetrieveMode cacheRetrieveMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedStatelessSessionBuilder connection(@Nonnull Connection connection);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedStatelessSessionBuilder connectionHandling(@Nonnull ConnectionAcquisitionMode acquisitionMode, @Nonnull ConnectionReleaseMode releaseMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedStatelessSessionBuilder jdbcTimeZone(@Nullable TimeZone timeZone);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedStatelessSessionBuilder asOf(@Nullable Instant instant);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedStatelessSessionBuilder atChangeset(@Nullable Object changesetId);
}
