/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.jdbc;

import java.sql.Types;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Descriptor for {@link Types#CHAR CHAR} handling.
 *
 * @author Steve Ebersole
 */
public class CharJdbcType extends VarcharJdbcType {
	public static final CharJdbcType INSTANCE = new CharJdbcType();

	public CharJdbcType() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "CharTypeDescriptor";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getJdbcTypeCode() {
		return Types.CHAR;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected int resolveIndicatedJdbcTypeCode(JdbcTypeIndicators indicators) {
		if ( indicators.isLob() ) {
			return indicators.isNationalized() ? Types.NCLOB : Types.CLOB;
		}
		else {
			return indicators.isNationalized() ? Types.NCHAR : Types.CHAR;
		}
	}
}
