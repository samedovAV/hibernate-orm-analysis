/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.jdbc.spi;

import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.JdbcLiteralFormatter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Abstract {@link JdbcLiteralFormatter} implementation managing the {@link JavaType}
 *
 * @author Steve Ebersole
 */
public abstract class AbstractJdbcLiteralFormatter<T> implements JdbcLiteralFormatter<T> {
	private final JavaType<T> javaType;

	public AbstractJdbcLiteralFormatter(JavaType<T> javaType) {
		this.javaType = javaType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected JavaType<T> getJavaType() {
		return javaType;
	}
}
