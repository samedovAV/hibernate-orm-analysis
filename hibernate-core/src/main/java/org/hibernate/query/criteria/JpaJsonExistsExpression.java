/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import org.hibernate.Incubating;

import jakarta.persistence.criteria.Expression;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A special expression for the {@code json_exists} function.
 * @since 7.0
 */
@Incubating
public interface JpaJsonExistsExpression extends JpaExpression<Boolean>, JpaJsonExistsNode {

	/**
	 * Passes the given {@link Expression} as value for the parameter with the given name in the JSON path.
	 *
	 * @return {@code this} for method chaining
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonExistsExpression passing(String parameterName, Expression<?> expression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonExistsExpression unspecifiedOnError();
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonExistsExpression errorOnError();
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonExistsExpression trueOnError();
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonExistsExpression falseOnError();
}
