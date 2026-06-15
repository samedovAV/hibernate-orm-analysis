/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Marker interface for valued model parts that have a declaring/owner type.
 */
public interface OwnedValuedModelPart extends ValuedModelPart {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MappingType getDeclaringType();
}
