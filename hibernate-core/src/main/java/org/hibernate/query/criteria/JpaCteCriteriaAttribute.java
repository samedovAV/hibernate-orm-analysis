/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Describes the attribute of a {@link JpaCteCriteriaType}.
 */
@Incubating
public interface JpaCteCriteriaAttribute extends JpaCriteriaNode {

	/**
	 * The declaring type.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCteCriteriaType<?> getDeclaringType();

	/**
	 * The name of the attribute.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();

	/**
	 * The java type of the attribute.
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<?> getJavaType();
}
