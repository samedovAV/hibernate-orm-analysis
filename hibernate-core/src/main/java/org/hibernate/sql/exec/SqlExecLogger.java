/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.exec;

import org.hibernate.Internal;
import org.hibernate.internal.log.SubSystemLogging;

import org.jboss.logging.BasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.ValidIdRange;

import java.lang.invoke.MethodHandles;
import java.util.Locale;
import java.util.Set;

import static org.jboss.logging.Logger.Level.DEBUG;
import static org.jboss.logging.Logger.Level.TRACE;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
@MessageLogger( projectCode = "HHH" )
@ValidIdRange( min = 90004001, max = 90005000 )
@SubSystemLogging(
		name = SqlExecLogger.LOGGER_NAME,
		description = "Logging related to the execution of SQL statements"
)
@Internal
public interface SqlExecLogger extends BasicLogger {
	String LOGGER_NAME = SubSystemLogging.BASE + ".sql.exec";

	SqlExecLogger SQL_EXEC_LOGGER = Logger.getMessageLogger( MethodHandles.lookup(), SqlExecLogger.class, LOGGER_NAME, Locale.ROOT );

	@LogMessage(level = DEBUG)
	@Message(id = 90004001, value = "Collection locking for collection table '%s' - %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void collectionLockingForCollectionTable(String keyTableName, String rootPathName);

	@LogMessage(level = DEBUG)
	@Message(id = 90004002, value = "Follow-on locking for collection table '%s' - %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void followOnLockingForCollectionTable(String keyTableName, String rootPathName);

	@LogMessage(level = DEBUG)
	@Message(id = 90004003, value = "Follow-on locking collected loaded values:\n%s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void followOnLockingCollectedLoadedValues(String summary);

	@LogMessage(level = DEBUG)
	@Message(id = 90004010, value = "Starting include-collections locking process - %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void startingIncludeCollectionsLockingProcess(String entityName);

	@LogMessage(level = DEBUG)
	@Message(id = 90004011, value = "Starting follow-on locking process - %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void startingFollowOnLockingProcess(String entityName);

	@LogMessage(level = DEBUG)
	@Message(id = 90004012, value = "Adding table '%s' for follow-on locking - %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addingTableForFollowOnLocking(String tableName, String entityName);

	// Trace messages (typesafe)
	@LogMessage(level = TRACE)
	@Message(id = 90004013, value = "Reading query result cache data [%s]")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void readingQueryResultCacheData(String cacheModeName);

	@LogMessage(level = TRACE)
	@Message(id = 90004014, value = "Affected query spaces unexpectedly empty")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void affectedQuerySpacesUnexpectedlyEmpty();

	@LogMessage(level = TRACE)
	@Message(id = 90004015, value = "Affected query spaces %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void affectedQuerySpaces(Set<String> querySpaces);

	@LogMessage(level = TRACE)
	@Message(id = 90004016, value = "Skipping reading query result cache data (query cache %s, cache mode %s)")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void skippingReadingQueryResultCacheData(String queryCacheStatus, String cacheModeName);
}
