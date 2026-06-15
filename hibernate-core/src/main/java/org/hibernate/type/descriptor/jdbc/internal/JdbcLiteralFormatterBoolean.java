/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.jdbc.internal;

import org.hibernate.dialect.Dialect;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.spi.BasicJdbcLiteralFormatter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * {@link org.hibernate.type.descriptor.jdbc.JdbcLiteralFormatter}
 * implementation for handling boolean literals
 *
 * @author Steve Ebersole
 */
public class JdbcLiteralFormatterBoolean<T> extends BasicJdbcLiteralFormatter<T> {
	public JdbcLiteralFormatterBoolean(JavaType<T> javaType) {
		super( javaType );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void appendJdbcLiteral(SqlAppender appender, T value, Dialect dialect, WrapperOptions wrapperOptions) {
		dialect.appendBooleanValueString( appender, unwrap( value, Boolean.class, wrapperOptions ) );
	}
}
