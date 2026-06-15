/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import java.util.List;

import org.hibernate.sql.ast.tree.select.SortSpecification;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models an ordered set-aggregate function expression at the SQL AST level.
 *
 * @author Christian Beikov
 */
public interface OrderedSetAggregateFunctionExpression extends AggregateFunctionExpression {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<SortSpecification> getWithinGroup();
}
