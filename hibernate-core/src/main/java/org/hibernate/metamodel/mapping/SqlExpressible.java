/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Unifying contract for things that are capable of being an expression in
 * the SQL AST.
 *
 * @author Steve Ebersole
 */
public interface SqlExpressible extends JdbcMappingContainer {
	/**
	 * Anything that is expressible at the SQL AST level
	 * would be of basic type.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcMapping getJdbcMapping();

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default JdbcMapping getJdbcMapping(int index) {
		assert index == 0;
		return getJdbcMapping();
	}

}
