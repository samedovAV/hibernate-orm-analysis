/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.BooleanExpression;
import java.util.List;

import jakarta.annotation.Nullable;
import org.hibernate.metamodel.model.domain.EntityDomainType;
import org.hibernate.metamodel.model.domain.PluralPersistentAttribute;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.PluralJoin;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Specialization of {@link JpaJoin} for {@link java.util.Set} typed attribute joins
 *
 * @author Steve Ebersole
 */
public interface JpaPluralJoin<O, C, E> extends JpaJoin<O, E>, PluralJoin<O, C, E> {
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PluralPersistentAttribute<? super O, C, E> getAttribute();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPluralJoin<O, ? extends C, E> on(@Nullable JpaExpression<Boolean> restriction);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPluralJoin<O, ? extends C, E> on(@Nonnull Expression<Boolean> restriction);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPluralJoin<O, ? extends C, E> on(@Nullable JpaPredicate... restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPluralJoin<O, ? extends C, E> on(@Nonnull BooleanExpression... restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPluralJoin<O, ? extends C, E> on(@Nonnull List<? extends Expression<Boolean>> restrictions);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends E> JpaTreatedJoin<O, E, S> treatAs(Class<S> treatAsType);

	@Nonnull
	@Override
	@SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <S extends E> JpaPluralJoin<O, ?, S> treat(@Nonnull Class<S> treatAsType) {
		return (JpaPluralJoin<O, ?, S>) treatAs( treatAsType );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends E> JpaTreatedJoin<O, E, S> treatAs(EntityDomainType<S> treatAsType);
}
