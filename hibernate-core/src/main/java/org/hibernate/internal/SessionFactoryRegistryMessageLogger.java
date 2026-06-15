/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal;

import org.hibernate.Internal;
import org.hibernate.engine.jndi.JndiException;
import org.hibernate.engine.jndi.JndiNameException;
import org.hibernate.internal.log.SubSystemLogging;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.annotations.Cause;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.ValidIdRange;

import javax.naming.NamingException;
import java.lang.invoke.MethodHandles;
import java.util.Locale;

import static org.jboss.logging.Logger.Level.DEBUG;
import static org.jboss.logging.Logger.Level.ERROR;
import static org.jboss.logging.Logger.Level.INFO;
import static org.jboss.logging.Logger.Level.TRACE;
import static org.jboss.logging.Logger.Level.WARN;
import static org.jboss.logging.Logger.getMessageLogger;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@MessageLogger(projectCode = "HHH")
@ValidIdRange( min = 20100, max = 20400 )
@SubSystemLogging(
		name = SessionFactoryRegistryMessageLogger.LOGGER_NAME,
		description = "Logging related to session factory registry"
)
@Internal
public interface SessionFactoryRegistryMessageLogger extends BasicLogger  {
	String LOGGER_NAME = SubSystemLogging.BASE + ".factoryRegistry";

	SessionFactoryRegistryMessageLogger REGISTRY_LOGGER =
			getMessageLogger( MethodHandles.lookup(), SessionFactoryRegistryMessageLogger.class, LOGGER_NAME, Locale.ROOT );

	@LogMessage(level = TRACE)
	@Message("Initializing SessionFactoryRegistry @%s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})

	void initializingSessionFactoryRegistry(int hashCode);
	@LogMessage(level = WARN)
	@Message(value = "Naming exception occurred accessing factory: %s", id = 20178)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void namingExceptionAccessingFactory(NamingException exception);

	@LogMessage(level = INFO)
	@Message(value = "Bound factory to JNDI name: '%s'", id = 20194)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void factoryBoundToJndiName(String name);

	@LogMessage(level = INFO)
	@Message(value = "A factory was renamed from [%s] to [%s] in JNDI", id = 20196)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void factoryJndiRename(String oldName, String newName);

	@LogMessage(level = DEBUG)
	@Message(value = "Could not bind JNDI listener", id = 20127)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void couldNotBindJndiListener();

	@LogMessage(level = ERROR)
	@Message(value = "Invalid JNDI name: '%s'", id = 20135)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void invalidJndiName(String name, @Cause JndiNameException e);

	@LogMessage(level = WARN)
	@Message(value = "Could not bind factory to JNDI", id = 20277)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void unableToBindFactoryToJndi(@Cause JndiException e);

	@LogMessage(level = INFO)
	@Message(value = "Unbound factory from JNDI name: '%s'", id = 20197)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void factoryUnboundFromJndiName(String name);

	@LogMessage(level = INFO)
	@Message(value = "A factory was unbound from name: '%s'", id = 20198)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void factoryUnboundFromName(String name);

	@LogMessage(level = WARN)
	@Message(value = "Could not unbind factory from JNDI", id = 20374)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void unableToUnbindFactoryFromJndi(@Cause JndiException e);

	@LogMessage(level = DEBUG)
	@Message(value = "Registering SessionFactory [%s] (%s)", id = 20384)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registeringSessionFactory(String uuid, String name);

	@LogMessage(level = DEBUG)
	@Message(value = "Attempting to bind SessionFactory [%s] to JNDI", id = 20280)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void attemptingToBindFactoryToJndi(String name);

	@LogMessage(level = DEBUG)
	@Message(value = "Attempting to unbind SessionFactory [%s] from JNDI", id = 20281)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void attemptingToUnbindFactoryFromJndi(String name);

	@LogMessage(level = DEBUG)
	@Message(value = "A SessionFactory was successfully bound to name '%s'", id = 20282)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void factoryBoundToJndi(String name);

	@LogMessage(level = TRACE)
	@Message(value = "Not binding SessionFactory to JNDI, no JNDI name configured", id = 20385)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void notBindingSessionFactory();

	@LogMessage(level = TRACE)
	@Message("JNDI lookup by name '%s'")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void jndiLookupByName(String name);

	@LogMessage(level = TRACE)
	@Message("JNDI lookup by UUID [%s]")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void jndiLookupByUuid(String uuid);

	@LogMessage(level = TRACE)
	@Message("Resolved to UUID [%s]")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void resolvedToUuid(String uuid);
}
