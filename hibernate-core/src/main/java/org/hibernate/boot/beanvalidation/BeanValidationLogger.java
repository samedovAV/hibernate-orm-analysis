/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.beanvalidation;

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

import static org.hibernate.cfg.ValidationSettings.JAKARTA_VALIDATION_MODE;
import static org.jboss.logging.Logger.Level.DEBUG;
import static org.jboss.logging.Logger.Level.INFO;
import static org.jboss.logging.Logger.Level.WARN;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Sub-system logging related to Bean Validation integration
 */
@SubSystemLogging(
		name = BeanValidationLogger.NAME,
		description = "Logging related to Bean Validation integration"
)
@MessageLogger(projectCode = "HHH")
@ValidIdRange(min = 101001, max = 101500)
@Internal
public interface BeanValidationLogger extends BasicLogger {
	String NAME = SubSystemLogging.BASE + ".beanvalidation";

	BeanValidationLogger BEAN_VALIDATION_LOGGER = Logger.getMessageLogger( MethodHandles.lookup(), BeanValidationLogger.class, NAME, Locale.ROOT );

	@LogMessage(level = DEBUG)
	@Message(id = 101001, value = "Unable to acquire Jakarta Validation ValidatorFactory, skipping activation")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void validationFactorySkipped();

	@LogMessage(level = DEBUG)
	@Message(id = 101003, value = "ConstraintComposition type could not be determined. Assuming AND")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void constraintCompositionTypeUnknown(@Cause Throwable ex);

	@LogMessage(level = DEBUG)
	@Message(id = 101004, value = "@NotNull was applied to attribute [%s] which is defined (at least partially) by formula(s); formula portions will be skipped")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void notNullOnFormulaPortion(String propertyName);

	@LogMessage(level = WARN)
	@Message(id = 101005, value = "Unable to apply constraints on DDL for %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void unableToApplyConstraints(String className, @Cause Exception e);

	@LogMessage(level = INFO)
	@Message(id = 101006, value = "'" + JAKARTA_VALIDATION_MODE + "' named multiple values: %s")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void multipleValidationModes(String modes);

}
