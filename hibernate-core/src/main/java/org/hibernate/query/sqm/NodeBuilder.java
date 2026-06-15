/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.BooleanExpression;
import org.hibernate.jpa.spi.JpaCompliance;
import org.hibernate.metamodel.model.domain.JpaMetamodel;
import org.hibernate.query.spi.ImmutableEntityUpdateQueryHandlingMode;
import org.hibernate.query.SortDirection;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCastTarget;
import org.hibernate.query.criteria.JpaCoalesce;
import org.hibernate.query.criteria.JpaCompoundSelection;
import org.hibernate.query.criteria.JpaExpression;
import org.hibernate.query.criteria.JpaOrder;
import org.hibernate.query.criteria.JpaParameterExpression;
import org.hibernate.query.criteria.JpaPredicate;
import org.hibernate.query.criteria.JpaSearchedCase;
import org.hibernate.query.criteria.JpaSimpleCase;
import org.hibernate.query.criteria.JpaWindow;
import org.hibernate.query.sqm.spi.SqmCreationContext;
import org.hibernate.query.sqm.tree.delete.SqmDeleteStatement;
import org.hibernate.query.sqm.tree.domain.SqmBagJoin;
import org.hibernate.query.sqm.tree.domain.SqmListJoin;
import org.hibernate.query.sqm.tree.domain.SqmMapJoin;
import org.hibernate.query.sqm.tree.domain.SqmPath;
import org.hibernate.query.sqm.tree.domain.SqmSetJoin;
import org.hibernate.query.sqm.tree.domain.SqmSingularJoin;
import org.hibernate.query.sqm.tree.expression.SqmCastTarget;
import org.hibernate.query.sqm.tree.expression.SqmExpression;
import org.hibernate.query.sqm.tree.expression.SqmFunction;
import org.hibernate.query.sqm.tree.expression.SqmJsonExistsExpression;
import org.hibernate.query.sqm.tree.expression.SqmJsonQueryExpression;
import org.hibernate.query.sqm.tree.expression.SqmJsonTableFunction;
import org.hibernate.query.sqm.tree.expression.SqmJsonValueExpression;
import org.hibernate.query.sqm.tree.expression.SqmModifiedSubQueryExpression;
import org.hibernate.query.sqm.tree.expression.SqmSetReturningFunction;
import org.hibernate.query.sqm.tree.expression.SqmXmlElementExpression;
import org.hibernate.query.sqm.tree.expression.SqmXmlTableFunction;
import org.hibernate.query.sqm.tree.from.SqmFrom;
import org.hibernate.query.sqm.tree.from.SqmRoot;
import org.hibernate.query.sqm.tree.insert.SqmInsertSelectStatement;
import org.hibernate.query.sqm.tree.insert.SqmInsertValuesStatement;
import org.hibernate.query.sqm.tree.insert.SqmValues;
import org.hibernate.query.sqm.tree.predicate.SqmInPredicate;
import org.hibernate.query.sqm.tree.predicate.SqmPredicate;
import org.hibernate.query.sqm.tree.select.SqmSelectStatement;
import org.hibernate.query.sqm.tree.select.SqmSortSpecification;
import org.hibernate.query.sqm.tree.select.SqmSubQuery;
import org.hibernate.query.sqm.tree.update.SqmUpdateStatement;
import org.hibernate.type.BasicType;

import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CollectionJoin;
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
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Adapts the JPA CriteriaBuilder to generate SQM nodes.
 *
 * @author Steve Ebersole
 * @author Yoobin Yoon
 */
