/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.id;

import java.util.Properties;

import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.GeneratorCreationContext;

import static org.hibernate.internal.log.DeprecationLogger.DEPRECATION_LOGGER;
import static org.hibernate.internal.util.config.ConfigurationHelper.getString;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * The legacy id generator named {@code uuid} / {@code uuid.hex}.
 * <p>
 * A {@link UUIDGenerator} that returns a string of length 32,
 * This string will consist of only hex digits. Optionally,
 * the string may be generated with separators between each
 * component of the UUID.
 * <p>
 * Mapping parameter supported: {@value #SEPARATOR}.
 *
 * @author Gavin King
 *
 * @deprecated This remains around as an implementation detail of {@code hbm.xml} mappings.
 */
@Deprecated(since = "6")
public class UUIDHexGenerator extends AbstractUUIDGenerator {

	/**
	 * The configuration parameter specifying the separator to use.
	 */
	public static final String SEPARATOR = "separator";

	private static boolean WARNED;

	private String sep = "";

	public UUIDHexGenerator() {
		if ( !WARNED ) {
			WARNED = true;
			DEPRECATION_LOGGER.deprecatedUuidHexGenerator(
					UUIDHexGenerator.class.getName(),
					UUIDGenerator.class.getName() );
		}
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void configure(GeneratorCreationContext creationContext, Properties parameters) throws MappingException {
		sep = getString( SEPARATOR, parameters, "" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getGeneratedType() {
		return String.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object generate(SharedSessionContractImplementor session, Object obj) {
		return format( getIP() ) + sep
				+ format( getJVM() ) + sep
				+ format( getHiTime() ) + sep
				+ format( getLoTime() ) + sep
				+ format( getCount() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String format(int intValue) {
		String formatted = Integer.toHexString( intValue );
		StringBuilder buf = new StringBuilder( "00000000" );
		buf.replace( 8 - formatted.length(), 8, formatted );
		return buf.toString();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String format(short shortValue) {
		String formatted = Integer.toHexString( shortValue );
		StringBuilder buf = new StringBuilder( "0000" );
		buf.replace( 4 - formatted.length(), 4, formatted );
		return buf.toString();
	}
}
