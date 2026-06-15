/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.from;

import jakarta.annotation.Nullable;
import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.Expression;
import org.hibernate.Internal;
import org.hibernate.metamodel.model.domain.EntityDomainType;
import org.hibernate.metamodel.model.domain.PersistentAttribute;
import org.hibernate.query.criteria.JpaExpression;
import org.hibernate.query.criteria.JpaFetch;
import org.hibernate.query.criteria.JpaJoin;
import org.hibernate.query.criteria.JpaPredicate;
import org.hibernate.query.sqm.SqmPathSource;
import org.hibernate.query.sqm.tree.SqmCopyContext;
import org.hibernate.query.sqm.tree.predicate.SqmPredicate;
import org.hibernate.type.descriptor.java.JavaType;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models a join based on a mapped attribute reference.
 *
 * @author Steve Ebersole
 */
public interface SqmAttributeJoin<O,T> extends SqmJoin<O,T>, JpaFetch<O,T>, JpaJoin<O,T> {
	@Override
	@Nonnull@Prove(complexity = Complexity.O_1, n = "", count = {})
 PersistentAttribute<? super O, ?> getAttribute();

	@Override
	@Nonnull@Prove(complexity = Complexity.O_1, n = "", count = {})
 SqmFrom<?,O> getLhs();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isImplicitlySelectable() {
		return !isFetched() && !isImplicitJoin();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPathSource<T> getReferencedPathSource();

	@Nullable
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JavaType<T> getJavaTypeDescriptor();

	/**
	 * Is this a fetch join?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isFetched();

	/**
	 * Is this an implicit join inferred from a path expression?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isImplicitJoin();

	@Internal
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void clearFetched();

	@Override
	@Nullable@Prove(complexity = Complexity.O_1, n = "", count = {})
 SqmPredicate getJoinPredicate();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setJoinPredicate(@Nullable SqmPredicate predicate);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default SqmJoin<O, T> on(@Nullable JpaExpression<Boolean> restriction) {
		return SqmJoin.super.on( restriction );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default SqmJoin<O, T> on(@Nonnull Expression<Boolean> restriction) {
		return SqmJoin.super.on( restriction );
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default SqmJoin<O, T> on(@Nullable JpaPredicate... restrictions) {
		return SqmJoin.super.on( restrictions );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default SqmJoin<O, T> on(@Nonnull List<? extends Expression<Boolean>> restrictions) {
		return SqmJoin.super.on( restrictions );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends T> SqmTreatedAttributeJoin<O,T,S> treatAs(@Nonnull Class<S> treatJavaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends T> SqmTreatedAttributeJoin<O,T,S> treatAs(@Nonnull Class<S> treatJavaType, @Nullable String alias);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends T> SqmTreatedAttributeJoin<O,T,S> treatAs(@Nonnull EntityDomainType<S> treatTarget);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends T> SqmTreatedAttributeJoin<O,T,S> treatAs(@Nonnull EntityDomainType<S> treatTarget, @Nullable String alias);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends T> SqmTreatedAttributeJoin<O,T,S> treatAs(@Nonnull EntityDomainType<S> treatTarget, @Nullable String alias, boolean fetch);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends T> SqmTreatedAttributeJoin<O,T,S> treatAs(@Nonnull Class<S> treatTarget, @Nullable String alias, boolean fetch);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmAttributeJoin<O, T> copy(SqmCopyContext context);

}
