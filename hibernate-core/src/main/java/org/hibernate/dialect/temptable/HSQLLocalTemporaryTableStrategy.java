/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.temptable;

import org.hibernate.query.sqm.mutation.spi.AfterUseAction;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * HSQL specific local temporary table strategy.
 */
public class HSQLLocalTemporaryTableStrategy extends StandardLocalTemporaryTableStrategy {

	public static final HSQLLocalTemporaryTableStrategy INSTANCE = new HSQLLocalTemporaryTableStrategy();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String adjustTemporaryTableName(String desiredTableName) {
		// With HSQLDB 2.0, the table name is qualified with session to assist the drop
		// statement (in-case there is a global name beginning with HT_)
		return "session." + desiredTableName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTemporaryTableCreateCommand() {
		return "declare local temporary table";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AfterUseAction getTemporaryTableAfterUseAction() {
		return AfterUseAction.DROP;
	}
}
