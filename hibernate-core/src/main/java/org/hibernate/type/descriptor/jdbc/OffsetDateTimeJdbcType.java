/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.jdbc;

import java.time.OffsetDateTime;

import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.internal.AbstractJavaTimeJdbcType;
import org.hibernate.type.descriptor.jdbc.internal.JdbcLiteralFormatterTemporal;

import jakarta.persistence.TemporalType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Descriptor for handling {@linkplain OffsetDateTime} directly through the JDBC driver
 *
 * @author Steve Ebersole
 */
public class OffsetDateTimeJdbcType extends AbstractJavaTimeJdbcType<OffsetDateTime>  {
	public static OffsetDateTimeJdbcType INSTANCE = new OffsetDateTimeJdbcType();

	public OffsetDateTimeJdbcType() {
		super( OffsetDateTime.class );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getJdbcTypeCode() {
		return SqlTypes.OFFSET_DATE_TIME;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getDdlTypeCode() {
		return SqlTypes.TIMESTAMP_WITH_TIMEZONE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <T> JdbcLiteralFormatter<T> getJdbcLiteralFormatter(JavaType<T> javaType) {
		return new JdbcLiteralFormatterTemporal<>( javaType, TemporalType.TIMESTAMP );
	}
}
