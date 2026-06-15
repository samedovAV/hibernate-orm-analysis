/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.expression;

import jakarta.annotation.Nonnull;
import org.hibernate.query.sqm.NodeBuilder;
import org.hibernate.query.sqm.SemanticQueryWalker;
import org.hibernate.query.sqm.SqmBindableType;
import org.hibernate.query.sqm.tree.SqmCopyContext;
import org.hibernate.query.sqm.tree.SqmRenderContext;

import static org.hibernate.internal.util.NullnessUtil.castNonNull;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class SqmCollation extends SqmLiteral<String> {
	public SqmCollation(String value, SqmBindableType<String> inherentType, NodeBuilder nodeBuilder) {
		super(value, inherentType == null ? nodeBuilder.getStringType() : inherentType, nodeBuilder);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmCollation copy(SqmCopyContext context) {
		final SqmCollation existing = context.getCopy( this );
		if ( existing != null ) {
			return existing;
		}
		final SqmCollation expression = context.registerCopy(
				this,
				new SqmCollation( getLiteralValue(), getNodeType(), nodeBuilder() )
		);
		copyTo( expression, context );
		return expression;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public @Nonnull String getLiteralValue() {
		return castNonNull( super.getLiteralValue() );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public @Nonnull SqmBindableType<String> getNodeType() {
		return castNonNull( super.getNodeType() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <R> R accept(SemanticQueryWalker<R> walker) {
		return walker.visitCollation( this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void appendHqlString(StringBuilder hql, SqmRenderContext context) {
		hql.append( getLiteralValue() );
	}
}
