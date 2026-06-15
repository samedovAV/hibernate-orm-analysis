/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Duration;

import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.BasicBinder;
import org.hibernate.type.descriptor.jdbc.BasicExtractor;
import org.hibernate.type.descriptor.jdbc.JdbcLiteralFormatter;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class H2DurationIntervalSecondJdbcType implements JdbcType {

	public static final H2DurationIntervalSecondJdbcType INSTANCE = new H2DurationIntervalSecondJdbcType();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getJdbcTypeCode() {
		return Types.OTHER;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getDefaultSqlTypeCode() {
		return SqlTypes.INTERVAL_SECOND;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getPreferredJavaTypeClass(WrapperOptions options) {
		return Duration.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isComparable() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <T> JdbcLiteralFormatter<T> getJdbcLiteralFormatter(JavaType<T> javaType) {
		return (appender, value, dialect, wrapperOptions) ->
				dialect.appendIntervalLiteral( appender, javaType.unwrap( value, Duration.class, wrapperOptions ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> ValueBinder<X> getBinder(JavaType<X> javaType) {
		return new BasicBinder<>( javaType, this ) {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options)
					throws SQLException {
				st.setObject( index, javaType.unwrap( value, Duration.class, options ) );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
					throws SQLException {
				st.setObject( name, javaType.unwrap( value, Duration.class, options ) );
			}
		};
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> ValueExtractor<X> getExtractor(JavaType<X> javaType) {
		return new BasicExtractor<>( javaType, this ) {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(ResultSet rs, int paramIndex, WrapperOptions options) throws SQLException {
				// Handle the fact that a duration could also come as number of nanoseconds
				final Object nativeValue = rs.getObject( paramIndex );
				if ( nativeValue instanceof Number ) {
					return javaType.wrap( nativeValue, options );
				}
				return javaType.wrap( rs.getObject( paramIndex, Duration.class ), options );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(CallableStatement statement, int index, WrapperOptions options) throws SQLException {
				// Handle the fact that a duration could also come as number of nanoseconds
				final Object nativeValue = statement.getObject( index );
				return nativeValue instanceof Number
						? javaType.wrap( nativeValue, options )
						: javaType.wrap( statement.getObject( index, Duration.class ), options );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(CallableStatement statement, String name, WrapperOptions options)
					throws SQLException {
				// Handle the fact that a duration could also come as number of nanoseconds
				final Object nativeValue = statement.getObject( name );
				return nativeValue instanceof Number
						? javaType.wrap( nativeValue, options )
						: javaType.wrap( statement.getObject( name, Duration.class ), options );
			}
		};
	}
}
