/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.format;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;

import java.lang.reflect.Type;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Yanming Zhou
 */
public abstract class AbstractJsonFormatMapper implements FormatMapper {

	@SuppressWarnings("unchecked")
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public final <T> T fromString(CharSequence charSequence, JavaType<T> javaType, WrapperOptions wrapperOptions) {
		final Type type = javaType.getJavaType();
		if ( type == String.class ) {
			return (T) charSequence.toString();
		}
		return fromString( charSequence, type );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public final <T> String toString(T value, JavaType<T> javaType, WrapperOptions wrapperOptions) {
		final Type type = javaType.getJavaType();
		if ( type == String.class ) {
			return (String) value;
		}
		return toString( value, type );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract <T> T fromString(CharSequence charSequence, Type type);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract <T> String toString(T value, Type type);

}
