/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.ast.builder;

import org.hibernate.sql.model.ast.MutatingTableReference;
import org.hibernate.sql.model.ast.TableMutation;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Generalized contract for building {@link TableMutation} instances
 *
 * @author Steve Ebersole
 */
public interface TableMutationBuilder<M extends TableMutation<?>> {
	/**
	 * Constant for `null`
	 */
	String NULL = "null";
	/**
	 * Constant for `not null`
	 */
	String NOT_NULL = "not null";

	/**
	 * Reference (in the SQL AST sense) to the mutating table
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutatingTableReference getMutatingTable();

	/**
	 * Build the mutation descriptor
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	M buildMutation();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasValueBindings();
}
