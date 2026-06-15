/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.jdbc.spi;

import org.hibernate.type.BasicType;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.spi.TypeConfiguration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Access to information about the underlying JDBC values
 * such as type, position, column name, etc
 */
public interface JdbcValuesMetadata {
	/**
	 * Number of values in the underlying result
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getColumnCount();

	/**
	 * Position of a particular result value by name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int resolveColumnPosition(String columnName);

	/**
	 * Name of a particular result value by position
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String resolveColumnName(int position);

	/**
	 * Determine the mapping to use for a particular position in the result
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<J> BasicType<J> resolveType(
			int position,
			JavaType<J> explicitJavaType,
			TypeConfiguration typeConfiguration);
}
