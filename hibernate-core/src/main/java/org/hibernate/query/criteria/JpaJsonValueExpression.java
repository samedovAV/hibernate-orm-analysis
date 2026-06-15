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
 * A special expression for the {@code json_value} function.
 * @since 7.0
 */
@Incubating
public interface JpaJsonValueExpression<T> extends JpaExpression<T>, JpaJsonValueNode<T> {

	/**
	 * Passes the given {@link Expression} as value for the parameter with the given name in the JSON path.
	 *
	 * @return {@code this} for method chaining
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonValueExpression<T> passing(String parameterName, Expression<?> expression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonValueExpression<T> unspecifiedOnError();
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonValueExpression<T> errorOnError();
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonValueExpression<T> nullOnError();
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonValueExpression<T> defaultOnError(Expression<?> expression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonValueExpression<T> unspecifiedOnEmpty();
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonValueExpression<T> errorOnEmpty();
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonValueExpression<T> nullOnEmpty();
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonValueExpression<T> defaultOnEmpty(Expression<?> expression);

}
