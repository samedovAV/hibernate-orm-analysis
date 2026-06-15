/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.action.queue.internal.constraint;


import org.hibernate.metamodel.mapping.SelectableMappings;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// @author Steve Ebersole
public interface Constraint {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getConstrainedTableName();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectableMappings getConstrainedColumnMappings();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isNullable();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Deferrability getDeferrability();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isDeferrable();
}
