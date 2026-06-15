/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.temptable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * DB2 specific global temporary table strategy.
 */
public class DB2GlobalTemporaryTableStrategy extends StandardGlobalTemporaryTableStrategy {

	public static final DB2GlobalTemporaryTableStrategy INSTANCE = new DB2GlobalTemporaryTableStrategy();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTemporaryTableCreateOptions() {
		return "not logged";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsTemporaryTablePrimaryKey() {
		return false;
	}
}
