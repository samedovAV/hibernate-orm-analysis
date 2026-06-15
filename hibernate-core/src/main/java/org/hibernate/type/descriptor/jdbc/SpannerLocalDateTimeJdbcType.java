/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.jdbc;


import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.internal.GetObjectExtractor;
import org.hibernate.type.descriptor.jdbc.internal.SetObjectBinder;

import java.sql.Timestamp;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class SpannerLocalDateTimeJdbcType extends LocalDateTimeJdbcType {

	public static final LocalDateTimeJdbcType INSTANCE = new SpannerLocalDateTimeJdbcType();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> ValueBinder<X> getBinder(JavaType<X> javaType) {
		return new SetObjectBinder<>( javaType, this, Timestamp.class, getDdlTypeCode() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> ValueExtractor<X> getExtractor(JavaType<X> javaType) {
		return new GetObjectExtractor<>( javaType, this, Timestamp.class );
	}
}
