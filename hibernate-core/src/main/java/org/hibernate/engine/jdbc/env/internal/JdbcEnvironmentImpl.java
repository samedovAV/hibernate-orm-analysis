/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.env.internal;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.cfg.JdbcSettings;
import org.hibernate.cfg.MappingSettings;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.hibernate.engine.config.spi.StandardConverters;
import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.hibernate.engine.jdbc.env.spi.ExtractedDatabaseMetaData;
import org.hibernate.engine.jdbc.env.spi.IdentifierHelper;
import org.hibernate.engine.jdbc.env.spi.IdentifierHelperBuilder;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.engine.jdbc.env.spi.LobCreatorBuilder;
import org.hibernate.engine.jdbc.env.spi.NameQualifierSupport;
import org.hibernate.engine.jdbc.env.spi.QualifiedObjectNameFormatter;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.internal.SQLExceptionTypeDelegate;
import org.hibernate.exception.internal.SQLStateConversionDelegate;
import org.hibernate.exception.internal.StandardSQLExceptionConverter;
import org.hibernate.exception.spi.SQLExceptionConversionDelegate;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.hibernate.sql.ast.SqlAstTranslatorFactory;
import org.hibernate.sql.ast.spi.StandardSqlAstTranslatorFactory;

import static org.hibernate.cfg.MappingSettings.DEFAULT_CATALOG;
import static org.hibernate.cfg.MappingSettings.DEFAULT_SCHEMA;
import static org.hibernate.engine.config.spi.StandardConverters.STRING;
import static org.hibernate.engine.jdbc.JdbcLogging.JDBC_LOGGER;
import static org.hibernate.engine.jdbc.env.internal.LobCreatorBuilderImpl.makeLobCreatorBuilder;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class JdbcEnvironmentImpl implements JdbcEnvironment {

	private final Dialect dialect;

	private final SqlAstTranslatorFactory sqlAstTranslatorFactory;

	private final SqlExceptionHelper sqlExceptionHelper;
	private final ExtractedDatabaseMetaData extractedMetaDataSupport;
	private final Identifier currentCatalog;
	private final Identifier currentSchema;
	private final IdentifierHelper identifierHelper;
	private final QualifiedObjectNameFormatter qualifiedObjectNameFormatter;
	private final LobCreatorBuilderImpl lobCreatorBuilder;

	private final NameQualifierSupport nameQualifierSupport;

	/**
	 * Constructor form used when the JDBC {@link DatabaseMetaData} is not available.
	 *
	 * @param serviceRegistry The service registry
	 * @param dialect The resolved dialect.
	 */
	public JdbcEnvironmentImpl(final ServiceRegistryImplementor serviceRegistry, final Dialect dialect) {
		this.dialect = dialect;

		sqlAstTranslatorFactory = resolveSqlAstTranslatorFactory( dialect );

		final var cfgService = configurationService( serviceRegistry );

		final var dialectNameQualifierSupport = dialect.getNameQualifierSupport();
		nameQualifierSupport =
				dialectNameQualifierSupport == null
						? NameQualifierSupport.BOTH  // assume both catalogs and schemas are supported
						: dialectNameQualifierSupport;

		sqlExceptionHelper =
				buildSqlExceptionHelper( dialect,
						logWarnings( cfgService, dialect ),
						logErrors( cfgService ) );

		identifierHelper = identifierHelper( dialect, identifierHelperBuilder( cfgService, nameQualifierSupport ) );

		extractedMetaDataSupport = new ExtractedDatabaseMetaDataImpl( this );

		currentCatalog = identifierHelper.toIdentifier( cfgService.getSetting( DEFAULT_CATALOG, STRING ) );
		currentSchema = Identifier.toIdentifier( cfgService.getSetting( DEFAULT_SCHEMA, STRING ) );

		qualifiedObjectNameFormatter =
				new QualifiedObjectNameFormatterStandardImpl( nameQualifierSupport, dialect.getCatalogSeparator() );

		lobCreatorBuilder = makeLobCreatorBuilder( dialect );

		logJdbcFetchSize( extractedMetaDataSupport.getDefaultFetchSize(), cfgService );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static ConfigurationService configurationService(ServiceRegistryImplementor serviceRegistry) {
		return serviceRegistry.requireService( ConfigurationService.class );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private IdentifierHelperBuilder identifierHelperBuilder(
			ConfigurationService cfgService, NameQualifierSupport nameQualifierSupport) {
		final var builder = IdentifierHelperBuilder.from( this );
		builder.setGloballyQuoteIdentifiers( globalQuoting( cfgService ) );
		builder.setSkipGlobalQuotingForColumnDefinitions( globalQuotingSkippedForColumnDefinitions( cfgService ) );
		builder.setAutoQuoteKeywords( autoKeywordQuoting( cfgService ) );
		builder.setNameQualifierSupport( nameQualifierSupport );
		return builder;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static IdentifierHelper identifierHelper(Dialect dialect, IdentifierHelperBuilder builder) {
		try {
			final var identifierHelper = dialect.buildIdentifierHelper( builder, null );
			if ( identifierHelper != null ) {
				return identifierHelper;
			}
		}
		catch (SQLException sqle) {
			// should never ever happen
			JDBC_LOGGER.noDatabaseMetaData( sqle );
		}
		return builder.build();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static SqlAstTranslatorFactory resolveSqlAstTranslatorFactory(Dialect dialect) {
		final var sqlAstTranslatorFactory = dialect.getSqlAstTranslatorFactory();
		return sqlAstTranslatorFactory == null ? new StandardSqlAstTranslatorFactory() : sqlAstTranslatorFactory;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static boolean logWarnings(ConfigurationService cfgService, Dialect dialect) {
		return cfgService.getSetting(
				JdbcSettings.LOG_JDBC_WARNINGS,
				StandardConverters.BOOLEAN,
				dialect.isJdbcLogWarningsEnabledByDefault()
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static boolean logErrors(ConfigurationService cfgService) {
		return cfgService.getSetting(
				JdbcSettings.LOG_JDBC_ERRORS,
				StandardConverters.BOOLEAN,
				true
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static boolean globalQuoting(ConfigurationService cfgService) {
		return cfgService.getSetting(
				MappingSettings.GLOBALLY_QUOTED_IDENTIFIERS,
				StandardConverters.BOOLEAN,
				false
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static boolean globalQuotingSkippedForColumnDefinitions(ConfigurationService cfgService) {
		return cfgService.getSetting(
				MappingSettings.GLOBALLY_QUOTED_IDENTIFIERS_SKIP_COLUMN_DEFINITIONS,
				StandardConverters.BOOLEAN,
				false
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static boolean autoKeywordQuoting(ConfigurationService cfgService) {
		return cfgService.getSetting(
				MappingSettings.KEYWORD_AUTO_QUOTING_ENABLED,
				StandardConverters.BOOLEAN,
				false
		);
	}

	/**
	 * Constructor form used from testing
	 */
	public JdbcEnvironmentImpl(
			DatabaseMetaData databaseMetaData,
			Dialect dialect,
			JdbcConnectionAccess jdbcConnectionAccess) throws SQLException {
		this.dialect = dialect;

		sqlAstTranslatorFactory = resolveSqlAstTranslatorFactory( dialect );

		sqlExceptionHelper = buildSqlExceptionHelper( dialect, false, true );

		nameQualifierSupport = nameQualifierSupport( databaseMetaData, dialect );

		identifierHelper = identifierHelper( databaseMetaData, dialect );

		extractedMetaDataSupport =
				new ExtractedDatabaseMetaDataImpl( this, jdbcConnectionAccess, databaseMetaData );

		currentCatalog = null;
		currentSchema = null;

		qualifiedObjectNameFormatter =
				new QualifiedObjectNameFormatterStandardImpl( nameQualifierSupport, databaseMetaData );

		lobCreatorBuilder = makeLobCreatorBuilder( dialect );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private IdentifierHelper identifierHelper(DatabaseMetaData databaseMetaData, Dialect dialect) {
		final var identifierHelperBuilder = IdentifierHelperBuilder.from( this );
		identifierHelperBuilder.setNameQualifierSupport( nameQualifierSupport );
		try {
			final var identifierHelper = dialect.buildIdentifierHelper( identifierHelperBuilder, databaseMetaData );
			if ( identifierHelper != null ) {
				return identifierHelper;
			}
		}
		catch (SQLException sqle) {
			// should never ever happen
			JDBC_LOGGER.noDatabaseMetaData( sqle );
		}
		return identifierHelperBuilder.build();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private NameQualifierSupport nameQualifierSupport(DatabaseMetaData databaseMetaData, Dialect dialect)
			throws SQLException {
		final var nameQualifierSupport = dialect.getNameQualifierSupport();
		return nameQualifierSupport == null ? determineNameQualifierSupport( databaseMetaData ) : nameQualifierSupport;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private NameQualifierSupport determineNameQualifierSupport(DatabaseMetaData databaseMetaData) throws SQLException {
		final boolean supportsCatalogs = databaseMetaData.supportsCatalogsInTableDefinitions();
		final boolean supportsSchemas = databaseMetaData.supportsSchemasInTableDefinitions();
		if ( supportsCatalogs && supportsSchemas ) {
			return NameQualifierSupport.BOTH;
		}
		else if ( supportsCatalogs ) {
			return NameQualifierSupport.CATALOG;
		}
		else if ( supportsSchemas ) {
			return NameQualifierSupport.SCHEMA;
		}
		else {
			return NameQualifierSupport.NONE;
		}
	}

	/**
	 * @deprecated currently used by Hibernate Reactive
	 * This version of the constructor should handle the case in which we do actually have
	 * the option to access the {@link DatabaseMetaData}, but since Hibernate Reactive is
	 * currently not making use of it we take a shortcut.
	 */
	@Deprecated
	public JdbcEnvironmentImpl(
			ServiceRegistryImplementor serviceRegistry,
			Dialect dialect,
			DatabaseMetaData databaseMetaData) {
		this( serviceRegistry, dialect );
	}

	/**
	 * The main constructor form.
	 * Builds a {@code JdbcEnvironment} using the available {@link DatabaseMetaData}.
	 *
	 * @param serviceRegistry The service registry
	 * @param dialect The resolved dialect
	 * @param databaseMetaData The available DatabaseMetaData
	 *
	 */
	public JdbcEnvironmentImpl(
			ServiceRegistryImplementor serviceRegistry,
			Dialect dialect,
			DatabaseMetaData databaseMetaData,
			JdbcConnectionAccess jdbcConnectionAccess) throws SQLException {
		this.dialect = dialect;

		sqlAstTranslatorFactory = resolveSqlAstTranslatorFactory( dialect );

		final var cfgService = configurationService( serviceRegistry );

		sqlExceptionHelper =
				buildSqlExceptionHelper( dialect,
						logWarnings( cfgService, dialect ),
						logErrors( cfgService ) );

		nameQualifierSupport = nameQualifierSupport( databaseMetaData, dialect );

		identifierHelper =
				identifierHelper( dialect, databaseMetaData,
						identifierHelperBuilder( cfgService, nameQualifierSupport ) );

		extractedMetaDataSupport =
				new ExtractedDatabaseMetaDataImpl( this, jdbcConnectionAccess, databaseMetaData );

		// and that current-catalog and current-schema happen after it
		currentCatalog = identifierHelper.toIdentifier( extractedMetaDataSupport.getConnectionCatalogName() );
		currentSchema = identifierHelper.toIdentifier( extractedMetaDataSupport.getConnectionSchemaName() );

		qualifiedObjectNameFormatter =
				new QualifiedObjectNameFormatterStandardImpl( nameQualifierSupport, databaseMetaData );

		lobCreatorBuilder = makeLobCreatorBuilder( dialect, cfgService.getSettings(), databaseMetaData.getConnection() );

		logJdbcFetchSize( extractedMetaDataSupport.getDefaultFetchSize(), cfgService );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private IdentifierHelper identifierHelper(
			Dialect dialect,
			DatabaseMetaData databaseMetaData,
			IdentifierHelperBuilder builder) {
		try {
			final var identifierHelper = dialect.buildIdentifierHelper( builder, databaseMetaData );
			if ( identifierHelper != null ) {
				return identifierHelper;
			}
		}
		catch (SQLException sqle) {
			// should never ever happen
			JDBC_LOGGER.noDatabaseMetaData( sqle );
		}
		return builder.build();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static SqlExceptionHelper buildSqlExceptionHelper(Dialect dialect, boolean logWarnings, boolean logErrors) {
		final var dialectDelegate = dialect.buildSQLExceptionConversionDelegate();
		final var delegates = dialectDelegate == null
				? new SQLExceptionConversionDelegate[] { new SQLExceptionTypeDelegate( dialect ), new SQLStateConversionDelegate( dialect ) }
				: new SQLExceptionConversionDelegate[] { dialectDelegate, new SQLExceptionTypeDelegate( dialect ), new SQLStateConversionDelegate( dialect ) };
		return new SqlExceptionHelper( new StandardSQLExceptionConverter( delegates ), logWarnings, logErrors );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Dialect getDialect() {
		return dialect;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqlAstTranslatorFactory getSqlAstTranslatorFactory() {
		return sqlAstTranslatorFactory;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ExtractedDatabaseMetaData getExtractedDatabaseMetaData() {
		return extractedMetaDataSupport;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Identifier getCurrentCatalog() {
		return currentCatalog;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Identifier getCurrentSchema() {
		return currentSchema;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QualifiedObjectNameFormatter getQualifiedObjectNameFormatter() {
		return qualifiedObjectNameFormatter;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public IdentifierHelper getIdentifierHelper() {
		return identifierHelper;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NameQualifierSupport getNameQualifierSupport() {
		return nameQualifierSupport;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqlExceptionHelper getSqlExceptionHelper() {
		return sqlExceptionHelper;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public LobCreatorBuilder getLobCreatorBuilder() {
		return lobCreatorBuilder;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static void logJdbcFetchSize(int defaultFetchSize, ConfigurationService cfgService) {
		if ( !cfgService.getSettings().containsKey( JdbcSettings.STATEMENT_FETCH_SIZE ) ) {
			if ( defaultFetchSize > 0 && defaultFetchSize < 100 ) {
				JDBC_LOGGER.warnLowFetchSize( defaultFetchSize );
			}
			else {
				JDBC_LOGGER.usingFetchSize( defaultFetchSize );
			}
		}
	}
}
