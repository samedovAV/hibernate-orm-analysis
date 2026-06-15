/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.resource.transaction.spi;

import org.hibernate.resource.jdbc.spi.JdbcSessionOwner;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models an owner of a {@link TransactionCoordinator}.  Mainly used in 2 ways:<ul>
 *     <li>
 *         First to allow the coordinator to determine if its owner is still active (open, etc).
 *     </li>
 *     <li>
 *         Second is to allow the coordinator to dispatch before and after completion events to the owner
 *     </li>
 * </ul>
 *
 * @author Steve Ebersole
 */
public interface TransactionCoordinatorOwner {
	/**
	 * Is the TransactionCoordinator owner considered active?
	 *
	 * @return {@code true} indicates the owner is still active; {@code false} indicates it is not.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isActive();

	/**
	 * Callback indicating recognition of entering into a transactional
	 * context whether that is explicitly via the Hibernate
	 * {@link org.hibernate.Transaction} API or via registration
	 * of Hibernate's JTA Synchronization impl with a JTA Transaction
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void startTransactionBoundary() {
		getJdbcSessionOwner().startTransactionBoundary();
	}

	/**
	 * An after-begin callback from the coordinator to its owner.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void afterTransactionBegin();

	/**
	 * A before-completion callback from the coordinator to its owner.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void beforeTransactionCompletion();

	/**
	 * An after-completion callback from the coordinator to its owner.
	 *
	 * @param successful Was the transaction successful?
	 * @param delayed Is this delayed after transaction completion call (aka after a timeout)?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void afterTransactionCompletion(boolean successful, boolean delayed);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcSessionOwner getJdbcSessionOwner();

	/**
	 * Set the effective transaction timeout period for the current transaction, in seconds.
	 *
	 * @param seconds The number of seconds before a time out should occur.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setTransactionTimeOut(int seconds);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void flushBeforeTransactionCompletion();
}
