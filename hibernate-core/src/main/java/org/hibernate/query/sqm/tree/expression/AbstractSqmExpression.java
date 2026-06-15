/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.expression;

import jakarta.annotation.Nullable;
import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Subquery;
import org.hibernate.query.criteria.JpaNumericExpression;
import org.hibernate.query.sqm.NodeBuilder;
import org.hibernate.query.sqm.SqmBindableType;
import org.hibernate.query.sqm.internal.SqmCriteriaNodeBuilder;
import org.hibernate.query.sqm.tree.jpa.AbstractJpaSelection;
import org.hibernate.query.sqm.tree.predicate.SqmInPredicate;
import org.hibernate.query.sqm.tree.predicate.SqmPredicate;
import org.hibernate.query.sqm.tree.select.SqmSubQuery;
import org.hibernate.type.BasicType;
import org.hibernate.type.descriptor.java.JavaType;

import java.util.Collection;

import static org.hibernate.query.internal.QueryHelper.highestPrecedenceType2;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractSqmExpression<T> extends AbstractJpaSelection<T> implements SqmExpression<T> {

	public AbstractSqmExpression(@Nullable SqmBindableType<? super T> type, NodeBuilder criteriaBuilder) {
		super( type, criteriaBuilder );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmCriteriaNodeBuilder nodeBuilder() {
		return (SqmCriteriaNodeBuilder) super.nodeBuilder();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applyInferableType(@Nullable SqmBindableType<?> type) {
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void internalApplyInferableType(@Nullable SqmBindableType<?> newType) {
//		SqmTreeCreationLogger.LOGGER.tracef(
//				"Applying inferable type to SqmExpression [%s]: %s -> %s",
//				this,
//				getExpressible(),
//				newType
//		);

		setExpressibleType( highestPrecedenceType2( newType, getExpressible() ) );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> SqmExpression<X> as(@Nonnull Class<X> type) {
		final BasicType<X> basicTypeForJavaType = nodeBuilder().getTypeConfiguration().getBasicTypeForJavaType( type );
		if ( basicTypeForJavaType == null ) {
			throw new IllegalArgumentException( "Can't cast expression to unknown type: " + type.getCanonicalName() );
		}
		return new AsWrapperSqmExpression<>( basicTypeForJavaType, this );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmPredicate isNull() {
		return nodeBuilder().isNull( this );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmPredicate isNotNull() {
		return nodeBuilder().isNotNull( this );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmPredicate equalTo(@Nonnull Expression<?> value) {
		return nodeBuilder().equal( this, value );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmPredicate equalTo(Object value) {
		return nodeBuilder().equal( this, value );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmPredicate notEqualTo(@Nonnull Expression<?> value) {
		return nodeBuilder().notEqual( this, value );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmPredicate notEqualTo(Object value) {
		return nodeBuilder().notEqual( this, value );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public <X> SqmExpression<X> cast(@Nonnull Class<X> castTarget) {
		return nodeBuilder().cast( this, castTarget );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public <R> SqmCaseSimple<T, R> selectCase() {
		return nodeBuilder().selectCase( this );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public <R> SqmCaseSimple<T, R> selectCase(@Nonnull Class<R> resultType) {
		return nodeBuilder().selectCase( this, resultType );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmPredicate in(@Nonnull Object... values) {
		return nodeBuilder().in( this, values );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmPredicate in(@Nonnull Expression<?>... values) {
		return nodeBuilder().in( this, values );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmPredicate in(@Nonnull Collection<?> values) {
		//noinspection unchecked
		return nodeBuilder().in( this, (Collection<T>) values );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmPredicate in(@Nonnull Expression<Collection<?>> values) {
		return nodeBuilder().in( this, values );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmInPredicate<T> in(@Nonnull Subquery<T> subquery) {
		return nodeBuilder().in( this, (SqmSubQuery<T>) subquery );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmExpression<T> coalesce(@Nonnull Expression<? extends T> y) {
		return nodeBuilder().coalesce( this, y );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmExpression<T> coalesce(T y) {
		return nodeBuilder().coalesce( this, y );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmExpression<T> nullif(@Nonnull Expression<? extends T> y) {
		return nodeBuilder().nullif( this, y );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmExpression<T> nullif(T y) {
		return nodeBuilder().nullif( this, y );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmPredicate isMember(@Nonnull Expression<? extends Collection<? super T>> collection) {
		//noinspection unchecked
		return nodeBuilder().isMember( this, (Expression<? extends Collection<T>>) collection );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmPredicate isNotMember(@Nonnull Expression<? extends Collection<? super T>> collection) {
		//noinspection unchecked
		return nodeBuilder().isNotMember( this, (Expression<? extends Collection<T>>) collection );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JpaNumericExpression<Long> count() {
		return new SqmNumericExpressionWrapper<>( nodeBuilder().count( this ) );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JpaNumericExpression<Long> countDistinct() {
		return new SqmNumericExpressionWrapper<>( nodeBuilder().countDistinct( this ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable JavaType<T> getJavaTypeDescriptor() {
		final SqmBindableType<T> nodeType = getNodeType();
		return nodeType == null ? null : nodeType.getExpressibleJavaType();
	}
}
