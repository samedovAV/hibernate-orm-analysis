/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.connections.spi;

import java.sql.Connection;
import java.sql.SQLException;

import jakarta.annotation.Nonnull;
import org.hibernate.service.UnknownUnwrapTypeException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Basic support for {@link MultiTenantConnectionProvider} implementations using
 * an individual {@link ConnectionProvider} instance per tenant behind the scenes.
 * <p>
 * This class is meant to be subclassed to implement application-specific
 * requirements.
 *
 * @author Steve Ebersole
 */
public abstract class AbstractMultiTenantConnectionProvider<T> implements MultiTenantConnectionProvider<T> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract ConnectionProvider getAnyConnectionProvider();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract ConnectionProvider selectConnectionProvider(T tenantIdentifier);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Connection getAnyConnection() throws SQLException {
		return getAnyConnectionProvider().getConnection();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void releaseAnyConnection(Connection connection) throws SQLException {
		getAnyConnectionProvider().closeConnection( connection );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Connection getConnection(T tenantIdentifier) throws SQLException {
		return selectConnectionProvider( tenantIdentifier ).getConnection();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void releaseConnection(T tenantIdentifier, Connection connection) throws SQLException {
		selectConnectionProvider( tenantIdentifier ).closeConnection( connection );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean supportsAggressiveRelease() {
		return getAnyConnectionProvider().supportsAggressiveRelease();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isUnwrappableAs(@Nonnull Class<?> unwrapType) {
		return unwrapType.isInstance( this )
			|| unwrapType.isAssignableFrom( ConnectionProvider.class );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> X unwrap(@Nonnull Class<X> unwrapType) {
		if ( unwrapType.isInstance( this ) ) {
			return unwrapType.cast( this );
		}
		else if ( unwrapType.isAssignableFrom( ConnectionProvider.class ) ) {
			return unwrapType.cast(  getAnyConnectionProvider() );
		}
		else {
			throw new UnknownUnwrapTypeException( unwrapType );
		}
	}
}
