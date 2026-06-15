/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.temptable;

import jakarta.annotation.Nullable;
import org.hibernate.query.sqm.mutation.spi.AfterUseAction;
import org.hibernate.query.sqm.mutation.spi.BeforeUseAction;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Strategy to interact with local temporary tables.
 */
public class StandardLocalTemporaryTableStrategy implements TemporaryTableStrategy {

	public static final StandardLocalTemporaryTableStrategy INSTANCE = new StandardLocalTemporaryTableStrategy();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String adjustTemporaryTableName(String desiredTableName) {
		return desiredTableName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TemporaryTableKind getTemporaryTableKind() {
		return TemporaryTableKind.LOCAL;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable String getTemporaryTableCreateOptions() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTemporaryTableCreateCommand() {
		return "create local temporary table";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTemporaryTableDropCommand() {
		return "drop table";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTemporaryTableTruncateCommand() {
		return "delete from";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getCreateTemporaryTableColumnAnnotation(int sqlTypeCode) {
		return "";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AfterUseAction getTemporaryTableAfterUseAction() {
		return AfterUseAction.NONE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public BeforeUseAction getTemporaryTableBeforeUseAction() {
		return BeforeUseAction.CREATE;
	}
}
