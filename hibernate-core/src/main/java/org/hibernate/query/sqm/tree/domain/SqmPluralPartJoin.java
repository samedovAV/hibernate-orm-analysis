/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.domain;

import jakarta.annotation.Nullable;
import jakarta.annotation.Nonnull;
import java.util.Locale;

import org.hibernate.metamodel.model.domain.EntityDomainType;
import org.hibernate.metamodel.model.domain.PersistentAttribute;
import org.hibernate.query.sqm.NodeBuilder;
import org.hibernate.query.sqm.SemanticQueryWalker;
import org.hibernate.query.sqm.SqmPathSource;
import org.hibernate.query.sqm.spi.SqmCreationHelper;
import org.hibernate.query.sqm.tree.SqmCopyContext;
import org.hibernate.query.sqm.tree.SqmJoinType;
import org.hibernate.query.sqm.tree.from.SqmFrom;
import org.hibernate.query.sqm.tree.predicate.SqmPredicate;
import org.hibernate.spi.NavigablePath;

import static org.hibernate.internal.util.NullnessUtil.castNonNull;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class SqmPluralPartJoin<O,T> extends AbstractSqmJoin<O,T> {

	public SqmPluralPartJoin(
			SqmFrom<?,O> lhs,
			SqmPathSource<T> joinedNavigable,
			@Nullable String alias,
			SqmJoinType joinType,
			NodeBuilder nodeBuilder) {
		super(
				SqmCreationHelper.buildSubNavigablePath( lhs, joinedNavigable.getPathName(), alias ),
				joinedNavigable,
				lhs,
				alias == SqmCreationHelper.IMPLICIT_ALIAS ? null : alias,
				joinType,
				nodeBuilder
		);
	}

	protected SqmPluralPartJoin(
			SqmFrom<?, O> lhs,
			NavigablePath navigablePath,
			SqmPathSource<T> joinedNavigable,
			@Nullable String alias,
			SqmJoinType joinType,
			NodeBuilder nodeBuilder) {
		super(
				navigablePath,
				joinedNavigable,
				lhs,
				alias == SqmCreationHelper.IMPLICIT_ALIAS ? null : alias,
				joinType,
				nodeBuilder
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isImplicitlySelectable() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmPluralPartJoin<O, T> copy(SqmCopyContext context) {
		final var existing = context.getCopy( this );
		if ( existing != null ) {
			return existing;
		}
		final SqmFrom<?, O> lhsCopy = getLhs().copy( context );
		final var path = context.registerCopy(
				this,
				new SqmPluralPartJoin<>(
						lhsCopy,
						getNavigablePathCopy( lhsCopy ),
						getReferencedPathSource(),
						getExplicitAlias(),
						getSqmJoinType(),
						nodeBuilder()
				)
		);
		copyTo( path, context );
		return path;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public @Nonnull SqmFrom<?, O> getLhs() {
		return castNonNull( super.getLhs() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable SqmPredicate getJoinPredicate() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setJoinPredicate(@Nullable SqmPredicate predicate) {
		throw new UnsupportedOperationException( "Setting a predicate for a plural part join is unsupported" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> X accept(SemanticQueryWalker<X> walker) {
		return walker.visitPluralPartJoin( this );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public <S extends T> SqmTreatedPluralPartJoin<O, T, S> treatAs(@Nonnull Class<S> treatJavaType) {
		return treatAs( nodeBuilder().getDomainModel().entity( treatJavaType ) );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public <S extends T> SqmTreatedPluralPartJoin<O, T, S> treatAs(@Nonnull EntityDomainType<S> treatTarget) {
		return treatAs( treatTarget, null );
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public <S extends T> SqmTreatedPluralPartJoin<O, T, S> treatAs(@Nonnull Class<S> treatJavaType, @Nullable String alias) {
		return treatAs( nodeBuilder().getDomainModel().entity( treatJavaType ), alias );
	}

	@SuppressWarnings("unchecked")
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <S extends T> SqmTreatedPluralPartJoin<O, T, S> treatAs(
			@Nonnull EntityDomainType<S> treatTarget,
			@Nullable String alias) {
		final var treat = (SqmTreatedPluralPartJoin<O, T, S>) findTreat( treatTarget, alias );
		if ( treat == null ) {
			return addTreat( new SqmTreatedPluralPartJoin<>( this, (SqmEntityDomainType<S>) treatTarget, alias ) );
		}
		else {
			return treat;
		}
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public <S extends T> SqmTreatedPluralPartJoin<O, T, S> treatAs(
			@Nonnull Class<S> treatJavaType,
			@Nullable String alias,
			boolean fetch) {
		return treatAs( nodeBuilder().getDomainModel().entity( treatJavaType ), alias, fetch );
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <S extends T> SqmTreatedPluralPartJoin<O, T, S> treatAs(
			@Nonnull EntityDomainType<S> treatTarget,
			@Nullable String alias,
			boolean fetch) {
		final var treat = (SqmTreatedPluralPartJoin<O, T, S>) findTreat( treatTarget, alias );
		if ( treat == null ) {
			return addTreat( new SqmTreatedPluralPartJoin<>( this, (SqmEntityDomainType<S>) treatTarget, alias ) );
		}
		else {
			return treat;
		}
	}

	@Nullable
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistentAttribute<? super O, ?> getAttribute() {
		return null;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmCorrelatedPluralPartJoin<O, T> createCorrelation() {
		return new SqmCorrelatedPluralPartJoin<>( this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return String.format(
				Locale.ROOT,
				"SqmPluralPartJoin(%s : %s)",
				getNavigablePath(),
				getReferencedPathSource().getPathName()
		);
	}
}
