/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query;

import jakarta.annotation.Nullable;
import org.hibernate.Incubating;
import org.hibernate.type.BindableType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Represents a parameter defined in the source (HQL/JPQL or criteria) query.
 *
 * @author Steve Ebersole
 */
@Incubating
public interface QueryParameter<T> extends jakarta.persistence.Parameter<T> {
	/**
	 * Determine if this a named parameter or ordinal.
	 *
	 * @return {@code true} if it is a named parameter;
	 *         {@code false} if it is ordinal
	 *
	 * @since 7.0
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isNamed() {
		return getName() != null;
	}

	/**
	 * Determine if this a named parameter or ordinal.
	 *
	 * @return {@code true} if it is an ordinal parameter;
	 *         {@code false} if it is named
	 *
	 * @since 7.0
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isOrdinal() {
		return getPosition() != null;
	}

	/**
	 * Does this parameter allow multi-valued (collection, array, etc) binding?
	 * <p>
	 * This is only valid for HQL/JPQL and (I think) Criteria queries, and is
	 * determined based on the context of the parameters declaration.
	 *
	 * @return {@code true} indicates that multi-valued binding is allowed for this
	 * parameter
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean allowsMultiValuedBinding();

	/**
	 * Get the Hibernate Type associated with this parameter, if one.  May
	 * return {@code null}.
	 *
	 * @return The associated Hibernate Type, may be {@code null}.
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	BindableType<T> getHibernateType();
}
