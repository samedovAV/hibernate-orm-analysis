/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.domain;

import jakarta.annotation.Nonnull;
import org.hibernate.query.sqm.NodeBuilder;
import org.hibernate.query.sqm.SqmPathSource;
import org.hibernate.query.sqm.tree.SqmCopyContext;
import org.hibernate.query.sqm.tree.from.SqmFrom;
import org.hibernate.query.sqm.tree.from.SqmJoin;
import org.hibernate.spi.NavigablePath;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class SqmCorrelatedDerivedRootJoin<T> extends SqmCorrelatedRootJoin<T> {

	public SqmCorrelatedDerivedRootJoin(
			NavigablePath navigablePath,
			SqmPathSource<T> referencedNavigable,
			NodeBuilder nodeBuilder) {
		super( navigablePath, referencedNavigable, nodeBuilder );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmCorrelatedDerivedRootJoin<T> copy(SqmCopyContext context) {
		final var existing = context.getCopy( this );
		if ( existing != null ) {
			return existing;
		}
		final var path = context.registerCopy(
				this,
				new SqmCorrelatedDerivedRootJoin<>(
						getNavigablePath(),
						getReferencedPathSource(),
						nodeBuilder()
				)
		);
		copyTo( path, context );
		return path;
	}

	// Need to suppress argument warnings because correlatedJoin which is under initialization is passed to addSqmJoin,
	// which expects an initialized argument. We know this is safe though because we only store the instance
	@SuppressWarnings({"unchecked", "argument"})
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static <X, J extends SqmJoin<X, ?>> SqmCorrelatedDerivedRootJoin<X> create(J correlationParent, J correlatedJoin) {
		final SqmFrom<?, X> parentPath = (SqmFrom<?, X>) correlationParent.getParentPath();
		final SqmCorrelatedDerivedRootJoin<X> rootJoin;
		if ( parentPath == null ) {
			rootJoin = new SqmCorrelatedDerivedRootJoin<>(
					correlationParent.getNavigablePath(),
					(SqmPathSource<X>) correlationParent.getReferencedPathSource(),
					correlationParent.nodeBuilder()
			);
		}
		else {
			rootJoin = new SqmCorrelatedDerivedRootJoin<>(
					parentPath.getNavigablePath(),
					parentPath.getReferencedPathSource(),
					correlationParent.nodeBuilder()
			);
		}
		rootJoin.addSqmJoin( correlatedJoin );
		return rootJoin;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean containsOnlyInnerJoins() {
		// The derived join is just referenced, no need to create any table groups
		return true;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// JPA

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmEntityDomainType<T> getModel() {
		throw new UnsupportedOperationException( "Correlated derived root does not have an entity type. Use getReferencedPathSource() instead." );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getEntityName() {
		throw new UnsupportedOperationException( "Correlated derived root does not have an entity type. Use getReferencedPathSource() instead." );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmPathSource<T> getResolvedModel() {
		return getReferencedPathSource();
	}
}
