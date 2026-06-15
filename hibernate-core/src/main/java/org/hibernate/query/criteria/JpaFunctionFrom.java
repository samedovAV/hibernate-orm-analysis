/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @since 7.0
 */
@Incubating
public interface JpaFunctionFrom<O, T> extends JpaFrom<O, T> {

	/**
	 * The function for this from node.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSetReturningFunction<T> getFunction();

	/**
	 * The expression referring to an iteration variable, indexing the rows produced by the function.
	 * This is the equivalent of the SQL {@code with ordinality} clause.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<Long> index();

}
