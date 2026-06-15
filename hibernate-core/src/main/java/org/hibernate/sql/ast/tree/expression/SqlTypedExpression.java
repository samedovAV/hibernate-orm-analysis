/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import org.hibernate.metamodel.mapping.SqlTypedMapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An expression that has SQL type information.
 */
public interface SqlTypedExpression extends Expression {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqlTypedMapping getSqlTypedMapping();
}