public interface NodeBuilder extends HibernateCriteriaBuilder, SqmCreationContext {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default JpaMetamodel getDomainModel() {
		return getJpaMetamodel();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default boolean isJpaQueryComplianceEnabled() {
		return getJpaCompliance().isJpaQueryComplianceEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default NodeBuilder getNodeBuilder() {
		return this;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Array functions for array types

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayAgg(JpaOrder order, Expression<? extends T> argument);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayAgg(JpaOrder order, JpaPredicate filter, Expression<? extends T> argument);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayAgg(JpaOrder order, JpaWindow window, Expression<? extends T> argument);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayAgg(
			JpaOrder order,
			JpaPredicate filter,
			JpaWindow window,
			Expression<? extends T> argument);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayLiteral(T... elements);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<Integer> arrayLength(Expression<T[]> arrayExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<Integer> arrayPosition(Expression<T[]> arrayExpression, T element);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<Integer> arrayPosition(Expression<T[]> arrayExpression, Expression<T> elementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<int[]> arrayPositions(Expression<T[]> arrayExpression, Expression<T> elementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<int[]> arrayPositions(Expression<T[]> arrayExpression, T element);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<List<Integer>> arrayPositionsList(Expression<T[]> arrayExpression, Expression<T> elementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<List<Integer>> arrayPositionsList(Expression<T[]> arrayExpression, T element);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayConcat(Expression<T[]> arrayExpression1, Expression<T[]> arrayExpression2);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayConcat(Expression<T[]> arrayExpression1, T[] array2);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayConcat(T[] array1, Expression<T[]> arrayExpression2);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayAppend(Expression<T[]> arrayExpression, Expression<T> elementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayAppend(Expression<T[]> arrayExpression, T element);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayPrepend(Expression<T> elementExpression, Expression<T[]> arrayExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayPrepend(T element, Expression<T[]> arrayExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T> arrayGet(Expression<T[]> arrayExpression, Expression<Integer> indexExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T> arrayGet(Expression<T[]> arrayExpression, Integer index);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arraySet(Expression<T[]> arrayExpression, Expression<Integer> indexExpression, Expression<T> elementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arraySet(Expression<T[]> arrayExpression, Expression<Integer> indexExpression, T element);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arraySet(Expression<T[]> arrayExpression, Integer index, Expression<T> elementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arraySet(Expression<T[]> arrayExpression, Integer index, T element);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayRemove(Expression<T[]> arrayExpression, Expression<T> elementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayRemove(Expression<T[]> arrayExpression, T element);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayRemoveIndex(Expression<T[]> arrayExpression, Expression<Integer> indexExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayRemoveIndex(Expression<T[]> arrayExpression, Integer index);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arraySlice(Expression<T[]> arrayExpression, Expression<Integer> lowerIndexExpression, Expression<Integer> upperIndexExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arraySlice(Expression<T[]> arrayExpression, Expression<Integer> lowerIndexExpression, Integer upperIndex);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arraySlice(Expression<T[]> arrayExpression, Integer lowerIndex, Expression<Integer> upperIndexExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arraySlice(Expression<T[]> arrayExpression, Integer lowerIndex, Integer upperIndex);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayReplace(Expression<T[]> arrayExpression, Expression<T> oldElementExpression, Expression<T> newElementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayReplace(Expression<T[]> arrayExpression, Expression<T> oldElementExpression, T newElement);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayReplace(Expression<T[]> arrayExpression, T oldElement, Expression<T> newElementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayReplace(Expression<T[]> arrayExpression, T oldElement, T newElement);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayTrim(Expression<T[]> arrayExpression, Expression<Integer> elementCountExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayTrim(Expression<T[]> arrayExpression, Integer elementCount);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayReverse(Expression<T[]> arrayExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arraySort(Expression<T[]> arrayExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arraySort(Expression<T[]> arrayExpression, boolean descending);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arraySort(Expression<T[]> arrayExpression, Expression<Boolean> descendingExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arraySort(Expression<T[]> arrayExpression, boolean descending, boolean nullsFirst);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arraySort(Expression<T[]> arrayExpression, Expression<Boolean> descendingExpression, Expression<Boolean> nullsFirstExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayFill(Expression<T> elementExpression, Expression<Integer> elementCountExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayFill(Expression<T> elementExpression, Integer elementCount);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayFill(T element, Expression<Integer> elementCountExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T[]> arrayFill(T element, Integer elementCount);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> arrayToString(Expression<? extends Object[]> arrayExpression, Expression<String> separatorExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> arrayToString(Expression<? extends Object[]> arrayExpression, String separator);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> arrayToString(Expression<? extends Object[]> arrayExpression, Expression<String> separatorExpression, Expression<String> defaultExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> arrayToString(Expression<? extends Object[]> arrayExpression, Expression<String> separatorExpression, String defaultValue);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> arrayToString(Expression<? extends Object[]> arrayExpression, String separator, Expression<String> defaultExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> arrayToString(Expression<? extends Object[]> arrayExpression, String separator, String defaultValue);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmPredicate arrayContains(Expression<T[]> arrayExpression, Expression<T> elementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmPredicate arrayContains(Expression<T[]> arrayExpression, T element);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmPredicate arrayContains(T[] array, Expression<T> elementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmPredicate arrayContainsNullable(Expression<T[]> arrayExpression, Expression<T> elementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmPredicate arrayContainsNullable(Expression<T[]> arrayExpression, T element);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmPredicate arrayContainsNullable(T[] array, Expression<T> elementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmPredicate arrayIncludes(Expression<T[]> arrayExpression, Expression<T[]> subArrayExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmPredicate arrayIncludes(Expression<T[]> arrayExpression, T[] subArray);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmPredicate arrayIncludes(T[] array, Expression<T[]> subArrayExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmPredicate arrayIncludesNullable(Expression<T[]> arrayExpression, Expression<T[]> subArrayExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmPredicate arrayIncludesNullable(Expression<T[]> arrayExpression, T[] subArray);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmPredicate arrayIncludesNullable(T[] array, Expression<T[]> subArrayExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmPredicate arrayIntersects(Expression<T[]> arrayExpression1, Expression<T[]> arrayExpression2);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmPredicate arrayIntersects(Expression<T[]> arrayExpression1, T[] array2);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmPredicate arrayIntersects(T[] array1, Expression<T[]> arrayExpression2);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmPredicate arrayIntersectsNullable(Expression<T[]> arrayExpression1, Expression<T[]> arrayExpression2);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmPredicate arrayIntersectsNullable(Expression<T[]> arrayExpression1, T[] array2);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmPredicate arrayIntersectsNullable(T[] array1, Expression<T[]> arrayExpression2);

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Array functions for collection types

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<E>> SqmExpression<C> collectionLiteral(E... elements);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<Integer> collectionLength(Expression<? extends Collection<?>> collectionExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmExpression<Integer> collectionPosition(Expression<? extends Collection<? extends E>> collectionExpression, E element);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmExpression<Integer> collectionPosition(Expression<? extends Collection<? extends E>> collectionExpression, Expression<E> elementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<int[]> collectionPositions(Expression<? extends Collection<? super T>> collectionExpression, Expression<T> elementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<int[]> collectionPositions(Expression<? extends Collection<? super T>> collectionExpression, T element);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<List<Integer>> collectionPositionsList(Expression<? extends Collection<? super T>> collectionExpression, Expression<T> elementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<List<Integer>> collectionPositionsList(Expression<? extends Collection<? super T>> collectionExpression, T element);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> SqmExpression<C> collectionConcat(Expression<C> collectionExpression1, Expression<? extends Collection<? extends E>> collectionExpression2);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> SqmExpression<C> collectionConcat(Expression<C> collectionExpression1, Collection<? extends E> collection2);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> SqmExpression<C> collectionConcat(C collection1, Expression<? extends Collection<? extends E>> collectionExpression2);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> SqmExpression<C> collectionAppend(Expression<C> collectionExpression, Expression<? extends E> elementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> SqmExpression<C> collectionAppend(Expression<C> collectionExpression, E element);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> SqmExpression<C> collectionPrepend(Expression<? extends E> elementExpression, Expression<C> collectionExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> SqmExpression<C> collectionPrepend(E element, Expression<C> collectionExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmExpression<E> collectionGet(Expression<? extends Collection<E>> collectionExpression, Expression<Integer> indexExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmExpression<E> collectionGet(Expression<? extends Collection<E>> collectionExpression, Integer index);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> SqmExpression<C> collectionSet(Expression<C> collectionExpression, Expression<Integer> indexExpression, Expression<? extends E> elementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> SqmExpression<C> collectionSet(Expression<C> collectionExpression, Expression<Integer> indexExpression, E element);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> SqmExpression<C> collectionSet(Expression<C> collectionExpression, Integer index, Expression<? extends E> elementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> SqmExpression<C> collectionSet(Expression<C> collectionExpression, Integer index, E element);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> SqmExpression<C> collectionRemove(Expression<C> collectionExpression, Expression<? extends E> elementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> SqmExpression<C> collectionRemove(Expression<C> collectionExpression, E element);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> SqmExpression<C> collectionRemoveIndex(Expression<C> collectionExpression, Expression<Integer> indexExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> SqmExpression<C> collectionRemoveIndex(Expression<C> collectionExpression, Integer index);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> SqmExpression<C> collectionSlice(Expression<C> collectionExpression, Expression<Integer> lowerIndexExpression, Expression<Integer> upperIndexExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> SqmExpression<C> collectionSlice(Expression<C> collectionExpression, Expression<Integer> lowerIndexExpression, Integer upperIndex);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> SqmExpression<C> collectionSlice(Expression<C> collectionExpression, Integer lowerIndex, Expression<Integer> upperIndexExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> SqmExpression<C> collectionSlice(Expression<C> collectionExpression, Integer lowerIndex, Integer upperIndex);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> SqmExpression<C> collectionReplace(Expression<C> collectionExpression, Expression<? extends E> oldElementExpression, Expression<? extends E> newElementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> SqmExpression<C> collectionReplace(Expression<C> collectionExpression, Expression<? extends E> oldElementExpression, E newElement);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> SqmExpression<C> collectionReplace(Expression<C> collectionExpression, E oldElement, Expression<? extends E> newElementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<? super E>> SqmExpression<C> collectionReplace(Expression<C> collectionExpression, E oldElement, E newElement);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> SqmExpression<C> collectionTrim(Expression<C> arrayExpression, Expression<Integer> elementCountExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> SqmExpression<C> collectionTrim(Expression<C> arrayExpression, Integer elementCount);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> SqmExpression<C> collectionReverse(Expression<C> collectionExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> SqmExpression<C> collectionSort(Expression<C> collectionExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> SqmExpression<C> collectionSort(Expression<C> collectionExpression, boolean descending);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> SqmExpression<C> collectionSort(
			Expression<C> collectionExpression,
			Expression<Boolean> descendingExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> SqmExpression<C> collectionSort(
			Expression<C> collectionExpression,
			boolean descending,
			boolean nullsFirst);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> SqmExpression<C> collectionSort(
			Expression<C> collectionExpression,
			Expression<Boolean> descendingExpression,
			Expression<Boolean> nullsFirstExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<Collection<T>> collectionFill(Expression<T> elementExpression, Expression<Integer> elementCountExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<Collection<T>> collectionFill(Expression<T> elementExpression, Integer elementCount);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<Collection<T>> collectionFill(T element, Expression<Integer> elementCountExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<Collection<T>> collectionFill(T element, Integer elementCount);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> collectionToString(Expression<? extends Collection<?>> collectionExpression, Expression<String> separatorExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> collectionToString(Expression<? extends Collection<?>> collectionExpression, String separator);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> collectionToString(Expression<? extends Collection<?>> collectionExpression, Expression<String> separatorExpression, Expression<String> defaultExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> collectionToString(Expression<? extends Collection<?>> collectionExpression, Expression<String> separatorExpression, String defaultValue);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> collectionToString(Expression<? extends Collection<?>> collectionExpression, String separator, Expression<String> defaultExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> collectionToString(Expression<? extends Collection<?>> collectionExpression, String separator, String defaultValue);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmPredicate collectionContains(Expression<? extends Collection<E>> collectionExpression, Expression<? extends E> elementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmPredicate collectionContains(Expression<? extends Collection<E>> collectionExpression, E element);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmPredicate collectionContains(Collection<E> collection, Expression<E> elementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmPredicate collectionContainsNullable(Expression<? extends Collection<E>> collectionExpression, Expression<? extends E> elementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmPredicate collectionContainsNullable(Expression<? extends Collection<E>> collectionExpression, E element);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmPredicate collectionContainsNullable(Collection<E> collection, Expression<E> elementExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmPredicate collectionIncludes(Expression<? extends Collection<E>> collectionExpression, Expression<? extends Collection<? extends E>> subCollectionExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmPredicate collectionIncludes(Expression<? extends Collection<E>> collectionExpression, Collection<? extends E> subCollection);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmPredicate collectionIncludes(Collection<E> collection, Expression<? extends Collection<? extends E>> subArrayExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmPredicate collectionIncludesNullable(Expression<? extends Collection<E>> collectionExpression, Expression<? extends Collection<? extends E>> subCollectionExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmPredicate collectionIncludesNullable(Expression<? extends Collection<E>> collectionExpression, Collection<? extends E> subCollection);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmPredicate collectionIncludesNullable(Collection<E> collection, Expression<? extends Collection<? extends E>> subCollectionExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmPredicate collectionIntersects(Expression<? extends Collection<E>> collectionExpression1, Expression<? extends Collection<? extends E>> collectionExpression2);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmPredicate collectionIntersects(Expression<? extends Collection<E>> collectionExpression1, Collection<? extends E> collection2);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmPredicate collectionIntersects(Collection<E> collection1, Expression<? extends Collection<? extends E>> collectionExpression2);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmPredicate collectionIntersectsNullable(Expression<? extends Collection<E>> collectionExpression1, Expression<? extends Collection<? extends E>> collectionExpression2);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmPredicate collectionIntersectsNullable(Expression<? extends Collection<E>> collectionExpression1, Collection<? extends E> collection2);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmPredicate collectionIntersectsNullable(Collection<E> collection1, Expression<? extends Collection<? extends E>> collectionExpression2);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmJsonValueExpression<T> jsonValue(
			Expression<?> jsonDocument,
			Expression<String> jsonPath,
			Class<T> returningType);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmJsonValueExpression<String> jsonValue(Expression<?> jsonDocument, Expression<String> jsonPath);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmJsonValueExpression<T> jsonValue(Expression<?> jsonDocument, String jsonPath, Class<T> returningType);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmJsonValueExpression<String> jsonValue(Expression<?> jsonDocument, String jsonPath);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmJsonQueryExpression jsonQuery(Expression<?> jsonDocument, Expression<String> jsonPath);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmJsonQueryExpression jsonQuery(Expression<?> jsonDocument, String jsonPath);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmJsonExistsExpression jsonExists(Expression<?> jsonDocument, Expression<String> jsonPath);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmJsonExistsExpression jsonExists(Expression<?> jsonDocument, String jsonPath);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonArrayWithNulls(Expression<?>... values);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonArray(Expression<?>... values);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonObjectWithNulls(Map<?, ? extends Expression<?>> keyValues);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonObject(Map<?, ? extends Expression<?>> keyValues);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonArrayAgg(Expression<?> value);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonArrayAggWithNulls(Expression<?> value);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonArrayAggWithNulls(Expression<?> value, Predicate filter, JpaOrder... orderBy);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonArrayAggWithNulls(Expression<?> value, Predicate filter);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonArrayAggWithNulls(Expression<?> value, JpaOrder... orderBy);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonArrayAgg(Expression<?> value, Predicate filter, JpaOrder... orderBy);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonArrayAgg(Expression<?> value, Predicate filter);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonArrayAgg(Expression<?> value, JpaOrder... orderBy);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonObjectAggWithUniqueKeysAndNulls(Expression<?> key, Expression<?> value);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonObjectAggWithUniqueKeys(Expression<?> key, Expression<?> value);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonObjectAggWithNulls(Expression<?> key, Expression<?> value);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonObjectAgg(Expression<?> key, Expression<?> value);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonObjectAggWithUniqueKeysAndNulls(Expression<?> key, Expression<?> value, Predicate filter);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonObjectAggWithUniqueKeys(Expression<?> key, Expression<?> value, Predicate filter);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonObjectAggWithNulls(Expression<?> key, Expression<?> value, Predicate filter);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonObjectAgg(Expression<?> key, Expression<?> value, Predicate filter);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonSet(Expression<?> jsonDocument, Expression<String> jsonPath, Object value);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonSet(Expression<?> jsonDocument, String jsonPath, Object value);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonSet(Expression<?> jsonDocument, Expression<String> jsonPath, Expression<?> value);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonSet(Expression<?> jsonDocument, String jsonPath, Expression<?> value);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonRemove(Expression<?> jsonDocument, String jsonPath);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonRemove(Expression<?> jsonDocument, Expression<String> jsonPath);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonInsert(Expression<?> jsonDocument, Expression<String> jsonPath, Object value);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonInsert(Expression<?> jsonDocument, String jsonPath, Object value);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonInsert(Expression<?> jsonDocument, Expression<String> jsonPath, Expression<?> value);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonInsert(Expression<?> jsonDocument, String jsonPath, Expression<?> value);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonReplace(Expression<?> jsonDocument, Expression<String> jsonPath, Object value);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonReplace(Expression<?> jsonDocument, String jsonPath, Object value);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonReplace(Expression<?> jsonDocument, Expression<String> jsonPath, Expression<?> value);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonReplace(Expression<?> jsonDocument, String jsonPath, Expression<?> value);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonMergepatch(String document, Expression<?> patch);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonMergepatch(Expression<?> document, String patch);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> jsonMergepatch(Expression<?> document, Expression<?> patch);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmXmlElementExpression xmlelement(String elementName);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> xmlcomment(String comment);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T> named(Expression<T> expression, String name);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> xmlforest(List<? extends Expression<?>> elements);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> xmlforest(Expression<?>... elements);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> xmlconcat(Expression<?>... elements);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> xmlconcat(List<? extends Expression<?>> elements);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> xmlpi(String elementName);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> xmlpi(String elementName, Expression<String> content);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> xmlquery(String query, Expression<?> xmlDocument);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> xmlquery(Expression<String> query, Expression<?> xmlDocument);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<Boolean> xmlexists(String query, Expression<?> xmlDocument);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<Boolean> xmlexists(Expression<String> query, Expression<?> xmlDocument);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> xmlagg(JpaOrder order, Expression<?> argument);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> xmlagg(JpaOrder order, JpaPredicate filter, Expression<?> argument);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> xmlagg(JpaOrder order, JpaWindow window, Expression<?> argument);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> xmlagg(JpaOrder order, JpaPredicate filter, JpaWindow window, Expression<?> argument);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmSetReturningFunction<E> setReturningFunction(String name, Expression<?>... args);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmSetReturningFunction<E> unnestArray(Expression<E[]> array);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> SqmSetReturningFunction<E> unnestCollection(Expression<? extends Collection<E>> collection);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Temporal> SqmSetReturningFunction<E> generateTimeSeries(Expression<E> start, Expression<E> stop, Expression<? extends TemporalAmount> step);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Temporal> SqmSetReturningFunction<E> generateTimeSeries(E start, E stop, TemporalAmount step);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Temporal> SqmSetReturningFunction<E> generateTimeSeries(E start, Expression<E> stop, TemporalAmount step);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Temporal> SqmSetReturningFunction<E> generateTimeSeries(Expression<E> start, E stop, TemporalAmount step);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Temporal> SqmSetReturningFunction<E> generateTimeSeries(Expression<E> start, Expression<E> stop, TemporalAmount step);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Temporal> SqmSetReturningFunction<E> generateTimeSeries(E start, E stop, Expression<? extends TemporalAmount> step);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Temporal> SqmSetReturningFunction<E> generateTimeSeries(Expression<E> start, E stop, Expression<? extends TemporalAmount> step);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Temporal> SqmSetReturningFunction<E> generateTimeSeries(E start, Expression<E> stop, Expression<? extends TemporalAmount> step);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> SqmSetReturningFunction<E> generateSeries(Expression<E> start, Expression<E> stop, Expression<E> step);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> SqmSetReturningFunction<E> generateSeries(E start, E stop, E step);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> SqmSetReturningFunction<E> generateSeries(E start, E stop, Expression<E> step);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> SqmSetReturningFunction<E> generateSeries(Expression<E> start, E stop, E step);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> SqmSetReturningFunction<E> generateSeries(E start, Expression<E> stop, E step);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> SqmSetReturningFunction<E> generateSeries(Expression<E> start, Expression<E> stop, E step);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> SqmSetReturningFunction<E> generateSeries(Expression<E> start, E stop, Expression<E> step);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> SqmSetReturningFunction<E> generateSeries(E start, Expression<E> stop, Expression<E> step);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> SqmSetReturningFunction<E> generateSeries(Expression<E> start, Expression<E> stop);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> SqmSetReturningFunction<E> generateSeries(Expression<E> start, E stop);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> SqmSetReturningFunction<E> generateSeries(E start, Expression<E> stop);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Number> SqmSetReturningFunction<E> generateSeries(E start, E stop);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmJsonTableFunction<?> jsonTable(Expression<?> jsonDocument);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmJsonTableFunction<?> jsonTable(Expression<?> jsonDocument, String jsonPath);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmJsonTableFunction<?> jsonTable(Expression<?> jsonDocument, Expression<String> jsonPath);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmXmlTableFunction<?> xmlTable(String xpath, Expression<?> xmlDocument);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmXmlTableFunction<?> xmlTable(Expression<String> xpath, Expression<?> xmlDocument);

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Covariant overrides

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmSelectStatement<Object> createQuery();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmSelectStatement<T> createQuery(@Nonnull Class<T> resultClass);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmSelectStatement<T> createQuery(String hql, Class<T> resultClass);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmSelectStatement<Tuple> createTupleQuery();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaCompoundSelection<Y> construct(@Nonnull Class<Y> resultClass, @Nonnull Selection<?>... selections);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaCompoundSelection<Y> construct(Class<Y> resultClass, List<? extends Selection<?>> arguments);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCompoundSelection<Tuple> tuple(@Nonnull Selection<?>... selections);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCompoundSelection<Tuple> tuple(@Nonnull List<Selection<?>> selections);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCompoundSelection<Object[]> array(@Nonnull Selection<?>... selections);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCompoundSelection<Object[]> array(@Nonnull List<Selection<?>> selections);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmUpdateStatement<T> createCriteriaUpdate(@Nonnull Class<T> targetEntity);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmDeleteStatement<T> createCriteriaDelete(@Nonnull Class<T> targetEntity);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmInsertValuesStatement<T> createCriteriaInsertValues(Class<T> targetEntity);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmInsertSelectStatement<T> createCriteriaInsertSelect(Class<T> targetEntity);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmValues values(Expression<?>... expressions);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmValues values(List<? extends Expression<?>> expressions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> SqmExpression<N> abs(@Nonnull Expression<N> x);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, T> SqmExpression<X> cast(JpaExpression<T> expression, Class<X> castTargetJavaType);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, T> SqmExpression<X> cast(JpaExpression<T> expression, JpaCastTarget<X> castTarget);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> SqmCastTarget<X> castTarget(Class<X> castTargetJavaType);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> SqmCastTarget<X> castTarget(Class<X> castTargetJavaType, long length);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> SqmCastTarget<X> castTarget(Class<X> castTargetJavaType, int precision, int scale);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate wrap(Expression<Boolean> expression);

	@Override @SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate wrap(Expression<Boolean>... expressions);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate wrap(BooleanExpression... expressions);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate wrap(List<? extends Expression<Boolean>> restrictions);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<?> fk(Path<?> path);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<?> id(Path<?> path);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<?> version(Path<?> path);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, T extends X> SqmPath<T> treat(@Nonnull Path<X> path, @Nonnull Class<T> type);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, T extends X> SqmRoot<T> treat(@Nonnull Root<X> root, @Nonnull Class<T> type);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, Y, T extends Y> SqmFrom<X, T> treat(@Nonnull From<X, Y> from, @Nonnull Class<T> type);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, T, V extends T> SqmSingularJoin<X, V> treat(@Nonnull Join<X, T> join, @Nonnull Class<V> type);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, T, E extends T> SqmBagJoin<X, E> treat(@Nonnull CollectionJoin<X, T> join, @Nonnull Class<E> type);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, T, E extends T> SqmSetJoin<X, E> treat(@Nonnull SetJoin<X, T> join, @Nonnull Class<E> type);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, T, E extends T> SqmListJoin<X, E> treat(@Nonnull ListJoin<X, T> join, @Nonnull Class<E> type);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X, K, T, V extends T> SqmMapJoin<X, K, V> treat(@Nonnull MapJoin<X, K, T> join, @Nonnull Class<V> type);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> SqmExpression<Double> avg(@Nonnull Expression<N> argument);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> SqmExpression<N> sum(@Nonnull Expression<N> argument);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<Long> sumAsLong(@Nonnull Expression<Integer> argument);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<Double> sumAsDouble(@Nonnull Expression<Float> argument);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> SqmExpression<N> max(@Nonnull Expression<N> argument);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> SqmExpression<N> min(@Nonnull Expression<N> argument);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X extends Comparable<? super X>> SqmExpression<X> greatest(@Nonnull Expression<X> argument);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X extends Comparable<? super X>> SqmExpression<X> least(@Nonnull Expression<X> argument);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<Long> count(@Nonnull Expression<?> argument);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<Long> countDistinct(@Nonnull Expression<?> x);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<Long> count();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> SqmExpression<N> neg(@Nonnull Expression<N> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> SqmExpression<N> sum(@Nonnull Expression<? extends N> x, @Nonnull Expression<? extends N> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> SqmExpression<N> sum(@Nonnull Expression<? extends N> x, N y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> SqmExpression<N> sum(N x, @Nonnull Expression<? extends N> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> SqmExpression<N> prod(@Nonnull Expression<? extends N> x, @Nonnull Expression<? extends N> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> SqmExpression<N> prod(@Nonnull Expression<? extends N> x, N y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> SqmExpression<N> prod(N x, @Nonnull Expression<? extends N> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> SqmExpression<N> diff(@Nonnull Expression<? extends N> x, @Nonnull Expression<? extends N> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> SqmExpression<N> diff(@Nonnull Expression<? extends N> x, N y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<N extends Number> SqmExpression<N> diff(N x, @Nonnull Expression<? extends N> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<Number> quot(@Nonnull Expression<? extends Number> x, @Nonnull Expression<? extends Number> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<Number> quot(@Nonnull Expression<? extends Number> x, Number y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<Number> quot(Number x, @Nonnull Expression<? extends Number> y);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<Number> quotPortable(Expression<? extends Number> x, Expression<? extends Number> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<Integer> mod(@Nonnull Expression<Integer> x, @Nonnull Expression<Integer> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<Integer> mod(@Nonnull Expression<Integer> x, Integer y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<Integer> mod(Integer x, @Nonnull Expression<Integer> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<Double> sqrt(@Nonnull Expression<? extends Number> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<Long> toLong(@Nonnull Expression<? extends Number> number);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<Integer> toInteger(@Nonnull Expression<? extends Number> number);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<Float> toFloat(@Nonnull Expression<? extends Number> number);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<Double> toDouble(@Nonnull Expression<? extends Number> number);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<BigDecimal> toBigDecimal(@Nonnull Expression<? extends Number> number);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<BigInteger> toBigInteger(@Nonnull Expression<? extends Number> number);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> toString(@Nonnull Expression<Character> character);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T> literal(@Nonnull T value);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> List<? extends SqmExpression<T>> literals(T[] values);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> List<? extends SqmExpression<T>> literals(List<T> values);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmExpression<T> nullLiteral(@Nonnull Class<T> resultClass);

	/**
	 * @implNote Notice that this returns a JPA parameter not the SqmParameter
	 * @see JpaParameterExpression
	 *
	 */
	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaParameterExpression<T> parameter(@Nonnull Class<T> paramClass);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaParameterExpression<T> parameter(@Nonnull Class<T> paramClass, @Nonnull String name);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> concat(@Nonnull Expression<String> x, @Nonnull Expression<String> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> concat(@Nonnull Expression<String> x, @Nonnull String y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> concat(@Nonnull String x, @Nonnull Expression<String> y);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<String> concat(String x, String y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunction<String> substring(@Nonnull Expression<String> x, @Nonnull Expression<Integer> from);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunction<String> substring(@Nonnull Expression<String> x, int from);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunction<String> substring(@Nonnull Expression<String> x, @Nonnull Expression<Integer> from, @Nonnull Expression<Integer> len);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunction<String> substring(@Nonnull Expression<String> x, int from, int len);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunction<String> trim(@Nonnull Expression<String> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunction<String> trim(@Nonnull Trimspec ts, @Nonnull Expression<String> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunction<String> trim(@Nonnull Expression<Character> t, @Nonnull Expression<String> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunction<String> trim(@Nonnull Trimspec ts, @Nonnull Expression<Character> t, @Nonnull Expression<String> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunction<String> trim(char t, @Nonnull Expression<String> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunction<String> trim(@Nonnull Trimspec ts, char t, @Nonnull Expression<String> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunction<String> lower(@Nonnull Expression<String> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunction<String> upper(@Nonnull Expression<String> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunction<Integer> length(@Nonnull Expression<String> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunction<Integer> locate(@Nonnull Expression<String> x, @Nonnull Expression<String> pattern);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunction<Integer> locate(@Nonnull Expression<String> x, @Nonnull String pattern);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunction<Integer> locate(@Nonnull Expression<String> x, @Nonnull Expression<String> pattern, @Nonnull Expression<Integer> from);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunction<Integer> locate(@Nonnull Expression<String> x, @Nonnull String pattern, int from);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunction<Date> currentDate();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunction<Timestamp> currentTimestamp();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunction<Time> currentTime();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunction<Instant> currentInstant();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<LocalDate> localDate();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<LocalDateTime> localDateTime();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<LocalTime> localTime();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmFunction<T> function(@Nonnull String name, @Nonnull Class<T> type, @Nonnull Expression<?>[] args);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> SqmModifiedSubQueryExpression<Y> all(@Nonnull Subquery<Y> subquery);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> SqmModifiedSubQueryExpression<Y> some(@Nonnull Subquery<Y> subquery);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> SqmModifiedSubQueryExpression<Y> any(@Nonnull Subquery<Y> subquery);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<K, L extends List<?>> SqmExpression<Set<K>> indexes(L list);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> SqmExpression<Integer> size(@Nonnull Expression<C> collection);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> SqmExpression<Integer> size(@Nonnull C collection);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> JpaCoalesce<T> coalesce();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaCoalesce<Y> coalesce(
			@Nonnull Expression<? extends Y> x, @Nonnull Expression<? extends Y> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaCoalesce<Y> coalesce(@Nonnull Expression<? extends Y> x, Y y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> SqmExpression<Y> nullif(@Nonnull Expression<Y> x, @Nonnull Expression<?> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> SqmExpression<Y> nullif(@Nonnull Expression<Y> x, Y y);

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

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate and(@Nonnull Expression<Boolean> x, @Nonnull Expression<Boolean> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate and(@Nonnull Predicate... restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate or(@Nonnull Expression<Boolean> x, @Nonnull Expression<Boolean> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate or(@Nonnull Predicate... restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate not(@Nonnull Expression<Boolean> restriction);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate conjunction();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate disjunction();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate isTrue(@Nonnull Expression<Boolean> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate isFalse(@Nonnull Expression<Boolean> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate isNull(@Nonnull Expression<?> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate isNotNull(@Nonnull Expression<?> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate equal(@Nonnull Expression<?> x, @Nonnull Expression<?> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate equal(@Nonnull Expression<?> x, Object y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate notEqual(@Nonnull Expression<?> x, @Nonnull Expression<?> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate notEqual(@Nonnull Expression<?> x, Object y);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate distinctFrom(Expression<?> x, Expression<?> y);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate distinctFrom(Expression<?> x, Object y);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate notDistinctFrom(Expression<?> x, Expression<?> y);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate notDistinctFrom(Expression<?> x, Object y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends Comparable<? super Y>> SqmPredicate greaterThan(@Nonnull Expression<? extends Y> x, @Nonnull Expression<? extends Y> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends Comparable<? super Y>> SqmPredicate greaterThan(@Nonnull Expression<? extends Y> x, Y y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends Comparable<? super Y>> SqmPredicate greaterThanOrEqualTo(
			@Nonnull Expression<? extends Y> x,
			@Nonnull Expression<? extends Y> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends Comparable<? super Y>> SqmPredicate greaterThanOrEqualTo(@Nonnull Expression<? extends Y> x, Y y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends Comparable<? super Y>> SqmPredicate lessThan(@Nonnull Expression<? extends Y> x, @Nonnull Expression<? extends Y> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends Comparable<? super Y>> SqmPredicate lessThan(@Nonnull Expression<? extends Y> x, Y y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends Comparable<? super Y>> SqmPredicate lessThanOrEqualTo(
			@Nonnull Expression<? extends Y> x,
			@Nonnull Expression<? extends Y> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends Comparable<? super Y>> SqmPredicate lessThanOrEqualTo(@Nonnull Expression<? extends Y> x, Y y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends Comparable<? super Y>> SqmPredicate between(
			@Nonnull Expression<? extends Y> value,
			@Nonnull Expression<? extends Y> lower,
			@Nonnull Expression<? extends Y> upper);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate between(Expression<?> value, Expression<?> lower, Expression<?> upper, boolean negated);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate comparison(Expression<?> x, ComparisonOperator operator, Expression<?> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends Comparable<? super Y>> SqmPredicate between(@Nonnull Expression<? extends Y> value, Y lower, Y upper);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y extends Comparable<? super Y>> SqmPredicate between(
			Y value,
			@Nonnull Expression<? extends Y> lower,
			@Nonnull Expression<? extends Y> upper);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate gt(@Nonnull Expression<? extends Number> x, @Nonnull Expression<? extends Number> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate gt(@Nonnull Expression<? extends Number> x, Number y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate ge(@Nonnull Expression<? extends Number> x, @Nonnull Expression<? extends Number> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate ge(@Nonnull Expression<? extends Number> x, Number y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate lt(@Nonnull Expression<? extends Number> x, @Nonnull Expression<? extends Number> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate lt(@Nonnull Expression<? extends Number> x, Number y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate le(@Nonnull Expression<? extends Number> x, @Nonnull Expression<? extends Number> y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate le(@Nonnull Expression<? extends Number> x, Number y);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> SqmPredicate isEmpty(@Nonnull Expression<C> collection);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<C extends Collection<?>> SqmPredicate isNotEmpty(@Nonnull Expression<C> collection);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<E>> SqmPredicate isMember(@Nonnull Expression<E> elem, @Nonnull Expression<C> collection);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<E>> SqmPredicate isMember(E elem, @Nonnull Expression<C> collection);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<E>> SqmPredicate isNotMember(@Nonnull Expression<E> elem, @Nonnull Expression<C> collection);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<E>> SqmPredicate isNotMember(E elem, @Nonnull Expression<C> collection);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate like(@Nonnull Expression<String> x, @Nonnull Expression<String> pattern);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate like(@Nonnull Expression<String> x, @Nonnull String pattern);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate like(@Nonnull Expression<String> x, @Nonnull Expression<String> pattern, @Nonnull Expression<Character> escapeChar);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate like(@Nonnull Expression<String> x, @Nonnull Expression<String> pattern, char escapeChar);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate like(@Nonnull Expression<String> x, @Nonnull String pattern, @Nonnull Expression<Character> escapeChar);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate like(@Nonnull Expression<String> x, @Nonnull String pattern, char escapeChar);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate notLike(@Nonnull Expression<String> x, @Nonnull Expression<String> pattern);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate notLike(@Nonnull Expression<String> x, @Nonnull String pattern);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate notLike(@Nonnull Expression<String> x, @Nonnull Expression<String> pattern, @Nonnull Expression<Character> escapeChar);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate notLike(@Nonnull Expression<String> x, @Nonnull Expression<String> pattern, char escapeChar);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate notLike(@Nonnull Expression<String> x, @Nonnull String pattern, @Nonnull Expression<Character> escapeChar);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate notLike(@Nonnull Expression<String> x, @Nonnull String pattern, char escapeChar);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmInPredicate<T> in(@Nonnull Expression<? extends T> expression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmInPredicate<T> in(Expression<? extends T> expression, Expression<? extends T>... values);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmInPredicate<T> in(Expression<? extends T> expression, T... values);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmInPredicate<T> in(Expression<? extends T> expression, Collection<T> values);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> SqmInPredicate<T> in(Expression<? extends T> expression, SqmSubQuery<T> subQuery);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate exists(@Nonnull Subquery<?> subquery);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<M extends Map<?, ?>> SqmPredicate isMapEmpty(JpaExpression<M> mapExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<M extends Map<?, ?>> SqmPredicate isMapNotEmpty(JpaExpression<M> mapExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<M extends Map<?,?>> SqmExpression<Integer> mapSize(JpaExpression<M> mapExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<M extends Map<?, ?>> SqmExpression<Integer> mapSize(M map);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmSortSpecification sort(JpaExpression<?> sortExpression, SortDirection sortOrder, Nulls nullPrecedence);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmSortSpecification sort(
			JpaExpression<?> sortExpression,
			SortDirection sortOrder,
			Nulls nullPrecedence,
			boolean ignoreCase);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmSortSpecification sort(JpaExpression<?> sortExpression, SortDirection sortOrder);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmSortSpecification sort(JpaExpression<?> sortExpression);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmSortSpecification asc(@Nonnull Expression<?> x);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmSortSpecification desc(@Nonnull Expression<?> x);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	BasicType<Boolean> getBooleanType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	BasicType<Integer> getIntegerType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	BasicType<Long> getLongType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	BasicType<Character> getCharacterType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	BasicType<String> getStringType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCompliance getJpaCompliance();

	@Deprecated(since = "7.0", forRemoval = true)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ImmutableEntityUpdateQueryHandlingMode getImmutableEntityUpdateQueryHandlingMode();

	@Deprecated(since = "8.0", forRemoval = true)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean allowImmutableEntityUpdate();
}
