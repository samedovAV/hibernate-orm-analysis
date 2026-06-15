/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.jdbc;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Descriptor for binding objects, but binding nulls with Types.NULL
 *
 * @author Christian Beikov
 */
public class ObjectNullAsNullTypeJdbcType extends ObjectJdbcType {
	/**
	 * Singleton access
	 */
	public static final ObjectNullAsNullTypeJdbcType INSTANCE = new ObjectNullAsNullTypeJdbcType( Types.JAVA_OBJECT );

	public ObjectNullAsNullTypeJdbcType(int jdbcTypeCode) {
		super( jdbcTypeCode );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public <X> ValueBinder<X> getBinder(JavaType<X> javaType) {
		if ( Serializable.class.isAssignableFrom( javaType.getJavaTypeClass() ) ) {
			return VarbinaryJdbcType.INSTANCE.getBinder( javaType );
		}

		return new BasicBinder<>( javaType, this ) {

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBindNull(PreparedStatement st, int index, WrapperOptions options)
					throws SQLException {
				st.setNull( index, Types.NULL );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBindNull(CallableStatement st, String name, WrapperOptions options)
					throws SQLException {
				st.setNull( name, Types.NULL );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options)
					throws SQLException {
				st.setObject( index, value, getJdbcTypeCode() );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
					throws SQLException {
				st.setObject( name, value, getJdbcTypeCode() );
			}
		};
	}
}
