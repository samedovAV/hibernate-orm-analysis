/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Model part which can be soft-deleted
 *
 * @author Steve Ebersole
 */
public interface SoftDeletableModelPart extends ModelPartContainer {
	/**
	 * Get the mapping of the soft-delete indicator
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SoftDeleteMapping getSoftDeleteMapping();

	/**
	 * Details about the table which holds the soft-delete column.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TableDetails getSoftDeleteTableDetails();
}
