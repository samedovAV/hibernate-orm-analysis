/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.jdbc;

import java.sql.Types;

import org.hibernate.type.SqlTypes;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Descriptor for {@link java.time.Duration}.
 *
 * @author Marco Belladelli
 */
public class DurationJdbcType extends NumericJdbcType {
	public static final DurationJdbcType INSTANCE = new DurationJdbcType();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getDdlTypeCode() {
		return Types.NUMERIC;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getDefaultSqlTypeCode() {
		return SqlTypes.DURATION;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getFriendlyName() {
		return "DURATION";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "DurationJdbcType";
	}
}
