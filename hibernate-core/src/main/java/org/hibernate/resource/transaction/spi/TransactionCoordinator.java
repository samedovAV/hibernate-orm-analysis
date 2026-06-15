/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.resource.transaction.spi;

import org.hibernate.jpa.spi.JpaCompliance;

import static org.hibernate.resource.transaction.spi.TransactionStatus.ACTIVE;
import static org.hibernate.resource.transaction.spi.TransactionStatus.MARKED_ROLLBACK;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models the coordination of all transaction related flows.
 *
 * @author Steve Ebersole
 */
public interface TransactionCoordinator {
	/**
	 * Access to the builder that generated this coordinator
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TransactionCoordinatorBuilder getTransactionCoordinatorBuilder();

	/**
	 * Get the delegate used by the local transaction driver to control the underlying transaction
	 *
	 * @return The control delegate.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TransactionDriver getTransactionDriverControl();

	/**
	 * Get access to the local registry of Synchronization instances
	 *
	 * @return The local Synchronization registry
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SynchronizationRegistry getLocalSynchronizations();


	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCompliance getJpaCompliance();

	/**
	 * Indicates an explicit request to join a transaction.  This is mainly intended to handle the JPA requirement
	 * around {@link jakarta.persistence.EntityManager#joinTransaction()}, and generally speaking only has an impact in
	 * JTA environments
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void explicitJoin();

	/**
	 * Determine if there is an active transaction that this coordinator is already joined to.
	 *
	 * @return {@code true} if there is an active transaction this coordinator is already joined to; {@code false}
	 * otherwise.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isJoined();

	/**
	 * Used by owner of the "JDBC session" as a means to indicate that implicit joining should be done if needed.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void pulse();

	/**
	 * Is this transaction still active?
	 * <p>
	 * Answers on a best-effort basis.  For example, in the case of JDBC based transactions we cannot know that a
	 * transaction is active when it is initiated directly through the JDBC {@link java.sql.Connection}, only when
	 * it is initiated from here.
	 *
	 * @return {@code true} if the transaction is still active; {@code false} otherwise.
	 *
	 * @throws org.hibernate.HibernateException Indicates a problem checking the transaction status.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isActive();

	/**
	 * Retrieve an isolation delegate appropriate for this transaction strategy.
	 *
	 * @return An isolation delegate.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	IsolationDelegate createIsolationDelegate();

	/**
	 * Adds an observer to the coordinator.
	 * <p>
	 * Observers are not to be cleared on transaction completion.
	 *
	 * @param observer The observer to add.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addObserver(TransactionObserver observer);

	/**
	 * Remove an observer from the coordinator.
	 *
	 * @param observer The observer to remove.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void removeObserver(TransactionObserver observer);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setTimeOut(int seconds);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getTimeOut();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isTransactionActive() {
		return isJoined()
			&& getTransactionDriverControl().isActive();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void invalidate(){}

	/**
	 * Provides the means for resource-local transactions (as transaction drivers) to control the
	 * underlying "physical transaction" currently associated with the {@code TransactionCoordinator}.
	 *
	 * @author Steve Ebersole
	 */
	interface TransactionDriver {
		/**
		 * Begin the physical transaction
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void begin();

		/**
		 * Commit the physical transaction
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void commit();

		/**
		 * Rollback the physical transaction
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void rollback();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		TransactionStatus getStatus();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void markRollbackOnly();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		default boolean isActive() {
			final var status = getStatus();
			return status == ACTIVE
				|| status == MARKED_ROLLBACK;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		default boolean isActiveAndNoMarkedForRollback() {
			return getStatus() == ACTIVE;
		}

		// todo : org.hibernate.Transaction will need access to register local Synchronizations.
		//		depending on how we integrate TransactionCoordinator/TransactionDriverControl with
		//		org.hibernate.Transaction that might be best done by:
		//			1) exposing registerSynchronization here (if the Transaction is just passed this)
		//			2) using the exposed TransactionCoordinator#getLocalSynchronizations (if the Transaction is passed the TransactionCoordinator)
	}
}
