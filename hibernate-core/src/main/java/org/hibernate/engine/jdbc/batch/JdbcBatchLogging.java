/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.batch;

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

import static org.jboss.logging.Logger.Level.INFO;
import static org.jboss.logging.Logger.Level.TRACE;
import static org.jboss.logging.Logger.Level.WARN;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Subsystem logging related to JDBC batch execution
 *
 * @author Steve Ebersole
 */
@SubSystemLogging(
		name = JdbcBatchLogging.NAME,
		description = "Logging related to JDBC batch execution"
)
@MessageLogger(projectCode = "HHH")
@ValidIdRange(min = 100501, max = 101000)
@Internal
public interface JdbcBatchLogging extends BasicLogger {
	String NAME = "org.hibernate.orm.jdbc.batch";

	JdbcBatchLogging BATCH_MESSAGE_LOGGER = Logger.getMessageLogger( MethodHandles.lookup(), JdbcBatchLogging.class, NAME, Locale.ROOT );

	@LogMessage(level = INFO)
	@Message(id=100501, value = "Automatic JDBC statement batching enabled (maximum batch size %s)")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void batchingEnabled(int batchSize);

	@LogMessage(level = WARN)
	@Message(id = 100502, value = "Unable to release JDBC batch statement")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void unableToReleaseBatchStatement();

	@LogMessage(level = INFO)
	@Message(id=100503, value = "JDBC batch still contained JDBC statements on release")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void batchContainedStatementsOnRelease();

	@LogMessage(level = TRACE)
	@Message("Created JDBC batch (%s) - [%s]")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void createBatch(int batchSize, String batchKey);

	@LogMessage(level = TRACE)
	@Message("Adding to JDBC batch (%s / %s) - [%s]")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addToBatch(int batchPosition, int batchSize, String batchKey);

	@LogMessage(level = TRACE)
	@Message("Executing JDBC batch (%s / %s) - [%s]")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void executeBatch(int batchPosition, int batchSize, String batchKey);

	@LogMessage(level = TRACE)
	@Message("Conditionally executing JDBC batch - [%s]")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void conditionallyExecuteBatch(String batchKey);

	@LogMessage(level = TRACE)
	@Message("Aborting JDBC batch - [%s]")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void abortBatch(String batchKey);

	@LogMessage(level = TRACE)
	@Message("Using standard JDBC batch builder")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void usingStandardBatchBuilder();

	@LogMessage(level = TRACE)
	@Message("No statements to execute in JDBC batch - [%s]")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void emptyBatch(String batchKey);

	// this might actually belong in JdbcLogging
	@LogMessage(level = TRACE)
	@Message("PreparedStatementDetails did not contain PreparedStatement on releaseStatements: %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void noPreparedStatements(String sqlString);

	@LogMessage(level = TRACE)
	@Message( "Success of batch update unknown: %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void batchSuccessUnknown(int batchPosition);
}
