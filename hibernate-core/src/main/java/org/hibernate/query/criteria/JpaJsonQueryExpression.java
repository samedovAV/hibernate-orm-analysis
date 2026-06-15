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
 * A special expression for the {@code json_query} function.
 * @since 7.0
 */
@Incubating
public interface JpaJsonQueryExpression extends JpaExpression<String>, JpaJsonQueryNode {

	/**
	 * Passes the given {@link Expression} as value for the parameter with the given name in the JSON path.
	 *
	 * @return {@code this} for method chaining
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonQueryExpression passing(String parameterName, Expression<?> expression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonQueryExpression withoutWrapper();
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonQueryExpression withWrapper();
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonQueryExpression withConditionalWrapper();
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonQueryExpression unspecifiedWrapper();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonQueryExpression unspecifiedOnError();
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonQueryExpression errorOnError();
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonQueryExpression nullOnError();
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonQueryExpression emptyArrayOnError();
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonQueryExpression emptyObjectOnError();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonQueryExpression unspecifiedOnEmpty();
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonQueryExpression errorOnEmpty();
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonQueryExpression nullOnEmpty();
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonQueryExpression emptyArrayOnEmpty();
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaJsonQueryExpression emptyObjectOnEmpty();

}
