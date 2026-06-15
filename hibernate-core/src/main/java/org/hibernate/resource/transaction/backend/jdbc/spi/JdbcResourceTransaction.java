/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.resource.transaction.backend.jdbc.spi;

import org.hibernate.resource.transaction.spi.TransactionStatus;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models access to the resource transaction of the underlying JDBC resource.
 *
 * @author Steve Ebersole
 */
public interface JdbcResourceTransaction {
	/**
	 * Begin the resource transaction
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void begin();

	/**
	 * Commit the resource transaction
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void commit();

	/**
	 * Rollback the resource transaction
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void rollback();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TransactionStatus getStatus();

	/**
	 * Mark this transaction for rollback.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void markRollbackOnly();
}
