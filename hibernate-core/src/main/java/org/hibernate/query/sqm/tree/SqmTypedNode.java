/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree;

import org.hibernate.query.sqm.SqmBindableType;
import org.hibernate.query.sqm.SqmExpressible;
import org.hibernate.type.descriptor.java.JavaType;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Optional contract for {@link SqmNode} implementations which are typed.
 *
 * @author Steve Ebersole
 */
public interface SqmTypedNode<T> extends SqmNode, SqmExpressibleAccessor<T>, SqmVisitableNode {
	/**
	 * The Java type descriptor for this node.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable JavaType<T> getNodeJavaType() {
		final SqmExpressible<T> nodeType = getNodeType();
		return nodeType == null ? null : nodeType.getExpressibleJavaType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable SqmBindableType<T> getExpressible() {
		return getNodeType();
	}

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmBindableType<T> getNodeType();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmTypedNode<T> copy(SqmCopyContext context);
}
