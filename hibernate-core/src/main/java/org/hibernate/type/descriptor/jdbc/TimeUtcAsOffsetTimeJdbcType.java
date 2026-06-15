/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.jdbc;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.OffsetTime;
import java.time.ZoneOffset;

import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.internal.JdbcLiteralFormatterTemporal;
import org.hibernate.type.spi.TypeConfiguration;

import jakarta.persistence.TemporalType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Descriptor for {@link SqlTypes#TIME_UTC TIME_UTC} handling.
 *
 * @author Christian Beikov
 */
public class TimeUtcAsOffsetTimeJdbcType implements JdbcType {

	public static final TimeUtcAsOffsetTimeJdbcType INSTANCE = new TimeUtcAsOffsetTimeJdbcType();

	public TimeUtcAsOffsetTimeJdbcType() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getJdbcTypeCode() {
		return Types.TIME_WITH_TIMEZONE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getDefaultSqlTypeCode() {
		return SqlTypes.TIME_UTC;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getFriendlyName() {
		return "TIME_UTC";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "TimeUtcDescriptor";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<?> getRecommendedJavaType(
			Integer length,
			Integer scale,
			TypeConfiguration typeConfiguration) {
		return typeConfiguration.getJavaTypeRegistry().resolveDescriptor( OffsetTime.class );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getPreferredJavaTypeClass(WrapperOptions options) {
		return OffsetTime.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <T> JdbcLiteralFormatter<T> getJdbcLiteralFormatter(JavaType<T> javaType) {
		return new JdbcLiteralFormatterTemporal<>( javaType, TemporalType.TIME );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> ValueBinder<X> getBinder(final JavaType<X> javaType) {
		return new BasicBinder<>( javaType, this ) {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options) throws SQLException {
				final OffsetTime offsetTime = javaType.unwrap( value, OffsetTime.class, options );
				st.setObject( index, offsetTime.withOffsetSameInstant( ZoneOffset.UTC ), Types.TIME_WITH_TIMEZONE );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
					throws SQLException {
				final OffsetTime offsetTime = javaType.unwrap( value, OffsetTime.class, options );
				st.setObject( name, offsetTime.withOffsetSameInstant( ZoneOffset.UTC ), Types.TIME_WITH_TIMEZONE );
			}
		};
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> ValueExtractor<X> getExtractor(final JavaType<X> javaType) {
		return new BasicExtractor<>( javaType, this ) {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(ResultSet rs, int paramIndex, WrapperOptions options) throws SQLException {
				return javaType.wrap( rs.getObject( paramIndex, OffsetTime.class ), options );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(CallableStatement statement, int index, WrapperOptions options) throws SQLException {
				return javaType.wrap( statement.getObject( index, OffsetTime.class ), options );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(CallableStatement statement, String name, WrapperOptions options) throws SQLException {
				return javaType.wrap( statement.getObject( name, OffsetTime.class ), options );
			}
		};
	}
}
