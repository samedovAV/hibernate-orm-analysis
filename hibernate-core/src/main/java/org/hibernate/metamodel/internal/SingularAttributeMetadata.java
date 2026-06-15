/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.internal;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Attribute metadata contract for a non-plural attribute.
 *
 * @param <X> The owner type
 * @param <Y> The attribute type
 */
public interface SingularAttributeMetadata<X, Y> extends AttributeMetadata<X, Y> {
	/**
	 * Retrieve the value context for this attribute
	 *
	 * @return The attributes value context
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ValueContext getValueContext();
}
