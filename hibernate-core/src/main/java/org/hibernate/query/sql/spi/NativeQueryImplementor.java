/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sql.spi;

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
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.Type;
import org.hibernate.CacheMode;
import org.hibernate.Incubating;
import org.hibernate.LockMode;
import org.hibernate.Locking;
import org.hibernate.MappingException;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.metamodel.model.domain.BasicDomainType;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Page;
import jakarta.persistence.QueryFlushMode;
import org.hibernate.query.QueryParameter;
import org.hibernate.query.ResultListTransformer;
import org.hibernate.query.TupleTransformer;
import org.hibernate.query.named.NameableQuery;
import org.hibernate.query.named.NamedMutationMemento;
import org.hibernate.query.named.NamedNativeQueryMemento;
import org.hibernate.query.named.internal.NativeSelectionMementoImpl;
import org.hibernate.query.results.internal.dynamic.DynamicResultBuilderEntityStandard;
import org.hibernate.query.spi.MutationQueryImplementor;
import org.hibernate.query.spi.SelectionQueryImplementor;

import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
@Incubating
public interface NativeQueryImplementor<R>
		extends SelectionQueryImplementor<R>, MutationQueryImplementor<R>, NativeQuery<R>, NameableQuery {

	/**
	 * Best guess whether this is a select query.  {@code null}
	 * indicates unknown
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isSelectQuery();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NamedNativeQueryMemento<?> toMemento(String name);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NamedMutationMemento<?> toMutationMemento(String name);

	@Override @SuppressWarnings({"unchecked", "rawtypes"})
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set getOptions();

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Casts

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default NativeQueryImplementor<R> asMutationQuery() {
		return (NativeQueryImplementor<R>) MutationQueryImplementor.super.asMutationQuery();
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> asSelectionQuery();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> NativeQueryImplementor<X> asSelectionQuery(Class<X> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> NativeQueryImplementor<X> asSelectionQuery(EntityGraph<X> entityGraph);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Executions

	@Override
	@SuppressWarnings("deprecation")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<R> list();

	@Override
	@SuppressWarnings("deprecation")
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default List<R> getResultList() {
		return SelectionQueryImplementor.super.getResultList();
	}

	@Override
	@SuppressWarnings("deprecation")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ScrollableResults<R> scroll();

	@Override
	@SuppressWarnings("deprecation")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ScrollableResults<R> scroll(@Nonnull ScrollMode scrollMode);

	@Override
	@SuppressWarnings("deprecation")
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default Stream<R> getResultStream() {
		return SelectionQueryImplementor.super.getResultStream();
	}

	@Override
	@SuppressWarnings("deprecation")
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default Stream<R> stream() {
		return SelectionQueryImplementor.super.stream();
	}

	@Override
	@SuppressWarnings("deprecation")
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	R uniqueResult();

	@Override
	@SuppressWarnings("deprecation")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Optional<R> uniqueResultOptional();

	@Override
	@SuppressWarnings("deprecation")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	R getSingleResult();

	@Override
	@SuppressWarnings("deprecation")
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	R getSingleResultOrNull();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int execute();

	@Override
	@SuppressWarnings({"deprecation", "removal"})
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int executeUpdate();

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// covariant overrides - NativeQuery

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeSelectionMementoImpl<R> toSelectionMemento(String name);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> addSynchronizedQuerySpace(String querySpace);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> addSynchronizedEntityName(String entityName) throws MappingException;

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> addSynchronizedEntityClass(@SuppressWarnings("rawtypes") Class entityClass) throws MappingException;


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// covariant overrides - Query / QueryImplementor

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setHint(@Nonnull String hintName, @Nullable Object value);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setTimeout(int timeout);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setTimeout(@Nullable Integer timeout);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setTimeout(@Nullable Timeout timeout);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setQueryFlushMode(@Nonnull QueryFlushMode queryFlushMode);

	@Override @Deprecated(since = "7")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setFlushMode(@Nonnull FlushModeType flushMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setQueryPlanCacheable(boolean queryPlanCacheable);

	@Override @SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setCacheable(boolean cacheable);

	@Override @SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setCacheMode(@Nonnull CacheMode cacheMode);

	@Override @SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CacheMode getCacheMode();

	@Override @SuppressWarnings("deprecation")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CacheStoreMode getCacheStoreMode();

	@Override @SuppressWarnings("deprecation")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CacheRetrieveMode getCacheRetrieveMode();

	@Override @SuppressWarnings("removal")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setCacheRegion(@Nullable String cacheRegion);

	@Override @SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setFetchSize(int fetchSize);

	@Override
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setReadOnly(boolean readOnly);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LockMode getHibernateLockMode();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setHibernateLockMode(@Nonnull LockMode lockMode);

	@Override @SuppressWarnings("deprecation")
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LockModeType getLockMode();

	@Override @SuppressWarnings("deprecation")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setLockMode(@Nonnull LockModeType lockMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setComment(@Nullable String comment);

	@Override @SuppressWarnings("deprecation")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getMaxResults();

	@Override @SuppressWarnings("deprecation")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setMaxResults(int maxResults);

	@Override @SuppressWarnings("deprecation")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getFirstResult();

	@Override @SuppressWarnings("deprecation")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setFirstResult(int startPosition);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> addQueryHint(@Nonnull String hint);

	@Override
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> NativeQueryImplementor<X> setTupleTransformer(@Nonnull TupleTransformer<X> transformer);

	@Override
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setResultListTransformer(@Nonnull ResultListTransformer<R> transformer);

	@Override @Deprecated @SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setEntityGraph(@Nonnull EntityGraph<? super R> entityGraph);

	@Override @Deprecated
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setEntityGraph(@Nonnull EntityGraph<? super R> graph, @Nonnull GraphSemantic semantic);

	@Override @Deprecated
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> disableFetchProfile(@Nonnull String profileName);

	@Override @Deprecated
	@SuppressWarnings("removal")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> enableFetchProfile(@Nonnull String profileName);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setPage(Page page);

	@Override @SuppressWarnings("deprecation")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setCacheStoreMode(@Nonnull CacheStoreMode cacheStoreMode);

	@Override @SuppressWarnings("deprecation")
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setCacheRetrieveMode(@Nonnull CacheRetrieveMode cacheRetrieveMode);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setFollowOnStrategy(@Nonnull Locking.FollowOn followOnStrategy);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setLockScope(@Nonnull PessimisticLockScope lockScope);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setParameter(@Nonnull String name, @Nullable Object val);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setParameter(@Nonnull String name, @Nullable P val, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setParameter(@Nonnull String name, @Nullable P val, @Nonnull Class<P> type);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setParameter(@Nonnull String name, @Nullable Instant value, @Nonnull TemporalType temporalType);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setParameter(@Nonnull String name, @Nullable Date value, @Nonnull TemporalType temporalType);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setParameter(@Nonnull String name, @Nullable Calendar value, @Nonnull TemporalType temporalType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setParameter(int position, @Nullable Object val);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setParameters(@Nonnull Object... arguments);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setParameter(int position, @Nullable P val, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setParameter(int position, @Nullable P val, @Nonnull Type<P> type);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setParameter(int position, @Nullable Instant value, @Nonnull TemporalType temporalType);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setParameter(int position, @Nullable Date value, @Nonnull TemporalType temporalType);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setParameter(int position, @Nullable Calendar value, @Nonnull TemporalType temporalType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setParameter(@Nonnull QueryParameter<P> parameter, @Nullable P val);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setParameter(@Nonnull QueryParameter<P> parameter, @Nullable P val, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setParameter(@Nonnull QueryParameter<P> parameter, @Nullable P val, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setParameter(@Nonnull Parameter<P> param, @Nullable P value);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setParameter(@Nonnull Parameter<Date> param, @Nullable Date value, @Nonnull TemporalType temporalType);

	@Override @Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setParameter(@Nonnull Parameter<Calendar> param, @Nullable Calendar value, @Nonnull TemporalType temporalType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setParameterList(@Nonnull String name, @SuppressWarnings("rawtypes") @Nonnull Collection values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setParameterList(@Nonnull String name, @Nonnull Collection<? extends P> values, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setParameterList(@Nonnull String name, @Nonnull Collection<? extends P> values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setParameterList(@Nonnull String name, @Nonnull Object[] values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setParameterList(@Nonnull String name, @Nonnull P[] values, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setParameterList(@Nonnull String name, @Nonnull P[] values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setParameterList(int position, @SuppressWarnings("rawtypes") @Nonnull Collection values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setParameterList(int position, @Nonnull Collection<? extends P> values, @Nonnull Class<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setParameterList(int position, @Nonnull Collection<? extends P> values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setParameterList(int position, @Nonnull Object[] values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setParameterList(int position, @Nonnull P[] values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setParameterList(int position, @Nonnull P[] values, @Nonnull Type<P> type);


	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull Collection<? extends P> values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull Collection<? extends P> values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull Collection<? extends P> values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull P[] values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull P[] values, @Nonnull Class<P> javaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setParameterList(@Nonnull QueryParameter<P> parameter, @Nonnull P[] values, @Nonnull Type<P> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setProperties(@Nonnull Object bean);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> setProperties(@SuppressWarnings("rawtypes") @Nonnull Map bean);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setConvertedParameter(@Nonnull String name, @Nullable P value, @Nonnull Class<? extends AttributeConverter<P, ?>> converter);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> NativeQueryImplementor<R> setConvertedParameter(int position, @Nullable P value, @Nonnull Class<? extends AttributeConverter<P, ?>> converter);

	@SuppressWarnings("unused") // Used by Hibernate Reactive
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addResultTypeClass(Class<?> resultClass);



	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> addScalar(String columnAlias);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> addScalar(String columnAlias, @SuppressWarnings("rawtypes") BasicDomainType type);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> addScalar(String columnAlias, @SuppressWarnings("rawtypes") Class javaType);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C> NativeQueryImplementor<R> addScalar(String columnAlias, Class<C> relationalJavaType, AttributeConverter<?,C> converter);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<O, J> NativeQueryImplementor<R> addScalar(String columnAlias, Class<O> domainJavaType, Class<J> jdbcJavaType, AttributeConverter<O, J> converter);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C> NativeQueryImplementor<R> addScalar(String columnAlias, Class<C> relationalJavaType, Class<? extends AttributeConverter<?,C>> converter);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<O, J> NativeQueryImplementor<R> addScalar(
			String columnAlias,
			Class<O> domainJavaType,
			Class<J> jdbcJavaType,
			Class<? extends AttributeConverter<O, J>> converter);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> addAttributeResult(String columnAlias, @SuppressWarnings("rawtypes") Class entityJavaType, String attributePath);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> addAttributeResult(String columnAlias, String entityName, String attributePath);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> addAttributeResult(String columnAlias, @SuppressWarnings("rawtypes") SingularAttribute attribute);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DynamicResultBuilderEntityStandard addRoot(String tableAlias, String entityName);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> addEntity(String entityName);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> addEntity(String tableAlias, String entityName);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> addEntity(String tableAlias, String entityName, LockMode lockMode);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> addEntity(@SuppressWarnings("rawtypes") Class entityType);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> addEntity(Class<R> entityType, LockMode lockMode);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> addEntity(String tableAlias, @SuppressWarnings("rawtypes") Class entityType);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> addEntity(String tableAlias, @SuppressWarnings("rawtypes") Class entityClass, LockMode lockMode);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> addJoin(String tableAlias, String path);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> addJoin(
			String tableAlias,
			String ownerTableAlias,
			String joinPropertyName);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<R> addJoin(String tableAlias, String path, LockMode lockMode);
}
