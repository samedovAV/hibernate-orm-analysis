/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.insert;

import java.util.List;
import java.util.function.BiConsumer;

import org.hibernate.sql.ast.tree.MutationStatement;
import org.hibernate.sql.ast.tree.expression.ColumnReference;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Specialization of MutationStatement for inserts
 *
 * @author Steve Ebersole
 */
public interface InsertStatement extends MutationStatement {
	/**
	 * Get all target columns
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<ColumnReference> getTargetColumns();

	/**
	 * The number of target columns associated with this insert.
	 *
	 * @implNote By default, returns the size of {@link #getTargetColumns()}
	 * which may be appropriate or not
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default int getNumberOfTargetColumns() {
		return getTargetColumns().size();
	}

	/**
	 * Iterates each target column
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void forEachTargetColumn(BiConsumer<Integer, ColumnReference> consumer);
}
