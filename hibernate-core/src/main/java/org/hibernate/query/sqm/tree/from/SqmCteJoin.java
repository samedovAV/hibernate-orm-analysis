/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.from;

import jakarta.annotation.Nullable;
import jakarta.annotation.Nonnull;
import org.hibernate.Incubating;
import org.hibernate.metamodel.model.domain.EntityDomainType;
import org.hibernate.metamodel.model.domain.PersistentAttribute;
import org.hibernate.query.sqm.SemanticQueryWalker;
import org.hibernate.query.sqm.SqmPathSource;
import org.hibernate.query.sqm.tree.SqmCopyContext;
import org.hibernate.query.sqm.tree.SqmJoinType;
import org.hibernate.query.sqm.tree.cte.SqmCteStatement;
import org.hibernate.query.sqm.tree.domain.AbstractSqmJoin;
import org.hibernate.query.sqm.tree.domain.SqmCorrelatedCteJoin;
import org.hibernate.query.sqm.tree.domain.SqmSingularValuedJoin;
import org.hibernate.query.sqm.tree.domain.SqmTreatedJoin;
import org.hibernate.spi.NavigablePath;

import jakarta.persistence.criteria.JoinType;

import java.util.Objects;

import static org.hibernate.internal.util.NullnessUtil.castNonNull;
import static org.hibernate.query.sqm.spi.SqmCreationHelper.buildRootNavigablePath;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
@Incubating
public class SqmCteJoin<T> extends AbstractSqmJoin<T, T> implements SqmSingularValuedJoin<T, T> {
	private final SqmCteStatement<T> cte;

	public SqmCteJoin(
			SqmCteStatement<T> cte,
			@Nullable String alias,
			SqmJoinType joinType,
			SqmRoot<T> sqmRoot) {
		//noinspection unchecked
		this(
				buildRootNavigablePath( "<<cte>>", alias ),
				cte,
				(SqmPathSource<T>) cte.getCteTable().getTupleType(),
				alias,
				joinType,
				sqmRoot
		);
	}

	protected SqmCteJoin(
			NavigablePath navigablePath,
			SqmCteStatement<T> cte,
			SqmPathSource<T> pathSource,
			@Nullable String alias,
			SqmJoinType joinType,
			SqmRoot<T> sqmRoot) {
		super(
				navigablePath,
				pathSource,
				sqmRoot,
				alias,
				joinType,
				sqmRoot.nodeBuilder()
		);
		this.cte = cte;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isImplicitlySelectable() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmCteJoin<T> copy(SqmCopyContext context) {
		final var existing = context.getCopy( this );
		if ( existing != null ) {
			return existing;
		}
		//noinspection unchecked
		final var path = context.registerCopy(
				this,
				new SqmCteJoin<>(
						getNavigablePath(),
						cte.copy( context ),
						getReferencedPathSource(),
						getExplicitAlias(),
						getSqmJoinType(),
						(SqmRoot<T>) findRoot().copy( context )
				)
		);
		copyTo( path, context );
		return path;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmRoot<?> getRoot() {
		return (SqmRoot<?>) castNonNull( super.getLhs() );
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmRoot<?> findRoot() {
		return getRoot();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmCteStatement<T> getCte() {
		return cte;
	}

	@Nullable
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmFrom<?,T> getLhs() {
		// A cte-join has no LHS
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> X accept(SemanticQueryWalker<X> walker) {
		return walker.visitQualifiedCteJoin( this );
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// JPA

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmCorrelatedCteJoin<T> createCorrelation() {
		return new SqmCorrelatedCteJoin<>( this );
	}

	@Nullable
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistentAttribute<? super T, ?> getAttribute() {
		return null;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <S extends T> SqmTreatedJoin<T, T, S> treatAs(@Nonnull Class<S> treatJavaType, @Nullable String alias) {
		throw new UnsupportedOperationException( "CTE joins can not be treated" );
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <S extends T> SqmTreatedJoin<T, T, S> treatAs(@Nonnull EntityDomainType<S> treatTarget, @Nullable String alias) {
		throw new UnsupportedOperationException( "CTE joins can not be treated" );
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <S extends T> SqmTreatedJoin<T, T, S> treatAs(@Nonnull Class<S> treatJavaType, @Nullable String alias, boolean fetched) {
		throw new UnsupportedOperationException( "CTE joins can not be treated" );
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <S extends T> SqmTreatedJoin<T, T, S> treatAs(@Nonnull EntityDomainType<S> treatTarget, @Nullable String alias, boolean fetched) {
		throw new UnsupportedOperationException( "CTE joins can not be treated" );
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmFrom<?, T> getParent() {
		return getCorrelationParent();
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JoinType getJoinType() {
		return getSqmJoinType().getCorrespondingJpaJoinType();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean deepEquals(SqmFrom<?, ?> object) {
		return super.deepEquals( object )
			&& Objects.equals( cte.getCteTable().getCteName(),
				((SqmCteJoin<?>) object).cte.getCteTable().getCteName() );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isDeepCompatible(SqmFrom<?, ?> object) {
		return super.isDeepCompatible( object )
			&& Objects.equals( cte.getCteTable().getCteName(),
				((SqmCteJoin<?>) object).cte.getCteTable().getCteName() );
	}
}
