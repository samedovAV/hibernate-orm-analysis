/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Parameter;
import jakarta.persistence.PessimisticLockScope;
import jakarta.persistence.QueryFlushMode;
import jakarta.persistence.Statement;
import jakarta.persistence.StatementOrTypedQuery;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Timeout;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.Type;
import org.hibernate.CacheMode;
import org.hibernate.LockMode;
import org.hibernate.MappingException;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.metamodel.mapping.PluralAttributeMapping;
import org.hibernate.metamodel.model.domain.BasicDomainType;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.results.graph.Fetchable;
import org.hibernate.type.BasicTypeReference;

import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Within the context of an active {@linkplain org.hibernate.Session session},
 * an instance of this type represents an executable query written in the
 * native SQL dialect of the underlying database. Since Hibernate does not
 * actually understand SQL, it often requires some help in interpreting the
 * semantics of a native SQL query.
 * <p>
 * Along with the operations inherited from {@link Query}, this interface
 * provides control over:
 * <ul>
 * <li>mapping the result set of the native SQL query, and
 * <li>synchronization of the database with state held in memory before
 *     execution of the query, via automatic flushing of the session.
 * </ul>
 * <p>
 * A {@code NativeQuery} may be obtained from the {@link org.hibernate.Session}
 * by calling:
 * <ul>
 * <li>{@link org.hibernate.SharedSessionContract#createNativeQuery(String, Class)}, passing
 *     native SQL as a string, or
 * <li>{@link org.hibernate.SharedSessionContract#createNativeQuery(String, String, Class)}
 *     passing the native SQL string and the name of a result set mapping
 *     defined using {@link jakarta.persistence.SqlResultSetMapping}.
 * </ul>
 * <p>
 * A result set mapping may be specified by:
 * <ul>
 * <li>a named {@link jakarta.persistence.SqlResultSetMapping} passed to
 *     {@link org.hibernate.SharedSessionContract#createNativeQuery(String, String, Class)},
 * <li>a named  {@link jakarta.persistence.SqlResultSetMapping} specified
 *     using {@link jakarta.persistence.NamedNativeQuery#resultSetMapping}
 *     for a named query, or
 * <li>by calling the various {@link #addEntity}, {@link #addRoot},
 *     {@link #addJoin}, {@link #addFetch} and {@link #addScalar} methods
 *     of this object.
 * </ul>
 * <p>
 * The third option is a legacy of much older versions of Hibernate and is
 * currently disfavored.
 * <p>
 * To determine if an automatic {@linkplain org.hibernate.Session#flush flush}
 * is required before execution of the query, Hibernate must know which tables
 * affect the query result set. JPA provides no standard way to do this.
 * Instead, this information may be provided via:
 * <ul>
 * <li>{@link org.hibernate.annotations.NamedNativeQuery#querySpaces} for
 *     a named query, or
 * <li>by calling {@link #addSynchronizedEntityClass},
 *     {@link #addSynchronizedEntityName}, or
 *     {@link #addSynchronizedQuerySpace}.
 * </ul>
 * <p>
 * When the affected tables are not known to Hibernate, the behavior depends
 * on whether Hibernate is operating in fully JPA-compliant mode.
 * <ul>
 * <li>In JPA-compliant mode, {@link FlushModeType#AUTO} specifies that the
 *     session should be flushed before execution of a native query when the
 *     affected tables are not known.
 * <li>Otherwise, when Hibernate is not operating in JPA-compliant mode,
 *     {@code AUTO} specifies that the session is <em>not</em> flushed before
 *     execution of a native query, unless the affected tables are known and
 *     Hibernate determines that a flush is required.
 * </ul>
 *
 * @author Gavin King
 * @author Steve Ebersole
 *
 * @see Query
 * @see SynchronizeableQuery
 * @see org.hibernate.SharedSessionContract
 */
public interface NativeQuery<T>
		extends SelectionQuery<T>, MutationQuery, SynchronizeableQuery, StatementOrTypedQuery {

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> addOption(TypedQuery.Option option);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> addOption(Statement.Option option);

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set getOptions();


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// covariant overrides - SynchronizeableQuery
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> addSynchronizedQuerySpace(String querySpace);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> addSynchronizedEntityName(String entityName) throws MappingException;

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> addSynchronizedEntityClass(@SuppressWarnings("rawtypes") Class entityClass) throws MappingException;


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// covariant overrides - SelectionQuery & MutationQuery


	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setTimeout(@Nullable Integer timeout);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setQueryPlanCacheable(boolean queryPlanCacheable);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setQueryFlushMode(@Nonnull QueryFlushMode queryFlushMode);

	@Override @Deprecated(since = "7")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setFlushMode(@Nonnull FlushModeType flushMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setCacheMode(@Nonnull CacheMode cacheMode);

	@Override @SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setCacheStoreMode(@Nonnull CacheStoreMode cacheStoreMode);

	@Override @SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setCacheRetrieveMode(@Nonnull CacheRetrieveMode cacheRetrieveMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setCacheable(boolean cacheable);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setCacheRegion(@Nullable String cacheRegion);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setTimeout(int timeout);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setFetchSize(int fetchSize);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setReadOnly(boolean readOnly);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setComment(@Nullable String comment);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> addQueryHint(@Nonnull String hint);

	@Override @SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setMaxResults(int maxResults);

	@Override @SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setFirstResult(int startPosition);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setHint(@Nonnull String hintName, @Nullable Object value);

	/**
	 * Not applicable to native SQL queries, due to an unfortunate
	 * requirement of the JPA specification.
	 * <p>
	 * Use {@link #getHibernateLockMode()} to obtain the lock mode.
	 *
	 * @throws IllegalStateException as required by JPA
	 */
	@SuppressWarnings("removal")
	@Override
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LockModeType getLockMode();

	/**
	 * {@inheritDoc}
	 * <p>
	 * This operation is supported even for native queries.
	 * Note that specifying an explicit lock mode might
	 * result in changes to the native SQL query that is
	 * actually executed.
	 */
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LockMode getHibernateLockMode();

	/**
	 * Not applicable to native SQL queries, due to an unfortunate
	 * requirement of the JPA specification.
	 * <p>
	 * Use {@link #setHibernateLockMode(LockMode)} or the hint named
	 * {@value org.hibernate.jpa.HibernateHints#HINT_NATIVE_LOCK_MODE}
	 * to set the lock mode.
	 *
	 * @throws IllegalStateException as required by JPA
	 */
	@Override @SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setLockMode(@Nonnull LockModeType lockMode);

	/**
	 * {@inheritDoc}
	 * <p>
	 * This operation is supported even for native queries.
	 * Note that specifying an explicit lock mode might
	 * result in changes to the native SQL query that is
	 * actually executed.
	 */
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setHibernateLockMode(@Nonnull LockMode lockMode);

	/**
	 * Apply a timeout to the corresponding database query.
	 *
	 * @param timeout The timeout to apply
	 *
	 * @return {@code this}, for method chaining
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setTimeout(@Nullable Timeout timeout);

	/**
	 * Apply a scope to any pessimistic locking applied to the query.
	 *
	 * @param lockScope The lock scope to apply
	 *
	 * @return {@code this}, for method chaining
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setLockScope(@Nonnull PessimisticLockScope lockScope);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> SelectionQuery<X> setTupleTransformer(@Nonnull TupleTransformer<X> transformer);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQuery<T> setResultListTransformer(@Nonnull ResultListTransformer<T> transformer);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setParameter(@Nonnull String name, @Nullable Object value);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setParameter(@Nonnull String name, @Nullable P val, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setParameter(@Nonnull String name, @Nullable P val, @Nonnull Type<P> type);

	@Override @Deprecated(since = "7")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setParameter(@Nonnull String name, @Nullable Instant value, @Nonnull TemporalType temporalType);

	@Override @Deprecated(since = "7")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setParameter(@Nonnull String name, @Nullable Calendar value, @Nonnull TemporalType temporalType);

	@Override @Deprecated(since = "7")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setParameter(@Nonnull String name, @Nullable Date value, @Nonnull TemporalType temporalType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setParameter(int position, @Nullable Object value);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setParameters(@Nonnull Object... arguments);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setParameter(int position, @Nullable P val, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setParameter(int position, @Nullable P val, @Nonnull Type<P> type);

	@Override @Deprecated(since = "7")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setParameter(int position, @Nullable Instant value, @Nonnull TemporalType temporalType);

	@Override @Deprecated(since = "7")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setParameter(int position, @Nullable Calendar value, @Nonnull TemporalType temporalType);

	@Override @Deprecated(since = "7")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setParameter(int position, @Nullable Date value, @Nonnull TemporalType temporalType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setParameter(@Nonnull QueryParameter<P> parameter, @Nullable P val);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setParameter(@Nonnull QueryParameter<P> parameter, @Nullable P val, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setParameter(@Nonnull QueryParameter<P> parameter, @Nullable P val, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setParameter(@Nonnull Parameter<P> param, @Nullable P value);

	@Override @Deprecated(since = "7")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setParameter(@Nonnull Parameter<Calendar> param, @Nullable Calendar value, @Nonnull TemporalType temporalType);

	@Override @Deprecated(since = "7")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setParameter(@Nonnull Parameter<Date> param, @Nullable Date value, @Nonnull TemporalType temporalType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setProperties(@Nonnull Object bean);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setProperties(@SuppressWarnings("rawtypes") @Nonnull Map bean);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setConvertedParameter(@Nonnull String name, @Nullable P value, @Nonnull Class<? extends AttributeConverter<P, ?>> converter);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setConvertedParameter(int position, @Nullable P value, @Nonnull Class<? extends AttributeConverter<P, ?>> converter);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setParameterList(@Nonnull String name, @SuppressWarnings("rawtypes") @Nonnull Collection values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setParameterList(@Nonnull String name, @Nonnull Collection<? extends P> values, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setParameterList(@Nonnull String name, @Nonnull Collection<? extends P> values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setParameterList(@Nonnull String name, @Nonnull Object[] values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setParameterList(@Nonnull String name, @Nonnull P[] values, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setParameterList(@Nonnull String name, @Nonnull P[] values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setParameterList(int position, @SuppressWarnings("rawtypes") @Nonnull Collection values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setParameterList(int position, @Nonnull Collection<? extends P> values, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setParameterList(int position, @Nonnull Collection<? extends P> values, @Nonnull Type<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> setParameterList(int position, @Nonnull Object[] values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setParameterList(int position, @Nonnull P[] values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setParameterList(int position, @Nonnull P[] values, @Nonnull Type<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull Collection<? extends P> values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull Collection<? extends P> values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull Collection<? extends P> values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull P[] values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull P[] values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQuery<T> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull P[] values, @Nonnull Type<P> type);


	/**
	 * Declare a scalar query result. Hibernate will attempt to automatically
	 * detect the underlying type.
	 * <p>
	 * Functions like {@code <return-scalar/>} in {@code hbm.xml} or
	 * {@link jakarta.persistence.ColumnResult} in annotations
	 *
	 * @param columnAlias The column alias in the result set to be
	 *                    processed as a scalar result
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> addScalar(String columnAlias);

	/**
	 * Declare a scalar query result.
	 * <p>
	 * Functions like {@code <return-scalar/>} in {@code hbm.xml} or
	 * {@link jakarta.persistence.ColumnResult} in annotations.
	 *
	 * @param columnAlias The column alias in the result set to be
	 *                    processed as a scalar result
	 * @param type The Hibernate type as which to treat the value.
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> addScalar(String columnAlias, @SuppressWarnings("rawtypes") BasicTypeReference type);

	/**
	 * Declare a scalar query result.
	 * <p>
	 * Functions like {@code <return-scalar/>} in {@code hbm.xml} or
	 * {@link jakarta.persistence.ColumnResult} in annotations.
	 *
	 * @param columnAlias The column alias in the result set to be
	 *                    processed as a scalar result
	 * @param type The Hibernate type as which to treat the value.
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> addScalar(String columnAlias, @SuppressWarnings("rawtypes") BasicDomainType type);

	/**
	 * Declare a scalar query result using the specified result type.
	 * <p>
	 * Hibernate will implicitly determine an appropriate conversion,
	 * if it can. Otherwise, an exception will be thrown.
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @since 6.0
	 *
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> addScalar(String columnAlias, @SuppressWarnings("rawtypes") Class javaType);

	/**
	 * Declare a scalar query result with an explicit conversion.
	 *
	 * @param relationalJavaType The Java type expected by the converter as its
	 *                           "relational" type.
	 * @param converter The conversion to apply. Consumes the JDBC value based
	 *                  on {@code relationalJavaType}.
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @since 6.0
	 *
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C> NativeQuery<T> addScalar(String columnAlias, Class<C> relationalJavaType, AttributeConverter<?,C> converter);

	/**
	 * Declare a scalar query result with an explicit conversion.
	 *
	 * @param jdbcJavaType The Java type expected by the converter as its
	 *                     "relational model" type.
	 * @param domainJavaType The Java type expected by the converter as its
	 *                       "object model" type.
	 * @param converter The conversion to apply. Consumes the JDBC value based
	 *                  on {@code relationalJavaType}.
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @since 6.0
	 *
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<O,R> NativeQuery<T> addScalar(String columnAlias, Class<O> domainJavaType, Class<R> jdbcJavaType, AttributeConverter<O,R> converter);

	/**
	 * Declare a scalar query result with an explicit conversion.
	 *
	 * @param relationalJavaType The Java type expected by the converter as its
	 *                           "relational" type.
	 * @param converter The conversion to apply. Consumes the JDBC value based
	 *                  on {@code relationalJavaType}.
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @since 6.0
	 *
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C> NativeQuery<T> addScalar(String columnAlias, Class<C> relationalJavaType, Class<? extends AttributeConverter<?,C>> converter);

	/**
	 * Declare a scalar query result with an explicit conversion.
	 *
	 * @param jdbcJavaType The Java type expected by the converter as its
	 *                     "relational model" type.
	 * @param domainJavaType The Java type expected by the converter as its
	 *                       "object model" type.
	 * @param converter The conversion to apply.  Consumes the JDBC value
	 *                  based on {@code relationalJavaType}.
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @since 6.0
	 *
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<O,R> NativeQuery<T> addScalar(
			String columnAlias,
			Class<O> domainJavaType,
			Class<R> jdbcJavaType,
			Class<? extends AttributeConverter<O,R>> converter);

	/**
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<J> InstantiationResultNode<J> addInstantiation(Class<J> targetJavaType);

	/**
	 * Defines a result based on a specified attribute. Differs from adding
	 * a scalar in that any conversions or other semantics defined on the
	 * attribute are automatically applied to the mapping.
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @since 6.0
	 *
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> addAttributeResult(String columnAlias, @SuppressWarnings("rawtypes") Class entityJavaType, String attributePath);

	/**
	 * Defines a result based on a specified attribute.  Differs from adding
	 * a scalar in that any conversions or other semantics defined on the
	 * attribute are automatically applied to the mapping.
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @since 6.0
	 *
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> addAttributeResult(String columnAlias, String entityName, String attributePath);

	/**
	 * Defines a result based on a specified attribute. Differs from adding a
	 * scalar in that any conversions or other semantics defined on the attribute
	 * are automatically applied to the mapping.
	 * <p>
	 * This form accepts the JPA Attribute mapping describing the attribute
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @since 6.0
	 *
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> addAttributeResult(String columnAlias, @SuppressWarnings("rawtypes") SingularAttribute attribute);

	/**
	 * Add a new root return mapping, returning a {@link RootReturn} to allow
	 * further definition.
	 *
	 * @param tableAlias The SQL table alias to map to this entity
	 * @param entityName The name of the entity
	 *
	 * @return The return config object for further control.
	 *
	 * @since 3.6
	 *
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	RootReturn addRoot(String tableAlias, String entityName);

	/**
	 * Add a new root return mapping, returning a {@link RootReturn} to allow
	 * further definition.
	 *
	 * @param tableAlias The SQL table alias to map to this entity
	 * @param entityType The java type of the entity
	 *
	 * @return The return config object for further control.
	 *
	 * @since 3.6
	 *
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	RootReturn addRoot(String tableAlias, @SuppressWarnings("rawtypes") Class entityType);

	/**
	 * Declare a "root" entity, without specifying an alias. The expectation
	 * here is that the table alias is the same as the unqualified entity name.
	 * <p>
	 * Use {@link #addRoot} if you need further control of the mapping
	 *
	 * @param entityName The entity name that is the root return of the query
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> addEntity(String entityName);

	/**
	 * Declare a "root" entity.
	 *
	 * @param tableAlias The SQL table alias
	 * @param entityName The entity name
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> addEntity(String tableAlias, String entityName);

	/**
	 * Declare a "root" entity, specifying a lock mode.
	 *
	 * @param tableAlias The SQL table alias
	 * @param entityName The entity name
	 * @param lockMode The lock mode for this return.
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> addEntity(String tableAlias, String entityName, LockMode lockMode);

	/**
	 * Declare a "root" entity, without specifying an alias. The expectation here
	 * is that the table alias is the same as the unqualified entity name.
	 *
	 * @param entityType The java type of the entity to add as a root
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> addEntity(@SuppressWarnings("rawtypes") Class entityType);

	/**
	 * Declare a "root" entity.
	 *
	 * @param tableAlias The SQL table alias
	 * @param entityType The java type of the entity to add as a root
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> addEntity(String tableAlias, @SuppressWarnings("rawtypes") Class entityType);

	/**
	 * Declare a "root" entity, specifying a lock mode.
	 *
	 * @param tableAlias The SQL table alias
	 * @param entityClass The entity {@link Class}
	 * @param lockMode The lock mode for this return
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> addEntity(String tableAlias, @SuppressWarnings("rawtypes") Class entityClass, LockMode lockMode);

	/**
	 * Declare a join fetch result.
	 *
	 * @param tableAlias The SQL table alias for the data to be mapped to this fetch.
	 * @param ownerTableAlias Identify the table alias of the owner of this association.
	 *                        Should match the alias of a previously added root or fetch.
	 * @param joinPropertyName The name of the property being join fetched.
	 *
	 * @return The return config object for further control.
	 *
	 * @since 3.6
	 *
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FetchReturn addFetch(String tableAlias, String ownerTableAlias, String joinPropertyName);

	/**
	 * Declare a join fetch result.
	 *
	 * @param tableAlias The SQL table alias for the data to be mapped to this fetch.
	 * @param path The association path of form {@code [owner-alias].[property-name]}.
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> addJoin(String tableAlias, String path);

	/**
	 * Declare a join fetch result.
	 *
	 * @param tableAlias The SQL table alias for the data to be mapped to this fetch
	 * @param ownerTableAlias Identify the table alias of the owner of this association.
	 *                        Should match the alias of a previously added root or fetch.
	 * @param joinPropertyName The name of the property being join fetched.
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @since 3.6
	 *
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> addJoin(String tableAlias, String ownerTableAlias, String joinPropertyName);

	/**
	 * Declare a join fetch result, specifying a lock mode.
	 *
	 * @param tableAlias The SQL table alias for the data to be mapped to this fetch
	 * @param path The association path of form {@code [owner-alias].[property-name]}.
	 * @param lockMode The lock mode for this return.
	 *
	 * @return {@code this}, for method chaining
	 *
	 * @deprecated Use {@linkplain jakarta.persistence.sql.ResultSetMapping}, or
	 * similar approach pending the outcome of
	 * <a href="https://github.com/jakartaee/persistence/issues/887">this Jakarta Persistence request</a>.
	 */
	@Deprecated(since = "8.0")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQuery<T> addJoin(String tableAlias, String path, LockMode lockMode);

	/**
	 * Simple unification interface for all returns from the various {@code addXYZ()}
	 * methods. Allows control over the "shape" of that particular part of the fetch
	 * graph.
	 * <p>
	 * Some nodes can be query results, while others simply describe a part of one of
	 * the results.
	 */
	interface ResultNode {
	}

	/**
	 * A {@link ResultNode} which can be a query result.
	 */
	interface ReturnableResultNode extends ResultNode {
	}

	interface InstantiationResultNode<J> extends ReturnableResultNode {
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		default InstantiationResultNode<J> addBasicArgument(String columnAlias) {
			return addBasicArgument( columnAlias, null );
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		InstantiationResultNode<J> addBasicArgument(String columnAlias, String argumentAlias);
	}

	/**
	 * Allows access to further control how properties within a root or join
	 * fetch are mapped back from the result set. Generally used in composite
	 * value scenarios.
	 */
	interface ReturnProperty extends ResultNode {
		/**
		 * Add a column alias to this property mapping.
		 *
		 * @param columnAlias The column alias.
		 *
		 * @return {@code this}, for method chaining
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		ReturnProperty addColumnAlias(String columnAlias);
	}

	/**
	 * Allows access to further control how root returns are mapped back from
	 * result sets.
	 */
	interface RootReturn extends ReturnableResultNode {

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		String getTableAlias();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		String getDiscriminatorAlias();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		EntityMappingType getEntityMapping();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		NavigablePath getNavigablePath();

	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LockMode getLockMode();

		/**
		 * Set the lock mode for this return.
		 *
		 * @param lockMode The new lock mode.
		 *
		 * @return {@code this}, for method chaining
		 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	RootReturn setLockMode(LockMode lockMode);

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		RootReturn addIdColumnAliases(String... aliases);

		/**
		 * Name the column alias that identifies the entity's discriminator.
		 *
		 * @param columnAlias The discriminator column alias
		 *
		 * @return {@code this}, for method chaining
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		RootReturn setDiscriminatorAlias(String columnAlias);

		/**
		 * Add a simple property-to-one-column mapping.
		 *
		 * @param propertyName The name of the property.
		 * @param columnAlias The name of the column
		 *
		 * @return {@code this}, for method chaining
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		RootReturn addProperty(String propertyName, String columnAlias);

		/**
		 * Add a property, presumably with more than one column.
		 *
		 * @param propertyName The name of the property.
		 *
		 * @return The config object for further control.
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		ReturnProperty addProperty(String propertyName);
	}

	/**
	 * Allows access to further control how collection returns are mapped back
	 * from result sets.
	 */
	interface CollectionReturn extends ReturnableResultNode {

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		String getTableAlias();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		PluralAttributeMapping getPluralAttribute();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		NavigablePath getNavigablePath();
	}

	/**
	 * Allows access to further control how join fetch returns are mapped back
	 * from result sets.
	 */
	interface FetchReturn extends ResultNode {

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		String getTableAlias();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		String getOwnerAlias();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		Fetchable getFetchable();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		String getFetchableName();

		/**
		 * Set the lock mode for this return.
		 *
		 * @param lockMode The new lock mode.
		 *
		 * @return {@code this}, for method chaining
		 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FetchReturn setLockMode(LockMode lockMode);

		/**
		 * Add a simple property-to-one-column mapping.
		 *
		 * @param propertyName The name of the property.
		 * @param columnAlias The name of the column
		 *
		 * @return {@code this}, for method chaining
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		FetchReturn addProperty(String propertyName, String columnAlias);

		/**
		 * Add a property, presumably with more than one column.
		 *
		 * @param propertyName The name of the property.
		 *
		 * @return The config object for further control.
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		ReturnProperty addProperty(String propertyName);
	}
}
