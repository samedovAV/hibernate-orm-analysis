/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.id.enhanced;

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

import static org.jboss.logging.Logger.Level.DEBUG;
import static org.jboss.logging.Logger.Level.INFO;
import static org.jboss.logging.Logger.Level.TRACE;
import static org.jboss.logging.Logger.Level.WARN;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Logging related to Optimizer operations in identifier generation
 */
@SubSystemLogging(
		name = OptimizerLogger.NAME,
		description = "Logging related to identifier generator optimizers"
)
@MessageLogger(projectCode = "HHH")
@ValidIdRange(min = 90401, max = 90500)
@Internal
public interface OptimizerLogger extends BasicLogger {
	String NAME = SubSystemLogging.BASE + ".id.optimizer";

	OptimizerLogger OPTIMIZER_MESSAGE_LOGGER = Logger.getMessageLogger(
			MethodHandles.lookup(),
			OptimizerLogger.class,
			NAME,
			Locale.ROOT
	);

	@LogMessage(level = TRACE)
	@Message(value = "Creating hilo optimizer with [incrementSize=%s, returnClass=%s]", id = 90401)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void creatingHiLoOptimizer(int incrementSize, String returnClassName);

	@LogMessage(level = TRACE)
	@Message(value = "Creating hilo optimizer (legacy) with [incrementSize=%s, returnClass=%s]", id = 90402)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void creatingLegacyHiLoOptimizer(int incrementSize, String returnClassName);

	@LogMessage(level = TRACE)
	@Message(value = "Creating pooled optimizer with [incrementSize=%s, returnClass=%s]", id = 90403)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void creatingPooledOptimizer(int incrementSize, String returnClassName);

	@LogMessage(level = DEBUG)
	@Message(value = "Creating pooled optimizer (lo) with [incrementSize=%s, returnClass=%s]", id = 90404)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void creatingPooledLoOptimizer(int incrementSize, String returnClassName);

	@LogMessage(level = INFO)
	@Message(value = "Pooled optimizer source reported [%s] as the initial value; use of 1 or greater highly recommended", id = 90405)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void pooledOptimizerReportedInitialValue(long value);

	@LogMessage(level = WARN)
	@Message(value = "Unable to interpret specified optimizer [%s], falling back to noop optimizer", id = 90406)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void unableToLocateCustomOptimizerClass(String type);

	@LogMessage(level = WARN)
	@Message(value = "Unable to instantiate specified optimizer [%s], falling back to noop optimizer", id = 90407)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void unableToInstantiateOptimizer(String type);
}
