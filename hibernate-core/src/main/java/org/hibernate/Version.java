/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate;

import org.hibernate.internal.build.AllowSysOut;

import static org.hibernate.internal.CoreMessageLogger.CORE_LOGGER;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Information about the version of Hibernate.
 *
 * @author Steve Ebersole
 */
public final class Version {

	private static final String VERSION = initVersion();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static String initVersion() {
		final String version = Version.class.getPackage().getImplementationVersion();
		return version != null ? version : "[WORKING]";
	}

	private Version() {
	}

	/**
	 * Access to the Hibernate ORM version.
	 *
	 * @return The Hibernate version
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String getVersionString() {
		return VERSION;
	}

	/**
	 * Logs the Hibernate version (using {@link #getVersionString()}) to the logging system.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static void logVersion() {
		CORE_LOGGER.version( getVersionString() );
	}

	/**
	 * Prints the Hibernate version (using {@link #getVersionString()}) to SYSOUT.  Defined as the main-class in
	 * the hibernate-core jar
	 *
	 * @param args n/a
	 */
	@AllowSysOut
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static void main(String[] args) {
		System.out.println( "Hibernate ORM core version " + getVersionString() );
	}
}
