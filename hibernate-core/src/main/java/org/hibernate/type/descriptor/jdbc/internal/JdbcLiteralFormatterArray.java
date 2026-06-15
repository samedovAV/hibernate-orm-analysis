/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.jdbc.internal;

import org.hibernate.dialect.Dialect;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.JdbcLiteralFormatter;
import org.hibernate.type.descriptor.jdbc.spi.BasicJdbcLiteralFormatter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class JdbcLiteralFormatterArray<T> extends BasicJdbcLiteralFormatter<T> {

	private final JdbcLiteralFormatter<Object> elementFormatter;

	public JdbcLiteralFormatterArray(JavaType<T> javaType, JdbcLiteralFormatter<?> elementFormatter) {
		super( javaType );
		//noinspection unchecked
		this.elementFormatter = (JdbcLiteralFormatter<Object>) elementFormatter;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void appendJdbcLiteral(SqlAppender appender, T value, Dialect dialect, WrapperOptions wrapperOptions) {
		dialect.appendArrayLiteral( appender, unwrapArray( value, wrapperOptions ), elementFormatter, wrapperOptions );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private Object[] unwrapArray(Object value, WrapperOptions wrapperOptions) {
		return unwrap( value, Object[].class, wrapperOptions );
	}
}
