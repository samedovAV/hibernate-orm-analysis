/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Dialect support information for primary key functional dependency analysis
 * within {@code GROUP BY} and {@code ORDER BY} clauses.
 *
 * @author Marco Belladelli
 */
public interface FunctionalDependencyAnalysisSupport {
	/**
	 * Supports primary key functional dependency analysis
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean supportsAnalysis();

	/**
	 * Supports functional dependency analysis through joined tables and result sets (e.g. unions)
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean supportsTableGroups();

	/**
	 * Also supports functional dependency analysis for constant values other than table columns
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean supportsConstants();
}
