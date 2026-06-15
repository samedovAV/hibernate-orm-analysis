/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Subquery;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * API extension to the JPA {@link Expression} contract
 *
 * @author Steve Ebersole
 */
public interface JpaExpression<T> extends JpaSelection<T>, Expression<T> {

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Long> asLong();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Integer> asInteger();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Float> asFloat();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> asDouble();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<BigDecimal> asBigDecimal();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<BigInteger> asBigInteger();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> asString();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaExpression<X> as(@Nonnull Class<X> type);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate isNull();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate isNotNull();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate in(@Nonnull Object... values);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate in(@Nonnull Expression<?>... values);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate in(@Nonnull Collection<?> values);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate in(@Nonnull Expression<Collection<?>> values);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate in(@Nonnull Subquery<T> subquery);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<T> coalesce(@Nonnull Expression<? extends T> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<T> coalesce(T y);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<R> JpaSimpleCase<T, R> selectCase();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<R> JpaSimpleCase<T, R> selectCase(@Nonnull Class<R> resultType);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaNumericExpression<Long> count();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaNumericExpression<Long> countDistinct();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<T> nullif(@Nonnull Expression<? extends T> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<T> nullif(T y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate isMember(@Nonnull Expression<? extends Collection<? super T>> collection);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate isNotMember(@Nonnull Expression<? extends Collection<? super T>> collection);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate equalTo(@Nonnull Expression<?> value);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate equalTo(Object value);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaExpression<X> cast(@Nonnull Class<X> type);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate notEqualTo(@Nonnull Expression<?> value);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate notEqualTo(Object value);
}
