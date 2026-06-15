/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import org.hibernate.sql.ast.tree.predicate.Predicate;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models an aggregate function expression at the SQL AST level.
 *
 * @author Christian Beikov
 */
public interface AggregateFunctionExpression extends FunctionExpression {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Predicate getFilter();
}
