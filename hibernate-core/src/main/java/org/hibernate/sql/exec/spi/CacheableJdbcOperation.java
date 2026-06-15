/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.exec.spi;

import org.hibernate.query.spi.QueryOptions;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Optional contract for {@linkplain JdbcOperation} implementors allowing them
 * to be used with Query caching.
 *
 * @author Steve Ebersole
 */
public interface CacheableJdbcOperation {
	/**
	 * Signals that the SQL depends on the parameter bindings - e.g., due to the need for inlining
	 * of parameter values or multiValued parameters.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean dependsOnParameterBindings();

	/**
	 * Whether the given arguments are compatible with this operation's state.  Or,
	 * conversely, whether the arguments preclude this operation from being a cache-hit.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isCompatibleWith(JdbcParameterBindings jdbcParameterBindings, QueryOptions queryOptions);
}
