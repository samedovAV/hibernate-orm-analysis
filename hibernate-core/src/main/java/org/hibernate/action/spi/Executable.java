/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.action.spi;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.EventSource;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An operation which may be scheduled for later execution. Usually, the
 * operation is a database insert/update/delete, together with required
 * second-level cache management.
 *
 * @author Gavin King
 * @author Steve Ebersole
 */
public interface Executable {
	/**
	 * What spaces (tables) are affected by this action?
	 *
	 * @return The spaces affected by this action.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String[] getPropertySpaces();

	/**
	 * Called before executing any actions.  Gives actions a chance to perform any preparation.
	 *
	 * @throws HibernateException Indicates a problem during preparation.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void beforeExecutions() throws HibernateException;

	/**
	 * Execute this action.
	 *
	 * @throws HibernateException Indicates a problem during execution.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void execute() throws HibernateException;

	/**
	 * Get the after-transaction-completion process, if any, for this action.
	 *
	 * @return The after-transaction-completion process, or null if we have no
	 * after-transaction-completion process
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	AfterTransactionCompletionProcess getAfterTransactionCompletionProcess();

	/**
	 * Get the before-transaction-completion process, if any, for this action.
	 *
	 * @return The before-transaction-completion process, or null if we have no
	 * before-transaction-completion process
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	BeforeTransactionCompletionProcess getBeforeTransactionCompletionProcess();

	/**
	 * Reconnect to session after deserialization
	 *
	 * @param session The session being deserialized; must be an EventSource
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void afterDeserialize(EventSource session);
}
