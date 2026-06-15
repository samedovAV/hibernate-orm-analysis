/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import java.util.Set;
import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.metamodel.PluralAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JpaFetch<O,T> extends JpaFetchParent<O,T>, Fetch<O,T> {
	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<Fetch<T, ?>> getFetches();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaFetch<T, Y> fetch(@Nonnull SingularAttribute<? super T, Y> attribute);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaFetch<T, Y> fetch(@Nonnull SingularAttribute<? super T, Y> attribute, @Nonnull JoinType jt);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaFetch<T, Y> fetch(@Nonnull PluralAttribute<? super T, ?, Y> attribute);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaFetch<T, Y> fetch(@Nonnull PluralAttribute<? super T, ?, Y> attribute, @Nonnull JoinType jt);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaFetch<T, Y> fetch(@Nonnull String attributeName);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaFetch<T, Y> fetch(@Nonnull String attributeName, @Nonnull JoinType jt);

	/**
	 * Add a restriction to the fetch.
	 *
	 * @apiNote JPA does not allow restricting a fetch
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJoin<O, T> on(JpaExpression<Boolean> restriction);

	/**
	 * Add a restriction to the fetch.
	 *
	 * @apiNote JPA does not allow restricting a fetch
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJoin<O, T> on(JpaPredicate... restrictions);
}
