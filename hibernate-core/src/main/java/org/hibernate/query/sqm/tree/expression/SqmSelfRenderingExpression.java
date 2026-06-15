/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.expression;

import java.util.function.Function;

import jakarta.annotation.Nullable;
import org.hibernate.query.sqm.NodeBuilder;
import org.hibernate.query.sqm.SemanticQueryWalker;
import org.hibernate.query.sqm.SqmBindableType;
import org.hibernate.query.sqm.tree.SqmCopyContext;
import org.hibernate.query.sqm.tree.SqmRenderContext;
import org.hibernate.sql.ast.tree.expression.Expression;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class SqmSelfRenderingExpression<T> extends AbstractSqmExpression<T> {
	private final Function<SemanticQueryWalker<?>, Expression> renderer;

	public SqmSelfRenderingExpression(
			Function<SemanticQueryWalker<?>, Expression> renderer,
			@Nullable SqmBindableType<T> type,
			NodeBuilder criteriaBuilder) {
		super( type, criteriaBuilder );
		this.renderer = renderer;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmSelfRenderingExpression<T> copy(SqmCopyContext context) {
		final var existing = context.getCopy( this );
		if ( existing != null ) {
			return existing;
		}
		final SqmSelfRenderingExpression<T> expression = context.registerCopy(
				this,
				new SqmSelfRenderingExpression<>( renderer, getNodeType(), nodeBuilder() )
		);
		copyTo( expression, context );
		return expression;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> X accept(SemanticQueryWalker<X> walker) {
		//noinspection unchecked
		return (X) renderer.apply( walker );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void appendHqlString(StringBuilder hql, SqmRenderContext context) {
		throw new UnsupportedOperationException();
	}

	// No equals() / hashCode() because this stuff is only
	// ever used internally and is irrelevant for caching,
	// so basing equality on the object identity is fine

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCompatible(Object object) {
		return this == object;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int cacheHashCode() {
		return System.identityHashCode( this );
	}
}
