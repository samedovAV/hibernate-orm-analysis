/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import org.hibernate.engine.spi.CascadeStyle;
import org.hibernate.engine.spi.CascadeStyles;
import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.type.descriptor.java.MutabilityPlan;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface AttributeMetadata {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PropertyAccess getPropertyAccess();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutabilityPlan<?> getMutabilityPlan();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isNullable();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isInsertable();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isUpdatable();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isSelectable();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isIncludedInDirtyChecking();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isIncludedInOptimisticLocking();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default CascadeStyle getCascadeStyle() {
		// todo (6.0) - implement in each subclass.
		//		For now return a default NONE value for all contributors since this isn't
		//		to be supported as a part of Alpha1.
		return CascadeStyles.NONE;
	}
}
