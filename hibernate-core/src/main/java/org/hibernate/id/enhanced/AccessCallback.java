/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.id.enhanced;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Contract for providing callback access to a {@link DatabaseStructure},
 * typically from the {@link Optimizer}.
 *
 * @author Steve Ebersole
 */
public interface AccessCallback {
	/**
	 * Retrieve the next value from the underlying source.
	 *
	 * @return The next value.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getNextValue();

	/**
	 * Obtain the tenant identifier (multi-tenancy), if one, associated with this callback.
	 *
	 * @return The tenant identifier
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTenantIdentifier();
}
