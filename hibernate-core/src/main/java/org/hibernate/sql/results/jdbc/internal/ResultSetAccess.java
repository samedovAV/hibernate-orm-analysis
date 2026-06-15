/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.jdbc.internal;

import java.sql.ResultSet;

import org.hibernate.sql.results.jdbc.spi.JdbcValuesMetadata;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Access to a JDBC {@link ResultSet} and information about it.
 *
 * @author Steve Ebersole
 */
public interface ResultSetAccess extends JdbcValuesMetadata {
	/**
	 * The JDBC {@link ResultSet}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ResultSet getResultSet();

	/**
	 * Release the JDBC {@link ResultSet}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void release();

	/**
	 * An estimate for the number of results that can be expected for pre-sizing collections.
	 * May return zero or negative values if the count cannot be reasonably estimated.
	 *
	 * @since 6.6
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getResultCountEstimate();
}
