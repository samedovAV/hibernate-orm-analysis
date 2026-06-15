/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Generalized access to state information relative to the "current process" of
 * creating a SQL AST.
 *
 * @author Steve Ebersole
 */
public interface SqlAstProcessingState {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqlAstProcessingState getParentState();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqlExpressionResolver getSqlExpressionResolver();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqlAstCreationState getSqlAstCreationState();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isTopLevel() {//todo: naming
		return getParentState() == null;
	}
}
