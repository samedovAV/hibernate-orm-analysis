/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.jdbc;

import java.sql.Types;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Descriptor for {@link Types#LONGVARBINARY LONGVARBINARY} handling.
 *
 * @author Steve Ebersole
 */
public class LongVarbinaryJdbcType extends VarbinaryJdbcType {
	public static final LongVarbinaryJdbcType INSTANCE = new LongVarbinaryJdbcType();

	private final int defaultSqlTypeCode;
	private final int ddlTypeCode;

	public LongVarbinaryJdbcType() {
		this( Types.LONGVARBINARY, Types.LONGVARBINARY );
	}

	public LongVarbinaryJdbcType(int defaultSqlTypeCode) {
		this( defaultSqlTypeCode, defaultSqlTypeCode );
	}

	public LongVarbinaryJdbcType(int defaultSqlTypeCode, int ddlTypeCode) {
		this.defaultSqlTypeCode = defaultSqlTypeCode;
		this.ddlTypeCode = ddlTypeCode;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "LongVarbinaryTypeDescriptor";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getJdbcTypeCode() {
		return Types.LONGVARBINARY;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getDdlTypeCode() {
		return ddlTypeCode;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getDefaultSqlTypeCode() {
		return defaultSqlTypeCode;
	}
}
