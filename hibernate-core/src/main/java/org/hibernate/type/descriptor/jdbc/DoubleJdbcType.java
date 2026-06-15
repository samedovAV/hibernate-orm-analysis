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

import org.hibernate.query.sqm.CastType;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.internal.JdbcLiteralFormatterNumericData;
import org.hibernate.type.spi.TypeConfiguration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Descriptor for {@link Types#DOUBLE DOUBLE} handling.
 *
 * @author Steve Ebersole
 */
public class DoubleJdbcType implements JdbcType {
	public static final DoubleJdbcType INSTANCE = new DoubleJdbcType();

	public DoubleJdbcType() {
	}

	/**
	 * @return {@link Types#DOUBLE}
	 */
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getJdbcTypeCode() {
		return Types.DOUBLE;
	}

	/**
	 * @return {@link Types#FLOAT} for schema generation
	 */
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getDdlTypeCode() {
		return Types.FLOAT;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CastType getCastType() {
		return CastType.DOUBLE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getFriendlyName() {
		return "DOUBLE";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "DoubleTypeDescriptor";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<?> getRecommendedJavaType(
			Integer length,
			Integer scale,
			TypeConfiguration typeConfiguration) {
		return typeConfiguration.getJavaTypeRegistry().resolveDescriptor( Double.class );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <T> JdbcLiteralFormatter<T> getJdbcLiteralFormatter(JavaType<T> javaType) {
		return new JdbcLiteralFormatterNumericData<>( javaType, Double.class );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getPreferredJavaTypeClass(WrapperOptions options) {
		return Double.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> ValueBinder<X> getBinder(final JavaType<X> javaType) {
		return new BasicBinder<>( javaType, this ) {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options) throws SQLException {
				st.setDouble( index, javaType.unwrap( value, Double.class, options ) );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
					throws SQLException {
				st.setDouble( name, javaType.unwrap( value, Double.class, options ) );
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
				return javaType.wrap( rs.getDouble( paramIndex ), options );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(CallableStatement statement, int index, WrapperOptions options) throws SQLException {
				return javaType.wrap( statement.getDouble( index ), options );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(CallableStatement statement, String name, WrapperOptions options) throws SQLException {
				return javaType.wrap( statement.getDouble( name ), options );
			}
		};
	}
}
