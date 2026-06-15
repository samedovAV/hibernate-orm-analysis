/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Common descriptor for types in the mapping model - entities, embeddables, String, Integer, etc
 *
 * @author Steve Ebersole
 */
public interface MappingType {
	/**
	 * The {@linkplain JavaType descriptor} descriptor for the mapped Java type
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JavaType<?> getMappedJavaType();
}
