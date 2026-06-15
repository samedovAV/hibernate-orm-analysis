/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.temptable;

import org.hibernate.dialect.Dialect;
import org.hibernate.query.sqm.mutation.spi.AfterUseAction;
import org.hibernate.query.sqm.mutation.spi.BeforeUseAction;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Legacy strategy that delegates to deprecated Dialect methods.
 */
@Deprecated(forRemoval = true, since = "7.1")
public class LegacyTemporaryTableStrategy implements TemporaryTableStrategy {

	private final Dialect dialect;

	public LegacyTemporaryTableStrategy(Dialect dialect) {
		this.dialect = dialect;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String adjustTemporaryTableName(String desiredTableName) {
		return desiredTableName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TemporaryTableKind getTemporaryTableKind() {
		return dialect.getSupportedTemporaryTableKind();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getTemporaryTableCreateOptions() {
		return dialect.getTemporaryTableCreateOptions();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getTemporaryTableCreateCommand() {
		return dialect.getTemporaryTableCreateCommand();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getTemporaryTableDropCommand() {
		return dialect.getTemporaryTableDropCommand();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getTemporaryTableTruncateCommand() {
		return dialect.getTemporaryTableTruncateCommand();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getCreateTemporaryTableColumnAnnotation(int sqlTypeCode) {
		return dialect.getCreateTemporaryTableColumnAnnotation( sqlTypeCode );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public AfterUseAction getTemporaryTableAfterUseAction() {
		return dialect.getTemporaryTableAfterUseAction();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public BeforeUseAction getTemporaryTableBeforeUseAction() {
		return dialect.getTemporaryTableBeforeUseAction();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean supportsTemporaryTablePrimaryKey() {
		return dialect.supportsTemporaryTablePrimaryKey();
	}
}
