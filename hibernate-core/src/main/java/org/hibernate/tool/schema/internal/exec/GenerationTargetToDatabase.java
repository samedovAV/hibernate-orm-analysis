/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.internal.exec;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.engine.jdbc.spi.SqlStatementLogger;
import org.hibernate.resource.transaction.spi.DdlTransactionIsolator;
import org.hibernate.tool.schema.spi.CommandAcceptanceException;
import org.hibernate.tool.schema.spi.GenerationTarget;
import org.hibernate.tool.schema.spi.ScriptSourceInput;

import static org.hibernate.internal.CoreMessageLogger.CORE_LOGGER;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A {@link GenerationTarget} which exports DDL directly to the database.
 *
 * @author Steve Ebersole
 */
public class GenerationTargetToDatabase implements GenerationTarget {

	public final DdlTransactionIsolator ddlTransactionIsolator;
	private final boolean releaseAfterUse;

	private Statement jdbcStatement;
	private final boolean autocommit;

	public GenerationTargetToDatabase(DdlTransactionIsolator ddlTransactionIsolator) {
		this( ddlTransactionIsolator, true );
	}

	public GenerationTargetToDatabase(DdlTransactionIsolator ddlTransactionIsolator, boolean releaseAfterUse) {
		this( ddlTransactionIsolator, releaseAfterUse, true );
	}

	public GenerationTargetToDatabase(DdlTransactionIsolator ddlTransactionIsolator, boolean releaseAfterUse, boolean autocommit) {
		this.ddlTransactionIsolator = ddlTransactionIsolator;
		this.releaseAfterUse = releaseAfterUse;
		this.autocommit = autocommit;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private SqlStatementLogger getSqlStatementLogger() {
		return ddlTransactionIsolator.getJdbcContext().getSqlStatementLogger();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private SqlExceptionHelper getSqlExceptionHelper() {
		return ddlTransactionIsolator.getJdbcContext().getSqlExceptionHelper();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private Connection getIsolatedConnection() {
		return ddlTransactionIsolator.getIsolatedConnection( autocommit );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void prepare() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void beforeScript(ScriptSourceInput scriptSource) {
		if ( scriptSource.exists() ) {
			CORE_LOGGER.executingScript( scriptSource.getScriptDescription() );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(String command) {
		//TODO: temporary workaround DELETE ME
		if ( ddlTransactionIsolator.getJdbcContext().getDialect().throttleDdl() ) {
			try {
				Thread.sleep( 20 );
			}
			catch (InterruptedException e) {
				//ignore
			}
		}

		getSqlStatementLogger().logStatement( command, FormatStyle.NONE.getFormatter() );
		try {
			final var statement = jdbcStatement();
			statement.execute( command );
			logWarnings( statement );
		}
		catch (SQLException e) {
			throw new CommandAcceptanceException(
					"Error executing DDL \"" + command + "\" via JDBC [" + stripSql(e) + "]",
					e
			);
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private void logWarnings(Statement statement) {
		try {
			final var warnings = statement.getWarnings();
			if ( warnings != null) {
				getSqlExceptionHelper().logAndClearWarnings( statement );
			}
		}
		catch( SQLException e ) {
			CORE_LOGGER.unableToLogSqlWarnings( e );
		}
	}

	/**
	 * Strip repetition of the SQL statement from h2 messages.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static String stripSql(SQLException e) {
		final String message = e.getMessage();
		final int index = message.indexOf( " SQL statement:" );
		return index > 0 ? message.substring( 0, index ) : message;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private Statement jdbcStatement() {
		if ( jdbcStatement == null ) {
			try {
				jdbcStatement = getIsolatedConnection().createStatement();
			}
			catch (SQLException e) {
				throw getSqlExceptionHelper()
						.convert( e, "Unable to create JDBC Statement for DDL execution" );
			}
		}

		return jdbcStatement;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void release() {
		if ( jdbcStatement != null ) {
			try {
				jdbcStatement.close();
				jdbcStatement = null;
			}
			catch (SQLException e) {
				throw getSqlExceptionHelper()
						.convert( e, "Unable to close JDBC Statement after DDL execution" );
			}
		}
		if ( releaseAfterUse ) {
			ddlTransactionIsolator.release();
		}
	}
}
