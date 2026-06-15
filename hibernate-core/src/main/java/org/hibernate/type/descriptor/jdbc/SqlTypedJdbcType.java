/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.jdbc;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * A {@link JdbcType} with a fixed SQL type name.
 *
 * @see StructuredJdbcType
 * @see org.hibernate.dialect.type.OracleArrayJdbcType
 * @see org.hibernate.dialect.type.OracleNestedTableJdbcType
 */
public interface SqlTypedJdbcType extends JdbcType {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSqlTypeName();
}
