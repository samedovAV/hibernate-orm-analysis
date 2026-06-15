/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal;

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
import java.util.UUID;

import static org.jboss.logging.Logger.Level.DEBUG;
import static org.jboss.logging.Logger.Level.ERROR;
import static org.jboss.logging.Logger.Level.TRACE;
import static org.jboss.logging.Logger.Level.WARN;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Sub-system logging related to Session/StatelessSession runtime events
 */
@SubSystemLogging(
		name = SessionLogging.NAME,
		description = "Logging related to session lifecycle and operations"
)
@MessageLogger(projectCode = "HHH")
@ValidIdRange(min = 90010001, max = 90020000)
@Internal
public interface SessionLogging extends BasicLogger {
	String NAME = SubSystemLogging.BASE + ".session";

	SessionLogging SESSION_LOGGER = Logger.getMessageLogger( MethodHandles.lookup(), SessionLogging.class, NAME, Locale.ROOT );

	@LogMessage(level = DEBUG)
	@Message("Session creation specified 'autoJoinTransactions', "
			+ "which is invalid in conjunction with sharing JDBC connection between sessions; ignoring")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void invalidAutoJoinTransactionsWithSharedConnection();

	@LogMessage(level = DEBUG)
	@Message("Session creation specified a 'PhysicalConnectionHandlingMode', "
			+ "which is invalid in conjunction with sharing JDBC connection between sessions; ignoring")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void invalidPhysicalConnectionHandlingModeWithSharedConnection();

	@LogMessage(level = TRACE)
	@Message("Opened Session [%s] at timestamp: %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void openedSession(UUID sessionIdentifier, long timestamp);

	@LogMessage(level = TRACE)
	@Message("Already closed")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void alreadyClosed();

	@LogMessage(level = TRACE)
	@Message("Closing session [%s]")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void closingSession(UUID sessionIdentifier);

	@LogMessage(level = WARN)
	@Message(id = 90010101, value = "Closing shared session with unprocessed transaction completion actions")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void closingSharedSessionWithUnprocessedTxCompletions();

	@LogMessage(level = TRACE)
	@Message("Forcing-closing session since factory is already closed")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void forcingCloseBecauseFactoryClosed();

	@LogMessage(level = TRACE)
	@Message("Skipping auto-flush since the session is closed")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void skippingAutoFlushSessionClosed();

	@LogMessage(level = TRACE)
	@Message("Automatically flushing session")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void automaticallyFlushingSession();

	@LogMessage(level = TRACE)
	@Message("Automatically flushing child session")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void automaticallyFlushingChildSession();

	@LogMessage(level = TRACE)
	@Message("Automatically closing session")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void automaticallyClosingSession();


	@LogMessage(level = TRACE)
	@Message("Automatically closing child session")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void automaticallyClosingChildSession();

	@LogMessage(level = TRACE)
	@Message("%s remove orphan before updates: [%s]")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void removeOrphanBeforeUpdates(String timing, String entityInfo);

	@LogMessage(level = TRACE)
	@Message("Initializing proxy: %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void initializingProxy(String entityInfo);

	@LogMessage(level = TRACE)
	@Message("Clearing effective entity graph for subsequent select")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void clearingEffectiveEntityGraph();

	@LogMessage(level = TRACE)
	@Message("Flushing to force deletion of re-saved object: %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void flushingToForceDeletion(String entityInfo);

	@LogMessage(level = TRACE)
	@Message("Before transaction completion processing")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void beforeTransactionCompletion();

	@LogMessage(level = TRACE)
	@Message("After transaction completion processing (successful=%s, delayed=%s)")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void afterTransactionCompletion(boolean successful, boolean delayed);

	@LogMessage(level = ERROR)
	@Message(id = 90010102, value = "JDBC exception executing SQL; transaction rolled back")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void jdbcExceptionThrownWithTransactionRolledBack(@Cause Exception e);

	@LogMessage(level = DEBUG)
	@Message(id = 90010103, value = "Ignoring EntityNotFoundException for '%s.%s'")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void ignoringEntityNotFound(String entityName, Object id);

	@LogMessage(level = WARN)
	@Message(id = 90010104, value = "Property '%s' is not serializable, value won't be set")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void nonSerializableProperty(String propertyName);

	@LogMessage(level = WARN)
	@Message(id = 90010105, value = "Property having key null is illegal, value won't be set")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void nullPropertyKey();

	@LogMessage(level = TRACE)
	@Message("Serializing Session [%s]")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void serializingSession(UUID sessionIdentifier);

	@LogMessage(level = TRACE)
	@Message("Deserializing Session [%s]")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void deserializingSession(UUID sessionIdentifier);

	@LogMessage(level = ERROR)
	@Message(id = 90010106, value = "Exception in interceptor beforeTransactionCompletion()")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void exceptionInBeforeTransactionCompletionInterceptor(@Cause Throwable e);

	@LogMessage(level = ERROR)
	@Message(id = 90010107, value = "Exception in interceptor afterTransactionCompletion()")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void exceptionInAfterTransactionCompletionInterceptor(@Cause Throwable e);

	@LogMessage(level = WARN)
	@Message(id = 90010108, value = "Closing session with unprocessed clean up bulk operations, forcing their execution")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void closingSessionWithUnprocessedBulkOperations();

	// StatelessSession-specific

	@LogMessage(level = TRACE)
	@Message("Refreshing transient %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void refreshingTransient(String entityInfo);

	@LogMessage(level = TRACE)
	@Message("Initializing collection %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void initializingCollection(String collectionInfo);

	@LogMessage(level = TRACE)
	@Message("Collection initialized from cache")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void collectionInitializedFromCache();

	@LogMessage(level = TRACE)
	@Message("Collection initialized")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void collectionInitialized();

	@LogMessage(level = TRACE)
	@Message("Entity proxy found in session cache")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void entityProxyFoundInSessionCache();

	@LogMessage(level = DEBUG)
	@Message("Ignoring NO_PROXY to honor laziness")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void ignoringNoProxyToHonorLaziness();

	@LogMessage(level = TRACE)
	@Message("Creating a HibernateProxy for to-one association with subclasses to honor laziness")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void creatingHibernateProxyToHonorLaziness();

	@LogMessage(level = TRACE)
	@Message("Collection fetched from cache")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void collectionFetchedFromCache();

	@LogMessage(level = TRACE)
	@Message("Collection fetched")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void collectionFetched();
}
