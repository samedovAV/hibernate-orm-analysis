/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.naming;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Context for determining the implicit name of an entity's tenant identifier
 * column.
 *
 * @author Steve Ebersole
 */
public interface ImplicitTenantIdColumnNameSource extends ImplicitNameSource {
	/**
	 * Access the entity name information
	 *
	 * @return The entity name information
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityNaming getEntityNaming();
}
