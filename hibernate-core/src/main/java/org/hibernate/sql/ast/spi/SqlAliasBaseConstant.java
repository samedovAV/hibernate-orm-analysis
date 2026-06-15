/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * A SqlAliasBase that always returns the same constant.
 *
 * @author Christian Beikov
 */
public class SqlAliasBaseConstant implements SqlAliasBase {
	private final String constant;

	public SqlAliasBaseConstant(String constant) {
		this.constant = constant;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getAliasStem() {
		return constant;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String generateNewAlias() {
		return constant;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "SqlAliasBase(" + constant + ")";
	}
}
