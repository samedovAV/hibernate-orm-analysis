/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.jdbc;

import static org.hibernate.type.SqlTypes.ORDINAL_ENUM;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Represents an {@code enum} type for databases like MySQL and H2.
 * <p>
 * Hibernate will automatically use this for enums mapped
 * as {@link jakarta.persistence.EnumType#ORDINAL}.
 *
 * @see org.hibernate.type.SqlTypes#ORDINAL_ENUM
 * @see org.hibernate.dialect.MySQLDialect#getEnumTypeDeclaration(String, String[])
 */
public class OrdinalEnumJdbcType extends EnumJdbcType {

	public static final OrdinalEnumJdbcType INSTANCE = new OrdinalEnumJdbcType();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getDefaultSqlTypeCode() {
		return ORDINAL_ENUM;
	}

}
