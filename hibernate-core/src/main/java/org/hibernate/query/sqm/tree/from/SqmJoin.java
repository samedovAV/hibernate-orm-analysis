/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.from;

import jakarta.annotation.Nullable;
import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.BooleanExpression;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Subquery;
import jakarta.persistence.metamodel.CollectionAttribute;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.MapAttribute;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.List;

import org.hibernate.metamodel.model.domain.EntityDomainType;
import org.hibernate.query.criteria.JpaCrossJoin;
import org.hibernate.query.criteria.JpaCteCriteria;
import org.hibernate.query.criteria.JpaDerivedJoin;
import org.hibernate.query.criteria.JpaEntityJoin;
import org.hibernate.query.criteria.JpaExpression;
import org.hibernate.query.criteria.JpaJoin;
import org.hibernate.query.criteria.JpaPredicate;
import org.hibernate.query.sqm.tree.SqmCopyContext;
import org.hibernate.query.sqm.tree.SqmJoinType;
import org.hibernate.query.sqm.tree.domain.SqmBagJoin;
import org.hibernate.query.sqm.tree.domain.SqmListJoin;
import org.hibernate.query.sqm.tree.domain.SqmMapJoin;
import org.hibernate.query.sqm.tree.domain.SqmSetJoin;
import org.hibernate.query.sqm.tree.domain.SqmSingularJoin;
import org.hibernate.query.sqm.tree.domain.SqmTreatedJoin;
import org.hibernate.query.sqm.tree.predicate.SqmPredicate;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 */
public interface SqmJoin<L, R> extends SqmFrom<L, R>, JpaJoin<L,R> {
	/**
	 * The type of join - inner, cross, etc
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmJoinType getSqmJoinType();

	/**
	 * When applicable, whether this join should be included in an implicit select clause
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isImplicitlySelectable();

	/**
	 * Obtain the join predicate
	 *
	 * @return The join predicate
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate getJoinPredicate();

	/**
	 * Inject the join predicate
	 *
	 * @param predicate The join predicate
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setJoinPredicate(@Nullable SqmPredicate predicate);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> SqmAttributeJoin<R, Y> join(@Nonnull String attributeName);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> SqmAttributeJoin<R, Y> join(@Nonnull String attributeName, @Nonnull JoinType jt);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmJoin<L, R> copy(SqmCopyContext context);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends R> SqmTreatedJoin<L,R,S> treatAs(@Nonnull Class<S> treatAsType);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends R> SqmTreatedJoin<L,R,S> treatAs(@Nonnull EntityDomainType<S> treatAsType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends R> SqmTreatedJoin<L,R,S> treatAs(@Nonnull Class<S> treatJavaType, @Nullable String alias);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends R> SqmTreatedJoin<L,R,S> treatAs(@Nonnull EntityDomainType<S> treatTarget, @Nullable String alias);

	@Nullable
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqmPredicate getOn() {
		return getJoinPredicate();
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqmJoin<L, R> on(@Nullable JpaExpression<Boolean> restriction) {
		setJoinPredicate( restriction == null ? null : nodeBuilder().wrap( restriction ) );
		return this;
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqmJoin<L, R> on(@Nonnull Expression<Boolean> restriction) {
		setJoinPredicate( restriction == null ? null : nodeBuilder().wrap( restriction ) );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqmJoin<L, R> on(@Nullable JpaPredicate... restrictions) {
		setJoinPredicate( restrictions == null ? null : nodeBuilder().wrap( restrictions ) );
		return this;
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmJoin<L, R> on(@Nonnull BooleanExpression... restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqmJoin<L, R> on(@Nonnull List<? extends Expression<Boolean>> restrictions) {
		setJoinPredicate( nodeBuilder().wrap( restrictions ) );
		return this;
	}

	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default <X> JpaEntityJoin<R, X> join(@Nonnull Class<X> entityJavaType, @Nonnull SqmJoinType joinType) {
		return join( entityJavaType, joinType.getCorrespondingJpaJoinType() );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaJoin<R, Y> join(@Nonnull EntityType<Y> entity);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaJoin<R, Y> join(@Nonnull EntityType<Y> entity, @Nonnull JoinType joinType);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaEntityJoin<R, X> join(@Nonnull EntityDomainType<X> entity);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaDerivedJoin<X> join(@Nonnull Subquery<X> subquery);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaDerivedJoin<X> joinLateral(@Nonnull Subquery<X> subquery);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaJoin<?, X> join(@Nonnull JpaCteCriteria<X> cte);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaCrossJoin<R, X> crossJoin(@Nonnull Class<X> entityJavaType);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaCrossJoin<R, X> crossJoin(@Nonnull EntityDomainType<X> entity);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<A> SqmSingularJoin<R, A> join(@Nonnull SingularAttribute<? super R, A> attribute);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<A> SqmSingularJoin<R, A> join(@Nonnull SingularAttribute<? super R, A> attribute, @Nonnull JoinType jt);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmBagJoin<R, E> join(@Nonnull CollectionAttribute<? super R, E> attribute);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmBagJoin<R, E> join(@Nonnull CollectionAttribute<? super R, E> attribute, @Nonnull JoinType jt);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmSetJoin<R, E> join(@Nonnull SetAttribute<? super R, E> set);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmSetJoin<R, E> join(@Nonnull SetAttribute<? super R, E> set, @Nonnull JoinType jt);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmListJoin<R, E> join(@Nonnull ListAttribute<? super R, E> list);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmListJoin<R, E> join(@Nonnull ListAttribute<? super R, E> list, @Nonnull JoinType jt);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<K, V> SqmMapJoin<R, K, V> join(@Nonnull MapAttribute<? super R, K, V> map);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<K, V> SqmMapJoin<R, K, V> join(@Nonnull MapAttribute<? super R, K, V> map, @Nonnull JoinType jt);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> SqmBagJoin<R, Y> joinCollection(@Nonnull String attributeName);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> SqmBagJoin<R, Y> joinCollection(@Nonnull String attributeName, @Nonnull JoinType jt);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> SqmSetJoin<R, Y> joinSet(@Nonnull String attributeName);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> SqmSetJoin<R, Y> joinSet(@Nonnull String attributeName, @Nonnull JoinType jt);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> SqmListJoin<R, Y> joinList(@Nonnull String attributeName);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> SqmListJoin<R, Y> joinList(@Nonnull String attributeName, @Nonnull JoinType jt);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<K, V> SqmMapJoin<R, K, V> joinMap(@Nonnull String attributeName);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<K, V> SqmMapJoin<R, K, V> joinMap(@Nonnull String attributeName, @Nonnull JoinType jt);
}
