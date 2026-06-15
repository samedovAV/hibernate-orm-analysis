/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree;

import org.hibernate.sql.ast.SqlAstWalker;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Base contract for any statement
 *
 * @author Steve Ebersole
 */
public interface Statement extends SqlAstNode {
	/**
	 * Visitation
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void accept(SqlAstWalker walker);

	/**
	 * Whether this statement is a selection and will return results.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isSelection() {
		return false;
	}
}
