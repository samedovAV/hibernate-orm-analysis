/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.resource.transaction.spi;

import java.sql.Connection;

import org.hibernate.tool.schema.internal.exec.JdbcContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Provides access to a {@link Connection} that is isolated from any
 * "current transaction" with the designated purpose of performing DDL
 * commands.
 *
 * @author Steve Ebersole
 */
public interface DdlTransactionIsolator extends AutoCloseable {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcContext getJdbcContext();

	/**
	 * Returns a {@link Connection} that is usable within the bounds of the
	 * {@link TransactionCoordinatorBuilder#buildDdlTransactionIsolator}
	 * and {@link #release} calls, with autocommit mode enabled. Further,
	 * this {@code Connection} will be isolated (transactionally) from any
	 * transaction in effect prior to the call to
	 * {@code buildDdlTransactionIsolator}.
	 *
	 * @return The Connection.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Connection getIsolatedConnection();

	/**
	 * Returns a {@link Connection} that is usable within the bounds of the
	 * {@link TransactionCoordinatorBuilder#buildDdlTransactionIsolator}
	 * and {@link #release} calls, with the given autocommit mode. Further,
	 * this {@code Connection} will be isolated (transactionally) from any
	 * transaction in effect prior to the call to
	 * {@code buildDdlTransactionIsolator}.
	 *
	 * @return The Connection.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Connection getIsolatedConnection(boolean autocommit);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void release();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void close() {
		release();
	}
}
