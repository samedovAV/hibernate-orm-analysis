/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.jdbc;

import org.hibernate.Incubating;
import org.hibernate.type.TimeZoneStorageStrategy;
import org.hibernate.dialect.Dialect;
import org.hibernate.type.spi.TypeConfiguration;

import jakarta.persistence.EnumType;
import jakarta.persistence.TemporalType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class DelegatingJdbcTypeIndicators implements JdbcTypeIndicators {

	private final JdbcTypeIndicators delegate;

	public DelegatingJdbcTypeIndicators(JdbcTypeIndicators delegate) {
		this.delegate = delegate;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isNationalized() {
		return delegate.isNationalized();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isLob() {
		return delegate.isLob();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public EnumType getEnumeratedType() {
		return delegate.getEnumeratedType();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TemporalType getTemporalPrecision() {
		return delegate.getTemporalPrecision();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isPreferJavaTimeJdbcTypesEnabled() {
		return delegate.isPreferJavaTimeJdbcTypesEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isPreferNativeEnumTypesEnabled() {
		return delegate.isPreferNativeEnumTypesEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getPreferredSqlTypeCodeForBoolean() {
		return delegate.getPreferredSqlTypeCodeForBoolean();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getPreferredSqlTypeCodeForDuration() {
		return delegate.getPreferredSqlTypeCodeForDuration();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getPreferredSqlTypeCodeForUuid() {
		return delegate.getPreferredSqlTypeCodeForUuid();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getPreferredSqlTypeCodeForInstant() {
		return delegate.getPreferredSqlTypeCodeForInstant();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getPreferredSqlTypeCodeForArray() {
		return delegate.getPreferredSqlTypeCodeForArray();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public long getColumnLength() {
		return delegate.getColumnLength();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getColumnPrecision() {
		return delegate.getColumnPrecision();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getColumnScale() {
		return delegate.getColumnScale();
	}

	@Override
	@Incubating
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Integer getExplicitJdbcTypeCode() {
		return delegate.getExplicitJdbcTypeCode();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TimeZoneStorageStrategy getDefaultTimeZoneStorageStrategy() {
		return delegate.getDefaultTimeZoneStorageStrategy();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JdbcType getJdbcType(int jdbcTypeCode) {
		return delegate.getJdbcType( jdbcTypeCode );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int resolveJdbcTypeCode(int jdbcTypeCode) {
		return delegate.resolveJdbcTypeCode( jdbcTypeCode );
	}

	@Override
	@Incubating
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isXmlFormatMapperLegacyFormatEnabled() {
		return delegate.isXmlFormatMapperLegacyFormatEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean preferJdbcDatetimeTypes() {
		return delegate.preferJdbcDatetimeTypes();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getPreferredSqlTypeCodeForArray(int elementSqlTypeCode) {
		return delegate.getPreferredSqlTypeCodeForArray( elementSqlTypeCode );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TypeConfiguration getTypeConfiguration() {
		return delegate.getTypeConfiguration();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getDefaultZonedTimeSqlType() {
		return delegate.getDefaultZonedTimeSqlType();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getDefaultZonedTimestampSqlType() {
		return delegate.getDefaultZonedTimestampSqlType();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Dialect getDialect() {
		return delegate.getDialect();
	}
}
