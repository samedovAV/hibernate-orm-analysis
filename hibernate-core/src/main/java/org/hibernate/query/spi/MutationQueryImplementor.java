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
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.query.IllegalSelectQueryException;
import org.hibernate.query.MutationQuery;
import jakarta.persistence.QueryFlushMode;
import org.hibernate.query.QueryParameter;
import org.hibernate.query.named.StatementReferenceProducer;

import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * SPI form of {@linkplain MutationQuery}
 *
 * @author Steve Ebersole
 */
public interface MutationQueryImplementor<T>
		extends MutationQuery, QueryImplementor<T>, StatementReferenceProducer {

	@Override
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getMutationString() {
		return getQueryString();
	}

	@Override @Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<T> getTargetType();

	@Override @Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default MutationQueryImplementor<T> asMutationQuery() {
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setTimeout(int timeout);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setTimeout(@Nullable Integer integer);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setTimeout(@Nullable Timeout timeout);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setComment(@Nullable String comment);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setQueryFlushMode(@Nonnull QueryFlushMode queryFlushMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> addQueryHint(@Nonnull String hint);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setHint(@Nonnull String hintName, @Nullable Object value);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Parameters

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ParameterMetadataImplementor getParameterMetadata();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setParameter(@Nonnull String name, @Nullable Object value);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setParameter(@Nonnull String name, @Nullable P value, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setParameter(@Nonnull String name, @Nullable P value, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setParameter(int position, @Nullable Object value);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setParameters(@Nonnull Object... arguments);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setParameter(int position, @Nullable P value, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setParameter(int position, @Nullable P value, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setParameter(@Nonnull QueryParameter<P> parameter, @Nullable P value);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setParameter(@Nonnull QueryParameter<P> parameter, @Nullable P value, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setParameter(@Nonnull QueryParameter<P> parameter, @Nullable P val, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setParameter(@Nonnull Parameter<P> param, @Nullable P value);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setParameterList(@Nonnull String name, @SuppressWarnings("rawtypes") @Nonnull Collection values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setParameterList(@Nonnull String name, @Nonnull Collection<? extends P> values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setParameterList(@Nonnull String name, @Nonnull Collection<? extends P> values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setParameterList(@Nonnull String name, @Nonnull Object[] values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setParameterList(@Nonnull String name, @Nonnull P[] values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setParameterList(@Nonnull String name, @Nonnull P[] values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setParameterList(int position, @SuppressWarnings("rawtypes") @Nonnull Collection values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setParameterList(int position, @Nonnull Collection<? extends P> values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setParameterList(int position, @Nonnull Collection<? extends P> values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setParameterList(int position, @Nonnull Object[] values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setParameterList(int position, @Nonnull P[] values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setParameterList(int position, @Nonnull P[] values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull Collection<? extends P> values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull Collection<? extends P> values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull Collection<? extends P> values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull P[] values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull P[] values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull P[] values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setProperties(@Nonnull Object bean);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setProperties(@SuppressWarnings("rawtypes") @Nonnull Map bean);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setConvertedParameter(@Nonnull String name, @Nullable P value, @Nonnull Class<? extends AttributeConverter<P, ?>> converter);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> MutationQueryImplementor<T> setConvertedParameter(int position, @Nullable P value, @Nonnull Class<? extends AttributeConverter<P, ?>> converter);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// SelectionQuery stuff

	@Override
	@Deprecated
	@SuppressWarnings("removal")
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default LockModeType getLockMode() {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override
	@Deprecated
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default QueryImplementor<T> setLockMode(@Nonnull LockModeType lockMode) {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override
	@Deprecated
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default CacheStoreMode getCacheStoreMode() {
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override
	@Deprecated
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default QueryImplementor<T> setCacheStoreMode(@Nonnull CacheStoreMode cacheStoreMode) {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override
	@Deprecated
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default CacheRetrieveMode getCacheRetrieveMode() {
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override
	@Deprecated
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default QueryImplementor<T> setCacheRetrieveMode(@Nonnull CacheRetrieveMode cacheRetrieveMode) {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override
	@Deprecated
	@SuppressWarnings("removal")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default int getMaxResults() {
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override
	@Deprecated
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default QueryImplementor<T> setMaxResults(int maxResults) {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override
	@Deprecated
	@SuppressWarnings("removal")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default int getFirstResult() {
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override
	@Deprecated
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default QueryImplementor<T> setFirstResult(int startPosition) {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override @Deprecated
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default ScrollableResults<T> scroll() {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override @Deprecated
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default ScrollableResults<T> scroll(@Nonnull ScrollMode scrollMode) {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override @Deprecated
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default List<T> list() {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString() );
	}

	@Override @Deprecated
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default List<T> getResultList() {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString()  );	}

	@Override @Deprecated
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Stream<T> getResultStream() {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString()  );	}


	@Override @Deprecated
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Stream<T> stream() {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString()  );
	}

	@Override @Deprecated
	@SuppressWarnings("removal")
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default T uniqueResult() {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString()  );
	}

	@Override @Deprecated
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Optional<T> uniqueResultOptional() {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString()  );
	}

	@Override
	@Deprecated
	@SuppressWarnings("removal")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default T getSingleResult() {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString()  );
	}


	@Override
	@Deprecated
	@SuppressWarnings("removal")
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default T getSingleResultOrNull() {
		// IllegalStateException is the type required by JPA
		throw new IllegalStateException( "MutationQuery cannot be treated as a SelectionQuery - " + getMutationString()  );
	}


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Deprecations


	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setFlushMode(@Nonnull FlushModeType flushMode);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setParameter(@Nonnull String name, @Nullable Instant value, @Nonnull TemporalType temporalType);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setParameter(@Nonnull String name, @Nullable Calendar value, @Nonnull TemporalType temporalType);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setParameter(@Nonnull String name, @Nullable Date value, @Nonnull TemporalType temporalType);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setParameter(int position, @Nullable Instant value, @Nonnull TemporalType temporalType);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setParameter(int position, @Nullable Date value, @Nonnull TemporalType temporalType);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setParameter(int position, @Nullable Calendar value, @Nonnull TemporalType temporalType);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setParameter(@Nonnull Parameter<Calendar> param, @Nullable Calendar value, @Nonnull TemporalType temporalType);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> setParameter(@Nonnull Parameter<Date> param, @Nullable Date value, @Nonnull TemporalType temporalType);

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// SelectionQuery Handling

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SelectionQueryImplementor<T> asSelectionQuery() {
		throw new IllegalSelectQueryException( "Not a select query", getQueryString() );
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <X> SelectionQueryImplementor<X> asSelectionQuery(Class<X> type) {
		throw new IllegalSelectQueryException( "Not a select query", getQueryString() );
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <X> SelectionQueryImplementor<X> asSelectionQuery(EntityGraph<X> entityGraph) {
		throw new IllegalSelectQueryException( "Not a select query", getQueryString() );
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <X> SelectionQueryImplementor<X> asSelectionQuery(EntityGraph<X> entityGraph, GraphSemantic graphSemantic) {
		throw new IllegalSelectQueryException( "Not a select query", getQueryString() );
	}
}
