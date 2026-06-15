/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.spi;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Parameter;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Timeout;
import jakarta.persistence.metamodel.Type;
import jakarta.persistence.sql.ResultSetMapping;
import org.hibernate.Incubating;
import org.hibernate.LockMode;
import org.hibernate.Locking;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.query.Query;
import jakarta.persistence.QueryFlushMode;
import org.hibernate.query.QueryParameter;

import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
@Incubating
public interface QueryImplementor<T> extends Query<T>, CommonQueryContractImplementor {

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Casts (needed by MutationOrSelectionQueryImpl)

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> asMutationQuery();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<T> asSelectionQuery();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> SelectionQueryImplementor<X> asSelectionQuery(Class<X> type);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> SelectionQueryImplementor<X> asSelectionQuery(EntityGraph<X> entityGraph);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> SelectionQueryImplementor<X> asSelectionQuery(EntityGraph<X> entityGraph, GraphSemantic graphSemantic);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<R> SelectionQueryImplementor<R> withResultSetMapping(ResultSetMapping<R> mapping);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Options

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setQueryFlushMode(@Nonnull QueryFlushMode queryFlushMode);

	@Override @Deprecated @SuppressWarnings("deprecation")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setFlushMode(@Nonnull FlushModeType flushMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setComment(@Nullable String comment);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> addQueryHint(@Nonnull String hint);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setTimeout(int timeout);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setTimeout(@Nullable Integer timeout);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setTimeout(@Nullable Timeout timeout);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setQueryPlanCacheable(boolean queryPlanCacheable);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setCacheStoreMode(@Nonnull CacheStoreMode cacheStoreMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setCacheRetrieveMode(@Nonnull CacheRetrieveMode cacheRetrieveMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setMaxResults(int maxResults);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setFirstResult(int startPosition);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setLockMode(@Nonnull LockModeType lockMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setHibernateLockMode(@Nonnull LockMode lockMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setFollowOnStrategy(@Nonnull Locking.FollowOn strategy);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setHint(@Nonnull String hintName, @Nullable Object value);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Parameter Handling

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryParameterBindings getParameterBindings();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setParameter(@Nonnull String name, @Nullable Object value);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setParameter(@Nonnull String name, @Nullable P value, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setParameter(@Nonnull String name, @Nullable P value, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setParameter(int position, @Nullable Object value);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setParameters(@Nonnull Object... arguments);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setParameter(int position, @Nullable P value, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setParameter(int position, @Nullable P value, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setParameter(@Nonnull QueryParameter<P> parameter, @Nullable P value);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setParameter(@Nonnull QueryParameter<P> parameter, @Nullable P value, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setParameter(@Nonnull QueryParameter<P> parameter, @Nullable P val, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setParameter(@Nonnull Parameter<P> param, @Nullable P value);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setProperties(@Nonnull Object bean);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setProperties(@SuppressWarnings("rawtypes") @Nonnull Map bean);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setConvertedParameter(@Nonnull String name, @Nullable P value, @Nonnull Class<? extends AttributeConverter<P, ?>> converter);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setConvertedParameter(int position, @Nullable P value, @Nonnull Class<? extends AttributeConverter<P, ?>> converter);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setParameterList(@Nonnull String name, @SuppressWarnings("rawtypes") @Nonnull Collection values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setParameterList(@Nonnull String name, @Nonnull Collection<? extends P> values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setParameterList(@Nonnull String name, @Nonnull Collection<? extends P> values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setParameterList(@Nonnull String name, @Nonnull Object[] values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setParameterList(@Nonnull String name, @Nonnull P[] values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setParameterList(@Nonnull String name, @Nonnull P[] values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setParameterList(int position, @SuppressWarnings("rawtypes") @Nonnull Collection values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setParameterList(int position, @Nonnull Collection<? extends P> values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setParameterList(int position, @Nonnull Collection<? extends P> values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setParameterList(int position, @Nonnull Object[] values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setParameterList(int position, @Nonnull P[] values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setParameterList(int position, @Nonnull P[] values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull Collection<? extends P> values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull Collection<? extends P> values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull Collection<? extends P> values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull P[] values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull P[] values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryImplementor<T> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull P[] values, @Nonnull Type<P> type);

	@Override @Deprecated(since = "7")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setParameter(int position, @Nullable Instant value, @Nonnull TemporalType temporalType);

	@Override @Deprecated(since = "7")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setParameter(int position, @Nullable Date value, @Nonnull TemporalType temporalType);

	@Override @Deprecated(since = "7")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setParameter(int position, @Nullable Calendar value, @Nonnull TemporalType temporalType);

	@Override @Deprecated(since = "7")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setParameter(@Nonnull String name, @Nullable Instant value, @Nonnull TemporalType temporalType);

	@Override @Deprecated(since = "7")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setParameter(@Nonnull String name, @Nullable Calendar value, @Nonnull TemporalType temporalType);

	@Override @Deprecated(since = "7")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setParameter(@Nonnull String name, @Nullable Date value, @Nonnull TemporalType temporalType);

	@Override @Deprecated(since = "7")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setParameter(@Nonnull Parameter<Calendar> param, @Nullable Calendar value, @Nonnull TemporalType temporalType);

	@Override @Deprecated(since = "7")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> setParameter(@Nonnull Parameter<Date> param, @Nullable Date value, @Nonnull TemporalType temporalType);
}
