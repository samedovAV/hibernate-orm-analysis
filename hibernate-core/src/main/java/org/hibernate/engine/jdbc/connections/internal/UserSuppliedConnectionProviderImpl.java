/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.connections.internal;

import java.sql.Connection;
import java.sql.SQLException;

import jakarta.annotation.Nonnull;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An implementation of the {@link ConnectionProvider} interface that simply throws an
 * exception when a connection is requested, the assumption being that the application
 * is responsible for handing the connection to use to the session.
 *
 * @author Gavin King
 * @author Steve Ebersole
 */
public class UserSuppliedConnectionProviderImpl implements ConnectionProvider {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isUnwrappableAs(@Nonnull Class<?> unwrapType) {
		return unwrapType.isAssignableFrom( UserSuppliedConnectionProviderImpl.class );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <T> T unwrap(@Nonnull Class<T> unwrapType) {
		if ( unwrapType.isAssignableFrom( UserSuppliedConnectionProviderImpl.class ) ) {
			return unwrapType.cast( this );
		}
		else {
			throw new UnknownUnwrapTypeException( unwrapType );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Connection getConnection() throws SQLException {
		throw new UnsupportedOperationException( "The application must supply JDBC connections" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void closeConnection(Connection connection) throws SQLException {
		throw new UnsupportedOperationException( "The application must supply JDBC connections" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsAggressiveRelease() {
		return false;
	}
}
