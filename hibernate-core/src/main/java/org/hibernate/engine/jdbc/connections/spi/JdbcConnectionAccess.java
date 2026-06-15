/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.connections.spi;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Provides centralized access to JDBC connections.  Centralized to hide the complexity of accounting for contextual
 * (multi-tenant) versus non-contextual access.
 *
 * @author Steve Ebersole
 */
public interface JdbcConnectionAccess extends Serializable {
	/**
	 * Obtain a JDBC connection
	 *
	 * @return The obtained connection
	 *
	 * @throws SQLException Indicates a problem getting the connection
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Connection obtainConnection() throws SQLException;

	/**
	 * Release a previously obtained connection
	 *
	 * @param connection The connection to release
	 *
	 * @throws SQLException Indicates a problem releasing the connection
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void releaseConnection(Connection connection) throws SQLException;

	/**
	 * Does the underlying provider of connections support aggressive releasing of connections (and re-acquisition
	 * of those connections later, if need be) in JTA environments?
	 *
	 * @return true/false
	 *
	 * @see ConnectionProvider#supportsAggressiveRelease()
	 * @see MultiTenantConnectionProvider#supportsAggressiveRelease()
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean supportsAggressiveRelease();
}
