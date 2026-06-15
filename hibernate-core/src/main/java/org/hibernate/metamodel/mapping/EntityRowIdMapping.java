/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Mapping of a row-id
 *
 * @see org.hibernate.annotations.RowId
 */
public interface EntityRowIdMapping extends BasicValuedModelPart, VirtualModelPart, SelectableMapping {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getRowIdName();
}
