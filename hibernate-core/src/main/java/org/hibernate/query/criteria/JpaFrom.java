/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import java.util.Collection;

import org.hibernate.Incubating;
import org.hibernate.metamodel.model.domain.EntityDomainType;
import org.hibernate.query.sqm.tree.SqmJoinType;

import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Subquery;
import jakarta.persistence.metamodel.CollectionAttribute;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.MapAttribute;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * API extension to the JPA {@link From} contract
 *
 * @author Steve Ebersole
 */
public interface JpaFrom<O,T> extends JpaPath<T>, JpaFetchParent<O,T>, From<O,T> {
	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFrom<O,T> getCorrelationParent();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaEntityJoin<T, Y> join(@Nonnull Class<Y> entityClass);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaEntityJoin<T, Y> join(@Nonnull Class<Y> entityClass, @Nonnull JoinType joinType);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaEntityJoin<T, X> join(@Nonnull Class<X> entityJavaType, @Nonnull org.hibernate.query.common.JoinType joinType);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaJoin<T, Y> join(@Nonnull EntityType<Y> entity);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaJoin<T, Y> join(@Nonnull EntityType<Y> entity, @Nonnull JoinType joinType);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaEntityJoin<T,X> join(@Nonnull EntityDomainType<X> entity);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaEntityJoin<T,X> join(@Nonnull EntityDomainType<X> entity, @Nonnull org.hibernate.query.common.JoinType joinType);

	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaDerivedJoin<X> join(@Nonnull Subquery<X> subquery);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaDerivedJoin<X> join(@Nonnull Subquery<X> subquery, @Nonnull org.hibernate.query.common.JoinType joinType);

	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaDerivedJoin<X> joinLateral(@Nonnull Subquery<X> subquery);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaDerivedJoin<X> joinLateral(@Nonnull Subquery<X> subquery, @Nonnull org.hibernate.query.common.JoinType joinType);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaDerivedJoin<X> join(@Nonnull Subquery<X> subquery, @Nonnull org.hibernate.query.common.JoinType joinType, boolean lateral);

	/**
	 * Like calling the overload {@link #join(JpaSetReturningFunction, SqmJoinType)} with {@link SqmJoinType#INNER}.
	 *
	 * @see #join(JpaSetReturningFunction, SqmJoinType)
	 * @since 7.0
	 */
	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaFunctionJoin<X> join(@Nonnull JpaSetReturningFunction<X> function);

	/**
	 * Like calling the overload {@link #join(JpaSetReturningFunction, SqmJoinType, boolean)} passing {@code false}
	 * for the {@code lateral} parameter.
	 *
	 * @see #join(JpaSetReturningFunction, SqmJoinType, boolean)
	 * @since 7.0
	 */
	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaFunctionJoin<X> join(@Nonnull JpaSetReturningFunction<X> function, @Nonnull SqmJoinType joinType);

	/**
	 * Like calling the overload {@link #joinLateral(JpaSetReturningFunction, SqmJoinType)} with {@link SqmJoinType#INNER}.
	 *
	 * @see #joinLateral(JpaSetReturningFunction, SqmJoinType)
	 * @since 7.0
	 */
	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaFunctionJoin<X> joinLateral(@Nonnull JpaSetReturningFunction<X> function);

	/**
	 * Like calling the overload {@link #join(JpaSetReturningFunction, SqmJoinType, boolean)} passing {@code true}
	 * for the {@code lateral} parameter.
	 *
	 * @see #join(JpaSetReturningFunction, SqmJoinType, boolean)
	 * @since 7.0
	 */
	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaFunctionJoin<X> joinLateral(@Nonnull JpaSetReturningFunction<X> function, @Nonnull SqmJoinType joinType);

	/**
	 * Creates and returns a join node for the given set returning function.
	 * If function arguments refer to correlated paths, the {@code lateral} argument must be set to {@code true}.
	 * Failing to do so when necessary may lead to an error during query compilation or execution.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaFunctionJoin<X> join(@Nonnull JpaSetReturningFunction<X> function, @Nonnull SqmJoinType joinType, boolean lateral);

	/**
	 * Like calling the overload {@link #joinArray(String, SqmJoinType)} with {@link SqmJoinType#INNER}.
	 *
	 * @see #joinArray(String, SqmJoinType)
	 * @since 7.0
	 */
	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaFunctionJoin<X> joinArray(@Nonnull String arrayAttributeName);

	/**
	 * Like calling the overload {@link #join(JpaSetReturningFunction, SqmJoinType)} with {@link HibernateCriteriaBuilder#unnestArray(Expression)}
	 * with the result of {@link #get(String)} passing the given attribute name.
	 *
	 * @see #joinLateral(JpaSetReturningFunction, SqmJoinType)
	 * @since 7.0
	 */
	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaFunctionJoin<X> joinArray(@Nonnull String arrayAttributeName, @Nonnull SqmJoinType joinType);

	/**
	 * Like calling the overload {@link #joinArray(SingularAttribute, SqmJoinType)} with {@link SqmJoinType#INNER}.
	 *
	 * @see #joinArray(SingularAttribute, SqmJoinType)
	 * @since 7.0
	 */
	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaFunctionJoin<X> joinArray(@Nonnull SingularAttribute<? super T, X[]> arrayAttribute);

