/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.expression;

import jakarta.annotation.Nullable;
import org.hibernate.query.sqm.NodeBuilder;
import org.hibernate.query.sqm.SqmBindableType;
import org.hibernate.query.sqm.tree.SqmCopyContext;
import org.hibernate.query.sqm.tree.SqmRenderContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class SqmLiteralNull<T> extends SqmLiteral<T> {

	public SqmLiteralNull(NodeBuilder nodeBuilder) {
		//noinspection unchecked
		this( null, nodeBuilder );
	}

	public SqmLiteralNull(@Nullable SqmBindableType<T> expressibleType, NodeBuilder nodeBuilder) {
		super( expressibleType, nodeBuilder );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmLiteralNull<T> copy(SqmCopyContext context) {
		final var existing = context.getCopy( this );
		if ( existing != null ) {
			return existing;
		}
		final SqmLiteralNull<T> expression = context.registerCopy(
				this,
				new SqmLiteralNull<>(
						getNodeType(),
						nodeBuilder()
				)
		);
		copyTo( expression, context );
		return expression;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String asLoggableText() {
		return "<literal-null>";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void appendHqlString(StringBuilder hql, SqmRenderContext context) {
		hql.append( "null" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean equals(@Nullable Object object) {
		return object instanceof SqmLiteralNull;
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
