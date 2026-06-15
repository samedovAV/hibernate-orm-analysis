/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import jakarta.persistence.TupleElement;
import jakarta.annotation.Nullable;

import org.hibernate.type.descriptor.java.EnumJavaType;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * API extension to the JPA {@link TupleElement} contract
 *
 * @author Steve Ebersole
 */
public interface JpaTupleElement<T> extends TupleElement<T>, JpaCriteriaNode {
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JavaType<T> getJavaTypeDescriptor();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable Class<T> getJavaType() {
		final var javaType = getJavaTypeDescriptor();
		return javaType == null ? null : javaType.getJavaTypeClass();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getJavaTypeName() {
		final var javaType = getJavaTypeDescriptor();
		return javaType == null ? null : javaType.getTypeName();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isEnum() {
		return getJavaTypeDescriptor() instanceof EnumJavaType;
	}
}
