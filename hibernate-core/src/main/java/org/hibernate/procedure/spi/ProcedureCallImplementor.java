/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.procedure.spi;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Parameter;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Timeout;
import jakarta.persistence.metamodel.Type;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.procedure.NamedParametersNotSupportedException;
import org.hibernate.procedure.ProcedureCall;
import jakarta.persistence.QueryFlushMode;
import org.hibernate.query.named.NameableQuery;
import org.hibernate.query.spi.ProcedureParameterMetadataImplementor;
import org.hibernate.query.spi.QueryImplementor;
import org.hibernate.query.spi.QueryParameterBindings;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface ProcedureCallImplementor<R> extends ProcedureCall, QueryImplementor<R>, NameableQuery {
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionContractImplementor getSession();

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Options

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> setFlushMode(@Nonnull FlushModeType flushMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> setQueryFlushMode(@Nonnull QueryFlushMode queryFlushMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> setTimeout(@Nullable Integer timeout);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> setTimeout(int timeout);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> setTimeout(@Nullable Timeout timeout);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> setComment(@Nullable String comment);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> addQueryHint(@Nonnull String hint);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> setCacheRetrieveMode(@Nonnull CacheRetrieveMode cacheRetrieveMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> setCacheStoreMode(@Nonnull CacheStoreMode cacheStoreMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> setMaxResults(int maxResults);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> setFirstResult(int startPosition);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> setLockMode(@Nonnull LockModeType lockMode);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> setHint(@Nonnull String hintName, @Nullable Object value);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Parameter handling

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureParameterMetadataImplementor getParameterMetadata();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ParameterStrategy getParameterStrategy();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryParameterBindings getParameterBindings();

	@Override
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FunctionReturnImplementor<?> getFunctionReturn();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> FunctionReturnImplementor<T> registerResultParameter(Class<T> resultType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> registerStoredProcedureParameter(int position, Class<?> type, ParameterMode mode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> registerStoredProcedureParameter(String parameterName, Class<?> type, ParameterMode mode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> registerStoredProcedureParameter(int position, Type<?> type, ParameterMode mode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> registerStoredProcedureParameter(String parameterName, Type<?> type, ParameterMode mode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> ProcedureParameterImplementor<T> registerConvertedParameter(int position, Class<? extends AttributeConverter<T, ?>> converter, ParameterMode mode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> ProcedureParameterImplementor<T> registerConvertedParameter(String parameterName, Class<? extends AttributeConverter<T, ?>> converter, ParameterMode mode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> ProcedureParameterImplementor<T> registerParameter(int position, Class<T> type, ParameterMode mode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> ProcedureParameterImplementor<T> registerParameter(int position, Type<T> type, ParameterMode mode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> ProcedureParameterImplementor<T> registerParameter(String parameterName, Class<T> type, ParameterMode mode)
			throws NamedParametersNotSupportedException;

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> ProcedureParameterImplementor<T> registerParameter(String parameterName, Type<T> type, ParameterMode mode)
			throws NamedParametersNotSupportedException;

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureParameterImplementor<?> getParameterRegistration(int position);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureParameterImplementor<?> getParameterRegistration(@Nonnull String name);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> ProcedureCallImplementor<R> setParameter(@Nonnull Parameter<T> param, @Nullable T value);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> setParameter(@Nonnull Parameter<Calendar> param, @Nullable Calendar value, @Nonnull TemporalType temporalType);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> setParameter(@Nonnull Parameter<Date> param, @Nullable Date value, @Nonnull TemporalType temporalType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> setParameter(@Nonnull String name, @Nullable Object value);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> setParameter(@Nonnull String name, @Nullable Calendar value, @Nonnull TemporalType temporalType);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> setParameter(@Nonnull String name, @Nullable Date value, @Nonnull TemporalType temporalType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> ProcedureCallImplementor<R> setParameter(@Nonnull String name, @Nullable P value, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> ProcedureCallImplementor<R> setParameter(@Nonnull String name, @Nullable P value, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> setParameter(int position, @Nullable Object value);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> setParameters(@Nonnull Object... arguments);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> ProcedureCallImplementor<R> setParameter(int position, @Nullable P value, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> ProcedureCallImplementor<R> setParameter(int position, @Nullable P value, @Nonnull Type<P> type);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> setParameter(int position, @Nullable Calendar value, @Nonnull TemporalType temporalType);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> setParameter(int position, @Nullable Date value, @Nonnull TemporalType temporalType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> setProperties(@Nonnull Object bean);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor<R> setProperties(@SuppressWarnings("rawtypes") @Nonnull Map bean);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> ProcedureCallImplementor<R> setConvertedParameter(@Nonnull String name, @Nullable P value, @Nonnull Class<? extends AttributeConverter<P, ?>> converter);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> ProcedureCallImplementor<R> setConvertedParameter(int position, @Nullable P value, @Nonnull Class<? extends AttributeConverter<P, ?>> converter);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Execution

	@Override
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<R> getResultList();

	@Override
	@SuppressWarnings("removal")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	R getSingleResult();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NamedCallableQueryMemento toMemento(String name);
}
