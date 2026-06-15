/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.log;

import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.Locale;

import org.hibernate.Internal;
import org.hibernate.cfg.JdbcSettings;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.annotations.Cause;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.ValidIdRange;

import static org.jboss.logging.Logger.Level.DEBUG;
import static org.jboss.logging.Logger.Level.INFO;
import static org.jboss.logging.Logger.Level.WARN;
import static org.jboss.logging.Logger.Level.ERROR;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
@MessageLogger( projectCode = "HHH" )
@ValidIdRange( min = 10001001, max = 10001500 )
@SubSystemLogging(
		name = ConnectionInfoLogger.LOGGER_NAME,
		description = "Logging related to connection pooling"
)
@Internal
public interface ConnectionInfoLogger extends BasicLogger {
	String LOGGER_NAME = SubSystemLogging.BASE + ".connections.pooling";

	/**
	 * Static access to the logging instance
	 */
	ConnectionInfoLogger CONNECTION_INFO_LOGGER = Logger.getMessageLogger( MethodHandles.lookup(), ConnectionInfoLogger.class, LOGGER_NAME, Locale.ROOT );

	@LogMessage(level = WARN)
	@Message(value = "Using built-in connection pool (not intended for production use)", id = 10001002)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void usingHibernateBuiltInConnectionPool();

	@LogMessage(level = INFO)
	@Message(value = "Database info:\n%s", id = 10001005)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void logConnectionInfoDetails(String databaseConnectionInfo);

	@LogMessage(level = WARN)
	@Message(id = 10001006,
			value = "No JDBC Driver class was specified by property '"
					+ JdbcSettings.JAKARTA_JDBC_DRIVER + "', '"
					+ JdbcSettings.JPA_JDBC_DRIVER + "', or '"
					+ JdbcSettings.DRIVER + "'")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void jdbcDriverNotSpecified();

	@LogMessage(level = INFO)
	@Message(id = 10001007, value = "Available JDBC drivers: [%s]")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void availableJdbcDrivers(String availableDrivers);

	@LogMessage(level = DEBUG)
	@Message(value = "Cleaning up connection pool [%s]", id = 10001008)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void cleaningUpConnectionPool(String info);

	@LogMessage(level = WARN)
	@Message(value = "Problem closing pooled connection", id = 10001009)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void unableToClosePooledConnection(@Cause SQLException e);

	@LogMessage(level = WARN)
	@Message(value = "Could not destroy connection pool", id = 10001010)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void unableToDestroyConnectionPool(@Cause Exception e);

	@LogMessage(level = DEBUG)
	@Message(value = "Could not create connection pool", id = 10001011)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void unableToInstantiateConnectionPool(@Cause Exception e);

	@LogMessage(level = DEBUG)
	@Message(value = "Configuring connection pool [%s]", id = 10001012)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void configureConnectionPool(String type);

@LogMessage(level = INFO)
	@Message(value = "Ignoring setting '%s' for connection provider [%s]", id = 10001013)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void ignoredSetting(String setting, Class<?> provider);

	@LogMessage(level = DEBUG)
	@Message(value = "Initializing connection pool with %s connections", id = 10001014)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void initializingConnectionPool(int size);

	@LogMessage(level = DEBUG)
	@Message(value = "Connection pool now considered primed; min-size will be maintained", id = 10001015)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void connectionPoolPrimed();

	@LogMessage(level = DEBUG)
	@Message(value = "Adding %s connections to the pool", id = 10001016)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addingConnectionsToPool(int numberToBeAdded);

	@LogMessage(level = DEBUG)
	@Message(value = "Removing %s connections from the pool", id = 10001017)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void removingConnectionsFromPool(int numberToBeRemoved);

	@LogMessage(level = DEBUG)
	@Message(value = "Connection release failed, closing pooled connection", id = 10001018)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void connectionReleaseFailedClosingPooledConnection(@Cause Throwable t);

	@LogMessage(level = DEBUG)
	@Message(value = "Connection preparation failed, closing pooled connection", id = 10001019)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void connectionPreparationFailedClosingPooledConnection(@Cause Throwable t);

	@LogMessage(level = DEBUG)
	@Message(value = "Connection remove failed", id = 10001020)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void connectionRemoveFailed();

	@LogMessage(level = DEBUG)
	@Message(value = "No driver class specified", id = 10001021)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void noDriverClassSpecified();

	@LogMessage(level = DEBUG)
	@Message(value = "No connection creator factory class specified", id = 10001022)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void noConnectionCreatorFactoryClassSpecified();

	@LogMessage(level = ERROR)
	@Message(value = "Connection leak detected: there are %s unclosed connections", id = 10001023)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void connectionLeakDetected(int allocationCount);

	@LogMessage(level = WARN)
	@Message(value = "Could not set login timeout", id = 10001024)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void couldNotSetLoginTimeout(@Cause SQLException e);
}
