/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.java;

import jakarta.annotation.Nullable;
import org.hibernate.HibernateException;
import org.hibernate.type.descriptor.java.spi.UnknownBasicJavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class JavaTypeHelper {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected static <T extends JavaType<?>> HibernateException unknownUnwrap(Class<?> sourceType, Class<?> targetType, T jtd) {
		throw new HibernateException(
				"Could not convert '" + sourceType.getName()
						+ "' to '" + targetType.getName()
						+ "' using '" + jtd.getClass().getName() + "' to unwrap"
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected static <T extends JavaType<?>> HibernateException unknownWrap(Class<?> valueType, Class<?> sourceType, T jtd) {
		throw new HibernateException(
				"Could not convert '" + valueType.getName()
						+ "' to '" + sourceType.getName()
						+ "' using '" + jtd.getClass().getName() + "' to wrap"
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static boolean isTemporal(@Nullable JavaType<?> javaType) {
		return javaType != null && javaType.isTemporalType();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static boolean isUnknown(@Nullable JavaType<?> javaType) {
		return javaType == null
			|| javaType.getClass() == UnknownBasicJavaType.class;
	}
}
