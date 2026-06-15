/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain.internal;

import jakarta.annotation.Nonnull;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class PrimitiveBasicTypeImpl<J> extends BasicTypeImpl<J> {
	private final Class<J> primitiveClass;

	public PrimitiveBasicTypeImpl(JavaType<J> javaType, JdbcType jdbcType, Class<J> primitiveClass) {
		super( javaType, jdbcType );
		assert primitiveClass.isPrimitive();
		this.primitiveClass = primitiveClass;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<J> getJavaType() {
		return primitiveClass;
	}
}
