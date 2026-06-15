/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.temporal;

import org.hibernate.temporal.TemporalTableStrategy;
import org.hibernate.dialect.MariaDBDialect;
import org.hibernate.type.SqlTypes;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Gavin King
 */
public class MariaDBTemporalTableSupport extends MySQLTemporalTableSupport {

	public MariaDBTemporalTableSupport(MariaDBDialect dialect) {
		super( dialect );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsNativeTemporalTables() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsTemporalTablePartitioning() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTemporalTableOptions(
			TemporalTableStrategy strategy,
			String rowEndColumnName,
			boolean partitioned,
			String currentPartitionName,
			String historyPartitionName) {
		return strategy == TemporalTableStrategy.NATIVE
				? "with system versioning"
				: null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getExtraTemporalTableDeclarations(TemporalTableStrategy strategy, String rowStartColumn, String rowEndColumn, boolean partitioned) {
		return strategy == TemporalTableStrategy.NATIVE
				? "period for system_time (" + rowStartColumn + ", " + rowEndColumn + ")"
				: null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getTemporalColumnType() {
		return SqlTypes.TIMESTAMP_WITH_TIMEZONE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTemporalExclusionColumnOption() {
		return "without system versioning";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TemporalTableStrategy getDefaultTemporalTableStrategy() {
		return TemporalTableStrategy.NATIVE;
	}

}
