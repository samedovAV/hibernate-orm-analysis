/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.resource.transaction.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Observer of internal transaction events.
 * <p>
 * A {@link TransactionObserver} must be registered with the {@link TransactionCoordinator}
 * by calling {@link TransactionCoordinator#addObserver(TransactionObserver) addObserver()}.
 *
 * @author Steve Ebersole
 */
public interface TransactionObserver {
	/**
	 * Callback for processing the beginning of a transaction.
	 * <p>
	 * Do not rely on this being called as the transaction may be started
	 * in some way other than via the {@link org.hibernate.Transaction} API.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void afterBegin();

	/**
	 * Callback for processing the initial phase of transaction completion.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void beforeCompletion();

	/**
	 * Callback for processing the last phase of transaction completion.
	 *
	 * @param successful Was the transaction successful?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void afterCompletion(boolean successful, boolean delayed);
}
