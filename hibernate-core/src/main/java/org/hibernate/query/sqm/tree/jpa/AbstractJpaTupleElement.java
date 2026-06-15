/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.jpa;

import org.hibernate.query.criteria.JpaTupleElement;
import org.hibernate.query.sqm.NodeBuilder;
import org.hibernate.query.sqm.SqmBindableType;
import org.hibernate.query.sqm.tree.AbstractSqmNode;
import org.hibernate.query.sqm.tree.SqmCopyContext;
import org.hibernate.query.sqm.tree.SqmVisitableNode;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Base support for {@link JpaTupleElement} impls
 *
 * @author Steve Ebersole
 */
public abstract class AbstractJpaTupleElement<T>
		extends AbstractSqmNode
		implements SqmVisitableNode, JpaTupleElement<T> {

	private @Nullable SqmBindableType<T> expressibleType;
	private @Nullable String alias;

	protected AbstractJpaTupleElement(@Nullable SqmBindableType<? super T> expressibleType, NodeBuilder criteriaBuilder) {
		super( criteriaBuilder );
		setExpressibleType( expressibleType );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void copyTo(AbstractJpaTupleElement<T> target, SqmCopyContext context) {
		target.alias = alias;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable String getAlias() {
		return alias;
	}

	/**
	 * Protected access to set the alias.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void setAlias(@Nullable String alias) {
		this.alias = alias;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable SqmBindableType<T> getNodeType() {
		return expressibleType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected final void setExpressibleType(
			// This is fine, since this method is final
			AbstractJpaTupleElement<T> this,
			@Nullable SqmBindableType<?> expressibleType) {
		//noinspection unchecked
		this.expressibleType = (SqmBindableType<T>) expressibleType;
	}

}
