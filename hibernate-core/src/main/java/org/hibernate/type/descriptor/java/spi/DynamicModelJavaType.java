/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.java.spi;

import java.util.Map;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.JdbcTypeIndicators;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * JavaType for dynamic models
 *
 * @author Steve Ebersole
 */
public class DynamicModelJavaType implements JavaType<Map<?,?>> {

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcType getRecommendedJdbcType(JdbcTypeIndicators context) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Map<?,?> fromString(CharSequence string) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> X unwrap(Map<?,?> value, Class<X> type, WrapperOptions options) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> Map<?,?> wrap(X value, WrapperOptions options) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<Map<?,?>> getJavaTypeClass() {
		//noinspection unchecked,rawtypes
		return (Class) Map.class;
	}
}
