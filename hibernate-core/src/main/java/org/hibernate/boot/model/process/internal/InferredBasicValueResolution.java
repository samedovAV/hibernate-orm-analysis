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
public class InferredBasicValueResolution<J,T> implements BasicValue.Resolution<J> {
	private final JavaType<J> domainJtd;
	private final JavaType<T> relationalJtd;
	private final JdbcType jdbcType;

	private final MutabilityPlan<J> mutabilityPlan;

	private final JdbcMapping jdbcMapping;

	private final BasicType<J> legacyType;
	private BasicType<J> updatedType;

	public InferredBasicValueResolution(
			JdbcMapping jdbcMapping,
			JavaType<J> domainJtd,
			JavaType<T> relationalJtd,
			JdbcType jdbcType,
			BasicType<J> legacyType,
			MutabilityPlan<J> mutabilityPlan) {
		this.jdbcMapping = jdbcMapping;
		this.legacyType = legacyType;
		this.domainJtd = domainJtd;
		this.relationalJtd = relationalJtd;
		this.jdbcType = jdbcType;
		this.mutabilityPlan = mutabilityPlan == null ? domainJtd.getMutabilityPlan() : mutabilityPlan;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMapping getJdbcMapping() {
		return updatedType == null ? jdbcMapping : updatedType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public BasicType<J> getLegacyResolvedBasicType() {
		return updatedType == null ? legacyType : updatedType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<J> getDomainJavaType() {
		return domainJtd;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<?> getRelationalJavaType() {
		return relationalJtd;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JdbcType getJdbcType() {
		return updatedType == null ? jdbcType : updatedType.getJdbcType();
	}

	@Override @SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public BasicValueConverter<J,T> getValueConverter() {
		return updatedType == null
				? (BasicValueConverter<J, T>) jdbcMapping.getValueConverter()
				: (BasicValueConverter<J, T>) updatedType.getValueConverter();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MutabilityPlan<J> getMutabilityPlan() {
		return mutabilityPlan;
	}

	@Override @SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void updateResolution(BasicType<?> type) {
		updatedType = (BasicType<J>) type;
	}
}
