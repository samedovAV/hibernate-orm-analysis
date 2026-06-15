/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.temptable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Strategy to interact with Oracle private temporary tables that were introduced in Oracle 18c.
 */
public class OracleLocalTemporaryTableStrategy extends StandardLocalTemporaryTableStrategy {

	public static final OracleLocalTemporaryTableStrategy INSTANCE = new OracleLocalTemporaryTableStrategy();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String adjustTemporaryTableName(String desiredTableName) {
		return "ora$ptt_" + desiredTableName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTemporaryTableCreateOptions() {
		return "on commit drop definition";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTemporaryTableCreateCommand() {
		return "create private temporary table";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsTemporaryTablePrimaryKey() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsTemporaryTableNullConstraint() {
		return false;
	}
}
