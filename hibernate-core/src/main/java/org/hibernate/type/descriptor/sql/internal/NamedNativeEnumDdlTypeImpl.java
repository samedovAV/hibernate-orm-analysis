/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.sql.internal;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.Size;
import org.hibernate.metamodel.mapping.SqlExpressible;
import org.hibernate.type.Type;
import org.hibernate.type.descriptor.sql.DdlType;
import org.hibernate.type.descriptor.sql.spi.DdlTypeRegistry;

import static org.hibernate.type.SqlTypes.NAMED_ENUM;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A {@link DdlType} representing a named native SQL {@code enum} type,
 * one that often <em>cannot</em> be treated as a {@code varchar}.
 *
 * @see org.hibernate.type.SqlTypes#NAMED_ENUM
 * @see Dialect#getEnumTypeDeclaration(Class)
 *
 * @author Gavin King
 */
public class NamedNativeEnumDdlTypeImpl implements DdlType {

	private static final String[] ENUM_KEYWORD = {"enum"};

	public NamedNativeEnumDdlTypeImpl(Dialect dialect) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getSqlTypeCode() {
		// note: also used for NAMED_ENUM
		return NAMED_ENUM;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTypeName(Size columnSize, Type type, DdlTypeRegistry ddlTypeRegistry) {
		return type.getReturnedClass().getSimpleName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String[] getRawTypeNames() {
		return ENUM_KEYWORD;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getCastTypeName(Size columnSize, SqlExpressible type, DdlTypeRegistry ddlTypeRegistry) {
		return type.getJdbcMapping().getJavaTypeDescriptor().getJavaTypeClass().getSimpleName();
	}
}
