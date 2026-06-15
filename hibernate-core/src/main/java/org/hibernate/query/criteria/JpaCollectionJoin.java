/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.BooleanExpression;
import jakarta.persistence.criteria.CollectionJoin;
import jakarta.persistence.criteria.Expression;
import jakarta.annotation.Nullable;
import org.hibernate.metamodel.model.domain.EntityDomainType;

import java.util.Collection;
import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Specialization of {@link JpaJoin} for {@link java.util.Collection} typed attribute joins
 *
 * @author Steve Ebersole
 */
public interface JpaCollectionJoin<O, T> extends JpaPluralJoin<O, Collection<T>, T>, CollectionJoin<O, T> {

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCollectionJoin<O, T> on(@Nullable JpaExpression<Boolean> restriction);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCollectionJoin<O, T> on(@Nonnull Expression<Boolean> restriction);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCollectionJoin<O, T> on(@Nullable JpaPredicate... restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCollectionJoin<O, T> on(@Nonnull BooleanExpression... restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCollectionJoin<O, T> on(@Nonnull List<? extends Expression<Boolean>> restrictions);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends T> JpaTreatedJoin<O,T, S> treatAs(Class<S> treatAsType);

	@Nonnull
	@Override
	@SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <S extends T> JpaCollectionJoin<O, S> treat(@Nonnull Class<S> treatAsType) {
		return (JpaCollectionJoin<O, S>) treatAs( treatAsType );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends T> JpaTreatedJoin<O,T, S> treatAs(EntityDomainType<S> treatAsType);
}
