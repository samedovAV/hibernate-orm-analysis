/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.naming;

import org.hibernate.boot.model.source.spi.AttributePath;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Context for determining the implicit name for a collection table.
 *
 * @author Steve Ebersole
 *
 * @see jakarta.persistence.CollectionTable
 */
public interface ImplicitCollectionTableNameSource extends ImplicitNameSource {
	/**
	 * Access to the physical name of the owning entity's table.
	 *
	 * @return Owning entity's table name.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Identifier getOwningPhysicalTableName();

	/**
	 * Access to entity naming information for the owning side.
	 *
	 * @return Owning entity naming information
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityNaming getOwningEntityNaming();

	/**
	 * Access to the name of the attribute, from the owning side, that defines the association.
	 *
	 * @return The owning side's attribute name.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AttributePath getOwningAttributePath();
}
