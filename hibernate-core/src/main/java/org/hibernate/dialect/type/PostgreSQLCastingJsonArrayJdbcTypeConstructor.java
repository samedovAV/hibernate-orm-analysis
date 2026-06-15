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
 * Factory for {@link PostgreSQLCastingJsonArrayJdbcType}.
 */
public class PostgreSQLCastingJsonArrayJdbcTypeConstructor implements JdbcTypeConstructor {

	public static final PostgreSQLCastingJsonArrayJdbcTypeConstructor JSONB_INSTANCE = new PostgreSQLCastingJsonArrayJdbcTypeConstructor( true );
	public static final PostgreSQLCastingJsonArrayJdbcTypeConstructor JSON_INSTANCE = new PostgreSQLCastingJsonArrayJdbcTypeConstructor( false );

	private final boolean jsonb;

	public PostgreSQLCastingJsonArrayJdbcTypeConstructor(boolean jsonb) {
		this.jsonb = jsonb;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JdbcType resolveType(
			TypeConfiguration typeConfiguration,
			Dialect dialect,
			BasicType<?> elementType,
			ColumnTypeInformation columnTypeInformation) {
		return resolveType( typeConfiguration, dialect, elementType.getJdbcType(), columnTypeInformation );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcType resolveType(
			TypeConfiguration typeConfiguration,
			Dialect dialect,
			JdbcType elementType,
			ColumnTypeInformation columnTypeInformation) {
		return new PostgreSQLCastingJsonArrayJdbcType( elementType, jsonb );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getDefaultSqlTypeCode() {
		return SqlTypes.JSON_ARRAY;
	}
}
