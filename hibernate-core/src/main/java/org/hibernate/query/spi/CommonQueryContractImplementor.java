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
import jakarta.persistence.FlushModeType;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Parameter;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Timeout;
import jakarta.persistence.metamodel.Type;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.query.CommonQueryContract;
import jakarta.persistence.QueryFlushMode;
import org.hibernate.query.QueryParameter;

import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// SPI form of CommonQueryContract
///
/// @author Steve Ebersole
public interface CommonQueryContractImplementor extends CommonQueryContract {
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionContractImplementor getSession();

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Options

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setQueryFlushMode(@Nonnull QueryFlushMode queryFlushMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setComment(@Nullable String comment);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor addQueryHint(@Nonnull String hint);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setTimeout(int timeout);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setTimeout(@Nullable Integer timeout);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setTimeout(@Nullable Timeout timeout);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setHint(@Nonnull String hintName, @Nullable Object value);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Parameter Handling

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryParameterBindings getParameterBindings();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> CommonQueryContractImplementor setParameter(@Nonnull QueryParameter<T> parameter, @Nullable T value);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> CommonQueryContractImplementor setParameter(@Nonnull Parameter<T> param, @Nullable T value);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setParameter(@Nonnull String parameter, @Nullable Object value);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setParameter(int parameter, @Nullable Object value);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setParameters(@Nonnull Object... arguments);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> CommonQueryContractImplementor setParameter(@Nonnull QueryParameter<P> parameter, @Nullable P value, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> CommonQueryContractImplementor setParameter(@Nonnull String parameter, @Nullable P value, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> CommonQueryContractImplementor setParameter(int parameter, @Nullable P value, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> CommonQueryContractImplementor setParameter(@Nonnull QueryParameter<P> parameter, @Nullable P val, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> CommonQueryContractImplementor setParameter(@Nonnull String parameter, @Nullable P value, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> CommonQueryContractImplementor setParameter(int parameter, @Nullable P value, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setProperties(@Nonnull Object bean);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setProperties(@SuppressWarnings("rawtypes") @Nonnull Map bean);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> CommonQueryContractImplementor setConvertedParameter(@Nonnull String name, @Nullable P value, @Nonnull Class<? extends AttributeConverter<P, ?>> converter);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> CommonQueryContractImplementor setConvertedParameter(int position, @Nullable P value, @Nonnull Class<? extends AttributeConverter<P, ?>> converter);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setParameterList(@Nonnull String parameter, @SuppressWarnings("rawtypes") @Nonnull Collection values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> CommonQueryContractImplementor setParameterList(@Nonnull String parameter, @Nonnull Collection<? extends P> values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> CommonQueryContractImplementor setParameterList(@Nonnull String parameter, @Nonnull Collection<? extends P> values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setParameterList(@Nonnull String parameter, @Nonnull Object[] values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> CommonQueryContractImplementor setParameterList(@Nonnull String parameter, @Nonnull P[] values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> CommonQueryContractImplementor setParameterList(@Nonnull String parameter, @Nonnull P[] values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setParameterList(int parameter, @SuppressWarnings("rawtypes") @Nonnull Collection values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> CommonQueryContractImplementor setParameterList(int parameter, @Nonnull Collection<? extends P> values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> CommonQueryContractImplementor setParameterList(int parameter, @Nonnull Collection<? extends P> values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setParameterList(int parameter, @Nonnull Object[] values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> CommonQueryContractImplementor setParameterList(int parameter, @Nonnull P[] values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> CommonQueryContractImplementor setParameterList(int parameter, @Nonnull P[] values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> CommonQueryContractImplementor setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull Collection<? extends P> values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> CommonQueryContractImplementor setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull Collection<? extends P> values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> CommonQueryContractImplementor setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull Collection<? extends P> values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> CommonQueryContractImplementor setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull P[] values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> CommonQueryContractImplementor setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull P[] values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> CommonQueryContractImplementor setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull P[] values, @Nonnull Type<P> type);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Deprecated stuff

	@SuppressWarnings("removal")
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setMaxResults(int maxResult);

	@SuppressWarnings("removal")
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setFirstResult(int startPosition);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setFlushMode(@Nonnull FlushModeType flushMode);

	@SuppressWarnings("removal")
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setLockMode(@Nonnull LockModeType lockMode);

	@SuppressWarnings("removal")
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setCacheRetrieveMode(@Nonnull CacheRetrieveMode cacheRetrieveMode);

	@SuppressWarnings("removal")
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setCacheStoreMode(@Nonnull CacheStoreMode cacheStoreMode);

	@SuppressWarnings("deprecation")
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setParameter(@Nonnull String parameter, @Nullable Instant value, @Nonnull TemporalType temporalType);

	@SuppressWarnings("deprecation")
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setParameter(@Nonnull String parameter, @Nullable Calendar value, @Nonnull TemporalType temporalType);

	@SuppressWarnings("deprecation")
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setParameter(@Nonnull String parameter, @Nullable Date value, @Nonnull TemporalType temporalType);

	@SuppressWarnings("deprecation")
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setParameter(int parameter, @Nullable Instant value, @Nonnull TemporalType temporalType);

	@SuppressWarnings("deprecation")
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setParameter(int parameter, @Nullable Date value, @Nonnull TemporalType temporalType);

	@SuppressWarnings("deprecation")
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setParameter(int parameter, @Nullable Calendar value, @Nonnull TemporalType temporalType);

	@SuppressWarnings("deprecation")
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setParameter(@Nonnull Parameter<Calendar> param, @Nullable Calendar value, @Nonnull TemporalType temporalType);

	@SuppressWarnings("deprecation")
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CommonQueryContractImplementor setParameter(@Nonnull Parameter<Date> param, @Nullable Date value, @Nonnull TemporalType temporalType);
}
