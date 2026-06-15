/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.sql.internal;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.Size;
import org.hibernate.metamodel.mapping.SqlExpressible;
import org.hibernate.type.Type;
import org.hibernate.type.descriptor.converter.internal.EnumHelper;
import org.hibernate.type.descriptor.sql.DdlType;
import org.hibernate.type.descriptor.sql.spi.DdlTypeRegistry;

import static org.hibernate.type.SqlTypes.ORDINAL_ENUM;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A {@link DdlType} representing a SQL {@code enum} type that
 * may be treated as {@code int} for most purposes.
 *
 * @see org.hibernate.type.SqlTypes#ORDINAL_ENUM
 * @see Dialect#getEnumTypeDeclaration(Class)
 */

public class NativeOrdinalEnumDdlTypeImpl implements DdlType {
	private static final String[] ENUM_KEYWORD = {"enum"};
	private final Dialect dialect;

	public NativeOrdinalEnumDdlTypeImpl(Dialect dialect) {
		this.dialect = dialect;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getSqlTypeCode() {
		return ORDINAL_ENUM;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTypeName(Size columnSize, Type type, DdlTypeRegistry ddlTypeRegistry) {
		return type == null
				? "int"
				: dialect.getEnumTypeDeclaration(
						type.getReturnedClass().getSimpleName(),
						EnumHelper.getEnumeratedValues( type )
				);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String[] getRawTypeNames() {
		return ENUM_KEYWORD;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getCastTypeName(Size columnSize, SqlExpressible type, DdlTypeRegistry ddlTypeRegistry) {
		return "int";
	}
}
