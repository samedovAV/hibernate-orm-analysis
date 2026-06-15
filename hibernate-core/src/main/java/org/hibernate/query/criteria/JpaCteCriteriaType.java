/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import java.util.List;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.hibernate.Incubating;
import org.hibernate.metamodel.model.domain.DomainType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A CTE (common table expression) criteria type.
 */
@Incubating
public interface JpaCteCriteriaType<T> extends JpaCriteriaNode {

	/**
	 * The name under which this CTE is registered.
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();

	/**
	 * The domain type of the CTE.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DomainType<T> getType();

	/**
	 * The attributes of the CTE type.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JpaCteCriteriaAttribute> getAttributes();

	/**
	 * Returns the found attribute or null.
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCteCriteriaAttribute getAttribute(String name);
}
