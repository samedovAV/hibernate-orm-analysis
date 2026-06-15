/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.spi;

import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @since 7.2
 *
 * @author Gavin King
 */
@Incubating
public interface TransactionCompletionCallbacksImplementor extends TransactionCompletionCallbacks {
	/**
	 * Are there any registered before-completion callbacks?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasBeforeCompletionCallbacks();

	/**
	 * Are there any registered after-completion callbacks?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasAfterCompletionCallbacks();

	/**
	 * Execute registered before-completion callbacks, if any.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void beforeTransactionCompletion();

	/**
	 * Execute registered after-completion callbacks, if any.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void afterTransactionCompletion(boolean success);

	/**
	 * Register a cache space to be invalidated after successful transaction completion.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addSpaceToInvalidate(String space);

	/**
	 * Ensure internal queues are initialized for sharing between sessions that share
	 * the same transaction coordinator. Returns this instance for convenience/fluency.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TransactionCompletionCallbacksImplementor forSharing();


	/**
	 * Execute all pending {@link org.hibernate.action.internal.BulkOperationCleanupAction}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void executePendingBulkOperationCleanUpActions();
}