	/**
	 * Like calling the overload {@link #join(JpaSetReturningFunction, SqmJoinType)} with {@link HibernateCriteriaBuilder#unnestArray(Expression)}
	 * with the given attribute.
	 *
	 * @see #joinLateral(JpaSetReturningFunction, SqmJoinType)
	 * @since 7.0
	 */
	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaFunctionJoin<X> joinArray(@Nonnull SingularAttribute<? super T, X[]> arrayAttribute, @Nonnull SqmJoinType joinType);

	/**
	 * Like calling the overload {@link #joinArrayCollection(String, SqmJoinType)} with {@link SqmJoinType#INNER}.
	 *
	 * @see #joinArrayCollection(String, SqmJoinType)
	 * @since 7.0
	 */
	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaFunctionJoin<X> joinArrayCollection(@Nonnull String collectionAttributeName);

	/**
	 * Like calling the overload {@link #join(JpaSetReturningFunction, SqmJoinType)} with {@link HibernateCriteriaBuilder#unnestCollection(Expression)}
	 * with the result of {@link #get(String)} passing the given attribute name.
	 *
	 * @see #joinLateral(JpaSetReturningFunction, SqmJoinType)
	 * @since 7.0
	 */
	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaFunctionJoin<X> joinArrayCollection(@Nonnull String collectionAttributeName, @Nonnull SqmJoinType joinType);

	/**
	 * Like calling the overload {@link #joinArrayCollection(SingularAttribute, SqmJoinType)} with {@link SqmJoinType#INNER}.
	 *
	 * @see #joinArrayCollection(SingularAttribute, SqmJoinType)
	 * @since 7.0
	 */
	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaFunctionJoin<X> joinArrayCollection(@Nonnull SingularAttribute<? super T, ? extends Collection<X>> collectionAttribute);

	/**
	 * Like calling the overload {@link #join(JpaSetReturningFunction, SqmJoinType)} with {@link HibernateCriteriaBuilder#unnestCollection(Expression)}
	 * with the given attribute.
	 *
	 * @see #joinLateral(JpaSetReturningFunction, SqmJoinType)
	 * @since 7.0
	 */
	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaFunctionJoin<X> joinArrayCollection(@Nonnull SingularAttribute<? super T, ? extends Collection<X>> collectionAttribute, @Nonnull SqmJoinType joinType);

	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaJoin<?, X> join(@Nonnull JpaCteCriteria<X> cte);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaJoin<?, X> join(@Nonnull JpaCteCriteria<X> cte, @Nonnull org.hibernate.query.common.JoinType joinType);

	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaCrossJoin<T, X> crossJoin(@Nonnull Class<X> entityJavaType);

	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaCrossJoin<T, X> crossJoin(@Nonnull EntityDomainType<X> entity);

	// Covariant overrides

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaJoin<T, Y> join(@Nonnull SingularAttribute<? super T, Y> attribute);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaJoin<T, Y> join(@Nonnull SingularAttribute<? super T, Y> attribute, @Nonnull JoinType jt);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaCollectionJoin<T, Y> join(@Nonnull CollectionAttribute<? super T, Y> collection);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaSetJoin<T, Y> join(@Nonnull SetAttribute<? super T, Y> set);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaListJoin<T, Y> join(@Nonnull ListAttribute<? super T, Y> list);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<K, V> JpaMapJoin<T, K, V> join(@Nonnull MapAttribute<? super T, K, V> map);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaCollectionJoin<T, Y> join(@Nonnull CollectionAttribute<? super T, Y> collection, @Nonnull JoinType jt);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaSetJoin<T, Y> join(@Nonnull SetAttribute<? super T, Y> set, @Nonnull JoinType jt);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaListJoin<T, Y> join(@Nonnull ListAttribute<? super T, Y> list, @Nonnull JoinType jt);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<K, V> JpaMapJoin<T, K, V> join(@Nonnull MapAttribute<? super T, K, V> map, @Nonnull JoinType jt);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaJoin<T, Y> join(@Nonnull String attributeName);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaCollectionJoin<T, Y> joinCollection(@Nonnull String attributeName);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaSetJoin<T, Y> joinSet(@Nonnull String attributeName);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaListJoin<T, Y> joinList(@Nonnull String attributeName);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<K, V> JpaMapJoin<T, K, V> joinMap(@Nonnull String attributeName);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaJoin<T, Y> join(@Nonnull String attributeName, @Nonnull JoinType jt);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaCollectionJoin<T, Y> joinCollection(@Nonnull String attributeName, @Nonnull JoinType jt);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaSetJoin<T, Y> joinSet(@Nonnull String attributeName, @Nonnull JoinType jt);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaListJoin<T, Y> joinList(@Nonnull String attributeName, @Nonnull JoinType jt);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<K, V> JpaMapJoin<T, K, V> joinMap(@Nonnull String attributeName, @Nonnull JoinType jt);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends T> JpaTreatedFrom<O,T,S> treatAs(@Nonnull Class<S> treatJavaType);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <S extends T> JpaFrom<?, S> treat(@Nonnull Class<S> treatJavaType) {
		return treatAs( treatJavaType );
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends T> JpaTreatedFrom<O,T,S> treatAs(@Nonnull EntityDomainType<S> treatJavaType);

	@Incubating
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<?> id();
}
