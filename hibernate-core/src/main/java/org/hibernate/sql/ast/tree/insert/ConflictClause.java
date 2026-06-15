/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.insert;

import java.util.List;

import org.hibernate.sql.ast.tree.predicate.Predicate;
import org.hibernate.sql.ast.tree.update.Assignment;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @since 6.5
 */
public class ConflictClause {
	private final @Nullable String constraintName;
	private final List<String> constraintColumnNames;
	private final List<Assignment> assignments;
	private final @Nullable Predicate predicate;

	public ConflictClause(
			@Nullable String constraintName,
			List<String> constraintColumnNames,
			List<Assignment> assignments,
			@Nullable Predicate predicate) {
		this.constraintName = constraintName;
		this.constraintColumnNames = constraintColumnNames;
		this.assignments = assignments;
		this.predicate = predicate;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable String getConstraintName() {
		return constraintName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<String> getConstraintColumnNames() {
		return constraintColumnNames;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<Assignment> getAssignments() {
		return assignments;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isDoNothing() {
		return assignments.isEmpty();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isDoUpdate() {
		return !assignments.isEmpty();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Predicate getPredicate() {
		return predicate;
	}
}
