/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.internal;

import jakarta.annotation.Nonnull;
import org.hibernate.type.descriptor.converter.spi.BasicValueConverter;
import org.hibernate.type.descriptor.java.MutabilityPlan;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Wrapper of {@link CustomMutabilityConvertedBasicTypeImpl} for primitive type.
 *
 * @author Marco Belladelli
 */
public class CustomMutabilityConvertedPrimitiveBasicTypeImpl<J> extends CustomMutabilityConvertedBasicTypeImpl<J> {
	private final Class<J> primitiveClass;

	public CustomMutabilityConvertedPrimitiveBasicTypeImpl(
			String name,
			String description,
			JdbcType jdbcType,
			BasicValueConverter<J, ?> converter,
			Class<J> primitiveClass,
			MutabilityPlan<J> mutabilityPlan) {
		super( name, description, jdbcType, converter, mutabilityPlan );
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
