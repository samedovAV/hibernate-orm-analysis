/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.dialect.internal;

import org.hibernate.dialect.Database;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * The standard {@link DialectResolver} implementation
 *
 * @author Steve Ebersole
 */
public final class StandardDialectResolver implements DialectResolver {
	public StandardDialectResolver() {
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Dialect resolveDialect(DialectResolutionInfo info) {

		for ( Database database : Database.values() ) {
			if ( database.matchesResolutionInfo( info ) ) {
				return database.createDialect( info );
			}
		}

		return null;
	}
}
