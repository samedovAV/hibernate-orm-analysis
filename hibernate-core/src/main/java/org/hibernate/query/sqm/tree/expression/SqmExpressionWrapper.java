/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.expression;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 */
public interface SqmExpressionWrapper<T> extends SqmExpression<T> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<T> getWrappedExpression();
}
