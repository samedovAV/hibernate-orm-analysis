/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.identity;

import java.sql.Types;

import org.hibernate.MappingException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class CockroachDBIdentityColumnSupport extends IdentityColumnSupportImpl {

	public static final CockroachDBIdentityColumnSupport INSTANCE = new CockroachDBIdentityColumnSupport();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsIdentityColumns() {
		// Full support requires setting the sql.defaults.serial_normalization=sql_sequence in CockroachDB.
		// Also, support for serial4 is not enabled by default: https://github.com/cockroachdb/cockroach/issues/26925#issuecomment-1255293916
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	// CockroachDB does not create a sequence for id columns
	public String getIdentitySelectString(String table, String column, int type) {
		return "select 1";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getIdentityColumnString(int type) {
		// Note that the unique_rowid() function used to generated values with serial_normalization=rowid (default)
		// will always produce INT8 (Types.BIGINT) values which might not fit other data types.
		// See https://www.cockroachlabs.com/docs/stable/serial.html
		switch ( type ) {
			case Types.TINYINT:
			case Types.SMALLINT:
				return "serial2 not null";
			case Types.INTEGER:
				return "serial4 not null";
			case Types.BIGINT:
				return "serial8 not null";
			default:
				throw new MappingException( "illegal identity column type");
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasDataTypeInIdentityColumn() {
		return false;
	}
}
