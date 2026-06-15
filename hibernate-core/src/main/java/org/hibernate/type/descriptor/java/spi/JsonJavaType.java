/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.java.spi;

import java.lang.reflect.Type;

import org.hibernate.Incubating;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.java.MutabilityPlan;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.JdbcTypeIndicators;
import org.hibernate.type.format.FormatMapper;
import org.hibernate.type.spi.TypeConfiguration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@Incubating
public class JsonJavaType<T> extends FormatMapperBasedJavaType<T> {

	public JsonJavaType(
			Type type,
			MutabilityPlan<T> mutabilityPlan,
			TypeConfiguration typeConfiguration) {
		super( type, mutabilityPlan, typeConfiguration );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected FormatMapper getFormatMapper(TypeConfiguration typeConfiguration) {
		return typeConfiguration.getJsonFormatMapper();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcType getRecommendedJdbcType(JdbcTypeIndicators context) {
		return context.getJdbcType( SqlTypes.JSON );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "JsonJavaType(" + getTypeName() + ")";
	}
}
