/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.resource.transaction.backend.jdbc.internal;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.RollbackException;
import jakarta.transaction.Status;

import org.hibernate.resource.transaction.backend.jta.internal.StatusTranslator;
import org.hibernate.resource.transaction.spi.IsolationDelegate;
import org.hibernate.jpa.spi.JpaCompliance;
import org.hibernate.resource.transaction.backend.jdbc.spi.JdbcResourceTransaction;
import org.hibernate.resource.transaction.backend.jdbc.spi.JdbcResourceTransactionAccess;
import org.hibernate.resource.transaction.internal.SynchronizationRegistryStandardImpl;
import org.hibernate.resource.transaction.spi.SynchronizationRegistry;
import org.hibernate.resource.transaction.spi.TransactionCoordinator;
import org.hibernate.resource.transaction.spi.TransactionCoordinatorBuilder;
import org.hibernate.resource.transaction.spi.TransactionCoordinatorOwner;
import org.hibernate.resource.transaction.spi.TransactionObserver;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import static java.util.Collections.emptyList;
import static org.hibernate.engine.jdbc.JdbcLogging.JDBC_LOGGER;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An implementation of {@link TransactionCoordinator} based on managing a
 * transaction through the JDBC {@link Connection} via {@link JdbcResourceTransaction}.
 *
 * @author Steve Ebersole
 *
 * @see JdbcResourceTransaction
 */
public class JdbcResourceLocalTransactionCoordinatorImpl implements TransactionCoordinator {

	private final TransactionCoordinatorBuilder transactionCoordinatorBuilder;
	private final JdbcResourceTransactionAccess jdbcResourceTransactionAccess;
	private final TransactionCoordinatorOwner transactionCoordinatorOwner;
	private final SynchronizationRegistryStandardImpl synchronizationRegistry = new SynchronizationRegistryStandardImpl();

	private final JpaCompliance jpaCompliance;

	private TransactionDriverControlImpl physicalTransactionDelegate;

	private int timeOut = -1;

	private transient List<TransactionObserver> observers = null;

	/**
	 * Construct a ResourceLocalTransactionCoordinatorImpl instance.  package-protected to ensure access goes through
	 * builder.
	 *
	 * @param owner The transactionCoordinatorOwner
	 */
	JdbcResourceLocalTransactionCoordinatorImpl(
			TransactionCoordinatorBuilder transactionCoordinatorBuilder,
			TransactionCoordinatorOwner owner,
			JdbcResourceTransactionAccess jdbcResourceTransactionAccess) {
		this.transactionCoordinatorBuilder = transactionCoordinatorBuilder;
		this.jdbcResourceTransactionAccess = jdbcResourceTransactionAccess;
		this.transactionCoordinatorOwner = owner;
		this.jpaCompliance = owner.getJdbcSessionOwner().getJdbcSessionContext().getJpaCompliance();
	}

	/**
	 * Needed because while iterating the observers list and executing the before/update callbacks,
	 * some observers might get removed from the list.
	 *
	 * @return TransactionObserver
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private Iterable<TransactionObserver> observers() {
		return observers == null || observers.isEmpty() ? emptyList() : new ArrayList<>( observers );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TransactionDriver getTransactionDriverControl() {
		// Again, this PhysicalTransactionDelegate will act as the bridge from the local transaction back into the
		// coordinator.  We lazily build it as we invalidate each delegate after each transaction (a delegate is
		// valid for just one transaction)
		if ( physicalTransactionDelegate == null ) {
			physicalTransactionDelegate =
					new TransactionDriverControlImpl( jdbcResourceTransactionAccess.getResourceLocalTransaction() );
		}
		return physicalTransactionDelegate;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void explicitJoin() {
		// nothing to do here, but log a warning
		JDBC_LOGGER.callingJoinTransactionOnNonJtaEntityManager();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isJoined() {
		return physicalTransactionDelegate != null
			&& getTransactionDriverControl().isActive();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void pulse() {
		// nothing to do here
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SynchronizationRegistry getLocalSynchronizations() {
		return synchronizationRegistry;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JpaCompliance getJpaCompliance() {
		return jpaCompliance;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isActive() {
		return transactionCoordinatorOwner.isActive();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public IsolationDelegate createIsolationDelegate() {
		return new JdbcIsolationDelegate( transactionCoordinatorOwner );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TransactionCoordinatorBuilder getTransactionCoordinatorBuilder() {
		return transactionCoordinatorBuilder;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setTimeOut(int seconds) {
		this.timeOut = seconds;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getTimeOut() {
		return timeOut;
	}

	// PhysicalTransactionDelegate ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private void afterBeginCallback() {
		if ( timeOut > 0 ) {
			transactionCoordinatorOwner.setTransactionTimeOut( timeOut );
		}

		// report entering into a "transactional context"
		transactionCoordinatorOwner.startTransactionBoundary();

		JDBC_LOGGER.notifyingResourceLocalObserversAfterBegin();

		// trigger the Transaction-API-only after-begin callback
		transactionCoordinatorOwner.afterTransactionBegin();

		// notify all registered observers
		for ( TransactionObserver observer : observers() ) {
			observer.afterBegin();
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private void beforeCompletionCallback() {
		JDBC_LOGGER.notifyingResourceLocalObserversBeforeCompletion();
		try {
			transactionCoordinatorOwner.beforeTransactionCompletion();
			synchronizationRegistry.notifySynchronizationsBeforeTransactionCompletion();
			for ( var transactionObserver : observers() ) {
				transactionObserver.beforeCompletion();
			}
		}
		catch (RuntimeException e) {
			if ( physicalTransactionDelegate != null ) {
				// should never happen that the physicalTransactionDelegate is null, but to be safe
				physicalTransactionDelegate.markRollbackOnly();
			}
			throw e;
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private void afterCompletionCallback(int status) {
		JDBC_LOGGER.notifyingResourceLocalObserversAfterCompletion();
		synchronizationRegistry.notifySynchronizationsAfterTransactionCompletion( status );
		final boolean successful = status == Status.STATUS_COMMITTED;
		transactionCoordinatorOwner.afterTransactionCompletion( successful, false );
		for ( var transactionObserver : observers() ) {
			transactionObserver.afterCompletion( successful, false );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addObserver(TransactionObserver observer) {
		if ( observers == null ) {
			observers = new ArrayList<>( 6 );
		}
		observers.add( observer );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void removeObserver(TransactionObserver observer) {
		if ( observers != null ) {
			observers.remove( observer );
		}
	}

	/**
	 * The delegate bridging between the local (application facing) transaction and the "physical" notion of a
	 * transaction via the JDBC Connection.
	 */
	public class TransactionDriverControlImpl implements TransactionDriver {
		private final JdbcResourceTransaction jdbcResourceTransaction;
//		private boolean invalid;

