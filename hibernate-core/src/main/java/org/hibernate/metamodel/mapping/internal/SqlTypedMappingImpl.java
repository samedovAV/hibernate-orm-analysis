/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.internal;

import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.mapping.SqlTypedMapping;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class SqlTypedMappingImpl implements SqlTypedMapping {

	private final @Nullable Long length;
	private final @Nullable Integer arrayLength;
	private final @Nullable Integer precision;
	private final @Nullable Integer scale;
	private final @Nullable Integer temporalPrecision;
	private final JdbcMapping jdbcMapping;

	public SqlTypedMappingImpl(JdbcMapping jdbcMapping) {
		this( null, null, null, null, null, jdbcMapping );
	}

	public SqlTypedMappingImpl(
			@Nullable Long length,
			@Nullable Integer precision,
			@Nullable Integer scale,
			@Nullable Integer temporalPrecision,
			JdbcMapping jdbcMapping) {
		this( length, null, precision, scale, temporalPrecision, jdbcMapping );
	}

	public SqlTypedMappingImpl(
			@Nullable Long length,
			@Nullable Integer arrayLength,
			@Nullable Integer precision,
			@Nullable Integer scale,
			@Nullable Integer temporalPrecision,
			JdbcMapping jdbcMapping) {
		this.length = length;
		this.arrayLength = arrayLength;
		this.precision = precision;
		this.scale = scale;
		this.temporalPrecision = temporalPrecision;
		this.jdbcMapping = jdbcMapping;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Long getLength() {
		return length;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Integer getArrayLength() {
		return arrayLength;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Integer getPrecision() {
		return precision;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Integer getTemporalPrecision() {
		return temporalPrecision;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Integer getScale() {
		return scale;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMapping getJdbcMapping() {
		return jdbcMapping;
	}
}
