/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree;

import org.hibernate.sql.ast.SqlAstWalker;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface SqlAstNode {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void accept(SqlAstWalker sqlTreeWalker);
}
