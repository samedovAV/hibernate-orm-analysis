/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.expression;

import org.hibernate.query.sqm.NodeBuilder;
import org.hibernate.query.sqm.SemanticQueryWalker;
import org.hibernate.query.sqm.SqmBindableType;
import org.hibernate.query.sqm.tree.SqmCopyContext;
import org.hibernate.query.sqm.tree.SqmRenderContext;
import org.hibernate.query.sqm.tree.SqmTypedNode;
import org.hibernate.sql.ast.tree.expression.JsonNullBehavior;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Describes how a {@code null} should be treated in a JSON document.
 *
 * @since 7.0
 */
public enum SqmJsonNullBehavior implements SqmTypedNode<Object> {
	/**
	 * {@code null} values are removed.
	 */
	ABSENT,
	/**
	 * {@code null} values are retained as JSON {@code null} literals.
	 */
	NULL;

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable SqmBindableType<Object> getNodeType() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NodeBuilder nodeBuilder() {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmJsonNullBehavior copy(SqmCopyContext context) {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> X accept(SemanticQueryWalker<X> walker) {
		//noinspection unchecked
		return (X) (this == NULL ? JsonNullBehavior.NULL : JsonNullBehavior.ABSENT);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void appendHqlString(StringBuilder hql, SqmRenderContext context) {
		hql.append( this == NULL ? " null on null" : " absent on null" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCompatible(Object object) {
		return this == object;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int cacheHashCode() {
		return hashCode();
	}
}
