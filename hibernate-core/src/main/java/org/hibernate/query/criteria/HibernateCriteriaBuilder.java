/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.BooleanExpression;
import jakarta.annotation.Nullable;
import org.hibernate.Incubating;
import org.hibernate.query.SortDirection;
import org.hibernate.query.common.FrameKind;
import org.hibernate.query.common.TemporalUnit;

import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.AbstractQuery;
import jakarta.persistence.criteria.CollectionJoin;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaSelect;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.ListJoin;
import jakarta.persistence.criteria.MapJoin;
import jakarta.persistence.criteria.Nulls;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import jakarta.persistence.criteria.SetJoin;
import jakarta.persistence.criteria.Subquery;
import jakarta.persistence.criteria.TemporalField;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A JPA {@link CriteriaBuilder} is a source of objects which may be composed
 * to express a criteria query. The JPA-standard API defines all the operations
 * needed to express any query written in standard JPQL. This interface extends
 * {@code CriteriaBuilder}, adding operations needed to express features of HQL
 * which are not available in standard JPQL. For example:
 * <ul>
 * <li>JPQL does not have a {@code format()} function, so
 *     {@link #format(Expression, String)} is declared here, and
 * <li>since JPQL does not have {@code insert} statements, this interface
 *     defines the operations {@link #createCriteriaInsertSelect(Class)} and
 *     {@link #createCriteriaInsertValues(Class)}.
 * </ul>
 * <p>
 * Furthermore, the operations of this interface return types defined in the
 * package {@link org.hibernate.query.criteria}, which extend the equivalent
 * types in {@link jakarta.persistence.criteria} with additional operations.
 * For example {@link JpaCriteriaQuery} adds the methods:
 * <ul>
 * <li>{@link JpaCriteriaQuery#from(Subquery)}, which allows the use of a
 *     subquery in the {@code from} clause of the query, and
 * <li>{@link JpaCriteriaQuery#with(AbstractQuery)}, which allows the creation
 *     of {@link JpaCteCriteria common table expressions}.
 * </ul>
 * <p>
 * Finally, the method {@link #createQuery(String, Class)} allows a query
 * written in HQL to be translated to a tree of criteria objects for further
 * manipulation and execution.
 * <p>
 * An instance of this interface may be obtained by calling
 * {@link org.hibernate.SessionFactory#getCriteriaBuilder()}.
 *
 * @see org.hibernate.SessionFactory#getCriteriaBuilder()
 * @see JpaCriteriaQuery
 * @see JpaCriteriaUpdate
 * @see JpaCriteriaDelete
 * @see JpaCriteriaInsertValues
 * @see JpaCriteriaInsertSelect
 * @see JpaCteCriteria
 * @see JpaSubQuery
 * @see JpaExpression
 *
 * @since 6.0
 *
 * @author Steve Ebersole
 * @author Yoobin Yoon
 */
@Incubating
public interface HibernateCriteriaBuilder extends CriteriaBuilder {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, T> JpaExpression<X> cast(JpaExpression<T> expression, Class<X> castTargetJavaType);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, T> JpaExpression<X> cast(JpaExpression<T> expression, JpaCastTarget<X> castTarget);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaCastTarget<X> castTarget(Class<X> castTargetJavaType);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaCastTarget<X> castTarget(Class<X> castTargetJavaType, long length);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> JpaCastTarget<X> castTarget(Class<X> castTargetJavaType, int precision, int scale);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate wrap(Expression<Boolean> expression);

	@SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate wrap(Expression<Boolean>... expressions);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate wrap(BooleanExpression... expressions);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T extends HibernateCriteriaBuilder> T unwrap(Class<T> clazz);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Criteria creation

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<Object> createQuery();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaCriteriaQuery<T> createQuery(@Nonnull Class<T> resultClass);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaQuery<Tuple> createTupleQuery();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaCriteriaUpdate<T> createCriteriaUpdate(@Nonnull Class<T> targetEntity);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaCriteriaDelete<T> createCriteriaDelete(@Nonnull Class<T> targetEntity);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaCriteriaInsertValues<T> createCriteriaInsertValues(Class<T> targetEntity);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaCriteriaInsertSelect<T> createCriteriaInsertSelect(Class<T> targetEntity);

	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaValues values(Expression<?>... expressions);

	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaValues values(List<? extends Expression<?>> expressions);

	/**
	 * Transform the given HQL {@code select} query to an equivalent criteria query.
	 *
	 * @param hql The HQL {@code select} query
	 * @param resultClass The result type of the query
	 *
	 * @return The equivalent criteria query
	 *
	 * @since 6.3
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaCriteriaQuery<T> createQuery(String hql, Class<T> resultClass);

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Set operation

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <T> JpaCriteriaQuery<T> unionAll(CriteriaQuery<? extends T> query1, CriteriaQuery<?>... queries) {
		return union( true, query1, queries );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default <T> JpaCriteriaQuery<T> union(CriteriaQuery<? extends T> query1, CriteriaQuery<?>... queries) {
		return union( false, query1, queries );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaCriteriaQuery<T> union(boolean all, CriteriaQuery<? extends T> query1, CriteriaQuery<?>... queries);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <T> JpaCriteriaQuery<T> intersectAll(CriteriaQuery<? extends T> query1, CriteriaQuery<?>... queries) {
		return intersect( true, query1, queries );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default <T> JpaCriteriaQuery<T> intersect(CriteriaQuery<? extends T> query1, CriteriaQuery<?>... queries) {
		return intersect( false, query1, queries );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaCriteriaQuery<T> intersect(boolean all, CriteriaQuery<? extends T> query1, CriteriaQuery<?>... queries);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <T> JpaCriteriaQuery<T> exceptAll(CriteriaQuery<? extends T> query1, CriteriaQuery<?>... queries) {
		return except( true, query1, queries );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default <T> JpaCriteriaQuery<T> except(CriteriaQuery<? extends T> query1, CriteriaQuery<?>... queries) {
		return except( false, query1, queries );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaCriteriaQuery<T> except(boolean all, CriteriaQuery<? extends T> query1, CriteriaQuery<?>... queries);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> CriteriaSelect<T> union(@Nonnull CriteriaSelect<? extends T> left, @Nonnull CriteriaSelect<? extends T> right);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaCriteriaQuery<T> union(CriteriaQuery<? extends T> left, CriteriaQuery<? extends T> right);

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default <T> JpaSubQuery<T> union(Subquery<? extends T> query1, Subquery<?>... queries) {
		return union( false, query1, queries );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaSubQuery<T> union(boolean all, Subquery<? extends T> query1, Subquery<?>... queries);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <T> JpaSubQuery<T> unionAll(JpaSubQuery<? extends T> query1, JpaSubQuery<? extends T> query2) {
		return union( true, query1, query2 );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> CriteriaSelect<T> unionAll(@Nonnull CriteriaSelect<? extends T> left, @Nonnull CriteriaSelect<? extends T> right);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaCriteriaQuery<T> unionAll(CriteriaQuery<? extends T> left, CriteriaQuery<? extends T> right);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> CriteriaSelect<T> intersect(@Nonnull CriteriaSelect<? super T> left, @Nonnull CriteriaSelect<? super T> right);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> CriteriaSelect<T> intersectAll(@Nonnull CriteriaSelect<? super T> left, @Nonnull CriteriaSelect<? super T> right);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaCriteriaQuery<T> intersect(CriteriaQuery<? super T> left, CriteriaQuery<? super T> right);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaCriteriaQuery<T> intersectAll(CriteriaQuery<? super T> left, CriteriaQuery<? super T> right);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <T> JpaSubQuery<T> intersectAll(Subquery<? extends T> query1, Subquery<?>... queries) {
		return intersect( true, query1, queries );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default <T> JpaSubQuery<T> intersect(Subquery<? extends T> query1, Subquery<?>... queries) {
		return intersect( false, query1, queries );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaSubQuery<T> intersect(boolean all, Subquery<? extends T> query1, Subquery<?>... queries);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> CriteriaSelect<T> except(@Nonnull CriteriaSelect<T> left, @Nonnull CriteriaSelect<?> right);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> CriteriaSelect<T> exceptAll(@Nonnull CriteriaSelect<T> left, @Nonnull CriteriaSelect<?> right);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaCriteriaQuery<T> except(CriteriaQuery<T> left, CriteriaQuery<?> right);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaCriteriaQuery<T> exceptAll(CriteriaQuery<T> left, CriteriaQuery<?> right);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <T> JpaSubQuery<T> exceptAll(Subquery<? extends T> query1, Subquery<?>... queries) {
		return except( true, query1, queries );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default <T> JpaSubQuery<T> except(Subquery<? extends T> query1, Subquery<?>... queries) {
		return except( false, query1, queries );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaSubQuery<T> except(boolean all, Subquery<? extends T> query1, Subquery<?>... queries);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// JPA 3.1

	/**
	 * Create an expression that returns the sign of its
	 * argument, that is, {@code 1} if its argument is
	 * positive, {@code -1} if its argument is negative,
	 * or {@code 0} if its argument is exactly zero.
	 * @param x expression
	 * @return sign
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Integer> sign(@Nonnull Expression<? extends Number> x);

	/**
	 * Create an expression that returns the ceiling of its
	 * argument, that is, the smallest integer greater than
	 * or equal to its argument.
	 * @param x expression
	 * @return ceiling
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> JpaExpression<N> ceiling(@Nonnull Expression<N> x);

	/**
	 * Create an expression that returns the floor of its
	 * argument, that is, the largest integer smaller than
	 * or equal to its argument.
	 * @param x expression
	 * @return floor
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> JpaExpression<N> floor(@Nonnull Expression<N> x);

	/**
	 * Create an expression that returns the exponential
	 * of its argument, that is, Euler's number <i>e</i>
	 * raised to the power of its argument.
	 * @param x expression
	 * @return exponential
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> exp(@Nonnull Expression<? extends Number> x);

	/**
	 * Create an expression that returns the natural logarithm
	 * of its argument.
	 * @param x expression
	 * @return natural logarithm
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> ln(@Nonnull Expression<? extends Number> x);

	/**
	 * Create an expression that returns the first argument
	 * raised to the power of its second argument.
	 * @param x base
	 * @param y exponent
	 * @return the base raised to the power of the exponent
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> power(@Nonnull Expression<? extends Number> x, @Nonnull Expression<? extends Number> y);

	/**
	 * Create an expression that returns the first argument
	 * raised to the power of its second argument.
	 * @param x base
	 * @param y exponent
	 * @return the base raised to the power of the exponent
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> power(@Nonnull Expression<? extends Number> x, Number y);

	/**
	 * Create an expression that returns the first argument
	 * rounded to the number of decimal places given by the
	 * second argument.
	 * @param x base
	 * @param n number of decimal places
	 * @return the rounded value
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T extends Number> JpaExpression<T> round(@Nonnull Expression<T> x, @Nonnull Integer n);

	/**
	 * Create an expression that returns the first argument
	 * truncated to the number of decimal places given by the
	 * second argument.
	 * @param x base
	 * @param n number of decimal places
	 * @return the truncated value
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T extends Number> JpaExpression<T> truncate(Expression<T> x, Integer n);

	/**
	 *  Create expression to return current local date.
	 *  @return expression for current date
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<java.time.LocalDate> localDate();

	/**
	 *  Create expression to return current local datetime.
	 *  @return expression for current timestamp
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<java.time.LocalDateTime> localDateTime();

	/**
	 *  Create expression to return current local time.
	 *  @return expression for current time
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<java.time.LocalTime> localTime();

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Paths

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<?> id(Path<?> path);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<?> version(Path<?> path);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<?> fk(Path<?> path);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, T extends X> JpaPath<T> treat(@Nonnull Path<X> path, @Nonnull Class<T> type);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, T extends X> JpaRoot<T> treat(@Nonnull Root<X> root, @Nonnull Class<T> type);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, Y, T extends Y> JpaFrom<X, T> treat(@Nonnull From<X, Y> from, @Nonnull Class<T> type);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, T, V extends T> JpaJoin<X, V> treat(@Nonnull Join<X, T> join, @Nonnull Class<V> type);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, T, E extends T> JpaCollectionJoin<X, E> treat(@Nonnull CollectionJoin<X, T> join, @Nonnull Class<E> type);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, T, E extends T> JpaSetJoin<X, E> treat(@Nonnull SetJoin<X, T> join, @Nonnull Class<E> type);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, T, E extends T> JpaListJoin<X, E> treat(@Nonnull ListJoin<X, T> join, @Nonnull Class<E> type);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, K, T, V extends T> JpaMapJoin<X, K, V> treat(@Nonnull MapJoin<X, K, T> join, @Nonnull Class<V> type);



	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Selections

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaCompoundSelection<Y> construct(@Nonnull Class<Y> resultClass, @Nonnull Selection<?>... selections);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaCompoundSelection<Y> construct(Class<Y> resultClass, List<? extends Selection<?>> arguments);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCompoundSelection<Tuple> tuple(@Nonnull Selection<?>... selections);
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCompoundSelection<Tuple> tuple(@Nonnull List<Selection<?>> selections);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCompoundSelection<Object[]> array(@Nonnull Selection<?>... selections);
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCompoundSelection<Object[]> array(@Nonnull List<Selection<?>> selections);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaCompoundSelection<Y> array(Class<Y> resultClass, Selection<?>... selections);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaCompoundSelection<Y> array(Class<Y> resultClass, List<? extends Selection<?>> selections);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Expressions

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> JpaExpression<Double> avg(@Nonnull Expression<N> argument);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> JpaExpression<N> sum(@Nonnull Expression<N> argument);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Long> sumAsLong(@Nonnull Expression<Integer> argument);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> sumAsDouble(@Nonnull Expression<Float> argument);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> JpaExpression<N> max(@Nonnull Expression<N> argument);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> JpaExpression<N> min(@Nonnull Expression<N> argument);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X extends Comparable<? super X>> JpaExpression<X> greatest(@Nonnull Expression<X> argument);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X extends Comparable<? super X>> JpaExpression<X> least(@Nonnull Expression<X> argument);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Long> count(@Nonnull Expression<?> argument);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Long> countDistinct(@Nonnull Expression<?> x);

	/**
	 * Equivalent to HQL {@code count(*)}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Long> count();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> JpaExpression<N> neg(@Nonnull Expression<N> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> JpaExpression<N> abs(@Nonnull Expression<N> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> JpaExpression<N> sum(@Nonnull Expression<? extends N> x, @Nonnull Expression<? extends N> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> JpaExpression<N> sum(@Nonnull Expression<? extends N> x, N y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> JpaExpression<N> sum(N x, @Nonnull Expression<? extends N> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> JpaExpression<N> prod(@Nonnull Expression<? extends N> x, @Nonnull Expression<? extends N> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> JpaExpression<N> prod(@Nonnull Expression<? extends N> x, N y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> JpaExpression<N> prod(N x, @Nonnull Expression<? extends N> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> JpaExpression<N> diff(@Nonnull Expression<? extends N> x, @Nonnull Expression<? extends N> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> JpaExpression<N> diff(@Nonnull Expression<? extends N> x, N y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> JpaExpression<N> diff(N x, @Nonnull Expression<? extends N> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Number> quot(@Nonnull Expression<? extends Number> x, @Nonnull Expression<? extends Number> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Number> quot(@Nonnull Expression<? extends Number> x, Number y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Number> quot(Number x, @Nonnull Expression<? extends Number> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Integer> mod(@Nonnull Expression<Integer> x, @Nonnull Expression<Integer> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Integer> mod(@Nonnull Expression<Integer> x, Integer y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Integer> mod(Integer x, @Nonnull Expression<Integer> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> sqrt(@Nonnull Expression<? extends Number> x);

	/**
	 * Add two {@linkplain Duration durations}.
	 * @since 6.3
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Duration> durationSum(Expression<Duration> x, Expression<Duration> y);

	/**
	 * Add two {@linkplain Duration durations}.
	 * @since 6.3
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Duration> durationSum(Expression<Duration> x, Duration y);

	/**
	 * Subtract one {@linkplain Duration duration} from another.
	 * @since 6.3
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Duration> durationDiff(Expression<Duration> x, Expression<Duration> y);

	/**
	 * Subtract one {@linkplain Duration duration} from another.
	 * @since 6.3
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Duration> durationDiff(Expression<Duration> x, Duration y);

	/**
	 * Scale a {@linkplain Duration duration} by a number.
	 * @since 6.3
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Duration> durationScaled(Expression<? extends Number> number, Expression<Duration> duration);

	/**
	 * Scale a {@linkplain Duration duration} by a number.
	 * @since 6.3
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Duration> durationScaled(Number number, Expression<Duration> duration);

	/**
	 * Scale a {@linkplain Duration duration} by a number.
	 * @since 6.3
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Duration> durationScaled(Expression<? extends Number> number, Duration duration);

	/**
	 * A literal {@link Duration}, for example, "five days" or "30 minutes".
	 * @since 6.3
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Duration> duration(long magnitude, TemporalUnit unit);

	/**
	 * Convert a {@link Duration} to a numeric magnitude in the given units.
	 * @param unit a choice of temporal granularity
	 * @param duration the duration in a "unit-free" form
	 * @return the magnitude of the duration measured in the given units
	 * @since 6.3
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Long> durationByUnit(TemporalUnit unit, Expression<Duration> duration);

	/**
	 * Subtract two dates or two datetimes, returning the duration between the
	 * two dates or between two datetimes.
	 * @since 6.3
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T extends Temporal> JpaExpression<Duration> durationBetween(Expression<T> x, Expression<T> y);

	/**
	 * Subtract two dates or two datetimes, returning the duration between the
	 * two dates or between two datetimes.
	 * @since 6.3
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T extends Temporal> JpaExpression<Duration> durationBetween(Expression<T> x, T y);

	/**
	 * Add a duration to a date or datetime, that is, return a later date or
	 * datetime which is separated from the given date or datetime by the given
	 * duration.
	 * @since 6.3
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T extends Temporal> JpaExpression<T> addDuration(Expression<T> datetime, Expression<Duration> duration);

	/**
	 * Add a duration to a date or datetime, that is, return a later date or
	 * datetime which is separated from the given date or datetime by the given
	 * duration.
	 * @since 6.3
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T extends Temporal> JpaExpression<T> addDuration(Expression<T> datetime, Duration duration);

	/**
	 * Add a duration to a date or datetime, that is, return a later date or
	 * datetime which is separated from the given date or datetime by the given
	 * duration.
	 * @since 6.3
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T extends Temporal> JpaExpression<T> addDuration(T datetime, Expression<Duration> duration);

	/**
	 * Subtract a duration to a date or datetime, that is, return an earlier date
	 * or datetime which is separated from the given date or datetime by the given
	 * duration.
	 * @since 6.3
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T extends Temporal> JpaExpression<T> subtractDuration(Expression<T> datetime, Expression<Duration> duration);

	/**
	 * Subtract a duration to a date or datetime, that is, return an earlier date
	 * or datetime which is separated from the given date or datetime by the given
	 * duration.
	 * @since 6.3
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T extends Temporal> JpaExpression<T> subtractDuration(Expression<T> datetime, Duration duration);

	/**
	 * Subtract a duration to a date or datetime, that is, return an earlier date
	 * or datetime which is separated from the given date or datetime by the given
	 * duration.
	 * @since 6.3
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T extends Temporal> JpaExpression<T> subtractDuration(T datetime, Expression<Duration> duration);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Long> toLong(@Nonnull Expression<? extends Number> number);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Integer> toInteger(@Nonnull Expression<? extends Number> number);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Float> toFloat(@Nonnull Expression<? extends Number> number);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> toDouble(@Nonnull Expression<? extends Number> number);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<BigDecimal> toBigDecimal(@Nonnull Expression<? extends Number> number);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<BigInteger> toBigInteger(@Nonnull Expression<? extends Number> number);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> toString(@Nonnull Expression<Character> character);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> literal(@Nonnull T value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> List<? extends JpaExpression<T>> literals(T... values);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> List<? extends JpaExpression<T>> literals(List<T> values);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> nullLiteral(@Nonnull Class<T> resultClass);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaParameterExpression<T> parameter(@Nonnull Class<T> paramClass);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaParameterExpression<T> parameter(@Nonnull Class<T> paramClass, @Nonnull String name);

	/**
	 * Create a multivalued parameter accepting multiple arguments
	 * packaged together as a {@link List}.
	 * @param paramClass the type of each argument to the parameter
	 * @param <T> the type of each argument to the parameter
	 * @since 7.0
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaParameterExpression<List<T>> listParameter(Class<T> paramClass);

	/**
	 * Create a multivalued parameter accepting multiple arguments
	 * packaged together as a {@link List}.
	 * @param paramClass the type of each argument to the parameter
	 * @param name the parameter name
	 * @param <T> the type of each argument to the parameter
	 * @since 7.0
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaParameterExpression<List<T>> listParameter(Class<T> paramClass, String name);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> concat(@Nonnull Expression<String> x, @Nonnull Expression<String> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> concat(@Nonnull Expression<String> x, @Nonnull String y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> concat(@Nonnull String x, @Nonnull Expression<String> y);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> concat(String x, String y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> substring(@Nonnull Expression<String> x, @Nonnull Expression<Integer> from);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> substring(@Nonnull Expression<String> x, int from);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> substring(
			@Nonnull Expression<String> x,
			@Nonnull Expression<Integer> from,
			@Nonnull Expression<Integer> len);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> substring(@Nonnull Expression<String> x, int from, int len);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> trim(@Nonnull Expression<String> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> trim(@Nonnull Trimspec ts, @Nonnull Expression<String> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> trim(@Nonnull Expression<Character> t, @Nonnull Expression<String> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> trim(@Nonnull Trimspec ts, @Nonnull Expression<Character> t, @Nonnull Expression<String> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> trim(char t, @Nonnull Expression<String> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> trim(@Nonnull Trimspec ts, char t, @Nonnull Expression<String> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> lower(@Nonnull Expression<String> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> upper(@Nonnull Expression<String> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<Integer> length(@Nonnull Expression<String> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<Integer> locate(@Nonnull Expression<String> x, @Nonnull Expression<String> pattern);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<Integer> locate(@Nonnull Expression<String> x, @Nonnull String pattern);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<Integer> locate(
			@Nonnull Expression<String> x,
			@Nonnull Expression<String> pattern,
			@Nonnull Expression<Integer> from);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<Integer> locate(@Nonnull Expression<String> x, @Nonnull String pattern, int from);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<Date> currentDate();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<Time> currentTime();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<Timestamp> currentTimestamp();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<Instant> currentInstant();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaFunction<T> function(@Nonnull String name, @Nonnull Class<T> type, @Nonnull Expression<?>... args);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaExpression<Y> all(@Nonnull Subquery<Y> subquery);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaExpression<Y> some(@Nonnull Subquery<Y> subquery);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaExpression<Y> any(@Nonnull Subquery<Y> subquery);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<K, L extends List<?>> JpaExpression<Set<K>> indexes(L list);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> value(@Nullable T value);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> JpaExpression<Integer> size(@Nonnull Expression<C> collection);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> JpaExpression<Integer> size(@Nonnull C collection);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaCoalesce<T> coalesce();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaCoalesce<Y> coalesce(@Nonnull Expression<? extends Y> x, @Nonnull Expression<? extends Y> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaCoalesce<Y> coalesce(@Nonnull Expression<? extends Y> x, Y y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaExpression<Y> nullif(@Nonnull Expression<Y> x, @Nonnull Expression<?> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaExpression<Y> nullif(@Nonnull Expression<Y> x, Y y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C, R> JpaSimpleCase<C, R> selectCase(@Nonnull Expression<? extends C> expression);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C, R> JpaSimpleCase<C, R> selectCase(@Nonnull Expression<? extends C> expression, @Nonnull Class<R> resultType);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<R> JpaSearchedCase<R> selectCase();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<R> JpaSearchedCase<R> selectCase(@Nonnull Class<R> resultType);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Predicates


	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate and(@Nonnull Expression<Boolean> x, @Nonnull Expression<Boolean> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate and(@Nonnull BooleanExpression... restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate and(@Nonnull List<? extends Expression<Boolean>> restrictions);

	/**
	 * @deprecated Prefer {@linkplain #and(BooleanExpression...)} instead.  This method used to be
	 * defined as part of Jakarta Persistence, which removed it as of 4.0.
	 */
	@Deprecated
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate and(Predicate... restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate or(@Nonnull Expression<Boolean> x, @Nonnull Expression<Boolean> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate or(@Nonnull BooleanExpression... restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate or(@Nonnull List<? extends Expression<Boolean>> restrictions);

	/**
	 * @deprecated Prefer {@linkplain #or(BooleanExpression...)} instead.  This method used to be
	 * defined as part of Jakarta Persistence, which removed it as of 4.0.
	 */
	@Deprecated
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate or(Predicate... restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate not(@Nonnull Expression<Boolean> restriction);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate conjunction();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate disjunction();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate isTrue(@Nonnull Expression<Boolean> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate isFalse(@Nonnull Expression<Boolean> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate isNull(@Nonnull Expression<?> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate isNotNull(@Nonnull Expression<?> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate equal(@Nonnull Expression<?> x, @Nonnull Expression<?> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate equal(@Nonnull Expression<?> x, Object y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate notEqual(@Nonnull Expression<?> x, @Nonnull Expression<?> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate notEqual(@Nonnull Expression<?> x, Object y);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate distinctFrom(Expression<?> x, Expression<?> y);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate distinctFrom(Expression<?> x, Object y);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate notDistinctFrom(Expression<?> x, Expression<?> y);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate notDistinctFrom(Expression<?> x, Object y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends Comparable<? super Y>> JpaPredicate greaterThan(
			@Nonnull Expression<? extends Y> x,
			@Nonnull Expression<? extends Y> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends Comparable<? super Y>> JpaPredicate greaterThan(@Nonnull Expression<? extends Y> x, Y y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends Comparable<? super Y>> JpaPredicate greaterThanOrEqualTo(
			@Nonnull Expression<? extends Y> x,
			@Nonnull Expression<? extends Y> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends Comparable<? super Y>> JpaPredicate greaterThanOrEqualTo(@Nonnull Expression<? extends Y> x, Y y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends Comparable<? super Y>> JpaPredicate lessThan(
			@Nonnull Expression<? extends Y> x,
			@Nonnull Expression<? extends Y> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends Comparable<? super Y>> JpaPredicate lessThan(@Nonnull Expression<? extends Y> x, Y y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends Comparable<? super Y>> JpaPredicate lessThanOrEqualTo(
			@Nonnull Expression<? extends Y> x,
			@Nonnull Expression<? extends Y> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends Comparable<? super Y>> JpaPredicate lessThanOrEqualTo(@Nonnull Expression<? extends Y> x, Y y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends Comparable<? super Y>> JpaPredicate between(
			@Nonnull Expression<? extends Y> value,
			@Nonnull Expression<? extends Y> lower,
			@Nonnull Expression<? extends Y> upper);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends Comparable<? super Y>> JpaPredicate between(@Nonnull Expression<? extends Y> value, Y lower, Y upper);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends Comparable<? super Y>> JpaPredicate between(
			Y value,
			@Nonnull Expression<? extends Y> lower,
			@Nonnull Expression<? extends Y> upper);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate gt(@Nonnull Expression<? extends Number> x, @Nonnull Expression<? extends Number> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate gt(@Nonnull Expression<? extends Number> x, Number y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate ge(@Nonnull Expression<? extends Number> x, @Nonnull Expression<? extends Number> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate ge(@Nonnull Expression<? extends Number> x, Number y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate lt(@Nonnull Expression<? extends Number> x, @Nonnull Expression<? extends Number> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate lt(@Nonnull Expression<? extends Number> x, Number y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate le(@Nonnull Expression<? extends Number> x, @Nonnull Expression<? extends Number> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate le(@Nonnull Expression<? extends Number> x, Number y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> JpaPredicate isEmpty(@Nonnull Expression<C> collection);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> JpaPredicate isNotEmpty(@Nonnull Expression<C> collection);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<E>> JpaPredicate isMember(@Nonnull Expression<E> elem, @Nonnull Expression<C> collection);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<E>> JpaPredicate isMember(E elem, @Nonnull Expression<C> collection);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<E>> JpaPredicate isNotMember(@Nonnull Expression<E> elem, @Nonnull Expression<C> collection);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<E>> JpaPredicate isNotMember(E elem, @Nonnull Expression<C> collection);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate like(@Nonnull Expression<String> x, @Nonnull Expression<String> pattern);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate like(@Nonnull Expression<String> x, @Nonnull String pattern);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate like(@Nonnull Expression<String> x, @Nonnull Expression<String> pattern, @Nonnull Expression<Character> escapeChar);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate like(@Nonnull Expression<String> x, @Nonnull Expression<String> pattern, char escapeChar);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate like(@Nonnull Expression<String> x, @Nonnull String pattern, @Nonnull Expression<Character> escapeChar);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate like(@Nonnull Expression<String> x, @Nonnull String pattern, char escapeChar);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate ilike(Expression<String> x, Expression<String> pattern);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate ilike(Expression<String> x, String pattern);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate ilike(Expression<String> x, Expression<String> pattern, Expression<Character> escapeChar);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate ilike(Expression<String> x, Expression<String> pattern, char escapeChar);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate ilike(Expression<String> x, String pattern, Expression<Character> escapeChar);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate ilike(Expression<String> x, String pattern, char escapeChar);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate notLike(@Nonnull Expression<String> x, @Nonnull Expression<String> pattern);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate notLike(@Nonnull Expression<String> x, @Nonnull String pattern);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate notLike(@Nonnull Expression<String> x, @Nonnull Expression<String> pattern, @Nonnull Expression<Character> escapeChar);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate notLike(@Nonnull Expression<String> x, @Nonnull Expression<String> pattern, char escapeChar);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate notLike(@Nonnull Expression<String> x, @Nonnull String pattern, @Nonnull Expression<Character> escapeChar);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate notLike(@Nonnull Expression<String> x, @Nonnull String pattern, char escapeChar);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate notIlike(Expression<String> x, Expression<String> pattern);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate notIlike(Expression<String> x, String pattern);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate notIlike(Expression<String> x, Expression<String> pattern, Expression<Character> escapeChar);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate notIlike(Expression<String> x, Expression<String> pattern, char escapeChar);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate notIlike(Expression<String> x, String pattern, Expression<Character> escapeChar);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate notIlike(Expression<String> x, String pattern, char escapeChar);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate likeRegexp(Expression<String> x, String pattern);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate ilikeRegexp(Expression<String> x, String pattern);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate notLikeRegexp(Expression<String> x, String pattern);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate notIlikeRegexp(Expression<String> x, String pattern);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaInPredicate<T> in(@Nonnull Expression<? extends T> expression);

	@SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaInPredicate<T> in(Expression<? extends T> expression, Expression<? extends T>... values);

	@SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaInPredicate<T> in(Expression<? extends T> expression, T... values);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaInPredicate<T> in(Expression<? extends T> expression, Collection<T> values);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate exists(@Nonnull Subquery<?> subquery);

	/**
	 * Create a predicate that tests whether a Map is empty.
	 *
	 * @apiNote Due to type-erasure we cannot name this the same as
	 *          {@link CriteriaBuilder#isEmpty}.
	 *
	 * @param mapExpression The expression resolving to a Map which we
	 * want to check for emptiness
	 *
	 * @return is-empty predicate
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<M extends Map<?,?>> JpaPredicate isMapEmpty(JpaExpression<M> mapExpression);

	/**
	 * Create a predicate that tests whether a Map is not empty.
	 *
	 * @apiNote Due to type-erasure we cannot name this the same as
	 *          {@link CriteriaBuilder#isNotEmpty}
	 *
	 * @param mapExpression The expression resolving to a Map which we
	 * want to check for non-emptiness
	 *
	 * @return is-not-empty predicate
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<M extends Map<?,?>> JpaPredicate isMapNotEmpty(JpaExpression<M> mapExpression);

	/**
	 * Create an expression that tests the size of a map.
	 *
	 * @apiNote Due to type-erasure we cannot name this the same as
	 *          {@link CriteriaBuilder#size}
	 *
	 * @param mapExpression The expression resolving to a Map for which we
	 * want to know the size
	 *
	 * @return size expression
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<M extends Map<?,?>> JpaExpression<Integer> mapSize(JpaExpression<M> mapExpression);

	/**
	 * Create an expression that tests the size of a map.
	 *
	 * @param map The Map for which we want to know the size
	 *
	 * @return size expression
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<M extends Map<?, ?>> JpaExpression<Integer> mapSize(M map);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Ordering


	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaOrder sort(JpaExpression<?> sortExpression);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaOrder sort(JpaExpression<?> sortExpression, SortDirection sortOrder);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaOrder sort(JpaExpression<?> sortExpression, SortDirection sortOrder, Nulls nullPrecedence);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaOrder sort(JpaExpression<?> sortExpression, SortDirection sortOrder, Nulls nullPrecedence, boolean ignoreCase);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaOrder asc(@Nonnull Expression<?> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaOrder desc(@Nonnull Expression<?> x);

	/**
	 * Create an ordering by the ascending value of the expression.
	 * @param x  expression used to define the ordering
	 * @param nullsFirst Whether <code>null</code> should be sorted first
	 * @return ascending ordering corresponding to the expression
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaOrder asc(Expression<?> x, boolean nullsFirst);

	/**
	 * Create an ordering by the descending value of the expression.
	 * @param x  expression used to define the ordering
	 * @param nullsFirst Whether <code>null</code> should be sorted first
	 * @return descending ordering corresponding to the expression
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaOrder desc(Expression<?> x, boolean nullsFirst);

	/**
	 * Create a search ordering based on the sort order and null precedence of the value of the CTE attribute.
	 * @param cteAttribute CTE attribute used to define the ordering
	 * @param sortOrder The sort order
	 * @param nullPrecedence The null precedence
	 * @return ordering corresponding to the CTE attribute
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSearchOrder search(JpaCteCriteriaAttribute cteAttribute, SortDirection sortOrder, Nulls nullPrecedence);

	/**
	 * Create a search ordering based on the sort order of the value of the CTE attribute.
	 * @param cteAttribute CTE attribute used to define the ordering
	 * @param sortOrder The sort order
	 * @return ordering corresponding to the CTE attribute
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSearchOrder search(JpaCteCriteriaAttribute cteAttribute, SortDirection sortOrder);

	/**
	 * Create a search ordering based on the ascending value of the CTE attribute.
	 * @param cteAttribute CTE attribute used to define the ordering
	 * @return ascending ordering corresponding to the CTE attribute
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSearchOrder search(JpaCteCriteriaAttribute cteAttribute);

	/**
	 * Create a search ordering by the ascending value of the CTE attribute.
	 * @param x  CTE attribute used to define the ordering
	 * @return ascending ordering corresponding to the CTE attribute
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSearchOrder asc(JpaCteCriteriaAttribute x);

	/**
	 * Create a search ordering by the descending value of the CTE attribute.
	 * @param x CTE attribute used to define the ordering
	 * @return descending ordering corresponding to the CTE attribute
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSearchOrder desc(JpaCteCriteriaAttribute x);

	/**
	 * Create a search ordering by the ascending value of the CTE attribute.
	 * @param x  CTE attribute used to define the ordering
	 * @param nullsFirst Whether <code>null</code> should be sorted first
	 * @return ascending ordering corresponding to the CTE attribute
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSearchOrder asc(JpaCteCriteriaAttribute x, boolean nullsFirst);

	/**
	 * Create a search ordering by the descending value of the CTE attribute.
	 * @param x CTE attribute used to define the ordering
	 * @param nullsFirst Whether <code>null</code> should be sorted first
	 * @return descending ordering corresponding to the CTE attribute
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSearchOrder desc(JpaCteCriteriaAttribute x, boolean nullsFirst);

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Non-standard HQL functions

	/**
	 * Embed native {@code pattern} that will be unquoted and embedded in the generated SQL.
	 * Occurrences of {@code ?} in the pattern are replaced with the remaining {@code arguments}
	 * of the function.
	 *
	 * @param pattern native SQL pattern
	 * @param type type of this expression
	 * @param arguments optional arguments to the SQL pattern
	 * @param <T> type of this expression
	 *
	 * @return native SQL expression
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> sql(String pattern, Class<T> type, Expression<?>... arguments);

	/**
	 * Format a date, time, or datetime according to a pattern.
	 * The pattern must be written in a subset of the pattern language defined by
	 * Java’s {@link java.time.format.DateTimeFormatter}.
	 * <p>
	 * See {@link org.hibernate.dialect.Dialect#appendDatetimeFormat}
	 * for a full list of pattern elements.
	 *
	 * @param datetime the datetime expression to format
	 * @param pattern the pattern to use for formatting
	 *
	 * @return format expression
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> format(Expression<? extends TemporalAccessor> datetime, String pattern);

	/**
	 * Extracts the {@link TemporalUnit#YEAR} of a date, time, or datetime expression.
	 *
	 * @param datetime the date, time, or datetime to extract the value from
	 *
	 * @return the extracted value
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<Integer> year(Expression<? extends TemporalAccessor> datetime);

	/**
	 * Extracts the {@link TemporalUnit#MONTH} of a date, time, or datetime expression.
	 *
	 * @param datetime the date, time, or datetime to extract the value from
	 *
	 * @return the extracted value
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<Integer> month(Expression<? extends TemporalAccessor> datetime);

	/**
	 * Extracts the {@link TemporalUnit#DAY} of a date, time, or datetime expression.
	 *
	 * @param datetime the date, time, or datetime to extract the value from
	 *
	 * @return the extracted value
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<Integer> day(Expression<? extends TemporalAccessor> datetime);

	/**
	 * Extracts the {@link TemporalUnit#HOUR} of a date, time, or datetime expression.
	 *
	 * @param datetime the date, time, or datetime to extract the value from
	 *
	 * @return the extracted value
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<Integer> hour(Expression<? extends TemporalAccessor> datetime);

	/**
	 * Extracts the {@link TemporalUnit#MINUTE} of a date, time, or datetime expression.
	 *
	 * @param datetime the date, time, or datetime to extract the value from
	 *
	 * @return the extracted value
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<Integer> minute(Expression<? extends TemporalAccessor> datetime);

	/**
	 * Extracts the {@link TemporalUnit#SECOND} of a date, time, or datetime expression.
	 *
	 * @param datetime the date, time, or datetime to extract the value from
	 *
	 * @return the extracted value
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<Float> second(Expression<? extends TemporalAccessor> datetime);

	/**
	 * Truncates a date, time or datetime expression to the given {@link TemporalUnit}.
	 * Supported units are: {@code YEAR}, {@code MONTH}, {@code DAY},  {@code HOUR}, {@code MINUTE}, {@code SECOND}.
	 * <p>
	 * Truncating translates to obtaining a value of the same type in which all temporal units smaller than {@code field} have been pruned.
	 * For hours, minutes and second this means setting them to {@code 00}. For months and days, this means setting them to {@code 01}.
	 *
	 * @param datetime the date, time or datetime expression to be truncated
	 * @param temporalUnit the temporal unit for truncation
	 *
	 * @return the truncated value
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T extends TemporalAccessor> JpaFunction<T> truncate(Expression<T> datetime, TemporalUnit temporalUnit);

	/**
	 * @see #overlay(Expression, Expression, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> overlay(Expression<String> string, String replacement, int start);

	/**
	 * @see #overlay(Expression, Expression, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> overlay(Expression<String> string, Expression<String> replacement, int start);

	/**
	 * @see #overlay(Expression, Expression, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> overlay(Expression<String> string, String replacement, Expression<Integer> start);

	/**
	 * @see #overlay(Expression, Expression, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> overlay(Expression<String> string, Expression<String> replacement, Expression<Integer> start);

	/**
	 * @see #overlay(Expression, Expression, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> overlay(Expression<String> string, String replacement, int start, int length);

	/**
	 * @see #overlay(Expression, Expression, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> overlay(Expression<String> string, Expression<String> replacement, int start, int length);

	/**
	 * @see #overlay(Expression, Expression, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> overlay(Expression<String> string, String replacement, Expression<Integer> start, int length);

	/**
	 * @see #overlay(Expression, Expression, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> overlay(
			Expression<String> string,
			Expression<String> replacement,
			Expression<Integer> start,
			int length);

	/**
	 * @see #overlay(Expression, Expression, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> overlay(Expression<String> string, String replacement, int start, Expression<Integer> length);

	/**
	 * @see #overlay(Expression, Expression, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> overlay(
			Expression<String> string,
			Expression<String> replacement,
			int start,
			Expression<Integer> length);

	/**
	 * @see #overlay(Expression, Expression, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> overlay(
			Expression<String> string,
			String replacement,
			Expression<Integer> start,
			Expression<Integer> length);

	/**
	 * Overlay the {@code string} expression with the {@code replacement} expression,
	 * starting from index {@code start} and substituting a number of characters
	 * corresponding to the length of the {@code replacement} expression or the
	 * {@code length} parameter if specified.
	 *
	 * @param string string expression to be manipulated
	 * @param replacement string expression to replace in original
	 * @param start start position
	 * @param length optional, number of characters to substitute
	 *
	 * @return overlay expression
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> overlay(
			Expression<String> string,
			Expression<String> replacement,
			Expression<Integer> start,
			Expression<Integer> length);

	/**
	 * @see #pad(Trimspec, Expression, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> pad(Expression<String> x, int length);

	/**
	 * @see #pad(Trimspec, Expression, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> pad(Trimspec ts, Expression<String> x, int length);

	/**
	 * @see #pad(Trimspec, Expression, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> pad(Expression<String> x, Expression<Integer> length);

	/**
	 * @see #pad(Trimspec, Expression, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> pad(Trimspec ts, Expression<String> x, Expression<Integer> length);

	/**
	 * @see #pad(Trimspec, Expression, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> pad(Expression<String> x, int length, char padChar);

	/**
	 * @see #pad(Trimspec, Expression, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> pad(Trimspec ts, Expression<String> x, int length, char padChar);

	/**
	 * @see #pad(Trimspec, Expression, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> pad(Expression<String> x, Expression<Integer> length, char padChar);

	/**
	 * @see #pad(Trimspec, Expression, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> pad(Trimspec ts, Expression<String> x, Expression<Integer> length, char padChar);

	/**
	 * @see #pad(Trimspec, Expression, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> pad(Expression<String> x, int length, Expression<Character> padChar);

	/**
	 * @see #pad(Trimspec, Expression, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> pad(Trimspec ts, Expression<String> x, int length, Expression<Character> padChar);

	/**
	 * @see #pad(Trimspec, Expression, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> pad(Expression<String> x, Expression<Integer> length, Expression<Character> padChar);

	/**
	 * Pad the specified string expression with whitespace or with the {@code padChar} character if specified.
	 * Optionally pass a {@link jakarta.persistence.criteria.CriteriaBuilder.Trimspec} to pad the
	 * string expression with {@code LEADING} or {@code TRAILING} (default) characters.
	 *
	 * @param ts optional {@link jakarta.persistence.criteria.CriteriaBuilder.Trimspec}
	 * @param x string expression to pad
	 * @param length length of the result string after padding
	 * @param padChar optional pad character
	 *
	 * @return pad expression
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> pad(
			Trimspec ts,
			Expression<String> x,
			Expression<Integer> length,
			Expression<Character> padChar);

	/**
	 * Concatenate the given string expression with itself the given number of times.
	 *
	 * @param x the string expression to concatenate
	 * @param times the number of times it should be repeated
	 *
	 * @return repeat expression
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> repeat(Expression<String> x, Expression<Integer> times);

	/**
	 * Concatenate the given string expression with itself the given number of times.
	 *
	 * @param x the string expression to concatenate
	 * @param times the number of times it should be repeated
	 *
	 * @return repeat expression
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> repeat(Expression<String> x, int times);

	/**
	 * Concatenate the given string expression with itself the given number of times.
	 *
	 * @param x the string expression to concatenate
	 * @param times the number of times it should be repeated
	 *
	 * @return repeat expression
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> repeat(String x, Expression<Integer> times);

	/**
	 * @see #left(Expression, Expression)
	 */
	@Nonnull
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> left(@Nonnull Expression<String> x, int length);

	/**
	 * Extract the {@code length} leftmost characters of a string.
	 *
	 * @param x original string
	 * @param length number of characters
	 *
	 * @return left expression
	 */
	@Nonnull
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> left(@Nonnull Expression<String> x, @Nonnull Expression<Integer> length);

	/**
	 * @see #right(Expression, Expression)
	 */
	@Nonnull
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> right(@Nonnull Expression<String> x, int length);

	/**
	 * Extract the {@code length} rightmost characters of a string.
	 *
	 * @param x original string
	 * @param length number of characters
	 *
	 * @return left expression
	 */
	@Nonnull
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> right(@Nonnull Expression<String> x, @Nonnull Expression<Integer> length);

	/**
	 * @see #replace(Expression, Expression, Expression)
	 */
	@Nonnull
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> replace(@Nonnull Expression<String> x, @Nonnull String pattern, @Nonnull String replacement);

	/**
	 * @see #replace(Expression, Expression, Expression)
	 */
	@Nonnull
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> replace(@Nonnull Expression<String> x, @Nonnull String pattern, @Nonnull Expression<String> replacement);

	/**
	 * @see #replace(Expression, Expression, Expression)
	 */
	@Nonnull
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> replace(@Nonnull Expression<String> x, @Nonnull Expression<String> pattern, @Nonnull String replacement);

	/**
	 * Replace all occurrences of {@code pattern} within the original string with {@code replacement}.
	 *
	 * @param x original string
	 * @param pattern the string to be replaced
	 * @param replacement the new replacement string
	 *
	 * @return replace expression
	 */
	@Nonnull
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> replace(@Nonnull Expression<String> x, @Nonnull Expression<String> pattern, @Nonnull Expression<String> replacement);

	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaFunction<String> collate(Expression<String> x, String collation);

	/**
	 * Create an expression that returns the base-10 logarithm
	 * of its argument.
	 *
	 * @param x expression
	 *
	 * @return base-10 logarithm
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> log10(Expression<? extends Number> x);

	/**
	 * @see #log(Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> log(Number b, Expression<? extends Number> x);

	/**
	 * Create an expression that returns the logarithm of {@code x} to the base {@code b}.
	 *
	 * @param b base
	 * @param x expression
	 *
	 * @return arbitrary-base logarithm
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> log(Expression<? extends Number> b, Expression<? extends Number> x);

	/**
	 * Literal expression corresponding to the value of pi.
	 *
	 * @return pi expression
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> pi();

	/**
	 * Create an expression that returns the sine of its argument.
	 *
	 * @param x expression
	 *
	 * @return sine
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> sin(Expression<? extends Number> x);

	/**
	 * Create an expression that returns the cosine of its argument.
	 *
	 * @param x expression
	 *
	 * @return cosine
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> cos(Expression<? extends Number> x);

	/**
	 * Create an expression that returns the tangent of its argument.
	 *
	 * @param x expression
	 *
	 * @return tangent
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> tan(Expression<? extends Number> x);

	/**
	 * Create an expression that returns the inverse sine of its argument.
	 *
	 * @param x expression
	 *
	 * @return inverse sine
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> asin(Expression<? extends Number> x);

	/**
	 * Create an expression that returns the inverse cosine of its argument.
	 *
	 * @param x expression
	 *
	 * @return inverse cosine
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> acos(Expression<? extends Number> x);

	/**
	 * Create an expression that returns the inverse tangent of its argument.
	 *
	 * @param x expression
	 *
	 * @return inverse tangent
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> atan(Expression<? extends Number> x);

	/**
	 * @see #atan2(Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> atan2(Number y, Expression<? extends Number> x);

	/**
	 * @see #atan2(Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> atan2(Expression<? extends Number> y, Number x);

	/**
	 * Create an expression that returns the inverse tangent of {@code y} over {@code x}.
	 *
	 * @param y y coordinate
	 * @param x x coordinate
	 *
	 * @return 2-argument inverse tangent
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> atan2(Expression<? extends Number> y, Expression<? extends Number> x);

	/**
	 * Create an expression that returns the hyperbolic sine of its argument.
	 *
	 * @param x expression
	 *
	 * @return hyperbolic sine
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> sinh(Expression<? extends Number> x);

	/**
	 * Create an expression that returns the hyperbolic cosine of its argument.
	 *
	 * @param x expression
	 *
	 * @return hyperbolic cosine
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> cosh(Expression<? extends Number> x);

	/**
	 * Create an expression that returns the hyperbolic tangent of its argument.
	 *
	 * @param x expression
	 *
	 * @return hyperbolic tangent
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> tanh(Expression<? extends Number> x);

	/**
	 * Create an expression that converts an angle measured in radians
	 * to an approximately equivalent angle measured in degrees.
	 *
	 * @param x expression
	 *
	 * @return degrees
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> degrees(Expression<? extends Number> x);

	/**
	 * Create an expression that converts an angle measured in degrees
	 * to an approximately equivalent angle measured in radians.
	 *
	 * @param x expression
	 *
	 * @return radians
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> radians(Expression<? extends Number> x);

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Window functions

	/**
	 * Create an empty {@link JpaWindow} to use with window and aggregate functions.
	 *
	 * @return the empty window
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaWindow createWindow();

	/**
	 * Create a window frame of type {@link FrameKind#UNBOUNDED_PRECEDING} to use with {@link JpaWindow}s.
	 *
	 * @return the window frame
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaWindowFrame frameUnboundedPreceding();

	/**
	 * @see #frameBetweenPreceding(Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaWindowFrame frameBetweenPreceding(int offset);

	/**
	 * Create window frame of type {@link FrameKind#OFFSET_PRECEDING} to use with {@link JpaWindow}s.
	 *
	 * @param offset the {@code offset} expression
	 *
	 * @return the window frame
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaWindowFrame frameBetweenPreceding(Expression<?> offset);

	/**
	 * Create a window frame of type {@link FrameKind#CURRENT_ROW} to use with {@link JpaWindow}s.
	 *
	 * @return the window frame
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaWindowFrame frameCurrentRow();

	/**
	 * @see #frameBetweenFollowing(Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaWindowFrame frameBetweenFollowing(int offset);

	/**
	 * Create a window frame of type {@link FrameKind#OFFSET_FOLLOWING} to use with {@link JpaWindow}s.
	 *
	 * @param offset the {@code offset} expression
	 *
	 * @return the window frame
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaWindowFrame frameBetweenFollowing(Expression<?> offset);

	/**
	 * Create a window frame of type {@link FrameKind#UNBOUNDED_FOLLOWING} to use with {@link JpaWindow}s.
	 *
	 * @return the window frame
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaWindowFrame frameUnboundedFollowing();

	/**
	 * Create a generic window function expression that will be applied
	 * over the specified {@link JpaWindow window}.
	 *
	 * @param name name of the window function
	 * @param type type of this expression
	 * @param window window over which the function will be applied
	 * @param args arguments to the function
	 * @param <T> type of this expression
	 *
	 * @return window function expression
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> windowFunction(String name, Class<T> type, JpaWindow window, Expression<?>... args);

	/**
	 * Create a {@code row_number} window function expression.
	 *
	 * @param window window over which the function will be applied
	 *
	 * @return window function expression
	 *
	 * @see #windowFunction
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Long> rowNumber(JpaWindow window);

	/**
	 * Create a {@code first_value} window function expression.
	 *
	 * @param argument argument expression to pass to {@code first_value}
	 * @param window window over which the function will be applied
	 * @param <T> type of the expression
	 *
	 * @return window function expression
	 *
	 * @see #windowFunction
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> firstValue(Expression<T> argument, JpaWindow window);

	/**
	 * Create a {@code last_value} window function expression.
	 *
	 * @param argument argument expression to pass to {@code last_value}
	 * @param window window over which the function will be applied
	 * @param <T> type of the expression
	 *
	 * @return window function expression
	 *
	 * @see #windowFunction
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> lastValue(Expression<T> argument, JpaWindow window);

	/**
	 * @see #nthValue(Expression, Expression, JpaWindow) nthValue
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> nthValue(Expression<T> argument, int n, JpaWindow window);

	/**
	 * Create a {@code nth_value} window function expression.
	 *
	 * @param argument argument expression to pass to {@code nth_value}
	 * @param n the {@code N} argument for the function
	 * @param window window over which the function will be applied
	 * @param <T> type of the expression
	 *
	 * @return window function expression
	 *
	 * @see #windowFunction
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> nthValue(Expression<T> argument, Expression<Integer> n, JpaWindow window);

	/**
	 * Create a {@code rank} window function expression.
	 *
	 * @param window window over which the function will be applied
	 *
	 * @return window function expression
	 *
	 * @see #windowFunction
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Long> rank(JpaWindow window);

	/**
	 * Create a {@code dense_rank} window function expression.
	 *
	 * @param window window over which the function will be applied
	 *
	 * @return window function expression
	 *
	 * @see #windowFunction
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Long> denseRank(JpaWindow window);

	/**
	 * Create a {@code percent_rank} window function expression.
	 *
	 * @param window window over which the function will be applied
	 *
	 * @return window function expression
	 *
	 * @see #windowFunction
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> percentRank(JpaWindow window);

	/**
	 * Create a {@code cume_dist} window function expression.
	 *
	 * @param window window over which the function will be applied
	 *
	 * @return window function expression
	 *
	 * @see #windowFunction
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> cumeDist(JpaWindow window);

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Aggregate functions

	/**
	 * @see #functionAggregate(String, Class, JpaPredicate, JpaWindow, Expression...)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> functionAggregate(
			String name,
			Class<T> type,
			JpaPredicate filter,
			Expression<?>... args);

	/**
	 * @see #functionAggregate(String, Class, JpaPredicate, JpaWindow, Expression...)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> functionAggregate(
			String name,
			Class<T> type,
			JpaWindow window,
			Expression<?>... args);

	/**
	 * Create a generic aggregate function expression.
	 *
	 * @param name name of the ordered set-aggregate function
	 * @param type type of this expression
	 * @param filter optional filter clause
	 * @param window optional window over which to apply the function
	 * @param args optional arguments to the function
	 * @param <T> type of this expression
	 *
	 * @return aggregate function expression
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> functionAggregate(
			String name,
			Class<T> type,
			JpaPredicate filter,
			JpaWindow window,
			Expression<?>... args);

	/**
	 * @see #sum(Expression, JpaPredicate, JpaWindow)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> JpaExpression<Number> sum(Expression<N> argument, JpaPredicate filter);

	/**
	 * @see #sum(Expression, JpaPredicate, JpaWindow)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> JpaExpression<Number> sum(Expression<N> argument, JpaWindow window);

	/**
	 * Create a {@code sum} aggregate function expression.
	 *
	 * @param argument argument to the function
	 * @param filter optional filter clause
	 * @param window optional window over which to apply the function
	 * @param <N> type of the input expression
	 *
	 * @return aggregate function expression
	 *
	 * @see #functionAggregate(String, Class, JpaPredicate, JpaWindow, Expression...)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> JpaExpression<Number> sum(Expression<N> argument, JpaPredicate filter, JpaWindow window);

	/**
	 * @see #avg(Expression, JpaPredicate, JpaWindow)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> JpaExpression<Double> avg(Expression<N> argument, JpaPredicate filter);

	/**
	 * @see #avg(Expression, JpaPredicate, JpaWindow)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> JpaExpression<Double> avg(Expression<N> argument, JpaWindow window);

	/**
	 * Create an {@code avg} aggregate function expression.
	 *
	 * @param argument argument to the function
	 * @param filter optional filter clause
	 * @param window optional window over which to apply the function
	 * @param <N> type of the input expression
	 *
	 * @return aggregate function expression
	 *
	 * @see #functionAggregate(String, Class, JpaPredicate, JpaWindow, Expression...)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> JpaExpression<Double> avg(Expression<N> argument, JpaPredicate filter, JpaWindow window);

	/**
	 * @see #count(Expression, JpaPredicate, JpaWindow)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Long> count(Expression<?> argument, JpaPredicate filter);

	/**
	 * @see #count(Expression, JpaPredicate, JpaWindow)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Long> count(Expression<?> argument, JpaWindow window);

	/**
	 * Create a {@code count} aggregate function expression.
	 *
	 * @param argument argument to the function
	 * @param filter optional filter clause
	 * @param window optional window over which to apply the function
	 *
	 * @return aggregate function expression
	 *
	 * @see #functionAggregate(String, Class, JpaPredicate, JpaWindow, Expression...)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Long> count(Expression<?> argument, JpaPredicate filter, JpaWindow window);

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Ordered-Set Aggregate functions

	/**
	 * @see #functionWithinGroup(String, Class, JpaOrder, JpaPredicate, JpaWindow, Expression...)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> functionWithinGroup(String name, Class<T> type, JpaOrder order, Expression<?>... args);

	/**
	 * @see #functionWithinGroup(String, Class, JpaOrder, JpaPredicate, JpaWindow, Expression...)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> functionWithinGroup(
			String name,
			Class<T> type,
			JpaOrder order,
			JpaPredicate filter,
			Expression<?>... args);

	/**
	 * @see #functionWithinGroup(String, Class, JpaOrder, JpaPredicate, JpaWindow, Expression...)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> functionWithinGroup(
			String name,
			Class<T> type,
			JpaOrder order,
			JpaWindow window,
			Expression<?>... args);

	/**
	 * Create a generic ordered set-aggregate function expression.
	 *
	 * @param name name of the ordered set-aggregate function
	 * @param type type of this expression
	 * @param order order by clause used in within group
	 * @param filter optional filter clause
	 * @param window optional window over which to apply the function
	 * @param args optional arguments to the function
	 * @param <T> type of this expression
	 *
	 * @return ordered set-aggregate function expression
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> functionWithinGroup(
			String name,
			Class<T> type,
			JpaOrder order,
			JpaPredicate filter,
			JpaWindow window,
			Expression<?>... args);

	/**
	 * @see #listagg(JpaOrder, JpaPredicate, JpaWindow, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> listagg(JpaOrder order, Expression<String> argument, String separator);

	/**
	 * @see #listagg(JpaOrder, JpaPredicate, JpaWindow, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> listagg(JpaOrder order, Expression<String> argument, Expression<String> separator);

	/**
	 * @see #listagg(JpaOrder, JpaPredicate, JpaWindow, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> listagg(JpaOrder order, JpaPredicate filter, Expression<String> argument, String separator);

	/**
	 * @see #listagg(JpaOrder, JpaPredicate, JpaWindow, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> listagg(
			JpaOrder order,
			JpaPredicate filter,
			Expression<String> argument,
			Expression<String> separator);

	/**
	 * @see #listagg(JpaOrder, JpaPredicate, JpaWindow, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> listagg(JpaOrder order, JpaWindow window, Expression<String> argument, String separator);

	/**
	 * @see #listagg(JpaOrder, JpaPredicate, JpaWindow, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> listagg(
			JpaOrder order,
			JpaWindow window,
			Expression<String> argument,
			Expression<String> separator);

	/**
	 * @see #listagg(JpaOrder, JpaPredicate, JpaWindow, Expression, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> listagg(
			JpaOrder order,
			JpaPredicate filter,
			JpaWindow window,
			Expression<String> argument,
			String separator);

	/**
	 * Create a {@code listagg} ordered set-aggregate function expression.
	 *
	 * @param order order by clause used in within group
	 * @param filter optional filter clause
	 * @param window optional window over which to apply the function
	 * @param argument values to join
	 * @param separator the separator used to join the values
	 *
	 * @return ordered set-aggregate expression
	 *
	 * @see #functionWithinGroup(String, Class, JpaOrder, JpaPredicate, JpaWindow, Expression...)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> listagg(
			JpaOrder order,
			JpaPredicate filter,
			JpaWindow window,
			Expression<String> argument,
			Expression<String> separator);

	/**
	 * @see #mode(JpaPredicate, JpaWindow, Expression, SortDirection, Nulls)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> mode(Expression<T> sortExpression, SortDirection sortOrder, Nulls nullPrecedence);

	/**
	 * @see #mode(JpaPredicate, JpaWindow, Expression, SortDirection, Nulls)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> mode(
			JpaPredicate filter,
			Expression<T> sortExpression,
			SortDirection sortOrder,
			Nulls nullPrecedence);

	/**
	 * @see #mode(JpaPredicate, JpaWindow, Expression, SortDirection, Nulls)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> mode(
			JpaWindow window,
			Expression<T> sortExpression,
			SortDirection sortOrder,
			Nulls nullPrecedence);

	/**
	 * Create a {@code mode} ordered set-aggregate function expression.
	 *
	 * @param filter optional filter clause
	 * @param window optional window over which to apply the function
	 * @param sortExpression the sort expression
	 * @param sortOrder the sort order
	 * @param nullPrecedence the null precedence
	 * @param <T> type of this expression
	 *
	 * @return ordered set-aggregate expression
	 *
	 * @see #functionWithinGroup(String, Class, JpaOrder, JpaPredicate, JpaWindow, Expression...)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> mode(
			JpaPredicate filter,
			JpaWindow window,
			Expression<T> sortExpression,
			SortDirection sortOrder,
			Nulls nullPrecedence);

	/**
	 * @see #percentileCont(Expression, JpaPredicate, JpaWindow, Expression, SortDirection, Nulls)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> percentileCont(
			Expression<? extends Number> argument,
			Expression<T> sortExpression,
			SortDirection sortOrder,
			Nulls nullPrecedence);

	/**
	 * @see #percentileCont(Expression, JpaPredicate, JpaWindow, Expression, SortDirection, Nulls)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> percentileCont(
			Expression<? extends Number> argument,
			JpaPredicate filter,
			Expression<T> sortExpression,
			SortDirection sortOrder,
			Nulls nullPrecedence);

	/**
	 * @see #percentileCont(Expression, JpaPredicate, JpaWindow, Expression, SortDirection, Nulls)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> percentileCont(
			Expression<? extends Number> argument,
			JpaWindow window,
			Expression<T> sortExpression,
			SortDirection sortOrder,
			Nulls nullPrecedence);

	/**
	 * Create a {@code percentile_cont} ordered set-aggregate function expression.
	 *
	 * @param argument argument to the function
	 * @param filter optional filter clause
	 * @param window optional window over which to apply the function
	 * @param sortExpression the sort expression
	 * @param sortOrder the sort order
	 * @param nullPrecedence the null precedence
	 *
	 * @return ordered set-aggregate expression
	 *
	 * @see #functionWithinGroup(String, Class, JpaOrder, JpaPredicate, JpaWindow, Expression...)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> percentileCont(
			Expression<? extends Number> argument,
			JpaPredicate filter,
			JpaWindow window,
			Expression<T> sortExpression,
			SortDirection sortOrder,
			Nulls nullPrecedence);

	/**
	 * @see #percentileDisc(Expression, JpaPredicate, JpaWindow, Expression, SortDirection, Nulls)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> percentileDisc(
			Expression<? extends Number> argument,
			Expression<T> sortExpression,
			SortDirection sortOrder,
			Nulls nullPrecedence);

	/**
	 * @see #percentileDisc(Expression, JpaPredicate, JpaWindow, Expression, SortDirection, Nulls)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> percentileDisc(
			Expression<? extends Number> argument,
			JpaPredicate filter,
			Expression<T> sortExpression,
			SortDirection sortOrder,
			Nulls nullPrecedence);

	/**
	 * @see #percentileDisc(Expression, JpaPredicate, JpaWindow, Expression, SortDirection, Nulls)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> percentileDisc(
			Expression<? extends Number> argument,
			JpaWindow window,
			Expression<T> sortExpression,
			SortDirection sortOrder,
			Nulls nullPrecedence);

	/**
	 * Create a {@code percentile_disc} ordered set-aggregate function expression.
	 *
	 * @param argument argument to the function
	 * @param filter optional filter clause
	 * @param window optional window over which to apply the function
	 * @param sortExpression the sort expression
	 * @param sortOrder the sort order
	 * @param nullPrecedence the null precedence
	 *
	 * @return ordered set-aggregate expression
	 *
	 * @see #functionWithinGroup(String, Class, JpaOrder, JpaPredicate, JpaWindow, Expression...)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> percentileDisc(
			Expression<? extends Number> argument,
			JpaPredicate filter,
			JpaWindow window,
			Expression<T> sortExpression,
			SortDirection sortOrder,
			Nulls nullPrecedence);

	/**
	 * @see #rank(JpaOrder, JpaPredicate, JpaWindow, Expression...)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Long> rank(JpaOrder order, Expression<?>... arguments);

	/**
	 * @see #rank(JpaOrder, JpaPredicate, JpaWindow, Expression...)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Long> rank(JpaOrder order, JpaPredicate filter, Expression<?>... arguments);

	/**
	 * @see #rank(JpaOrder, JpaPredicate, JpaWindow, Expression...)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Long> rank(JpaOrder order, JpaWindow window, Expression<?>... arguments);

	/**
	 * Create a {@code rank} ordered set-aggregate function expression.
	 *
	 * @param order order by clause used in within group
	 * @param filter optional filter clause
	 * @param window optional window over which to apply the function
	 * @param arguments arguments to the function
	 *
	 * @return ordered set-aggregate expression
	 *
	 * @see #functionWithinGroup(String, Class, JpaOrder, JpaPredicate, JpaWindow, Expression...)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Long> rank(JpaOrder order, JpaPredicate filter, JpaWindow window, Expression<?>... arguments);

	/**
	 * @see #percentRank(JpaOrder, JpaPredicate, JpaWindow, Expression...)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> percentRank(JpaOrder order, Expression<?>... arguments);

	/**
	 * @see #percentRank(JpaOrder, JpaPredicate, JpaWindow, Expression...)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> percentRank(JpaOrder order, JpaPredicate filter, Expression<?>... arguments);

	/**
	 * @see #percentRank(JpaOrder, JpaPredicate, JpaWindow, Expression...)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> percentRank(JpaOrder order, JpaWindow window, Expression<?>... arguments);

	/**
	 * Create a {@code percent_rank} ordered set-aggregate function expression.
	 *
	 * @param order order by clause used in within group
	 * @param filter optional filter clause
	 * @param window optional window over which to apply the function
	 * @param arguments arguments to the function
	 *
	 * @return ordered set-aggregate expression
	 *
	 * @see #functionWithinGroup(String, Class, JpaOrder, JpaPredicate, JpaWindow, Expression...)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Double> percentRank(
			JpaOrder order,
			JpaPredicate filter,
			JpaWindow window,
			Expression<?>... arguments);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Array functions for array types

	/**
	 * @see #arrayAgg(JpaOrder, JpaPredicate, JpaWindow, Expression)
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayAgg(JpaOrder order, Expression<? extends T> argument);

	/**
	 * @see #arrayAgg(JpaOrder, JpaPredicate, JpaWindow, Expression)
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayAgg(JpaOrder order, JpaPredicate filter, Expression<? extends T> argument);

	/**
	 * @see #arrayAgg(JpaOrder, JpaPredicate, JpaWindow, Expression)
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayAgg(JpaOrder order, JpaWindow window, Expression<? extends T> argument);

	/**
	 * Create a {@code array_agg} ordered set-aggregate function expression.
	 *
	 * @param order order by clause used in within group
	 * @param filter optional filter clause
	 * @param window optional window over which to apply the function
	 * @param argument values to aggregate
	 *
	 * @return ordered set-aggregate expression
	 *
	 * @see #functionWithinGroup(String, Class, JpaOrder, JpaPredicate, JpaWindow, Expression...)
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayAgg(
			JpaOrder order,
			JpaPredicate filter,
			JpaWindow window,
			Expression<? extends T> argument);

	/**
	 * Creates an array literal with the {@code array} constructor function.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayLiteral(T... elements);

	/**
	 * Determines the length of an array.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<Integer> arrayLength(Expression<T[]> arrayExpression);

	/**
	 * Determines the 1-based position of an element in an array.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<Integer> arrayPosition(Expression<T[]> arrayExpression, T element);

	/**
	 * Determines the 1-based position of an element in an array.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<Integer> arrayPosition(Expression<T[]> arrayExpression, Expression<T> elementExpression);

	/**
	 * Determines all 1-based positions of an element in an array.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<int[]> arrayPositions(Expression<T[]> arrayExpression, Expression<T> elementExpression);

	/**
	 * Determines all 1-based positions of an element in an array.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<int[]> arrayPositions(Expression<T[]> arrayExpression, T element);

	/**
	 * Determines all 1-based positions of an element in an array.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<List<Integer>> arrayPositionsList(Expression<T[]> arrayExpression, Expression<T> elementExpression);

	/**
	 * Determines all 1-based positions of an element in an array.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<List<Integer>> arrayPositionsList(Expression<T[]> arrayExpression, T element);

	/**
	 * Concatenates arrays with each other in order.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayConcat(Expression<T[]> arrayExpression1, Expression<T[]> arrayExpression2);

	/**
	 * Concatenates arrays with each other in order.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayConcat(Expression<T[]> arrayExpression1, T[] array2);

	/**
	 * Concatenates arrays with each other in order.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayConcat(T[] array1, Expression<T[]> arrayExpression2);

	/**
	 * Appends element to array.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayAppend(Expression<T[]> arrayExpression, Expression<T> elementExpression);

	/**
	 * Appends element to array.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayAppend(Expression<T[]> arrayExpression, T element);

	/**
	 * Prepends element to array.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayPrepend(Expression<T> elementExpression, Expression<T[]> arrayExpression);

	/**
	 * Prepends element to array.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayPrepend(T element, Expression<T[]> arrayExpression);

	/**
	 * Accesses the element of an array by 1-based index.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> arrayGet(Expression<T[]> arrayExpression, Expression<Integer> indexExpression);

	/**
	 * Accesses the element of an array by 1-based index.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> arrayGet(Expression<T[]> arrayExpression, Integer index);

	/**
	 * Creates array copy with given element at given 1-based index.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arraySet(Expression<T[]> arrayExpression, Expression<Integer> indexExpression, Expression<T> elementExpression);
	/**
	 * Creates array copy with given element at given 1-based index.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arraySet(Expression<T[]> arrayExpression, Expression<Integer> indexExpression, T element);

	/**
	 * Creates array copy with given element at given 1-based index.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arraySet(Expression<T[]> arrayExpression, Integer index, Expression<T> elementExpression);

	/**
	 * Creates array copy with given element at given 1-based index.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arraySet(Expression<T[]> arrayExpression, Integer index, T element);

	/**
	 * Creates array copy with given element removed.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayRemove(Expression<T[]> arrayExpression, Expression<T> elementExpression);

	/**
	 * Creates array copy with given element removed.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayRemove(Expression<T[]> arrayExpression, T element);

	/**
	 * Creates array copy with the element at the given 1-based index removed.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayRemoveIndex(Expression<T[]> arrayExpression, Expression<Integer> indexExpression);

	/**
	 * Creates array copy with the element at the given 1-based index removed.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayRemoveIndex(Expression<T[]> arrayExpression, Integer index);

	/**
	 * Creates a sub-array of the based on 1-based lower and upper index.
	 * Both indexes are inclusive.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arraySlice(Expression<T[]> arrayExpression, Expression<Integer> lowerIndexExpression, Expression<Integer> upperIndexExpression);

	/**
	 * Creates a sub-array of the based on 1-based lower and upper index.
	 * Both indexes are inclusive.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arraySlice(Expression<T[]> arrayExpression, Expression<Integer> lowerIndexExpression, Integer upperIndex);

	/**
	 * Creates a sub-array of the based on 1-based lower and upper index.
	 * Both indexes are inclusive.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arraySlice(Expression<T[]> arrayExpression, Integer lowerIndex, Expression<Integer> upperIndexExpression);

	/**
	 * Creates a sub-array of the based on 1-based lower and upper index.
	 * Both indexes are inclusive.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arraySlice(Expression<T[]> arrayExpression, Integer lowerIndex, Integer upperIndex);

	/**
	 * Creates array copy replacing a given element with another.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayReplace(Expression<T[]> arrayExpression, Expression<T> oldElementExpression, Expression<T> newElementExpression);

	/**
	 * Creates array copy replacing a given element with another.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayReplace(Expression<T[]> arrayExpression, Expression<T> oldElementExpression, T newElement);

	/**
	 * Creates array copy replacing a given element with another.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayReplace(Expression<T[]> arrayExpression, T oldElement, Expression<T> newElementExpression);

	/**
	 * Creates array copy replacing a given element with another.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayReplace(Expression<T[]> arrayExpression, T oldElement, T newElement);

	/**
	 * Creates array copy without the last N elements, specified by the second argument.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayTrim(Expression<T[]> arrayExpression, Expression<Integer> elementCountExpression);

	/**
	 * Creates array copy without the last N elements, specified by the second argument.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayTrim(Expression<T[]> arrayExpression, Integer elementCount);

	/**
	 * Reverses the order of elements in an array.
	 *
	 * @since 7.2
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayReverse(Expression<T[]> arrayExpression);

	/**
	 * Sorts the elements of an array in ascending order.
	 *
	 * @since 7.2
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arraySort(Expression<T[]> arrayExpression);

	/**
	 * Sorts the elements of an array in the specified order.
	 *
	 * @since 7.2
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arraySort(Expression<T[]> arrayExpression, boolean descending);

	/**
	 * Sorts the elements of an array in the specified order.
	 *
	 * @since 7.2
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arraySort(Expression<T[]> arrayExpression, Expression<Boolean> descendingExpression);

	/**
	 * Create an expression that sorts the given array with explicit null ordering.
	 *
	 * @since 7.2
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arraySort(Expression<T[]> arrayExpression, boolean descending, boolean nullsFirst);

	/**
	 * Create an expression that sorts the given array with explicit null ordering.
	 *
	 * @since 7.2
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arraySort(Expression<T[]> arrayExpression, Expression<Boolean> descendingExpression, Expression<Boolean> nullsFirstExpression);

	/**
	 * Creates array with the same element N times, as specified by the arguments.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayFill(Expression<T> elementExpression, Expression<Integer> elementCountExpression);

	/**
	 * Creates array with the same element N times, as specified by the arguments.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayFill(Expression<T> elementExpression, Integer elementCount);

	/**
	 * Creates array with the same element N times, as specified by the arguments.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayFill(T element, Expression<Integer> elementCountExpression);

	/**
	 * Creates array with the same element N times, as specified by the arguments.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T[]> arrayFill(T element, Integer elementCount);

	/**
	 * Concatenates the non-null array elements with a separator, as specified by the arguments.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> arrayToString(Expression<? extends Object[]> arrayExpression, Expression<String> separatorExpression);

	/**
	 * Concatenates the non-null array elements with a separator, as specified by the arguments.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> arrayToString(Expression<? extends Object[]> arrayExpression, String separator);

	/**
	 * Concatenates the array elements with a separator, as specified by the arguments. Null array elements are replaced
	 * with the given default element.
	 *
	 * @since 7.1
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> arrayToString(Expression<? extends Object[]> arrayExpression, Expression<String> separatorExpression, Expression<String> defaultExpression);

	/**
	 * Concatenates the array elements with a separator, as specified by the arguments. Null array elements are replaced
	 * with the given default element.
	 *
	 * @since 7.1
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> arrayToString(Expression<? extends Object[]> arrayExpression, Expression<String> separatorExpression, String defaultValue);

	/**
	 * Concatenates the array elements with a separator, as specified by the arguments. Null array elements are replaced
	 * with the given default element.
	 *
	 * @since 7.1
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> arrayToString(Expression<? extends Object[]> arrayExpression, String separator, Expression<String> defaultExpression);

	/**
	 * Concatenates the array elements with a separator, as specified by the arguments. Null array elements are replaced
	 * with the given default element.
	 *
	 * @since 7.1
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> arrayToString(Expression<? extends Object[]> arrayExpression, String separator, String defaultValue);

	/**
	 * Whether an array contains an element.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaPredicate arrayContains(Expression<T[]> arrayExpression, Expression<T> elementExpression);

	/**
	 * Whether an array contains an element.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaPredicate arrayContains(Expression<T[]> arrayExpression, T element);

	/**
	 * Whether an array contains an element.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaPredicate arrayContains(T[] array, Expression<T> elementExpression);

	/**
	 * Whether an array contains a nullable element.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaPredicate arrayContainsNullable(Expression<T[]> arrayExpression, Expression<T> elementExpression);

	/**
	 * Whether an array contains a nullable element.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaPredicate arrayContainsNullable(Expression<T[]> arrayExpression, T element);

	/**
	 * Whether an array contains a nullable element.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaPredicate arrayContainsNullable(T[] array, Expression<T> elementExpression);

	/**
	 * Whether an array is a subset of another array.
	 *
	 * @since 6.6
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaPredicate arrayIncludes(Expression<T[]> arrayExpression, Expression<T[]> subArrayExpression);

	/**
	 * Whether an array is a subset of another array.
	 *
	 * @since 6.6
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaPredicate arrayIncludes(Expression<T[]> arrayExpression, T[] subArray);

	/**
	 * Whether an array is a subset of another array.
	 *
	 * @since 6.6
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaPredicate arrayIncludes(T[] array, Expression<T[]> subArrayExpression);

	/**
	 * Whether an array is a subset of another array with nullable elements.
	 *
	 * @since 6.6
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaPredicate arrayIncludesNullable(Expression<T[]> arrayExpression, Expression<T[]> subArrayExpression);

	/**
	 * Whether an array is a subset of another array with nullable elements.
	 *
	 * @since 6.6
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaPredicate arrayIncludesNullable(Expression<T[]> arrayExpression, T[] subArray);

	/**
	 * Whether an array is a subset of another array with nullable elements.
	 *
	 * @since 6.6
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaPredicate arrayIncludesNullable(T[] array, Expression<T[]> subArrayExpression);

	/**
	 * Whether one array has any elements common with another array.
	 *
	 * @since 6.6
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaPredicate arrayIntersects(Expression<T[]> arrayExpression1, Expression<T[]> arrayExpression2);

	/**
	 * Whether one array has any elements common with another array.
	 *
	 * @since 6.6
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaPredicate arrayIntersects(Expression<T[]> arrayExpression1, T[] array2);

	/**
	 * Whether one array has any elements common with another array.
	 *
	 * @since 6.6
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaPredicate arrayIntersects(T[] array1, Expression<T[]> arrayExpression2);

	/**
	 * Whether one array has any elements common with another array, supporting {@code null} elements.
	 *
	 * @since 6.6
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaPredicate arrayIntersectsNullable(Expression<T[]> arrayExpression1, Expression<T[]> arrayExpression2);

	/**
	 * Whether one array has any elements common with another array, supporting {@code null} elements.
	 *
	 * @since 6.6
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaPredicate arrayIntersectsNullable(Expression<T[]> arrayExpression1, T[] array2);

	/**
	 * Whether one array has any elements common with another array, supporting {@code null} elements.
	 *
	 * @since 6.6
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaPredicate arrayIntersectsNullable(T[] array1, Expression<T[]> arrayExpression2);

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Array functions for collection types

	/**
	 * Creates a basic collection literal with the {@code array} constructor function.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<E>> JpaExpression<C> collectionLiteral(E... elements);

	/**
	 * Determines the length of a basic collection.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Integer> collectionLength(Expression<? extends Collection<?>> collectionExpression);

	/**
	 * Determines the 1-based position of an element in a basic collection.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaExpression<Integer> collectionPosition(Expression<? extends Collection<? extends E>> collectionExpression, E element);

	/**
	 * Determines the 1-based position of an element in a basic collection.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaExpression<Integer> collectionPosition(Expression<? extends Collection<? extends E>> collectionExpression, Expression<E> elementExpression);

	/**
	 * Determines all 1-based positions of an element in a basic collection.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<int[]> collectionPositions(Expression<? extends Collection<? super T>> collectionExpression, Expression<T> elementExpression);

	/**
	 * Determines all 1-based positions of an element in a basic collection.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<int[]> collectionPositions(Expression<? extends Collection<? super T>> collectionExpression, T element);

	/**
	 * Determines all 1-based positions of an element in a basic collection.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<List<Integer>> collectionPositionsList(Expression<? extends Collection<? super T>> collectionExpression, Expression<T> elementExpression);

	/**
	 * Determines all 1-based positions of an element in a basic collection.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<List<Integer>> collectionPositionsList(Expression<? extends Collection<? super T>> collectionExpression, T element);

	/**
	 * Concatenates basic collections with each other in order.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> JpaExpression<C> collectionConcat(Expression<C> collectionExpression1, Expression<? extends Collection<? extends E>> collectionExpression2);

	/**
	 * Concatenates basic collections with each other in order.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> JpaExpression<C> collectionConcat(Expression<C> collectionExpression1, Collection<? extends E> collection2);

	/**
	 * Concatenates basic collections with each other in order.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> JpaExpression<C> collectionConcat(C collection1, Expression<? extends Collection<? extends E>> collectionExpression2);

	/**
	 * Appends element to basic collection.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> JpaExpression<C> collectionAppend(Expression<C> collectionExpression, Expression<? extends E> elementExpression);

	/**
	 * Appends element to basic collection.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> JpaExpression<C> collectionAppend(Expression<C> collectionExpression, E element);

	/**
	 * Prepends element to basic collection.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> JpaExpression<C> collectionPrepend(Expression<? extends E> elementExpression, Expression<C> collectionExpression);

	/**
	 * Prepends element to basic collection.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> JpaExpression<C> collectionPrepend(E element, Expression<C> collectionExpression);

	/**
	 * Accesses the element of the basic collection by 1-based index.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaExpression<E> collectionGet(Expression<? extends Collection<E>> collectionExpression, Expression<Integer> indexExpression);

	/**
	 * Accesses the element of the basic collection by 1-based index.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaExpression<E> collectionGet(Expression<? extends Collection<E>> collectionExpression, Integer index);

	/**
	 * Creates basic collection copy with given element at given 1-based index.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> JpaExpression<C> collectionSet(Expression<C> collectionExpression, Expression<Integer> indexExpression, Expression<? extends E> elementExpression);

	/**
	 * Creates basic collection copy with given element at given 1-based index.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> JpaExpression<C> collectionSet(Expression<C> collectionExpression, Expression<Integer> indexExpression, E element);

	/**
	 * Creates basic collection copy with given element at given 1-based index.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> JpaExpression<C> collectionSet(Expression<C> collectionExpression, Integer index, Expression<? extends E> elementExpression);

	/**
	 * Creates basic collection copy with given element at given 1-based index.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> JpaExpression<C> collectionSet(Expression<C> collectionExpression, Integer index, E element);

	/**
	 * Creates basic collection copy with given element removed.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> JpaExpression<C> collectionRemove(Expression<C> collectionExpression, Expression<? extends E> elementExpression);

	/**
	 * Creates basic collection copy with given element removed.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> JpaExpression<C> collectionRemove(Expression<C> collectionExpression, E element);

	/**
	 * Creates basic collection copy with the element at the given 1-based index removed.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> JpaExpression<C> collectionRemoveIndex(Expression<C> collectionExpression, Expression<Integer> indexExpression);

	/**
	 * Creates basic collection copy with the element at the given 1-based index removed.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> JpaExpression<C> collectionRemoveIndex(Expression<C> collectionExpression, Integer index);

	/**
	 * Creates a sub-collection of the based on 1-based lower and upper index.
	 * Both indexes are inclusive.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> JpaExpression<C> collectionSlice(Expression<C> collectionExpression, Expression<Integer> lowerIndexExpression, Expression<Integer> upperIndexExpression);

	/**
	 * Creates a sub-collection of the based on 1-based lower and upper index.
	 * Both indexes are inclusive.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> JpaExpression<C> collectionSlice(Expression<C> collectionExpression, Expression<Integer> lowerIndexExpression, Integer upperIndex);

	/**
	 * Creates a sub-collection of the based on 1-based lower and upper index.
	 * Both indexes are inclusive.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> JpaExpression<C> collectionSlice(Expression<C> collectionExpression, Integer lowerIndex, Expression<Integer> upperIndexExpression);

	/**
	 * Creates a sub-collection of the based on 1-based lower and upper index.
	 * Both indexes are inclusive.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> JpaExpression<C> collectionSlice(Expression<C> collectionExpression, Integer lowerIndex, Integer upperIndex);

	/**
	 * Creates basic collection copy replacing a given element with another.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> JpaExpression<C> collectionReplace(Expression<C> collectionExpression, Expression<? extends E> oldElementExpression, Expression<? extends E> newElementExpression);

	/**
	 * Creates basic collection copy replacing a given element with another.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> JpaExpression<C> collectionReplace(Expression<C> collectionExpression, Expression<? extends E> oldElementExpression, E newElement);

	/**
	 * Creates basic collection copy replacing a given element with another.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> JpaExpression<C> collectionReplace(Expression<C> collectionExpression, E oldElement, Expression<? extends E> newElementExpression);

	/**
	 * Creates basic collection copy replacing a given element with another.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> JpaExpression<C> collectionReplace(Expression<C> collectionExpression, E oldElement, E newElement);

	/**
	 * Creates basic collection copy without the last N elements, specified by the second argument.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> JpaExpression<C> collectionTrim(Expression<C> arrayExpression, Expression<Integer> elementCountExpression);

	/**
	 * Creates basic collection copy without the last N elements, specified by the second argument.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> JpaExpression<C> collectionTrim(Expression<C> arrayExpression, Integer elementCount);

	/**
	 * Create an expression that reverses the order of the elements of a collection.
	 *
	 * @since 7.2
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> JpaExpression<C> collectionReverse(Expression<C> collectionExpression);

	/**
	 * Create an expression that sorts the elements of a collection.
	 *
	 * @since 7.2
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> JpaExpression<C> collectionSort(Expression<C> collectionExpression);

	/**
	 * Create an expression that sorts the given collection in specified order.
	 *
	 * @since 7.2
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> JpaExpression<C> collectionSort(Expression<C> collectionExpression, boolean descending);

	/**
	 * Create an expression that sorts the given collection in specified order.
	 *
	 * @since 7.2
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> JpaExpression<C> collectionSort(
			Expression<C> collectionExpression,
			Expression<Boolean> descendingExpression);

	/**
	 * Create an expression that sorts the given collection with explicit null ordering.
	 *
	 * @since 7.2
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> JpaExpression<C> collectionSort(
			Expression<C> collectionExpression,
			boolean descending,
			boolean nullsFirst);

	/**
	 * Create an expression that sorts the given collection with explicit null ordering.
	 *
	 * @since 7.2
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> JpaExpression<C> collectionSort(
			Expression<C> collectionExpression,
			Expression<Boolean> descendingExpression,
			Expression<Boolean> nullsFirstExpression);

	/**
	 * Creates basic collection with the same element N times, as specified by the arguments.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<Collection<T>> collectionFill(Expression<T> elementExpression, Expression<Integer> elementCountExpression);

	/**
	 * Creates basic collection with the same element N times, as specified by the arguments.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<Collection<T>> collectionFill(Expression<T> elementExpression, Integer elementCount);

	/**
	 * Creates basic collection with the same element N times, as specified by the arguments.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<Collection<T>> collectionFill(T element, Expression<Integer> elementCountExpression);

	/**
	 * Creates basic collection with the same element N times, as specified by the arguments.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<Collection<T>> collectionFill(T element, Integer elementCount);

	/**
	 * Concatenates the non-null basic collection elements with a separator, as specified by the arguments.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> collectionToString(Expression<? extends Collection<?>> collectionExpression, Expression<String> separatorExpression);

	/**
	 * Concatenates the non-null basic collection elements with a separator, as specified by the arguments.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> collectionToString(Expression<? extends Collection<?>> collectionExpression, String separator);

	/**
	 * Concatenates the collection elements with a separator, as specified by the arguments. Null collection elements
	 * are replaced with the given default element.
	 *
	 * @since 7.1
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> collectionToString(Expression<? extends Collection<?>> collectionExpression, Expression<String> separatorExpression, Expression<String> defaultExpression);

	/**
	 * Concatenates the collection elements with a separator, as specified by the arguments. Null collection elements
	 * are replaced with the given default element.
	 *
	 * @since 7.1
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> collectionToString(Expression<? extends Collection<?>> collectionExpression, Expression<String> separatorExpression, String defaultValue);

	/**
	 * Concatenates the collection elements with a separator, as specified by the arguments. Null collection elements
	 * are replaced with the given default element.
	 *
	 * @since 7.1
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> collectionToString(Expression<? extends Collection<?>> collectionExpression, String separator, Expression<String> defaultExpression);

	/**
	 * Concatenates the collection elements with a separator, as specified by the arguments. Null collection elements
	 * are replaced with the given default element.
	 *
	 * @since 7.1
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> collectionToString(Expression<? extends Collection<?>> collectionExpression, String separator, String defaultValue);

	/**
	 * Whether a basic collection contains an element.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaPredicate collectionContains(Expression<? extends Collection<E>> collectionExpression, Expression<? extends E> elementExpression);

	/**
	 * Whether a basic collection contains an element.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaPredicate collectionContains(Expression<? extends Collection<E>> collectionExpression, E element);

	/**
	 * Whether a basic collection contains an element.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaPredicate collectionContains(Collection<E> collection, Expression<E> elementExpression);

	/**
	 * Whether a basic collection contains a nullable element.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaPredicate collectionContainsNullable(Expression<? extends Collection<E>> collectionExpression, Expression<? extends E> elementExpression);

	/**
	 * Whether a basic collection contains a nullable element.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaPredicate collectionContainsNullable(Expression<? extends Collection<E>> collectionExpression, E element);

	/**
	 * Whether a basic collection contains a nullable element.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaPredicate collectionContainsNullable(Collection<E> collection, Expression<E> elementExpression);

	/**
	 * Whether a basic collection is a subset of another basic collection.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaPredicate collectionIncludes(Expression<? extends Collection<E>> collectionExpression, Expression<? extends Collection<? extends E>> subCollectionExpression);

	/**
	 * Whether a basic collection is a subset of another basic collection.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaPredicate collectionIncludes(Expression<? extends Collection<E>> collectionExpression, Collection<? extends E> subCollection);

	/**
	 * Whether a basic collection is a subset of another basic collection.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaPredicate collectionIncludes(Collection<E> collection, Expression<? extends Collection<? extends E>> subCollectionExpression);

	/**
	 * Whether a basic collection is a subset of another basic collection with nullable elements.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaPredicate collectionIncludesNullable(Expression<? extends Collection<E>> collectionExpression, Expression<? extends Collection<? extends E>> subCollectionExpression);

	/**
	 * Whether a basic collection is a subset of another basic collection with nullable elements.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaPredicate collectionIncludesNullable(Expression<? extends Collection<E>> collectionExpression, Collection<? extends E> subCollection);

	/**
	 * Whether a basic collection is a subset of another basic collection with nullable elements.
	 *
	 * @since 6.4
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaPredicate collectionIncludesNullable(Collection<E> collection, Expression<? extends Collection<? extends E>> subCollectionExpression);

	/**
	 * Whether one basic collection has any elements common with another basic collection.
	 *
	 * @since 6.6
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaPredicate collectionIntersects(Expression<? extends Collection<E>> collectionExpression1, Expression<? extends Collection<? extends E>> collectionExpression2);

	/**
	 * Whether one basic collection has any elements common with another basic collection.
	 *
	 * @since 6.6
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaPredicate collectionIntersects(Expression<? extends Collection<E>> collectionExpression1, Collection<? extends E> collection2);

	/**
	 * Whether one basic collection has any elements common with another basic collection.
	 *
	 * @since 6.6
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaPredicate collectionIntersects(Collection<E> collection1, Expression<? extends Collection<? extends E>> collectionExpression2);

	/**
	 * Whether one basic collection has any elements common with another basic collection, supporting {@code null} elements.
	 *
	 * @since 6.6
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaPredicate collectionIntersectsNullable(Expression<? extends Collection<E>> collectionExpression1, Expression<? extends Collection<? extends E>> collectionExpression2);

	/**
	 * Whether one basic collection has any elements common with another basic collection, supporting {@code null} elements.
	 *
	 * @since 6.6
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaPredicate collectionIntersectsNullable(Expression<? extends Collection<E>> collectionExpression1, Collection<? extends E> collection2);

	/**
	 * Whether one basic collection has any elements common with another basic collection, supporting {@code null} elements.
	 *
	 * @since 6.6
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaPredicate collectionIntersectsNullable(Collection<E> collection1, Expression<? extends Collection<? extends E>> collectionExpression2);

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// JSON functions

	/**
	 * @see #jsonValue(Expression, String, Class)
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonValueExpression<String> jsonValue(Expression<?> jsonDocument, String jsonPath);

	/**
	 * Extracts a value by JSON path from a JSON document.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaJsonValueExpression<T> jsonValue(Expression<?> jsonDocument, String jsonPath, Class<T> returningType);

	/**
	 * @see #jsonValue(Expression, Expression, Class)
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonValueExpression<String> jsonValue(Expression<?> jsonDocument, Expression<String> jsonPath);

	/**
	 * Extracts a value by JSON path from a JSON document.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaJsonValueExpression<T> jsonValue(Expression<?> jsonDocument, Expression<String> jsonPath, Class<T> returningType);

	/**
	 * @see #jsonQuery(Expression, Expression)
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonQueryExpression jsonQuery(Expression<?> jsonDocument, String jsonPath);

	/**
	 * Queries values by JSON path from a JSON document.
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonQueryExpression jsonQuery(Expression<?> jsonDocument, Expression<String> jsonPath);

	/**
	 * Checks if a JSON document contains a node for the given JSON path.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonExistsExpression jsonExists(Expression<?> jsonDocument, String jsonPath);

	/**
	 * Checks if a JSON document contains a node for the given JSON path.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonExistsExpression jsonExists(Expression<?> jsonDocument, Expression<String> jsonPath);

	/**
	 * Create a JSON object from the given map of key values.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonObject(Map<?, ? extends Expression<?>> keyValues);

	/**
	 * Create a JSON object from the given map of key values, retaining {@code null} values in the JSON.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonObjectWithNulls(Map<?, ? extends Expression<?>> keyValues);

	/**
	 * Create a JSON array from the array of values.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonArray(Expression<?>... values);

	/**
	 * Create a JSON object from the given array of values, retaining {@code null} values in the JSON array.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonArrayWithNulls(Expression<?>... values);

	/**
	 * Aggregates the given value into a JSON array.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonArrayAgg(Expression<?> value);

	/**
	 * Aggregates the given value into a JSON array.
	 * Ordering values based on the given order by items.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonArrayAgg(Expression<?> value, JpaOrder... orderBy);

	/**
	 * Aggregates the given value into a JSON array.
	 * Filtering rows that don't match the given filter predicate.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonArrayAgg(Expression<?> value, Predicate filter);

	/**
	 * Aggregates the given value into a JSON array.
	 * Filtering rows that don't match the given filter predicate.
	 * Ordering values based on the given order by items.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonArrayAgg(Expression<?> value, Predicate filter, JpaOrder... orderBy);

	/**
	 * Aggregates the given value into a JSON array, retaining {@code null} values in the JSON array.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonArrayAggWithNulls(Expression<?> value);

	/**
	 * Aggregates the given value into a JSON array, retaining {@code null} values in the JSON array.
	 * Ordering values based on the given order by items.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonArrayAggWithNulls(Expression<?> value, JpaOrder... orderBy);

	/**
	 * Aggregates the given value into a JSON array, retaining {@code null} values in the JSON array.
	 * Filtering rows that don't match the given filter predicate.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonArrayAggWithNulls(Expression<?> value, Predicate filter);

	/**
	 * Aggregates the given value into a JSON array, retaining {@code null} values in the JSON array.
	 * Filtering rows that don't match the given filter predicate.
	 * Ordering values based on the given order by items.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonArrayAggWithNulls(Expression<?> value, Predicate filter, JpaOrder... orderBy);

	/**
	 * Aggregates the given value under the given key into a JSON object.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonObjectAgg(Expression<?> key, Expression<?> value);

	/**
	 * Aggregates the given value under the given key into a JSON object, retaining {@code null} values in the JSON object.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonObjectAggWithNulls(Expression<?> key, Expression<?> value);

	/**
	 * Aggregates the given value under the given key into a JSON object.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonObjectAggWithUniqueKeys(Expression<?> key, Expression<?> value);

	/**
	 * Aggregates the given value under the given key into a JSON object, retaining {@code null} values in the JSON object.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonObjectAggWithUniqueKeysAndNulls(Expression<?> key, Expression<?> value);

	/**
	 * Aggregates the given value under the given key into a JSON object.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonObjectAgg(Expression<?> key, Expression<?> value, Predicate filter);

	/**
	 * Aggregates the given value under the given key into a JSON object, retaining {@code null} values in the JSON object.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonObjectAggWithNulls(Expression<?> key, Expression<?> value, Predicate filter);

	/**
	 * Aggregates the given value under the given key into a JSON object.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonObjectAggWithUniqueKeys(Expression<?> key, Expression<?> value, Predicate filter);

	/**
	 * Aggregates the given value under the given key into a JSON object, retaining {@code null} values in the JSON object.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonObjectAggWithUniqueKeysAndNulls(Expression<?> key, Expression<?> value, Predicate filter);

	/**
	 * Inserts/Replaces a value by JSON path within a JSON document.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonSet(Expression<?> jsonDocument, String jsonPath, Expression<?> value);

	/**
	 * Inserts/Replaces a value by JSON path within a JSON document.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonSet(Expression<?> jsonDocument, Expression<String> jsonPath, Expression<?> value);

	/**
	 * Inserts/Replaces a value by JSON path within a JSON document.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonSet(Expression<?> jsonDocument, String jsonPath, Object value);

	/**
	 * Inserts/Replaces a value by JSON path within a JSON document.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonSet(Expression<?> jsonDocument, Expression<String> jsonPath, Object value);

	/**
	 * Removes a value by JSON path within a JSON document.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonRemove(Expression<?> jsonDocument, String jsonPath);

	/**
	 * Removes a value by JSON path within a JSON document.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonRemove(Expression<?> jsonDocument, Expression<String> jsonPath);

	/**
	 * Inserts a value by JSON path within a JSON document.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonInsert(Expression<?> jsonDocument, String jsonPath, Expression<?> value);

	/**
	 * Inserts a value by JSON path within a JSON document.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonInsert(Expression<?> jsonDocument, Expression<String> jsonPath, Expression<?> value);

	/**
	 * Inserts a value by JSON path within a JSON document.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonInsert(Expression<?> jsonDocument, String jsonPath, Object value);

	/**
	 * Inserts a value by JSON path within a JSON document.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonInsert(Expression<?> jsonDocument, Expression<String> jsonPath, Object value);

	/**
	 * Replaces a value by JSON path within a JSON document.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonReplace(Expression<?> jsonDocument, String jsonPath, Expression<?> value);

	/**
	 * Replaces a value by JSON path within a JSON document.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonReplace(Expression<?> jsonDocument, Expression<String> jsonPath, Expression<?> value);

	/**
	 * Replaces a value by JSON path within a JSON document.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonReplace(Expression<?> jsonDocument, String jsonPath, Object value);

	/**
	 * Replaces a value by JSON path within a JSON document.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonReplace(Expression<?> jsonDocument, Expression<String> jsonPath, Object value);

	/**
	 * Applies the patch JSON document onto the other JSON document and returns that.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonMergepatch(Expression<?> document, Expression<?> patch);

	/**
	 * Applies the patch JSON document onto the other JSON document and returns that.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonMergepatch(Expression<?> document, String patch);

	/**
	 * Applies the patch JSON document onto the other JSON document and returns that.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> jsonMergepatch(String document, Expression<?> patch);

	/**
	 * Creates an XML element with the given element name.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaXmlElementExpression xmlelement(String elementName);

	/**
	 * Creates an XML comment with the given argument as content.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> xmlcomment(String comment);

	/**
	 * Creates an XML forest from the given XML element expressions.
	 *
	 * @since 7.0
	 * @see #named(Expression, String)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> xmlforest(Expression<?>... elements);

	/**
	 * Creates an XML forest from the given XML element expressions.
	 *
	 * @since 7.0
	 * @see #named(Expression, String)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> xmlforest(List<? extends Expression<?>> elements);

	/**
	 * Concatenates the given XML element expressions.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> xmlconcat(Expression<?>... elements);

	/**
	 * Concatenates the given XML element expressions.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> xmlconcat(List<? extends Expression<?>> elements);

	/**
	 * Creates an XML processing with the given name.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> xmlpi(String elementName);

	/**
	 * Creates an XML processing with the given name and content.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> xmlpi(String elementName, Expression<String> content);

	/**
	 * Queries the given XML document with the given XPath or XQuery query.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> xmlquery(String query, Expression<?> xmlDocument);

	/**
	 * Queries the given XML document with the given XPath or XQuery query.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> xmlquery(Expression<String> query, Expression<?> xmlDocument);

	/**
	 * Checks if the given XPath or XQuery query exists in the given XML document.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Boolean> xmlexists(String query, Expression<?> xmlDocument);

	/**
	 * Checks if the given XPath or XQuery query exists in the given XML document.
	 *
	 * @since 7.0
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Boolean> xmlexists(Expression<String> query, Expression<?> xmlDocument);

	/**
	 * @see #xmlagg(JpaOrder, JpaPredicate, JpaWindow, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> xmlagg(JpaOrder order, Expression<?> argument);

	/**
	 * @see #xmlagg(JpaOrder, JpaPredicate, JpaWindow, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> xmlagg(JpaOrder order, JpaPredicate filter, Expression<?> argument);

	/**
	 * @see #xmlagg(JpaOrder, JpaPredicate, JpaWindow, Expression)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> xmlagg(JpaOrder order, JpaWindow window, Expression<?> argument);

	/**
	 * Create a {@code xmlagg} ordered set-aggregate function expression.
	 *
	 * @param order order by clause used in within group
	 * @param filter optional filter clause
	 * @param window optional window over which to apply the function
	 * @param argument values to join
	 *
	 * @return ordered set-aggregate expression
	 *
	 * @see #functionWithinGroup(String, Class, JpaOrder, JpaPredicate, JpaWindow, Expression...)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> xmlagg(
			JpaOrder order,
			JpaPredicate filter,
			JpaWindow window,
			Expression<?> argument);

	/**
	 * Creates a named expression. The name is important for the result of the expression,
	 * e.g. when building an {@code xmlforest}, the name acts as the XML element name.
	 *
	 * @since 7.0
	 * @see #xmlforest(Expression[])
	 * @see #xmlforest(List)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaExpression<T> named(Expression<T> expression, String name);

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Set-Returning functions

	/**
	 * Create a new set-returning function expression.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaSetReturningFunction<E> setReturningFunction(String name, Expression<?>... args);

	/**
	 * Creates an unnest function expression to turn an array into a set of rows.
	 *
	 * @since 7.0
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaSetReturningFunction<E> unnestArray(Expression<E[]> array);

	/**
	 * Creates an unnest function expression to turn an array into a set of rows.
	 *
	 * @since 7.0
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> JpaSetReturningFunction<E> unnestCollection(Expression<? extends Collection<E>> collection);

	/**
	 * Creates a {@code generate_series} function expression to generate a set of values as rows.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> JpaSetReturningFunction<E> generateSeries(E start, E stop);

	/**
	 * Creates a {@code generate_series} function expression to generate a set of values as rows.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> JpaSetReturningFunction<E> generateSeries(E start, Expression<E> stop);

	/**
	 * Creates a {@code generate_series} function expression to generate a set of values as rows.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> JpaSetReturningFunction<E> generateSeries(Expression<E> start, E stop);

	/**
	 * Creates a {@code generate_series} function expression to generate a set of values as rows.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> JpaSetReturningFunction<E> generateSeries(Expression<E> start, Expression<E> stop);

	/**
	 * Creates a {@code generate_series} function expression to generate a set of values as rows.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> JpaSetReturningFunction<E> generateSeries(E start, Expression<E> stop, Expression<E> step);

	/**
	 * Creates a {@code generate_series} function expression to generate a set of values as rows.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> JpaSetReturningFunction<E> generateSeries(Expression<E> start, E stop, Expression<E> step);

	/**
	 * Creates a {@code generate_series} function expression to generate a set of values as rows.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> JpaSetReturningFunction<E> generateSeries(Expression<E> start, Expression<E> stop, E step);

	/**
	 * Creates a {@code generate_series} function expression to generate a set of values as rows.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> JpaSetReturningFunction<E> generateSeries(E start, Expression<E> stop, E step);

	/**
	 * Creates a {@code generate_series} function expression to generate a set of values as rows.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> JpaSetReturningFunction<E> generateSeries(Expression<E> start, E stop, E step);

	/**
	 * Creates a {@code generate_series} function expression to generate a set of values as rows.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> JpaSetReturningFunction<E> generateSeries(E start, E stop, Expression<E> step);

	/**
	 * Creates a {@code generate_series} function expression to generate a set of values as rows.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> JpaSetReturningFunction<E> generateSeries(E start, E stop, E step);

	/**
	 * Creates a {@code generate_series} function expression to generate a set of values as rows.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> JpaSetReturningFunction<E> generateSeries(Expression<E> start, Expression<E> stop, Expression<E> step);

	/**
	 * Creates a {@code generate_series} function expression to generate a set of values as rows.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Temporal> JpaSetReturningFunction<E> generateTimeSeries(E start, Expression<E> stop, Expression<? extends TemporalAmount> step);

	/**
	 * Creates a {@code generate_series} function expression to generate a set of values as rows.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Temporal> JpaSetReturningFunction<E> generateTimeSeries(Expression<E> start, E stop, Expression<? extends TemporalAmount> step);

	/**
	 * Creates a {@code generate_series} function expression to generate a set of values as rows.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Temporal> JpaSetReturningFunction<E> generateTimeSeries(E start, E stop, Expression<? extends TemporalAmount> step);

	/**
	 * Creates a {@code generate_series} function expression to generate a set of values as rows.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Temporal> JpaSetReturningFunction<E> generateTimeSeries(Expression<E> start, Expression<E> stop, TemporalAmount step);

	/**
	 * Creates a {@code generate_series} function expression to generate a set of values as rows.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Temporal> JpaSetReturningFunction<E> generateTimeSeries(Expression<E> start, E stop, TemporalAmount step);

	/**
	 * Creates a {@code generate_series} function expression to generate a set of values as rows.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Temporal> JpaSetReturningFunction<E> generateTimeSeries(E start, Expression<E> stop, TemporalAmount step);

	/**
	 * Creates a {@code generate_series} function expression to generate a set of values as rows.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Temporal> JpaSetReturningFunction<E> generateTimeSeries(E start, E stop, TemporalAmount step);

	/**
	 * Creates a {@code generate_series} function expression to generate a set of values as rows.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Temporal> JpaSetReturningFunction<E> generateTimeSeries(Expression<E> start, Expression<E> stop, Expression<? extends TemporalAmount> step);

	/**
	 * Creates a {@code json_table} function expression to generate rows from JSON array elements.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonTableFunction jsonTable(Expression<?> jsonDocument);

	/**
	 * Creates a {@code json_table} function expression to generate rows from JSON array elements.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonTableFunction jsonTable(Expression<?> jsonDocument, String jsonPath);

	/**
	 * Creates a {@code json_table} function expression to generate rows from JSON array elements.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonTableFunction jsonTable(Expression<?> jsonDocument, Expression<String> jsonPath);

	/**
	 * Creates a {@code xmltable} function expression to generate rows from XML elements.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaXmlTableFunction xmlTable(String xpath, Expression<?> xmlDocument);

	/**
	 * Creates a {@code xmltable} function expression to generate rows from XML elements.
	 *
	 * @since 7.0
	 * @see JpaSelectCriteria#from(JpaSetReturningFunction)
	 * @see JpaFrom#join(JpaSetReturningFunction)
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaXmlTableFunction xmlTable(Expression<String> xpath, Expression<?> xmlDocument);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<String> concat(@Nonnull List<Expression<String>> expressions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N, T extends Temporal> JpaExpression<N> extract(@Nonnull TemporalField<N, T> field, @Nonnull Expression<T> temporal);
}
