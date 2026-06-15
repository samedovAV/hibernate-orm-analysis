/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.creation.internal;

import org.hibernate.SessionEventListener;
import org.hibernate.Transaction;
import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.engine.spi.TransactionCompletionCallbacksImplementor;
import org.hibernate.resource.transaction.spi.TransactionCoordinator;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An extension of {@link SessionCreationOptions} for cases where the
 * session to be created shares some part of the "transaction context"
 * of another session.
 *
 * @author Steve Ebersole
 *
 * @see org.hibernate.SharedSessionBuilder
 *
 * @since 7.2
 */
public interface SharedSessionCreationOptions extends SessionCreationOptions {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isTransactionCoordinatorShared();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TransactionCoordinator getTransactionCoordinator();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcCoordinator getJdbcCoordinator();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Transaction getTransaction();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TransactionCompletionCallbacksImplementor getTransactionCompletionCallbacks();

	/**
	 * Registers callbacks for the child session to integrate with events of the parent session.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registerParentSessionObserver(ParentSessionObserver observer);

	/**
	 * Consolidated implementation of adding the parent session observer.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void registerParentSessionObserver(ParentSessionObserver observer, SharedSessionContractImplementor original) {
		original.getEventListenerManager().addListener( new SessionEventListener() {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public void flushEnd(int numberOfEntities, int numberOfCollections) {
				observer.onParentFlush();
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public void partialFlushEnd(int numberOfEntities, int numberOfCollections) {
				observer.onParentFlush();
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public void end() {
				observer.onParentClose();
			}
		} );
	}

}
