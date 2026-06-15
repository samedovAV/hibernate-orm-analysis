/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.spi;

import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Collection of {@linkplain BeforeCompletionCallback before} and {@linkplain AfterCompletionCallback after}
 * callbacks related to transaction completion.
 *
 * @author Steve Ebersole
 *
 * @since 7.2
 */
@Incubating
public interface TransactionCompletionCallbacks {
	/**
	 * Commonality for {@linkplain BeforeCompletionCallback before} and
	 * {@linkplain AfterCompletionCallback after} callbacks.
	 */
	interface CompletionCallback {
	}

	interface BeforeCompletionCallback extends CompletionCallback {
		/**
		 * Perform whatever processing is encapsulated here before completion of the transaction.
		 *
		 * @param session The session on which the transaction is preparing to complete.
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void doBeforeTransactionCompletion(SharedSessionContractImplementor session);

	}

	interface AfterCompletionCallback extends CompletionCallback {
		/**
		 * Perform whatever processing is encapsulated here after completion of the transaction.
		 *
		 * @param success Did the transaction complete successfully?  True means it did.
		 * @param session The session on which the transaction is completing.
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void doAfterTransactionCompletion(boolean success, SharedSessionContractImplementor session);
	}

	/**
	 * Register a {@code process} (callback) to be performed at the start of transaction completion.
	 *
	 * @param process The callback.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registerCallback(BeforeCompletionCallback process);

	/**
	 * Register a {@code process} (callback) to be performed at the end of transaction completion.
	 *
	 * @param process The callback.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registerCallback(AfterCompletionCallback process);
}
