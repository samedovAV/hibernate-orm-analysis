/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.internal;

import org.hibernate.type.descriptor.converter.spi.BasicValueConverter;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.java.MutabilityPlan;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class CustomMutabilityConvertedBasicTypeImpl<J> extends ConvertedBasicTypeImpl<J> {

	private final MutabilityPlan<J> mutabilityPlan;

	public CustomMutabilityConvertedBasicTypeImpl(
			String name,
			JdbcType jdbcType,
			BasicValueConverter<J, ?> converter,
			MutabilityPlan<J> mutabilityPlan) {
		super( name, jdbcType, converter );
		this.mutabilityPlan = mutabilityPlan;
	}

	public CustomMutabilityConvertedBasicTypeImpl(
			String name,
			String description,
			JdbcType jdbcType,
			BasicValueConverter<J, ?> converter,
			MutabilityPlan<J> mutabilityPlan) {
		super( name, description, jdbcType, converter );
		this.mutabilityPlan = mutabilityPlan;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected MutabilityPlan<J> getMutabilityPlan() {
		return mutabilityPlan;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JavaType<?> getRelationalJavaType() {
		return getValueConverter().getRelationalJavaType();
	}
}
