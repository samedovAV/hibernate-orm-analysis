/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree;

import org.hibernate.query.sqm.SemanticQueryWalker;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Optional contract for SqmNode implementations that can be visited
 * by a SemanticQueryWalker.
 *
 * @author Steve Ebersole
 */
public interface SqmVisitableNode extends SqmNode {
	/**
	 * Accept the walker per visitation
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> X accept(SemanticQueryWalker<X> walker);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void appendHqlString(StringBuilder hql, SqmRenderContext context);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String toHqlString() {
		final StringBuilder hql = new StringBuilder();
		appendHqlString( hql, SqmRenderContext.simpleContext() );
		return hql.toString();
	}
}
