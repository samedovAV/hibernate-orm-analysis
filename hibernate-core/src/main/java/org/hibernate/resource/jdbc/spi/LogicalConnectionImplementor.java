/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.resource.jdbc.spi;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;

import org.hibernate.resource.jdbc.LogicalConnection;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * SPI contract for {@link LogicalConnection}.
 *
 * @author Steve Ebersole
 *
 * @see org.hibernate.engine.jdbc.spi.JdbcCoordinator#getLogicalConnection()
 */
public interface LogicalConnectionImplementor extends LogicalConnection {
	/**
	 * Exposes access to the "real" {@link Connection}.
	 *
	 * @return The connection
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Connection getPhysicalConnection();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PhysicalConnectionHandlingMode getConnectionHandlingMode();

	/**
	 * Notification indicating a JDBC statement has been executed, to trigger
	 * {@link org.hibernate.ConnectionReleaseMode#AFTER_STATEMENT} releasing
	 * if needed.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void afterStatement();

	/**
	 * Notification indicating a transaction is about to be completed, to
	 * trigger release of the JDBC connection if needed, that is, if
	 * {@link org.hibernate.ConnectionReleaseMode#BEFORE_TRANSACTION_COMPLETION}
	 * is enabled.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void beforeTransactionCompletion();

	/**
	 * Notification indicating a transaction has just completed, to trigger
	 * {@link org.hibernate.ConnectionReleaseMode#AFTER_TRANSACTION} releasing
	 * if needed.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void afterTransaction();

	/**
	 * Manually disconnect the underlying JDBC Connection.
	 * The assumption here is that the manager will be reconnected at a
	 * later point in time.
	 *
	 * @return The connection maintained here at time of disconnect.
	 *         {@code null} if there was no connection cached internally.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Connection manualDisconnect();

	/**
	 * Manually reconnect the underlying JDBC Connection.
	 * Should be called at some point after {@link #manualDisconnect()}.
	 *
	 * @param suppliedConnection For user supplied connection strategy the
	 *                           user needs to hand us the connection with
	 *                           which to reconnect. It is an error to pass
	 *                           a connection in the other strategies.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void manualReconnect(Connection suppliedConnection);

	/**
	 * Access to the current underlying JDBC transaction.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PhysicalJdbcTransaction getPhysicalJdbcTransaction();

	/**
	 * Serialization hook.
	 *
	 * @param oos The stream to write out state to
	 *
	 * @throws IOException Problem accessing stream
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void serialize(ObjectOutputStream oos) throws IOException;

	/**
	 * Mark the associated transaction as rollback-only.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void markRollbackOnly();
}
