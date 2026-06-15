/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;



/**
 * Standard SqlAliasBase impl
 *
 * @author Steve Ebersole
 */
public class SqlAliasBaseImpl implements SqlAliasBase {
	private final String stem;
	private int aliasCount;

	public SqlAliasBaseImpl(String stem) {
		this.stem = stem;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getAliasStem() {
		return stem;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String generateNewAlias() {
		final String alias = stem + "_" + ( aliasCount++ );
//		SqlTreeCreationLogger.LOGGER.tracef( "Created new SQL alias: %s", alias );
		return alias;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "SqlAliasBase(" + stem + " : " + aliasCount + ")";
	}
}
