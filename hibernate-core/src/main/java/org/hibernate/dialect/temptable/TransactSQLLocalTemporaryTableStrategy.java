/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.temptable;

import org.hibernate.query.sqm.mutation.spi.AfterUseAction;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Transact-SQL specific local temporary table strategy.
 */
public class TransactSQLLocalTemporaryTableStrategy extends StandardLocalTemporaryTableStrategy {

	public static final TransactSQLLocalTemporaryTableStrategy INSTANCE = new TransactSQLLocalTemporaryTableStrategy();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String adjustTemporaryTableName(String desiredTableName) {
		return '#' + desiredTableName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTemporaryTableCreateCommand() {
		return "create table";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AfterUseAction getTemporaryTableAfterUseAction() {
		// sql-server, at least needed this dropped after use; strange!
		return AfterUseAction.DROP;
	}

}
