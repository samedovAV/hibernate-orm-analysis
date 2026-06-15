/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor;

import org.hibernate.Internal;
import org.hibernate.internal.log.SubSystemLogging;

import org.jboss.logging.Logger;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SubSystemLogging(
		name = JdbcExtractingLogging.NAME,
		description = "Logging of JDBC value extraction"
)
@Internal
public interface JdbcExtractingLogging {
	String NAME = SubSystemLogging.BASE + ".jdbc.extract";

	Logger LOGGER = Logger.getLogger( NAME );

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static void logExtracted(int jdbcPosition, int typeCode, Object value) {
		if ( LOGGER.isTraceEnabled() ) {
			JdbcExtractingLogging.LOGGER.tracef(
					"extracted value (%s:%s) -> [%s]",
					jdbcPosition,
					JdbcTypeNameMapper.getTypeName( typeCode ),
					value
			);
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static void logNullExtracted(int jdbcPosition, int typeCode) {
		if ( LOGGER.isTraceEnabled() ) {
			JdbcExtractingLogging.LOGGER.tracef(
					"extracted value (%s:%s) -> [null]",
					jdbcPosition,
					JdbcTypeNameMapper.getTypeName( typeCode )
			);
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static void logExtracted(String callableParamName, int typeCode, Object value) {
		if ( LOGGER.isTraceEnabled() ) {
			JdbcExtractingLogging.LOGGER.tracef(
					"extracted value (%s:%s) -> [%s]",
					callableParamName,
					JdbcTypeNameMapper.getTypeName( typeCode ),
					value
			);
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static void logNullExtracted(String callableParamName, int typeCode) {
		if ( LOGGER.isTraceEnabled() ) {
			JdbcExtractingLogging.LOGGER.tracef(
					"extracted value (%s:%s) -> [null]",
					callableParamName,
					JdbcTypeNameMapper.getTypeName( typeCode )
			);
		}
	}

}
