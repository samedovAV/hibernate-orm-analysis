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

import org.hibernate.dialect.Dialect;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.internal.JdbcLiteralFormatterBinary;
import org.hibernate.type.spi.TypeConfiguration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Descriptor for {@link Types#VARBINARY VARBINARY} handling.
 *
 * @author Steve Ebersole
 */
public class VarbinaryJdbcType implements AdjustableJdbcType {
	public static final VarbinaryJdbcType INSTANCE = new VarbinaryJdbcType();
	public static final VarbinaryJdbcType INSTANCE_WITHOUT_LITERALS = new VarbinaryJdbcType( false );

	private final boolean supportsLiterals;

	public VarbinaryJdbcType() {
		this( true );
	}

	public VarbinaryJdbcType(boolean supportsLiterals) {
		this.supportsLiterals = supportsLiterals;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getFriendlyName() {
		return "VARBINARY";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "VarbinaryTypeDescriptor";
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getJdbcTypeCode() {
		return Types.VARBINARY;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<?> getRecommendedJavaType(
			Integer length,
			Integer scale,
			TypeConfiguration typeConfiguration) {
		return typeConfiguration.getJavaTypeRegistry().resolveDescriptor( byte[].class );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getPreferredJavaTypeClass(WrapperOptions options) {
		return byte[].class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <T> JdbcLiteralFormatter<T> getJdbcLiteralFormatter(JavaType<T> javaType) {
		return supportsLiterals ? new JdbcLiteralFormatterBinary<>( javaType ) : null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcType resolveIndicatedType(JdbcTypeIndicators indicators, JavaType<?> domainJtd) {
		final var jdbcTypeRegistry = indicators.getTypeConfiguration().getJdbcTypeRegistry();
		if ( indicators.isLob() ) {
			return jdbcTypeRegistry.getDescriptor( indicators.resolveJdbcTypeCode( SqlTypes.BLOB ) );
		}
		else if ( shouldUseMaterializedLob( indicators ) ) {
			return jdbcTypeRegistry.getDescriptor( indicators.resolveJdbcTypeCode( SqlTypes.MATERIALIZED_BLOB ) );
		}
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected boolean shouldUseMaterializedLob(JdbcTypeIndicators indicators) {
		final Dialect dialect = indicators.getDialect();
		final long length = indicators.getColumnLength();
		final long maxLength = dialect.getMaxVarbinaryCapacity();
		return length > maxLength && dialect.useMaterializedLobWhenCapacityExceeded();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> ValueBinder<X> getBinder(final JavaType<X> javaType) {
		return new BasicBinder<>( javaType, this ) {

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options) throws SQLException {
				st.setBytes( index, javaType.unwrap( value, byte[].class, options ) );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
					throws SQLException {
				st.setBytes( name, javaType.unwrap( value, byte[].class, options ) );
			}
		};
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> ValueExtractor<X> getExtractor(final JavaType<X> javaType) {
		return new BasicExtractor<>( javaType, this ) {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(ResultSet rs, int paramIndex, WrapperOptions options) throws SQLException {
				return javaType.wrap( rs.getBytes( paramIndex ), options );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(CallableStatement statement, int index, WrapperOptions options) throws SQLException {
				return javaType.wrap( statement.getBytes( index ), options );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(CallableStatement statement, String name, WrapperOptions options) throws SQLException {
				return javaType.wrap( statement.getBytes( name ), options );
			}
		};
	}
}
