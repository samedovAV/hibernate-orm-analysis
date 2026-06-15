/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.expression;

import org.hibernate.query.sqm.tree.select.SqmOrderByClause;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A SQM ordered set-aggregate function.
 *
 * @param <T> The Java type of the expression
 *
 * @author Christian Beikov
 */
public interface SqmOrderedSetAggregateFunction<T> extends SqmAggregateFunction<T> {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmOrderByClause getWithinGroup();
}
