/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.hql;

import org.hibernate.Internal;
import org.hibernate.internal.log.SubSystemLogging;
import org.hibernate.query.QueryLogging;

import org.jboss.logging.BasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.ValidIdRange;

import java.lang.invoke.MethodHandles;
import java.util.Locale;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
@MessageLogger( projectCode = "HHH" )
@ValidIdRange( min = 90003501, max = 90004000 )
@SubSystemLogging(
		name = HqlLogging.LOGGER_NAME,
		description = "Logging related to HQL parsing"
)
@Internal
public interface HqlLogging extends BasicLogger {
	String LOGGER_NAME = QueryLogging.LOGGER_NAME + ".hql";

	HqlLogging QUERY_LOGGER = Logger.getMessageLogger( MethodHandles.lookup(), HqlLogging.class, LOGGER_NAME, Locale.ROOT );

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static String subLoggerName(String subName) {
		return LOGGER_NAME + '.' + subName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static Logger subLogger(String subName) {
		return Logger.getLogger( subLoggerName( subName ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static <T> T subLogger(String subName, Class<T> loggerJavaType) {
		return Logger.getMessageLogger( MethodHandles.lookup(), loggerJavaType, subLoggerName( subName ), Locale.ROOT );
	}
}
