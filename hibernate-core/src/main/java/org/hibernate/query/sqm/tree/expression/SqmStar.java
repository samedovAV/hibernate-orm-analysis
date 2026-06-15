/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.expression;

import jakarta.annotation.Nullable;
import org.hibernate.query.sqm.NodeBuilder;
import org.hibernate.query.sqm.SemanticQueryWalker;
import org.hibernate.query.sqm.tree.SqmCopyContext;
import org.hibernate.query.sqm.tree.SqmRenderContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Gavin King
 */
public class SqmStar extends AbstractSqmExpression<Object> {

	public SqmStar(NodeBuilder builder) {
		super( null, builder );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmStar copy(SqmCopyContext context) {
		final SqmStar existing = context.getCopy( this );
		if ( existing != null ) {
			return existing;
		}
		return context.registerCopy( this, new SqmStar( nodeBuilder() ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> X accept(SemanticQueryWalker<X> walker) {
		return walker.visitStar( this );
	}
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void appendHqlString(StringBuilder hql, SqmRenderContext context) {
		hql.append( "*" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean equals(@Nullable Object object) {
		return object instanceof SqmStar;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int hashCode() {
		return 1;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCompatible(Object object) {
		return equals( object );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int cacheHashCode() {
		return hashCode();
	}
}
