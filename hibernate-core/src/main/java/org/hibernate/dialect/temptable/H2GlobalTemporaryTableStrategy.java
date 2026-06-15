/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.temptable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * H2 specific global temporary table strategy.
 */
public class H2GlobalTemporaryTableStrategy extends StandardGlobalTemporaryTableStrategy {

	public static final H2GlobalTemporaryTableStrategy INSTANCE = new H2GlobalTemporaryTableStrategy();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTemporaryTableCreateOptions() {
		return "transactional";
	}
}
