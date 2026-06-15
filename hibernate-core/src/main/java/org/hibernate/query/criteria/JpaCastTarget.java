/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import jakarta.persistence.metamodel.Type;
import jakarta.annotation.Nullable;
import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * The target for cast.
 *
 * @since 7.0
 */
@Incubating
public interface JpaCastTarget<T> {

	/**
	 * Returns the JPA type for this cast target.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Type<T> getType();

	/**
	 * Returns the specified length of the cast target or {@code null}.
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	Long getLength();

	/**
	 * Returns the specified precision of the cast target or {@code null}.
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getPrecision();

	/**
	 * Returns the specified scale of the cast target or {@code null}.
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getScale();
}
