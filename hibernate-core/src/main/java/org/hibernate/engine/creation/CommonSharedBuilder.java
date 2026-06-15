/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.creation;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import org.hibernate.CacheMode;
import org.hibernate.ConnectionAcquisitionMode;
import org.hibernate.ConnectionReleaseMode;
import org.hibernate.Incubating;
import org.hibernate.Interceptor;

import java.sql.Connection;
import java.time.Instant;
import java.util.TimeZone;
import java.util.function.UnaryOperator;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Common options for builders of [stateful][org.hibernate.Session] and
/// [stateless][org.hibernate.StatelessSession] sessions which share state
/// from an underlying stateful/stateless session.
///
/// @since 7.2
///
/// @author Steve Ebersole
@Incubating
public interface CommonSharedBuilder extends CommonBuilder {

	/// Signifies that the connection from the original session should be used to create the new session.
	/// Implies that the overall "transaction context" should be shared as well.
	///
	/// @return `this`, for method chaining
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonSharedBuilder connection();

	/// Signifies the interceptor from the original session should be used to create the new session.
	///
	/// @return `this`, for method chaining
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonSharedBuilder interceptor();

	/// Signifies that the SQL statement inspector from the original session should be used to create the new session.
	///
	/// @return `this`, for method chaining
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonSharedBuilder statementInspector();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonSharedBuilder noStatementInspector();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonSharedBuilder interceptor(@Nullable Interceptor interceptor);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonSharedBuilder noInterceptor();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonSharedBuilder noSessionInterceptorCreation();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonSharedBuilder statementInspector(@Nullable UnaryOperator<String> operator);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonSharedBuilder readOnly(boolean readOnly);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonSharedBuilder initialCacheMode(@Nonnull CacheMode cacheMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonSharedBuilder tenantIdentifier(Object tenantIdentifier);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonSharedBuilder connection(@Nonnull Connection connection);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonSharedBuilder connectionHandling(@Nonnull ConnectionAcquisitionMode acquisitionMode, @Nonnull ConnectionReleaseMode releaseMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonSharedBuilder jdbcBatchSize(int batchSize);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonSharedBuilder cacheStoreMode(@Nullable CacheStoreMode cacheStoreMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonSharedBuilder cacheRetrieveMode(@Nullable CacheRetrieveMode cacheRetrieveMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonSharedBuilder jdbcTimeZone(@Nullable TimeZone timeZone);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonSharedBuilder asOf(@Nullable Instant instant);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonSharedBuilder atChangeset(@Nullable Object changesetId);
}
