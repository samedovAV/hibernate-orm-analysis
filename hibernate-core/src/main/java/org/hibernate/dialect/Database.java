/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect;

import java.sql.SQLException;

import org.hibernate.engine.jdbc.dialect.spi.BasicSQLExceptionConverter;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.internal.util.config.ConfigurationHelper;

import static org.hibernate.cfg.DialectSpecificSettings.COCKROACH_VERSION_STRING;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A list of relational database systems for which Hibernate can resolve a {@link Dialect}.
 *
 * However, Hibernate can work with other database systems that are not listed by the {@link Database}
 * enumeration, as long as a {@link Dialect} implementation class is provided via the {@code hibernate.dialect}
 * configuration property.
 *
 * @author Vlad Mihalcea
 */
public enum Database {

	DB2 {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Dialect createDialect(DialectResolutionInfo info) {
			final String databaseVersion = info.getDatabaseVersion();
			if ( databaseVersion != null ) {
				//See https://www.ibm.com/support/knowledgecenter/SSEPEK_12.0.0/java/src/tpc/imjcc_c0053013.html
				switch ( databaseVersion.substring( 0, 3 ) ) {
					case "SQL": {
						// Linux, UNIX, Windows
						return new DB2Dialect( info );
					}
					case "DSN": {
						// z/OS
						return new DB2zDialect( info );
					}
					case "QSQ": {
						// i
						// this only works if the "use drda metadata version"
						// property is set to true in the driver properties
						return new DB2iDialect( info );
					}
				}
			}
			// i
			return "DB2 UDB for AS/400".equals( info.getDatabaseName() )
					? new DB2iDialect( info ) // i
					: new DB2Dialect( info );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean productNameMatches(String databaseName) {
			return databaseName.startsWith( "DB2" );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getDriverClassName(String jdbcUrl) {
			return "com.ibm.db2.jcc.DB2Driver";
		}
	},

	ENTERPRISEDB {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Dialect createDialect(DialectResolutionInfo info) {
			return new PostgresPlusDialect( info );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean productNameMatches(String databaseName) {
			return "EnterpriseDB".equals( databaseName );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getDriverClassName(String jdbcUrl) {
			return "com.edb.Driver";
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getUrlPrefix() {
			return "jdbc:edb:";
		}
	},

	H2 {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Dialect createDialect(DialectResolutionInfo info) {
			return new H2Dialect( info );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean productNameMatches(String databaseName) {
			return "H2".equals( databaseName );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getDriverClassName(String jdbcUrl) {
			return "org.h2.Driver";
		}
	},

	HSQL {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Dialect createDialect(DialectResolutionInfo info) {
			return new HSQLDialect( info );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean productNameMatches(String databaseName) {
			return "HSQL Database Engine".equals( databaseName );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getDriverClassName(String jdbcUrl) {
			return "org.hsqldb.jdbc.JDBCDriver";
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getUrlPrefix() {
			return "jdbc:hsqldb:";
		}
	},

	HANA {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Dialect createDialect(DialectResolutionInfo info) {
			return new HANADialect( info );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean productNameMatches(String databaseName) {
			return "HDB".equals( databaseName );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getDriverClassName(String jdbcUrl) {
			return "com.sap.db.jdbc.Driver";
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getUrlPrefix() {
			return "jdbc:sap:";
		}
	},

	MARIADB {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean matchesResolutionInfo(DialectResolutionInfo info) {
			if ( productNameMatches( info.getDatabaseName() ) ) {
				return true;
			}
			else {
				//in case the product name has been set to MySQL
				final String driverName = info.getDriverName();
				return driverName != null && driverName.startsWith( "MariaDB" );
			}
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Dialect createDialect(DialectResolutionInfo info) {
			return new MariaDBDialect( info );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean productNameMatches(String productName) {
			return "MariaDB".equals( productName );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getDriverClassName(String jdbcUrl) {
			return "org.mariadb.jdbc.Driver";
		}
	},

	MYSQL {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Dialect createDialect(DialectResolutionInfo info) {
			return new MySQLDialect( info );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean productNameMatches(String databaseName) {
			return "MySQL".equals( databaseName );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getDriverClassName(String jdbcUrl) {
			return "com.mysql.cj.jdbc.Driver";
		}
	},

	ORACLE {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Dialect createDialect(DialectResolutionInfo info) {
			return new OracleDialect( info );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean productNameMatches(String databaseName) {
			return "Oracle".equals( databaseName );
		}
		/*@Override
		public String getDriverClassName() {
			return "oracle.jdbc.OracleDriver";
		}*/
	},

	POSTGRESQL {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Dialect createDialect(DialectResolutionInfo info) {
			final String version = getVersion( info );
			return version.startsWith( "Cockroach" )
					? new CockroachDialect( info, version )
					: new PostgreSQLDialect( info );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean productNameMatches(String databaseName) {
			return "PostgreSQL".equals( databaseName );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getDriverClassName(String jdbcUrl) {
			return "org.postgresql.Driver";
		}
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		private String getVersion(DialectResolutionInfo info) {
			final var databaseMetaData = info.getDatabaseMetadata();
			if ( databaseMetaData != null ) {
				try ( var statement = databaseMetaData.getConnection().createStatement();
						var rs = statement.executeQuery( "select version()" ) ) {
					if ( rs.next() ) {
						return rs.getString( 1 );
					}
				}
				catch (SQLException e) {
					throw BasicSQLExceptionConverter.INSTANCE.convert( e );
				}
			}

			// default to the dialect-specific configuration setting
			return ConfigurationHelper.getString( COCKROACH_VERSION_STRING, info.getConfigurationValues(), "" );
		}
	},

	SPANNER {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Dialect createDialect(DialectResolutionInfo info) {
			return new SpannerDialect( info );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean productNameMatches(String databaseName) {
			return databaseName.equals( "Google Cloud Spanner" );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getDriverClassName(String jdbcUrl) {
			return "com.google.cloud.spanner.jdbc.JdbcDriver";
		}
	},

	SPANNER_PG {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Dialect createDialect(DialectResolutionInfo info) {
			return new SpannerPostgreSQLDialect( info );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean productNameMatches(String databaseName) {
			return databaseName.equals( "Google Cloud Spanner PostgreSQL" );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getDriverClassName(String jdbcUrl) {
			return "com.google.cloud.spanner.jdbc.JdbcDriver";
		}
	},

	SQLSERVER {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Dialect createDialect(DialectResolutionInfo info) {
			return new SQLServerDialect( info );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean productNameMatches(String databaseName) {
			return databaseName.startsWith( "Microsoft SQL Server" );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getDriverClassName(String jdbcUrl) {
			return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		}
	},

	SYBASE {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Dialect createDialect(DialectResolutionInfo info) {
			final String databaseName = info.getDatabaseName();
			return isASE( databaseName ) ? new SybaseASEDialect( info ) : null;
		}
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		private boolean isASE(String databaseName) {
			return "Sybase SQL Server".equals( databaseName )
				|| "Adaptive Server Enterprise".equals( databaseName )
				|| "ASE".equals( databaseName );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean productNameMatches(String productName) {
			return isASE( productName );
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean matchesUrl(String jdbcUrl) {
			return jdbcUrl.startsWith( "jdbc:sybase:" )
				|| jdbcUrl.startsWith( "jdbc:sqlanywhere:" );
		}
	};

	/**
	 * Does this database match the given metadata?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean matchesResolutionInfo(DialectResolutionInfo info) {
		return productNameMatches( info.getDatabaseName() );
	}

	/**
	 * Does this database have the given product name?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public abstract boolean productNameMatches(String productName);

	/**
	 * Create a {@link Dialect} for the given metadata.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public abstract Dialect createDialect(DialectResolutionInfo info);

	/**
	 * Get the name of the JDBC driver class for this database,
	 * or null if we're not too sure what it should be.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDriverClassName(String jdbcUrl) {
		return null;
	}

	/**
	 * Get the JDBC URL prefix used by this database.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getUrlPrefix() {
		return "jdbc:" + toString().toLowerCase() + ":";
	}

	/**
	 * Does the given JDBC URL connect to this database?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean matchesUrl(String jdbcUrl) {
		return jdbcUrl.toLowerCase().startsWith( getUrlPrefix() );
	}

}
