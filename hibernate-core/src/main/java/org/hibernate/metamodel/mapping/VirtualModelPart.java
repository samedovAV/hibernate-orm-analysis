/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Marker interface for parts of the application domain model that do not actually
 * exist in the model classes.
 *
 * @see #isVirtual()
 *
 * @author Steve Ebersole
 */
public interface VirtualModelPart extends ModelPart {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isVirtual() {
		return true;
	}
}
