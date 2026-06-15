/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.expression;

import org.hibernate.query.criteria.JpaFunction;
import org.hibernate.query.sqm.tree.predicate.SqmPredicate;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A SQM aggregate function.
 *
 * @param <T> The Java type of the expression
 *
 * @author Christian Beikov
 */
public interface SqmAggregateFunction<T> extends JpaFunction<T>, SqmExpression<T> {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate getFilter();
}
