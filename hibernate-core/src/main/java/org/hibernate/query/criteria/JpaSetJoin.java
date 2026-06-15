/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import java.util.List;
import java.util.Set;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.BooleanExpression;
import org.hibernate.metamodel.model.domain.EntityDomainType;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.SetJoin;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Specialization of {@link JpaJoin} for {@link java.util.Set} typed attribute joins
 *
 * @author Steve Ebersole
 */
public interface JpaSetJoin<O, T> extends JpaPluralJoin<O, Set<T>, T>, SetJoin<O, T> {

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSetJoin<O, T> on(@Nullable JpaExpression<Boolean> restriction);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSetJoin<O, T> on(@Nonnull Expression<Boolean> restriction);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSetJoin<O, T> on(@Nullable JpaPredicate... restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSetJoin<O, T> on(@Nonnull BooleanExpression... restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSetJoin<O, T> on(@Nonnull List<? extends Expression<Boolean>> restrictions);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends T> JpaTreatedJoin<O,T,S> treatAs(Class<S> treatAsType);

	@Nonnull
	@Override
	@SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <S extends T> JpaSetJoin<O, S> treat(@Nonnull Class<S> treatAsType) {
		return (JpaSetJoin<O, S>) treatAs( treatAsType );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends T> JpaTreatedJoin<O,T,S> treatAs(EntityDomainType<S> treatAsType);
}
