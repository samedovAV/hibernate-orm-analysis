/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.resource.transaction.backend.jta.internal;

import jakarta.transaction.Transaction;
import org.hibernate.Internal;
import org.hibernate.internal.log.SubSystemLogging;

import org.jboss.logging.BasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.annotations.Cause;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.ValidIdRange;

import java.lang.invoke.MethodHandles;
import java.util.Locale;

import static org.jboss.logging.Logger.Level.TRACE;
import static org.jboss.logging.Logger.Level.WARN;
import static org.jboss.logging.Logger.Level.DEBUG;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Logging interface for JTA transaction operations.
 *
 * @author Gavin King
 */
@MessageLogger(projectCode = "HHH")
@ValidIdRange(min = 90007001, max = 90008000)
@SubSystemLogging(
		name = JtaLogging.LOGGER_NAME,
		description = "Logging related to JTA transaction management"
)
@Internal
public interface JtaLogging extends BasicLogger {
	String LOGGER_NAME = SubSystemLogging.BASE + ".jta";

	JtaLogging JTA_LOGGER = Logger.getMessageLogger( MethodHandles.lookup(), JtaLogging.class, LOGGER_NAME, Locale.ROOT );

	int NAMESPACE = 90007000;

	// TransactionManager methods

	@LogMessage(level = TRACE)
	@Message(
			value = "Calling TransactionManager.begin() to start a new JTA transaction",
			id = NAMESPACE + 1
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void callingTransactionManagerBegin();

	@LogMessage(level = TRACE)
	@Message(
			value = "Successfully called TransactionManager.begin()",
			id = NAMESPACE + 2
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void calledTransactionManagerBegin();

	@LogMessage(level = TRACE)
	@Message(
			value = "Skipping TransactionManager.begin() since there is an active transaction",
			id = NAMESPACE + 3
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void skippingTransactionManagerBegin();

	@LogMessage(level = TRACE)
	@Message(
			value = "Calling TransactionManager.commit() to commit the JTA transaction",
			id = NAMESPACE + 4
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void callingTransactionManagerCommit();

	@LogMessage(level = TRACE)
	@Message(
			value = "Successfully called TransactionManager.commit()",
			id = NAMESPACE + 5
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void calledTransactionManagerCommit();

	@LogMessage(level = TRACE)
	@Message(
			value = "Skipping TransactionManager.commit() since the transaction was not initiated here",
			id = NAMESPACE + 6
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void skippingTransactionManagerCommit();

	@LogMessage(level = TRACE)
	@Message(
			value = "Calling TransactionManager.rollback() to roll back the JTA transaction",
			id = NAMESPACE + 7
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void callingTransactionManagerRollback();

	@LogMessage(level = TRACE)
	@Message(
			value = "Successfully called TransactionManager.rollback()",
			id = NAMESPACE + 8
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void calledTransactionManagerRollback();

	// UserTransaction methods

	@LogMessage(level = TRACE)
	@Message(
			value = "Calling UserTransaction.begin() to start a new JTA transaction",
			id = NAMESPACE + 9
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void callingUserTransactionBegin();

	@LogMessage(level = TRACE)
	@Message(
			value = "Successfully called UserTransaction.begin()",
			id = NAMESPACE + 10
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void calledUserTransactionBegin();

	@LogMessage(level = TRACE)
	@Message(
			value = "Calling UserTransaction.commit() to commit the JTA transaction",
			id = NAMESPACE + 11
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void callingUserTransactionCommit();

	@LogMessage(level = TRACE)
	@Message(
			value = "Successfully called UserTransaction.commit()",
			id = NAMESPACE + 12
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void calledUserTransactionCommit();

	@LogMessage(level = TRACE)
	@Message(
			value = "Calling UserTransaction.rollback() to roll back the JTA transaction",
			id = NAMESPACE + 13
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void callingUserTransactionRollback();

	@LogMessage(level = TRACE)
	@Message(
			value = "Successfully called UserTransaction.rollback()",
			id = NAMESPACE + 14
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void calledUserTransactionRollback();

	@LogMessage(level = TRACE)
	@Message(
			value = "Surrounding JTA transaction suspended [%s]",
			id = NAMESPACE + 15
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void transactionSuspended(Object transaction);

	@LogMessage(level = TRACE)
	@Message(
			value = "Surrounding JTA transaction resumed [%s]",
			id = NAMESPACE + 16
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void transactionResumed(Object transaction);

	@LogMessage(level = Logger.Level.INFO)
	@Message(
			value = "Unable to roll back isolated transaction on error [%s]",
			id = NAMESPACE + 17
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void unableToRollBackIsolatedTransaction(Exception original, @Cause Exception ignore);

	@LogMessage(level = Logger.Level.INFO)
	@Message(
			value = "Unable to release isolated connection",
			id = NAMESPACE + 18
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void unableToReleaseIsolatedConnection(@Cause Throwable ignore);

	@LogMessage(level = WARN)
	@Message(
			id = NAMESPACE + 20,
			value = "Transaction afterCompletion called by a background thread; " +
					"delaying afterCompletion processing until the original thread can handle it. [status=%s]"
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void rollbackFromBackgroundThread(int status);

	@LogMessage(level = TRACE)
	@Message(
			value = "Suspended transaction to isolate DDL execution [%s]",
			id = NAMESPACE + 30
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void suspendedTransactionForDdlIsolation(Transaction suspendedTransaction);

	@LogMessage(level = TRACE)
	@Message(
			value = "Resumed transaction after isolated DDL execution",
			id = NAMESPACE + 31
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void resumedTransactionForDdlIsolation();

	@LogMessage(level = TRACE)
	@Message(
			value = "JTA platform says we cannot currently register synchronization; skipping",
			id = NAMESPACE + 32
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void cannotRegisterSynchronization();

	@LogMessage(level = TRACE)
	@Message(
			value = "Hibernate RegisteredSynchronization successfully registered with JTA platform",
			id = NAMESPACE + 33
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registeredSynchronization();

	@LogMessage(level = TRACE)
	@Message(
			value = "JTA transaction was already joined (RegisteredSynchronization already registered)",
			id = NAMESPACE + 34
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void alreadyJoinedJtaTransaction();

	@LogMessage(level = TRACE)
	@Message(
			value = "Notifying JTA transaction observers before completion",
			id = NAMESPACE + 35
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void notifyingJtaObserversBeforeCompletion();

	@LogMessage(level = TRACE)
	@Message(
			value = "Notifying JTA transaction observers after completion",
			id = NAMESPACE + 36
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void notifyingJtaObserversAfterCompletion();

	@LogMessage(level = TRACE)
	@Message(
			value = "Registered JTA Synchronization: beforeCompletion()",
			id = NAMESPACE + 37
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registeredSynchronizationBeforeCompletion();

	@LogMessage(level = TRACE)
	@Message(
			value = "Registered JTA Synchronization: afterCompletion(%s)",
			id = NAMESPACE + 38
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registeredSynchronizationAfterCompletion(int status);

	@LogMessage(level = TRACE)
	@Message(
			value = "Synchronization coordinator: beforeCompletion()",
			id = NAMESPACE + 39
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void synchronizationCoordinatorBeforeCompletion();

	@LogMessage(level = TRACE)
	@Message(
			value = "Synchronization coordinator: afterCompletion(status=%s)",
			id = NAMESPACE + 40
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void synchronizationCoordinatorAfterCompletion(int status);

	@LogMessage(level = TRACE)
	@Message(
			value = "Synchronization coordinator: doAfterCompletion(successful=%s, delayed=%s)",
			id = NAMESPACE + 41
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void synchronizationCoordinatorDoAfterCompletion(boolean successful, boolean delayed);
	@LogMessage(level = DEBUG)
	@Message(
			value = "Unable to access TransactionManager, attempting to use UserTransaction instead",
			id = NAMESPACE + 42
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void unableToAccessTransactionManagerTryingUserTransaction();

	@LogMessage(level = DEBUG)
	@Message(
			value = "Unable to access UserTransaction, attempting to use TransactionManager instead",
			id = NAMESPACE + 43
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void unableToAccessUserTransactionTryingTransactionManager();

	@LogMessage(level = DEBUG)
	@Message(
			value = "JtaPlatform.retrieveUserTransaction() returned null",
			id = NAMESPACE + 44
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void userTransactionReturnedNull();

	@LogMessage(level = DEBUG)
	@Message(
			value = "JtaPlatform.retrieveUserTransaction() threw an exception [%s]",
			id = NAMESPACE + 45
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void exceptionRetrievingUserTransaction(String message, @Cause Exception cause);

	@LogMessage(level = DEBUG)
	@Message(
			value = "JtaPlatform.retrieveTransactionManager() returned null",
			id = NAMESPACE + 46
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void transactionManagerReturnedNull();

	@LogMessage(level = DEBUG)
	@Message(
			value = "JtaPlatform.retrieveTransactionManager() threw an exception [%s]",
			id = NAMESPACE + 47
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void exceptionRetrievingTransactionManager(String message, @Cause Exception cause);
}
