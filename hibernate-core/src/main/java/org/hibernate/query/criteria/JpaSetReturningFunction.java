/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import jakarta.annotation.Nonnull;
import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A set returning function criteria.
 */
@Incubating
public interface JpaSetReturningFunction<T> extends JpaCriteriaNode {

	/**
	 * The name of the function.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getFunctionName();

}
