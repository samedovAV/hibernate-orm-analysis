/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.process.internal;

import org.hibernate.mapping.BasicValue;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.type.descriptor.converter.spi.BasicValueConverter;
import org.hibernate.type.BasicType;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.java.MutabilityPlan;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class NamedBasicTypeResolution<J> implements BasicValue.Resolution<J> {
	private final JavaType<J> domainJtd;

	private final BasicType<J> basicType;

	private final BasicValueConverter<J,?> valueConverter;
	private final MutabilityPlan<J> mutabilityPlan;

	public NamedBasicTypeResolution(
			JavaType<J> domainJtd,
			BasicType<J> basicType,
			BasicValueConverter<J,?> valueConverter,
			MutabilityPlan<J> explicitPlan) {
		this.domainJtd = domainJtd;
		this.basicType = basicType;
		// todo (6.0) : does it even make sense to allow a combo of explicit Type and a converter?
		this.valueConverter = valueConverter;
		this.mutabilityPlan = explicitPlan != null ? explicitPlan : domainJtd.getMutabilityPlan();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMapping getJdbcMapping() {
		return basicType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public BasicType<J> getLegacyResolvedBasicType() {
		return basicType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<J> getDomainJavaType() {
		return domainJtd;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JavaType<?> getRelationalJavaType() {
		return valueConverter == null
				? basicType.getJavaTypeDescriptor()
				: valueConverter.getRelationalJavaType();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JdbcType getJdbcType() {
		return basicType.getJdbcType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public BasicValueConverter<J,?> getValueConverter() {
		return valueConverter;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MutabilityPlan<J> getMutabilityPlan() {
		return mutabilityPlan;
	}
}
