/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.sql.exec.spi.JdbcParameterBinder;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface Literal extends JdbcParameterBinder, Expression {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getLiteralValue();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcMapping getJdbcMapping();
}
