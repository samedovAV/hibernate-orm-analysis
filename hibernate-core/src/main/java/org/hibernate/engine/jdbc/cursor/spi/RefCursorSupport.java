/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.cursor.spi;

import java.sql.CallableStatement;
import java.sql.ResultSet;

import org.hibernate.service.Service;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contract for JDBC REF_CURSOR support.
 *
 * @author Steve Ebersole
 *
 * @since 4.3
 */
public interface RefCursorSupport extends Service {
	/**
	 * Register a parameter capable of returning a {@link ResultSet} *by position*.
	 *
	 * @param statement The callable statement.
	 * @param position The bind position at which to register the output param.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registerRefCursorParameter(CallableStatement statement, int position);

	/**
	 * Register a parameter capable of returning a {@link ResultSet} *by name*.
	 *
	 * @param statement The callable statement.
	 * @param name The parameter name (for drivers which support named parameters).
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registerRefCursorParameter(CallableStatement statement, String name);

	/**
	 * Given a callable statement previously processed by {@link #registerRefCursorParameter(CallableStatement, int)},
	 * extract the {@link ResultSet}.
	 *
	 *
	 * @param statement The callable statement.
	 * @param position The bind position at which to register the output param.
	 *
	 * @return The extracted result set.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ResultSet getResultSet(CallableStatement statement, int position);

	/**
	 * Given a callable statement previously processed by {@link #registerRefCursorParameter(CallableStatement, String)},
	 * extract the {@link ResultSet}.
	 *
	 * @param statement The callable statement.
	 * @param name The parameter name (for drivers which support named parameters).
	 *
	 * @return The extracted result set.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ResultSet getResultSet(CallableStatement statement, String name);
}
