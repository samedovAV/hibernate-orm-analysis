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
import org.hibernate.type.descriptor.jdbc.OracleJsonArrayBlobJdbcType;
import org.hibernate.type.spi.TypeConfiguration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Factory for {@link OracleJsonArrayJdbcType} and {@link OracleJsonArrayBlobJdbcType}.
 */
public class OracleJsonArrayJdbcTypeConstructor implements JdbcTypeConstructor {

	public static final OracleJsonArrayJdbcTypeConstructor NATIVE_INSTANCE = new OracleJsonArrayJdbcTypeConstructor( true );
	public static final OracleJsonArrayJdbcTypeConstructor BLOB_INSTANCE = new OracleJsonArrayJdbcTypeConstructor( false );

	private final boolean nativeJson;

	public OracleJsonArrayJdbcTypeConstructor(boolean nativeJson) {
		this.nativeJson = nativeJson;
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
		return nativeJson ? new OracleJsonArrayJdbcType( elementType ) : new OracleJsonArrayBlobJdbcType( elementType );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getDefaultSqlTypeCode() {
		return SqlTypes.JSON_ARRAY;
	}
}
