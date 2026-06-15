/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.type;


import org.hibernate.dialect.Dialect;
import org.hibernate.tool.schema.extract.spi.ColumnTypeInformation;
import org.hibernate.type.BasicType;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.JdbcTypeConstructor;
import org.hibernate.type.spi.TypeConfiguration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Factory for {@link SybaseJtdsXmlAsStringArrayJdbcType}.
 */
public class SybaseJtdsXmlAsStringArrayJdbcTypeConstructor implements JdbcTypeConstructor {
	public static final SybaseJtdsXmlAsStringArrayJdbcTypeConstructor INSTANCE = new SybaseJtdsXmlAsStringArrayJdbcTypeConstructor();

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JdbcType resolveType(
			TypeConfiguration typeConfiguration,
			Dialect dialect,
			BasicType<?> elementType,
			ColumnTypeInformation columnTypeInformation) {
		return resolveType( typeConfiguration, dialect, elementType.getJdbcType(), columnTypeInformation );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcType resolveType(
			TypeConfiguration typeConfiguration,
			Dialect dialect,
			JdbcType elementType,
			ColumnTypeInformation columnTypeInformation) {
		return new SybaseJtdsXmlAsStringArrayJdbcType( elementType, SqlTypes.NCLOB );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getDefaultSqlTypeCode() {
		return SqlTypes.XML_ARRAY;
	}
}
