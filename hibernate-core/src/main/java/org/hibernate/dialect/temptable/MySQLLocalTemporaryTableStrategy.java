/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.temptable;

import org.hibernate.query.sqm.mutation.spi.AfterUseAction;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * MySQL specific local temporary table strategy.
 */
public class MySQLLocalTemporaryTableStrategy extends StandardLocalTemporaryTableStrategy {

	public static final MySQLLocalTemporaryTableStrategy INSTANCE = new MySQLLocalTemporaryTableStrategy();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTemporaryTableCreateCommand() {
		return "create temporary table if not exists";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTemporaryTableDropCommand() {
		return "drop temporary table";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AfterUseAction getTemporaryTableAfterUseAction() {
		return AfterUseAction.DROP;
	}

}
