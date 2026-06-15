/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.resource.jdbc.internal;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Jan Schatteman
 */
public class EmptyStatementInspector implements StatementInspector {
	/**
	 * The singleton reference.
	 */
	public static final StatementInspector INSTANCE = new EmptyStatementInspector();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String inspect(String sql) {
		return sql;
	}

	protected EmptyStatementInspector() {}
}
