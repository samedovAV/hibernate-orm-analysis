/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.java;

import org.hibernate.type.descriptor.WrapperOptions;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class ObjectJavaType extends AbstractClassJavaType<Object> {
	/**
	 * Singleton access
	 */
	public static final ObjectJavaType INSTANCE = new ObjectJavaType();

	public ObjectJavaType() {
		super( Object.class );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean useObjectEqualsHashCode() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isInstance(Object value) {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object cast(Object value) {
		return value;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> X unwrap(Object value, Class<X> type, WrapperOptions options) {
		return type.cast( value );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> Object wrap(X value, WrapperOptions options) {
		return value;
	}

}
