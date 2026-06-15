/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.jdbc;

import org.hibernate.engine.jdbc.mutation.ParameterUsage;
import org.hibernate.metamodel.mapping.JdbcMapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Descriptor for JDBC value within an operation.
 *
 * @implSpec Used while {@linkplain org.hibernate.engine.jdbc.mutation.JdbcValueBindings binding}
 * values to JDBC Statements
 *
 * @author Steve Ebersole
 */
public interface JdbcValueDescriptor {
	/**
	 * The name of the column this parameter "maps to"
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getColumnName();

	/**
	 * How the parameter is used in the query
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ParameterUsage getUsage();

	/**
	 * The position within the operation, starting at 1 per JDBC
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getJdbcPosition();

	/**
	 * The JDBC mapping (type, etc.) for the parameter
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcMapping getJdbcMapping();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean matches(String columnName, ParameterUsage usage) {
		return getColumnName().equals( columnName )
			&& getUsage() == usage;
	}
}
