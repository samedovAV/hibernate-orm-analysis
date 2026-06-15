/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import java.util.List;

import jakarta.annotation.Nonnull;
import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A tuple of values.
 *
 * @since 6.5
 */
@Incubating
public interface JpaValues {

	/**
	 * Returns the expressions of this tuple.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<? extends JpaExpression<?>> getExpressions();
}
