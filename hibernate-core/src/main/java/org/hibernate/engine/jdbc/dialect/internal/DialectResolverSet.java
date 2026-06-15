/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.dialect.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;
import org.hibernate.exception.JDBCConnectionException;

import static org.hibernate.internal.CoreMessageLogger.CORE_LOGGER;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A {@link DialectResolver} implementation which coordinates resolution by delegating to sub-resolvers.
 *
 * @author Tomoto Shimizu Washio
 * @author Steve Ebersole
 */
public class DialectResolverSet implements DialectResolver {

	private final List<DialectResolver> resolvers;

	public DialectResolverSet() {
		this( new ArrayList<>() );
	}

	public DialectResolverSet(List<DialectResolver> resolvers) {
		this.resolvers = resolvers;
	}

	public DialectResolverSet(DialectResolver... resolvers) {
		this( Arrays.asList( resolvers ) );
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public Dialect resolveDialect(DialectResolutionInfo info) {
		for ( DialectResolver resolver : resolvers ) {
			try {
				final Dialect dialect = resolver.resolveDialect( info );
				if ( dialect != null ) {
					return dialect;
				}
			}
			catch ( JDBCConnectionException e ) {
				throw e;
			}
			catch ( Exception e ) {
				CORE_LOGGER.exceptionInSubResolver( e.getMessage() );
			}
		}

		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addResolver(DialectResolver... resolvers) {
		this.resolvers.addAll( Arrays.asList( resolvers ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addResolverAtFirst(DialectResolver... resolvers) {
		this.resolvers.addAll( 0, Arrays.asList( resolvers ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addDiscoveredResolvers(Collection<DialectResolver> resolvers) {
		this.resolvers.addAll( 0, resolvers );
	}
}
