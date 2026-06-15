/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.spi;

import org.hibernate.boot.model.relational.ContributableDatabaseObject;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Matcher for whether tables and sequences should be included based on its
 * {@link ContributableDatabaseObject#getContributor()}
 */
@FunctionalInterface
public interface ContributableMatcher {
	/**
	 * Matches everything
	 */
	ContributableMatcher ALL = contributed -> true;
	/**
	 * Matches nothing
	 */
	ContributableMatcher NONE = contributed -> false;

	/**
	 * Does the given `contributed` match this matcher?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean matches(ContributableDatabaseObject contributed);
}
