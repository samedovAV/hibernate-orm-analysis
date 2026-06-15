/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.mutation.group;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Grouping of {@link java.sql.PreparedStatement} references.
 *
 * @author Steve Ebersole
 */
@Incubating
public interface PreparedStatementGroup {
	/**
	 * The number of statements in this group
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getNumberOfStatements();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getNumberOfActiveStatements();

	/**
	 * Get the single statement details.
	 *
	 * @throws IllegalStateException if there is more than one statement
	 * associated with this group.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PreparedStatementDetails getSingleStatementDetails();

	/**
	 * Visit the details for each table mutation
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void forEachStatement(BiConsumer<String, PreparedStatementDetails> action);

	/**
	 * Get the PreparedStatement in this group related to the given table-name.
	 * If the descriptor does not already exist, this method will create it.
	 *
	 * @see #getPreparedStatementDetails
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PreparedStatementDetails resolvePreparedStatementDetails(String tableName);

	/**
	 * Get the PreparedStatement in this group related to the given table-name.
	 * Will return null if no descriptor (yet) exists
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PreparedStatementDetails getPreparedStatementDetails(String tableName);

	/**
	 * Release resources held by this group.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void release();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasMatching(Predicate<PreparedStatementDetails> filter);
}
