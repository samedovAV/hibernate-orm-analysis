/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.resource.transaction.backend.jta.internal.synchronization;

import jakarta.transaction.Synchronization;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Funnels JTA {@link Synchronization} callbacks back into the Hibernate transaction engine.
 *
 * @author Steve Ebersole
 */
public interface SynchronizationCallbackCoordinator extends Synchronization {
	/**
	 * Called by the JTA {@link org.hibernate.resource.transaction.spi.TransactionCoordinator}
	 * when it registers the {@code Synchronization} with the JTA system.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void synchronizationRegistered();

	/**
	 * Called by the session to allow this {@link SynchronizationCallbackCoordinator} to
	 * process any after completion handling that it has delayed due to thread affinity.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void processAnyDelayedAfterCompletion();
}
