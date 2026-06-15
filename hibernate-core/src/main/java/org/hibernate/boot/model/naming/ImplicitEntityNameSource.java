/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.naming;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Context for determining the implicit name of an entity's primary table
 *
 * @author Steve Ebersole
 */
public interface ImplicitEntityNameSource extends ImplicitNameSource {
	/**
	 * Access to the entity's name information
	 *
	 * @return The entity's name information
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityNaming getEntityNaming();
}
