/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.type;

import org.hibernate.boot.model.relational.Database;
import org.hibernate.dialect.Dialect;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.java.BasicPluralJavaType;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.JdbcType;

import static org.hibernate.internal.util.StringHelper.truncate;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Descriptor for {@link SqlTypes#TABLE TABLE} handling.
 */
public class OracleNestedTableJdbcType extends OracleArrayJdbcType {

	public OracleNestedTableJdbcType(JdbcType elementJdbcType, String typeName) {
		super( elementJdbcType, typeName );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getDdlTypeCode() {
		return SqlTypes.TABLE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getExtraCreateTableInfo(JavaType<?> javaType, String columnName, String tableName, Database database) {
		final Dialect dialect = database.getDialect();
		final BasicPluralJavaType<?> pluralJavaType = (BasicPluralJavaType<?>) javaType;
		String elementTypeName = getTypeName( pluralJavaType.getElementJavaType(), getElementJdbcType(), dialect );
		return " nested table " + columnName + " store as \"" + truncate(
				tableName + " " + columnName + " " + elementTypeName,
				dialect.getMaxIdentifierLength()
		) + "\"";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "OracleNestedTableTypeDescriptor(" + getSqlTypeName() + ")";
	}
}
