/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.predicate;

import org.hibernate.query.criteria.JpaInPredicate;
import org.hibernate.query.sqm.tree.expression.SqmExpression;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface SqmInPredicate<T> extends SqmNegatablePredicate, JpaInPredicate<T> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<T> getTestExpression();
}
