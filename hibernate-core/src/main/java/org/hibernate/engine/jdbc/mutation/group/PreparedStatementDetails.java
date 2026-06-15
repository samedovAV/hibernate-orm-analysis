/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.mutation.group;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;

import org.hibernate.Incubating;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.jdbc.Expectation;
import org.hibernate.sql.model.TableMapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Descriptor for details about a {@link PreparedStatement}
 *
 * @author Steve Ebersole
 */
@Incubating
public interface PreparedStatementDetails {
	/**
	 * The name of the mutating table
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TableMapping getMutatingTableDetails();

	/**
	 * The SQL used to mutate the table
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSqlString();

	/**
	 * The {@link PreparedStatement} generated from the SQL.  May return null.
	 *
	 * @see #resolveStatement()
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PreparedStatement getStatement();

	/**
	 * The {@link PreparedStatement} generated from the SQL.
	 * <p>
	 * Unlike {@link #getStatement()}, this method will attempt to create the PreparedStatement
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PreparedStatement resolveStatement();

	/**
	 * The expectation used to validate the outcome of the execution
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Expectation getExpectation();

	/**
	 * Whether the statement is callable
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isCallable() {
		return getStatement() instanceof CallableStatement;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void releaseStatement(SharedSessionContractImplementor session);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean toRelease(){
		return false;
	}
}