		public TransactionDriverControlImpl(JdbcResourceTransaction jdbcResourceTransaction) {
			this.jdbcResourceTransaction = jdbcResourceTransaction;
		}

//		protected void invalidate() {
//			invalid = true;
//		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public void begin() {
//			errorIfInvalid();
			jdbcResourceTransaction.begin();
			JdbcResourceLocalTransactionCoordinatorImpl.this.afterBeginCallback();
		}

//		protected void errorIfInvalid() {
//			if ( invalid ) {
//				throw new IllegalStateException( "Physical transaction delegate is no longer valid" );
//			}
//		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public void commit() {
			if ( isRollbackOnly() ) {
				commitRollbackOnly();
			}
			else {
				commitNoRollbackOnly();
			}
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		private boolean isRollbackOnly() {
			return jdbcResourceTransaction.getStatus() == TransactionStatus.MARKED_ROLLBACK;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		private void commitNoRollbackOnly() {
			try {
				beforeCompletionCallback();
			}
			catch (RuntimeException e) {
				// error processing before completion callbacks
				// attempt to roll back the transaction
				try {
					jdbcResourceTransaction.rollback();
				}
				catch (RuntimeException e2) {
					e.addSuppressed( e2 );
				}
				try {
					afterCompletionCallback( StatusTranslator.STATUS_FAILED_ROLLBACK );
				}
				catch (RuntimeException e2) {
					e.addSuppressed( e2 );
				}
				throw e;
			}

			try {
				jdbcResourceTransaction.commit();
			}
			catch (RuntimeException e) {
				// commit failed
				try {
					afterCompletionCallback( StatusTranslator.STATUS_FAILED_COMMIT );
				}
				catch (RuntimeException e2) {
					e.addSuppressed( e2 );
				}
				throw e;
			}
			// commit successful
			afterCompletionCallback( Status.STATUS_COMMITTED );
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		private void commitRollbackOnly() {
			JDBC_LOGGER.onCommitMarkedRollbackOnlyRollingBack();
			rollback();
			if ( jpaCompliance.isJpaTransactionComplianceEnabled() ) {
				throw new RollbackException( "Transaction was marked for rollback only" );
			}
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public void rollback() {
			if ( isActive() ) {
				jdbcResourceTransaction.rollback();
				afterCompletionCallback( Status.STATUS_ROLLEDBACK );
			}
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public TransactionStatus getStatus() {
			return jdbcResourceTransaction.getStatus();
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public void markRollbackOnly() {
			if ( getStatus() != TransactionStatus.ROLLED_BACK ) {
				if ( JDBC_LOGGER.isTraceEnabled() ) {
					JDBC_LOGGER.jdbcTransactionMarkedForRollbackOnly(
							new Exception( "exception just for purpose of providing stack trace" ) );
				}
				jdbcResourceTransaction.markRollbackOnly();
			}
		}
	}
}
