/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.jdbc;

import org.hibernate.Incubating;
import org.hibernate.type.SqlTypes;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Descriptor for aggregate handling like {@link SqlTypes#STRUCT STRUCT}, {@link SqlTypes#JSON JSON} and {@link SqlTypes#SQLXML SQLXML}.
 */
@Incubating
public interface StructuredJdbcType extends AggregateJdbcType, SqlTypedJdbcType {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getStructTypeName();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getSqlTypeName() {
		return getStructTypeName();
	}
}
