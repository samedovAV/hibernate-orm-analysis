/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.temporal;

import org.hibernate.temporal.TemporalTableStrategy;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.type.SqlTypes;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Gavin King
 */
public class MySQLTemporalTableSupport extends DefaultTemporalTableSupport {

	public MySQLTemporalTableSupport(MySQLDialect dialect) {
		super( dialect );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsTemporalTablePartitioning() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTemporalTableOptions(
			TemporalTableStrategy strategy,
			String rowEndColumnName,
			boolean partitioned,
			String currentPartition,
			String historyPartition) {
		return partitioned
				? "partition by list (" + rowEndColumnName + "_null)"
				+ " (partition " + historyPartition + " values in (0),"
				+ " partition " + currentPartition + " values in (1))"
				: null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getExtraTemporalTableDeclarations(
			TemporalTableStrategy strategy,
			String rowStartColumn, String rowEndColumn,
			boolean partitioned) {
		return partitioned
				? rowEndColumn + "_null tinyint as (" + rowEndColumn + " is null) virtual invisible"
				: null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getTemporalColumnType() {
		return SqlTypes.TIMESTAMP_UTC;
	}
}
