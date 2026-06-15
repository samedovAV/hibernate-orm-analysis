/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.type;

import org.hibernate.dialect.Dialect;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.converter.spi.BasicValueConverter;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.BooleanJdbcType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class OracleBooleanJdbcType extends BooleanJdbcType {

	public static final OracleBooleanJdbcType INSTANCE = new OracleBooleanJdbcType();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getDdlTypeCode() {
		return SqlTypes.BIT;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getCheckCondition(String columnName, JavaType<?> javaType, BasicValueConverter<?, ?> converter, Dialect dialect) {
		return columnName + " in (0,1)";
	}
}
