/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.from;

import org.hibernate.sql.ast.SqlAstJoinType;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * The commonalities between {@link TableGroupJoin} and {@link TableReferenceJoin}.
 *
 * @author Christian Beikov
 */
public interface TableJoin extends SqlAstNode {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqlAstJoinType getJoinType();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Predicate getPredicate();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqlAstNode getJoinedNode();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isInitialized();
}
