/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.mutation.internal;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import org.hibernate.engine.jdbc.mutation.group.PreparedStatementDetails;
import org.hibernate.engine.jdbc.mutation.group.PreparedStatementGroup;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class PreparedStatementGroupNone implements PreparedStatementGroup {
	/**
	 * Singleton access
	 */
	public static final PreparedStatementGroupNone GROUP_OF_NONE = new PreparedStatementGroupNone();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getNumberOfStatements() {
		return 0;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getNumberOfActiveStatements() {
		return 0;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PreparedStatementDetails getSingleStatementDetails() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void forEachStatement(BiConsumer<String, PreparedStatementDetails> action) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PreparedStatementDetails resolvePreparedStatementDetails(String tableName) {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PreparedStatementDetails getPreparedStatementDetails(String tableName) {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasMatching(Predicate<PreparedStatementDetails> filter) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void release() {
	}
}
