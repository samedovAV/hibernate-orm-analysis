/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.java.spi;

import java.io.Serializable;

import org.hibernate.type.descriptor.java.BasicJavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Additional contract for primitive / primitive wrapper Java types.
 *
 * @author Steve Ebersole
 */
public interface PrimitiveJavaType<J extends Serializable> extends BasicJavaType<J> {
	/**
	 * Retrieve the primitive counterpart to the wrapper type identified by
	 * this descriptor
	 *
	 * @return The primitive Java type.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<?> getPrimitiveClass();

	/**
	 * Get the Java type that describes an array of this type.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<J[]> getArrayClass();

	/**
	 * Get the Java type that describes an array of this type's primitive variant.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<?> getPrimitiveArrayClass();
}
