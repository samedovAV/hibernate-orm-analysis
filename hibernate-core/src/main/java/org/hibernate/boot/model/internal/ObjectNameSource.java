/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.internal;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Source for database object names (identifiers).
 *
 * @author Steve Ebersole
 */
public interface ObjectNameSource {
	/**
	 * Retrieve the name explicitly provided by the user.
	 *
	 * @return The explicit name.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getExplicitName();

	/**
	 * Retrieve the logical name for this object.  Usually this is the name under which
	 * the "thing" is registered.
	 *
	 * @return The logical name.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getLogicalName();
}
