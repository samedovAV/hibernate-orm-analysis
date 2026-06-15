/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.transaction.jta.platform.internal;

import java.io.Serializable;
import jakarta.transaction.TransactionManager;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Provides access to a {@link TransactionManager} for use by {@link TransactionManager}-based
 * {@link JtaSynchronizationStrategy} implementations.
 *
 * @author Steve Ebersole
 */
public interface TransactionManagerAccess extends Serializable {
	/**
	 * Obtain the transaction manager
	 *
	 * @return The transaction manager.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TransactionManager getTransactionManager();
}
