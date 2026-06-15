/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.connections.spi;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import jakarta.annotation.Nonnull;
import org.hibernate.service.UnknownUnwrapTypeException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Basic support for implementations of {@link MultiTenantConnectionProvider} based on {@link DataSource}s.
 *
 * @author Steve Ebersole
 */
public abstract class AbstractDataSourceBasedMultiTenantConnectionProviderImpl<T>
		implements MultiTenantConnectionProvider<T> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract DataSource selectAnyDataSource();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract DataSource selectDataSource(T tenantIdentifier);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Connection getAnyConnection() throws SQLException {
		return selectAnyDataSource().getConnection();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void releaseAnyConnection(Connection connection) throws SQLException {
		connection.close();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Connection getConnection(T tenantIdentifier) throws SQLException {
		return selectDataSource( tenantIdentifier ).getConnection();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void releaseConnection(T tenantIdentifier, Connection connection) throws SQLException {
		connection.close();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsAggressiveRelease() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isUnwrappableAs(@Nonnull Class<?> unwrapType) {
		return unwrapType.isInstance( this )
			|| unwrapType.isAssignableFrom( DataSource.class );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> X unwrap(@Nonnull Class<X> unwrapType) {
		if ( unwrapType.isInstance( this ) ) {
			return unwrapType.cast( this );
		}
		else if ( unwrapType.isAssignableFrom( DataSource.class ) ) {
			return unwrapType.cast( selectAnyDataSource() );
		}
		else {
			throw new UnknownUnwrapTypeException( unwrapType );
		}
	}
}
