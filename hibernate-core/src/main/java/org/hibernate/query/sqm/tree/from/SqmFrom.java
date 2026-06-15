/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.from;

import jakarta.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.metamodel.CollectionAttribute;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.MapAttribute;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;

import org.hibernate.Incubating;
import org.hibernate.metamodel.model.domain.EntityDomainType;
import org.hibernate.query.criteria.JpaFrom;
import org.hibernate.query.sqm.SqmPathSource;
import org.hibernate.query.sqm.tree.SqmCopyContext;
import org.hibernate.query.sqm.tree.SqmRenderContext;
import org.hibernate.query.sqm.tree.SqmVisitableNode;
import org.hibernate.query.sqm.tree.domain.SqmBagJoin;
import org.hibernate.query.sqm.tree.domain.SqmListJoin;
import org.hibernate.query.sqm.tree.domain.SqmMapJoin;
import org.hibernate.query.sqm.tree.domain.SqmPath;
import org.hibernate.query.sqm.tree.domain.SqmSetJoin;
import org.hibernate.query.sqm.tree.domain.SqmSingularJoin;
import org.hibernate.query.sqm.tree.domain.SqmTreatedFrom;

import static org.hibernate.internal.util.collections.CollectionHelper.isEmpty;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models a SqmPathSource's inclusion in the {@code FROM} clause.
 *
 * @param <L> The from-element's "left hand side".  It may be the same as {@code R} for roots.
 * @param <R> The from-element's "right hand side".  For joins, this is the target side.
 *
 * @author Steve Ebersole
 */
public interface SqmFrom<L, R> extends SqmVisitableNode, SqmPath<R>, JpaFrom<L, R> {
	/**
	 * The Navigable for an SqmFrom will always be a NavigableContainer
	 *
	 * {@inheritDoc}
	 * @return
	 */
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPathSource<R> getReferencedPathSource();

	/**
	 * Retrieve the explicit alias, if one, otherwise return a generated one.
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default String resolveAlias(SqmRenderContext context) {
		return context.resolveAlias( this );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasJoins();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getNumberOfJoins();

	/**
	 * The joins associated with this SqmFrom
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<SqmJoin<R,?>> getSqmJoins();

	/**
	 * Add an associated join
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addSqmJoin(SqmJoin<R, ?> join);

	/**
	 * Visit all associated joins
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitSqmJoins(Consumer<SqmJoin<R, ?>> consumer);

	/**
	 * The treats associated with this SqmFrom
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<SqmTreatedFrom<L,R,?>> getSqmTreats();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean hasTreats() {
		return !isEmpty( getSqmTreats() );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends R> SqmTreatedFrom<L,R,S> treatAs(@Nonnull Class<S> treatJavaType);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends R> SqmTreatedFrom<L,R,S> treatAs(@Nonnull EntityDomainType<S> treatTarget);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends R> SqmTreatedFrom<L,R,S> treatAs(@Nonnull Class<S> treatJavaType, @Nullable String alias);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends R> SqmTreatedFrom<L,R,S> treatAs(@Nonnull EntityDomainType<S> treatTarget, @Nullable String alias);

	// Since equals only does "syntactic" equality to understand if a node in an expression and predicate is equal,
	// also define a method to do deep equality checking for the SqmFromClause
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean deepEquals(SqmFrom<?, ?> object) {
		return equals( object );
	}

	// Since isCompatible only does "syntactic" equality to understand if a node in an expression and predicate is compatible,
	// also define a method to do deep compatibility checking for the SqmFromClause
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isDeepCompatible(SqmFrom<?, ?> object) {
		return isCompatible( object );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	static boolean areDeepEqual(List<? extends SqmFrom<?, ?>> theseFroms, List<? extends SqmFrom<?, ?>> thoseFroms) {
		if ( theseFroms.size() != thoseFroms.size() ) {
			return false;
		}
		for ( int i = 0; i < theseFroms.size(); i++ ) {
			if ( !theseFroms.get( i ).deepEquals( thoseFroms.get( i ) ) ) {
				return false;
			}
		}
		return true;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	static boolean areDeepCompatible(List<? extends SqmFrom<?, ?>> theseFroms, List<? extends SqmFrom<?, ?>> thoseFroms) {
		if ( theseFroms.size() != thoseFroms.size() ) {
			return false;
		}
		for ( int i = 0; i < theseFroms.size(); i++ ) {
			if ( !theseFroms.get( i ).isDeepCompatible( thoseFroms.get( i ) ) ) {
				return false;
			}
		}
		return true;
	}


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// JPA

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFrom<L, R> getCorrelationParent();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> SqmEntityJoin<R, Y> join(@Nonnull Class<Y> entityClass);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> SqmEntityJoin<R, Y> join(@Nonnull Class<Y> entityClass, @Nonnull JoinType joinType);

	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasImplicitlySelectableJoin();

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
	<Y> SqmAttributeJoin<R, Y> join(@Nonnull String attributeName);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> SqmAttributeJoin<R, Y> join(@Nonnull String attributeName, @Nonnull JoinType jt);

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

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFrom<L, R> copy(SqmCopyContext context);
}
