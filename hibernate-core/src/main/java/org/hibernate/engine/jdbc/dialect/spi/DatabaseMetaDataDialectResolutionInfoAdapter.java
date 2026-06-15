/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.dialect.spi;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An implementation of {@link DialectResolutionInfo} that delegates calls to a wrapped {@link DatabaseMetaData}.
 * <p>
 * All {@link SQLException}s resulting from calls on the {@link DatabaseMetaData} are converted to the Hibernate
 * {@link org.hibernate.JDBCException} hierarchy.
 *
 * @author Steve Ebersole
 */
public class DatabaseMetaDataDialectResolutionInfoAdapter implements DialectResolutionInfo {
	private final DatabaseMetaData databaseMetaData;

	public DatabaseMetaDataDialectResolutionInfoAdapter(DatabaseMetaData databaseMetaData) {
		this.databaseMetaData = databaseMetaData;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDatabaseName() {
		try {
			return databaseMetaData.getDatabaseProductName();
		}
		catch (SQLException e) {
			throw BasicSQLExceptionConverter.INSTANCE.convert( e );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDatabaseVersion() {
		try {
			return databaseMetaData.getDatabaseProductVersion();
		}
		catch (SQLException e) {
			throw BasicSQLExceptionConverter.INSTANCE.convert( e );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getDatabaseMajorVersion() {
		try {
			return interpretVersion( databaseMetaData.getDatabaseMajorVersion() );
		}
		catch (SQLException e) {
			throw BasicSQLExceptionConverter.INSTANCE.convert( e );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static int interpretVersion(int result) {
		return result < 0 ? NO_VERSION : result;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getDatabaseMinorVersion() {
		try {
			return interpretVersion( databaseMetaData.getDatabaseMinorVersion() );
		}
		catch (SQLException e) {
			throw BasicSQLExceptionConverter.INSTANCE.convert( e );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getDriverName() {
		try {
			return databaseMetaData.getDriverName();
		}
		catch (SQLException e) {
			throw BasicSQLExceptionConverter.INSTANCE.convert( e );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getDriverMajorVersion() {
		return interpretVersion( databaseMetaData.getDriverMajorVersion() );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getDriverMinorVersion() {
		return interpretVersion( databaseMetaData.getDriverMinorVersion() );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getSQLKeywords() {
		try {
			return databaseMetaData.getSQLKeywords();
		}
		catch (SQLException e) {
			throw BasicSQLExceptionConverter.INSTANCE.convert( e );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DatabaseMetaData getDatabaseMetadata() {
		return databaseMetaData;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return getMajor() + "." + getMinor();
	}
}
