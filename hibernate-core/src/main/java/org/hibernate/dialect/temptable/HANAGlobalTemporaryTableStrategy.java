/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.temptable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * HANA specific global temporary table strategy.
 */
public class HANAGlobalTemporaryTableStrategy extends StandardGlobalTemporaryTableStrategy {

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTemporaryTableCreateCommand() {
		return "create global temporary row table";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTemporaryTableTruncateCommand() {
		return "truncate table";
	}
}
