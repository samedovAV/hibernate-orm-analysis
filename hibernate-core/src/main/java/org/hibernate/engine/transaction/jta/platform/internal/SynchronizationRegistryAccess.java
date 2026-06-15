/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.transaction.jta.platform.internal;

import java.io.Serializable;
import jakarta.transaction.TransactionSynchronizationRegistry;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Provides access to a {@link TransactionSynchronizationRegistry} for use by {@link TransactionSynchronizationRegistry}-based
 * {@link JtaSynchronizationStrategy} implementations.
 *
 * @author Steve Ebersole
 */
public interface SynchronizationRegistryAccess extends Serializable {
	/**
	 * Obtain the synchronization registry
	 *
	 * @return the synchronization registry
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TransactionSynchronizationRegistry getSynchronizationRegistry();
}
