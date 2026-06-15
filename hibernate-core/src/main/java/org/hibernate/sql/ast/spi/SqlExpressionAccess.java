/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.spi;

import org.hibernate.sql.ast.tree.expression.Expression;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface SqlExpressionAccess {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Expression getSqlExpression();
}
