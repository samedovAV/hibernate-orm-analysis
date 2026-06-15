/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.usertype;

import java.util.Comparator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A user type that may be used for a version property
 *
 * @author Gavin King
 */
public interface UserVersionType<T> extends UserType<T>, Comparator<T> {
	/**
	 * Generate an initial version.
	 *
	 * @param session The session from which this request originates.  May be
	 * null; currently this only happens during startup when trying to determine
	 * the "unsaved value" of entities.
	 * @return an instance of the type
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T seed(SharedSessionContractImplementor session);

	/**
	 * Increment the version.
	 *
	 * @param session The session from which this request originates.
	 * @param current the current version
	 * @return an instance of the type
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T next(T current, SharedSessionContractImplementor session);
}
