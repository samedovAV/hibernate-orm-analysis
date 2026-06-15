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
import jakarta.persistence.PessimisticLockScope;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Timeout;
import jakarta.persistence.metamodel.Type;
import org.hibernate.CacheMode;
import org.hibernate.LockMode;
import org.hibernate.Locking;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.query.IllegalMutationQueryException;
import org.hibernate.query.Page;
import jakarta.persistence.QueryFlushMode;
import org.hibernate.query.QueryParameter;
import org.hibernate.query.ResultListTransformer;
import org.hibernate.query.SelectionQuery;
import org.hibernate.query.TupleTransformer;
import org.hibernate.query.named.TypedQueryReferenceProducer;

import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * SPI form of {@linkplain SelectionQuery}
 *
 * @author Steve Ebersole
 */
public interface SelectionQueryImplementor<R>
		extends SelectionQuery<R>, QueryImplementor<R>, TypedQueryReferenceProducer {

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Options

	@Override @Deprecated
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setEntityGraph(@Nonnull EntityGraph<? super R> entityGraph);

	@Override @Deprecated
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setEntityGraph(@Nonnull EntityGraph<? super R> graph, @Nonnull GraphSemantic semantic);

	@Override
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> enableFetchProfile(@Nonnull String profileName);

	@Override
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> disableFetchProfile(@Nonnull String profileName);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setFlushMode(@Nonnull FlushModeType flushMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setQueryFlushMode(@Nonnull QueryFlushMode queryFlushMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setTimeout(int timeout);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setTimeout(@Nullable Integer timeout);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setTimeout(@Nullable Timeout timeout);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setComment(@Nullable String comment);

	@Override
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setFetchSize(int fetchSize);

	@Override
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setReadOnly(boolean readOnly);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setMaxResults(int maxResults);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setFirstResult(int startPosition);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setPage(Page page);

	@Override
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setCacheMode(@Nonnull CacheMode cacheMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setCacheStoreMode(@Nonnull CacheStoreMode cacheStoreMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setCacheRetrieveMode(@Nonnull CacheRetrieveMode cacheRetrieveMode);

	@Override
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setCacheable(boolean cacheable);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setQueryPlanCacheable(boolean queryPlanCacheable);

	@Override
	@SuppressWarnings("removal")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setCacheRegion(@Nullable String cacheRegion);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setLockMode(@Nonnull LockModeType lockMode);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setLockTimeout(Timeout lockTimeout);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setHibernateLockMode(@Nonnull LockMode lockMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setFollowOnStrategy(@Nonnull Locking.FollowOn followOnStrategy);

	@Override
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SelectionQueryImplementor<T> setTupleTransformer(@Nonnull TupleTransformer<T> transformer);

	@Override
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setResultListTransformer(@Nonnull ResultListTransformer<R> transformer);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setLockScope(@Nonnull PessimisticLockScope lockScope);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> addQueryHint(@Nonnull String hint);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setHint(@Nonnull String hintName, @Nullable Object value);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Parameter Handling

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setParameter(@Nonnull String name, @Nullable Object value);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> SelectionQueryImplementor<R> setParameter(@Nonnull String name, @Nullable P value, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> SelectionQueryImplementor<R> setParameter(@Nonnull String name, @Nullable P value, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setParameter(int position, @Nullable Object value);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setParameters(@Nonnull Object... arguments);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> SelectionQueryImplementor<R> setParameter(int position, @Nullable P value, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> SelectionQueryImplementor<R> setParameter(int position, @Nullable P value, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SelectionQueryImplementor<R> setParameter(@Nonnull QueryParameter<T> parameter, @Nullable T value);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> SelectionQueryImplementor<R> setParameter(@Nonnull QueryParameter<P> parameter, @Nullable P value, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> SelectionQueryImplementor<R> setParameter(@Nonnull QueryParameter<P> parameter, @Nullable P val, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SelectionQueryImplementor<R> setParameter(@Nonnull Parameter<T> param, @Nullable T value);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setProperties(@Nonnull Object bean);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setProperties(@SuppressWarnings("rawtypes") @Nonnull Map bean);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> SelectionQueryImplementor<R> setConvertedParameter(@Nonnull String name, @Nullable P value, @Nonnull Class<? extends AttributeConverter<P, ?>> converter);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> SelectionQueryImplementor<R> setConvertedParameter(int position, @Nullable P value, @Nonnull Class<? extends AttributeConverter<P, ?>> converter);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setParameterList(@Nonnull String name, @SuppressWarnings("rawtypes") @Nonnull Collection values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> SelectionQueryImplementor<R> setParameterList(@Nonnull String name, @Nonnull Collection<? extends P> values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> SelectionQueryImplementor<R> setParameterList(@Nonnull String name, @Nonnull Collection<? extends P> values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setParameterList(@Nonnull String name, @Nonnull Object[] values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> SelectionQueryImplementor<R> setParameterList(@Nonnull String name, @Nonnull P[] values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> SelectionQueryImplementor<R> setParameterList(@Nonnull String name, @Nonnull P[] values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setParameterList(int position, @SuppressWarnings("rawtypes") @Nonnull Collection values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> SelectionQueryImplementor<R> setParameterList(int position, @Nonnull Collection<? extends P> values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> SelectionQueryImplementor<R> setParameterList(int position, @Nonnull Collection<? extends P> values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setParameterList(int position, @Nonnull Object[] values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> SelectionQueryImplementor<R> setParameterList(int position, @Nonnull P[] values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> SelectionQueryImplementor<R> setParameterList(int position, @Nonnull P[] values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> SelectionQueryImplementor<R> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull Collection<? extends P> values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> SelectionQueryImplementor<R> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull Collection<? extends P> values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> SelectionQueryImplementor<R> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull Collection<? extends P> values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> SelectionQueryImplementor<R> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull P[] values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> SelectionQueryImplementor<R> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull P[] values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> SelectionQueryImplementor<R> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull P[] values, @Nonnull Type<P> type);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setParameter(@Nonnull String name, @Nullable Instant value, @Nonnull TemporalType temporalType);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setParameter(@Nonnull String name, @Nullable Calendar value, @Nonnull TemporalType temporalType);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setParameter(@Nonnull String name, @Nullable Date value, @Nonnull TemporalType temporalType);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setParameter(int position, @Nullable Instant value, @Nonnull TemporalType temporalType);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setParameter(int position, @Nullable Date value, @Nonnull TemporalType temporalType);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setParameter(int position, @Nullable Calendar value, @Nonnull TemporalType temporalType);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setParameter(@Nonnull Parameter<Calendar> param, @Nullable Calendar value, @Nonnull TemporalType temporalType);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<R> setParameter(@Nonnull Parameter<Date> param, @Nullable Date value, @Nonnull TemporalType temporalType);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// MutationQuery Handling

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default MutationQueryImplementor<R> asMutationQuery() {
		throw new IllegalMutationQueryException( "SelectionQuery cannot be treated as a MutationQuery", getQueryString() );
	}

	@Override
	@Deprecated
	@SuppressWarnings("removal")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default int executeUpdate() {
		// per JPA, again, needs to be IllegalStateException
		throw new IllegalStateException( "SelectionQuery cannot be treated as a MutationQuery - " + getQueryString() );
	}
}
