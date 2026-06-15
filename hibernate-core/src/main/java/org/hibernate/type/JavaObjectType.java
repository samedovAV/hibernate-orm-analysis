/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type;

import org.hibernate.type.descriptor.java.ObjectJavaType;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.ObjectJdbcType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class JavaObjectType extends AbstractSingleColumnStandardBasicType<Object> {
	/**
	 * Singleton access
	 */
	public static final JavaObjectType INSTANCE = new JavaObjectType();

	public JavaObjectType() {
		super( ObjectJdbcType.INSTANCE, ObjectJavaType.INSTANCE );
	}

	public JavaObjectType(JdbcType jdbcType, JavaType<Object> javaType) {
		super( jdbcType, javaType );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getName() {
		return "JAVA_OBJECT";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected boolean registerUnderJavaType() {
		return true;
	}
}
