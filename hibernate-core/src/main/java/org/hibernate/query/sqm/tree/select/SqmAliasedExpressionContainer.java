/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.select;

import org.hibernate.query.sqm.tree.expression.SqmExpression;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface SqmAliasedExpressionContainer<T extends SqmAliasedNode<?>> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T add(SqmExpression<?> expression, String alias);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void add(T aliasExpression);
}
