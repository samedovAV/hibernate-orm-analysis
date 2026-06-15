/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.resource.beans.internal;

import org.hibernate.Internal;
import org.hibernate.internal.log.SubSystemLogging;
import org.hibernate.resource.beans.container.spi.BeanContainer;

import org.jboss.logging.Logger;
import org.jboss.logging.annotations.Cause;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.ValidIdRange;

import java.lang.invoke.MethodHandles;
import java.util.Locale;

import static org.jboss.logging.Logger.Level.DEBUG;
import static org.jboss.logging.Logger.Level.INFO;
import static org.jboss.logging.Logger.Level.TRACE;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
@MessageLogger( projectCode = "HHH" )
@ValidIdRange( min = 10005001, max = 10010000 )
@SubSystemLogging(
		name = BeansMessageLogger.LOGGER_NAME,
		description = "Logging related to managed beans and the BeanContainer (CDI, etc)"
)
@Internal
public interface BeansMessageLogger {
	String LOGGER_NAME = SubSystemLogging.BASE + ".beans";

	BeansMessageLogger BEANS_MSG_LOGGER = Logger.getMessageLogger( MethodHandles.lookup(), BeansMessageLogger.class, LOGGER_NAME, Locale.ROOT );

	@LogMessage( level = TRACE )
	@Message(
			id = 10005001,
			value = "Creating ManagedBean [%s] using direct instantiation"
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void creatingManagedBeanUsingDirectInstantiation(String beanName);

	@LogMessage( level = INFO )
	@Message(
			id = 10005002,
			value = "No explicit CDI BeanManager reference was passed to Hibernate, " +
					"but CDI is available on the Hibernate class loader"
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void noBeanManagerButCdiAvailable();

	@LogMessage( level = DEBUG )
	@Message(
			id = 10005003,
			value = "Error resolving CDI bean - using fallback"
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void errorResolvingCdiBeanUsingFallback();

	@LogMessage( level = DEBUG )
	@Message(
			id = 10005004,
			value = "Stopping BeanContainer: %s"
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void stoppingBeanContainer(BeanContainer beanContainer);

	@LogMessage( level = DEBUG )
	@Message(
			id = 10005005,
			value = "Error resolving CDI bean [%s] - using fallback"
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void errorResolvingCdiBeanUsingFallback(String identifier);

	@LogMessage( level = DEBUG )
	@Message(
			id = 10005006,
			value = "Standard access to BeanManager"
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void standardAccessToBeanManager();

	@LogMessage( level = DEBUG )
	@Message(
			id = 10005007,
			value = "Extended access to BeanManager"
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void extendedAccessToBeanManager();

	@LogMessage( level = DEBUG )
	@Message(
			id = 10005008,
			value = "Error destroying managed bean instance [%s] - the context is not active anymore."
					+ " The instance must have been destroyed already - ignoring."
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void errorDestroyingManagedBeanInstanceContextNotActive(Object instance, @Cause Throwable e);
}
