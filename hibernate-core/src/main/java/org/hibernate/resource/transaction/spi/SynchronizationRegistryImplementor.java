/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.resource.transaction.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * SPI contract for {@link SynchronizationRegistry} implementors.
 *
 * @author Steve Ebersole
 */
public interface SynchronizationRegistryImplementor extends SynchronizationRegistry {
	/**
	 * Delegates the {@link jakarta.transaction.Synchronization#beforeCompletion}
	 * call to each registered {@code Synchronization}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void notifySynchronizationsBeforeTransactionCompletion();

	/**
	 * Delegates the {@link jakarta.transaction.Synchronization#afterCompletion}
	 * call to each registered {@code Synchronization} and clears the registered
	 * {@code Synchronization}s after all have been notified.
	 *
	 * @param status The transaction status, per {@link jakarta.transaction.Status}
	 *               constants
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void notifySynchronizationsAfterTransactionCompletion(int status);

	/**
	 * Clears all synchronizations from this registry.
	 * The synchronizations are automatically cleared during after-completion
	 * handling; see {@link #notifySynchronizationsAfterTransactionCompletion}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void clearSynchronizations();
}
