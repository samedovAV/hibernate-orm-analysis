/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree;

import java.util.List;

import org.hibernate.sql.ast.tree.expression.ColumnReference;
import org.hibernate.sql.ast.tree.from.NamedTableReference;
import org.hibernate.sql.model.MutationTarget;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Specialization of Statement for mutation (DML) statements
 *
 * @author Steve Ebersole
 */
public interface MutationStatement extends Statement {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NamedTableReference getTargetTable();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationTarget<?,?> getMutationTarget();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<ColumnReference> getReturningColumns();
}
