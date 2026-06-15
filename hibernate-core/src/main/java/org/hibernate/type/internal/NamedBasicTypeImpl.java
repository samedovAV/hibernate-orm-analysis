/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.internal;

import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class NamedBasicTypeImpl<J> extends BasicTypeImpl<J> {

	private final String name;

	public NamedBasicTypeImpl(JavaType<J> jtd, JdbcType std, String name) {
		super( jtd, std );
		this.name = name;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getName() {
		return name;
	}

}
