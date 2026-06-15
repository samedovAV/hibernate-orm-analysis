/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.type;

import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.BasicBinder;
import org.hibernate.type.descriptor.jdbc.BasicExtractor;
import org.hibernate.type.descriptor.jdbc.NVarcharJdbcType;

import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Specialized type mapping for {@code NVARCHAR} that binds/extracts UTF-16LE bytes,
 * because the jTDS driver can't handle Unicode characters and doesn't support nationalized methods.
 */
public class SybaseJtdsNVarcharJdbcType extends NVarcharJdbcType {

	public static final SybaseJtdsNVarcharJdbcType JTDS_INSTANCE = new SybaseJtdsNVarcharJdbcType();

	public SybaseJtdsNVarcharJdbcType() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "SybaseJtdsNVarcharJdbcType";
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> ValueBinder<X> getBinder(final JavaType<X> javaType) {
		return new BasicBinder<>( javaType, this ) {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options) throws SQLException {
				final String string = javaType.unwrap( value, String.class, options );
				st.setBytes( index, string == null ? null : string.getBytes( StandardCharsets.UTF_16LE ) );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
					throws SQLException {
				final String string = javaType.unwrap( value, String.class, options );
				st.setBytes( name, string == null ? null : string.getBytes( StandardCharsets.UTF_16LE ) );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBindNull(PreparedStatement st, int index, WrapperOptions options) throws SQLException {
				st.setNull( index, Types.VARCHAR );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBindNull(CallableStatement st, String name, WrapperOptions options)
					throws SQLException {
				st.setNull( name, Types.VARCHAR );
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
				final byte[] bytes = rs.getBytes( paramIndex );
				return bytes == null ? null : javaType.wrap( new String( bytes, StandardCharsets.UTF_16LE ), options );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(CallableStatement statement, int index, WrapperOptions options) throws SQLException {
				final byte[] bytes = statement.getBytes( index );
				return bytes == null ? null : javaType.wrap( new String( bytes, StandardCharsets.UTF_16LE ), options );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(CallableStatement statement, String name, WrapperOptions options) throws SQLException {
				final byte[] bytes = statement.getBytes( name );
				return bytes == null ? null : javaType.wrap( new String( bytes, StandardCharsets.UTF_16LE ), options );
			}
		};
	}
}
