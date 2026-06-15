/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.resource.jdbc;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.ResultSet;
import java.sql.Statement;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A registry for tracking JDBC resources.
 *
 * @author Steve Ebersole
 */
public interface ResourceRegistry {
	/**
	 * Does this registry currently have any registered resources?
	 *
	 * @return True if the registry does have registered resources; false otherwise.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasRegisteredResources();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void releaseResources();

	/**
	 * Register a JDBC statement.
	 *
	 * @param statement The statement to register.
	 * @param cancelable Is the statement being registered capable of being cancelled?  In other words,
	 * should we register it to be the target of subsequent {@link #cancelLastQuery()} calls?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void register(Statement statement, boolean cancelable);

	/**
	 * Release a previously registered statement.
	 *
	 * @param statement The statement to release.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void release(Statement statement);

	/**
	 * Register a JDBC result set.
	 * <p>
	 * Implementation note: Second parameter has been introduced to prevent
	 * multiple registrations of the same statement in case {@link ResultSet#getStatement()}
	 * does not return original {@link Statement} object.
	 *
	 * @param resultSet The result set to register.
	 * @param statement Statement from which {@link ResultSet} has been generated.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void register(ResultSet resultSet, Statement statement);

	/**
	 * Release a previously registered result set.
	 *
	 * @param resultSet The result set to release.
	 * @param statement Statement from which {@link ResultSet} has been generated.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void release(ResultSet resultSet, Statement statement);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void register(Blob blob);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void release(Blob blob);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void register(Clob clob);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void release(Clob clob);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void register(NClob nclob);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void release(NClob nclob);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void cancelLastQuery();

}
