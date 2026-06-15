/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.id;

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

import static org.jboss.logging.Logger.Level.WARN;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Logging related to UUID/GUID identifier generators
 */
@SubSystemLogging(
		name = UUIDLogger.NAME,
		description = "Logging related to UUID/GUID identifier generation"
)
@MessageLogger(projectCode = "HHH")
@ValidIdRange(min = 90301, max = 90400)
@Internal
public interface UUIDLogger extends BasicLogger {
	String NAME = SubSystemLogging.BASE + ".id.uuid";

	UUIDLogger UUID_MESSAGE_LOGGER = Logger.getMessageLogger(
			MethodHandles.lookup(),
			UUIDLogger.class,
			NAME,
			Locale.ROOT
	);

	@LogMessage(level = WARN)
	@Message(value = "Unable to instantiate UUID generation strategy class", id = 90301)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void unableToInstantiateUuidGenerationStrategy(@Cause Exception ignore);

	@LogMessage(level = WARN)
	@Message(value = "Unable to locate requested UUID generation strategy class: %s", id = 90302)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void unableToLocateUuidGenerationStrategy(String strategyClassName);

	@LogMessage(level = WARN)
	@Message(value = "GUID identifier generated: %s", id = 90305)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void guidGenerated(String result);
}
