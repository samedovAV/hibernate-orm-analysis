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
import java.util.Map;

import static org.jboss.logging.Logger.Level.DEBUG;
import static org.jboss.logging.Logger.Level.TRACE;
import static org.jboss.logging.Logger.Level.WARN;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Sub-system logging related to SessionFactory and its registry
 */
@SubSystemLogging(
		name = SessionFactoryLogging.NAME,
		description = "Logging related to SessionFactory lifecycle and serialization"
)
@MessageLogger(projectCode = "HHH")
@ValidIdRange(min = 90020001, max = 90030000)
@Internal
public interface SessionFactoryLogging extends BasicLogger {
	String NAME = SubSystemLogging.BASE + ".factory";

	SessionFactoryLogging SESSION_FACTORY_LOGGER = Logger.getMessageLogger( MethodHandles.lookup(), SessionFactoryLogging.class, NAME, Locale.ROOT );

	// ---- SessionFactoryImpl related ---------------------------------------------------------------

	@LogMessage(level = TRACE)
	@Message("Building session factory")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void buildingSessionFactory();

	@LogMessage(level = DEBUG)
	@Message(value = "Instantiating factory [%s] with settings: %s", id = 90020001)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void instantiatingFactory(String uuid, Map<String, Object> settings);

	@LogMessage(level = TRACE)
	@Message("Eating error closing factory after failed instantiation")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void eatingErrorClosingFactoryAfterFailedInstantiation();

	@LogMessage(level = TRACE)
	@Message("Instantiated factory: %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void instantiatedFactory(String uuid);

	@LogMessage(level = TRACE)
	@Message("Returning a Reference to the factory")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void returningReferenceToFactory();

	@LogMessage(level = TRACE)
	@Message("Already closed")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void alreadyClosed();

	@LogMessage(level = DEBUG)
	@Message(value = "Closing factory [%s]", id = 90020005)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void closingFactory(String uuid);

	@LogMessage(level = DEBUG)
	@Message(value = "Serializing factory [%s]", id = 90020010)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void serializingFactory(String uuid);

	@LogMessage(level = DEBUG)
	@Message(value = "Deserialized factory [%s]", id = 90020011)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void deserializedFactory(String uuid);

	@LogMessage(level = TRACE)
	@Message("Serialized factory")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void serializedFactory();

	@LogMessage(level = TRACE)
	@Message("Deserializing factory")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void deserializingFactory();

	@LogMessage(level = TRACE)
	@Message("Resolving serialized factory")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void resolvingSerializedFactory();

	@LogMessage(level = TRACE)
	@Message("Resolved factory by UUID: [%s]")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void resolvedFactoryByUuid(String uuid);

	@LogMessage(level = TRACE)
	@Message("Resolved factory by name: '%s'")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void resolvedFactoryByName(String name);

	@LogMessage(level = TRACE)
	@Message("Resolving factory from deserialized session")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void resolvingFactoryFromDeserializedSession();

	@LogMessage(level = WARN)
	@Message(value = "Unable to construct current session context [%s]", id = 90020030)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void unableToConstructCurrentSessionContext(String sessionContextType, @Cause Throwable throwable);

}
