/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.id.enhanced;

import java.sql.SQLException;

import org.hibernate.AssertionFailure;
import org.hibernate.boot.model.relational.Database;
import org.hibernate.boot.model.relational.InitCommand;
import org.hibernate.boot.model.relational.Namespace;
import org.hibernate.boot.model.relational.QualifiedName;
import org.hibernate.boot.model.relational.Sequence;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.mapping.Table;

import static org.hibernate.engine.jdbc.JdbcLogging.JDBC_LOGGER;
import static org.hibernate.id.IdentifierGeneratorHelper.extractLong;
import static org.hibernate.id.enhanced.ResyncHelper.getNextSequenceValue;
import static org.hibernate.id.enhanced.ResyncHelper.getMaxPrimaryKey;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Describes a sequence.
 *
 * @author Steve Ebersole
 */
public class SequenceStructure implements DatabaseStructure {

	private final String contributor;
	private final QualifiedName logicalQualifiedSequenceName;
	private final int initialValue;
	private final int incrementSize;
	private final String options;

	private String sql;
	private boolean applyIncrementSizeToSourceValues;
	private int accessCounter;
	protected QualifiedName physicalSequenceName;

	public SequenceStructure(
			String contributor,
			QualifiedName qualifiedSequenceName,
			int initialValue,
			int incrementSize,
			Class<?> numberType) {
		this.contributor = contributor;
		this.logicalQualifiedSequenceName = qualifiedSequenceName;

		this.initialValue = initialValue;
		this.incrementSize = incrementSize;
			this.options = null;
	}

	public SequenceStructure(
			String contributor,
			QualifiedName qualifiedSequenceName,
			int initialValue,
			int incrementSize,
			String options,
			Class<?> numberType) {
		this.contributor = contributor;
		this.logicalQualifiedSequenceName = qualifiedSequenceName;

		this.initialValue = initialValue;
		this.incrementSize = incrementSize;
		this.options = options;
		}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QualifiedName getPhysicalName() {
		return physicalSequenceName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getIncrementSize() {
		return incrementSize;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getTimesAccessed() {
		return accessCounter;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getInitialValue() {
		return initialValue;
	}

	@Override @Deprecated
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String[] getAllSqlForTests() {
		return new String[] { sql };
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AccessCallback buildCallback(final SharedSessionContractImplementor session) {
		if ( sql == null ) {
			throw new AssertionFailure( "SequenceStyleGenerator's SequenceStructure was not properly initialized" );
		}

			return new AccessCallback() {
				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				public long getNextValue() {
					accessCounter++;
					try {
					final var jdbcCoordinator = session.getJdbcCoordinator();
					final var statement = jdbcCoordinator.getStatementPreparer().prepareStatement( sql );
					final var resourceRegistry = jdbcCoordinator.getLogicalConnection().getResourceRegistry();
					try {
						final var resultSet = jdbcCoordinator.getResultSetReturn().extract( statement, sql );
							try {
								resultSet.next();
								final long value = extractLong( resultSet, 1 );
								if ( JDBC_LOGGER.isTraceEnabled() ) {
									JDBC_LOGGER.sequenceValueRetrievedFromDatabase( value );
								}
								return value;
						}
						finally {
							try {
								resourceRegistry.release( resultSet, statement );
							}
							catch( Throwable ignore ) {
								// intentionally empty
							}
						}
					}
					finally {
						resourceRegistry.release( statement );
						jdbcCoordinator.afterStatementExecution();
					}

				}
				catch ( SQLException sqle) {
					throw session.getJdbcServices().getSqlExceptionHelper().convert(
							sqle,
							"could not get next sequence value",
							sql
					);
				}
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public String getTenantIdentifier() {
				return session.getTenantIdentifier();
			}
		};
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void configure(Optimizer optimizer) {
		applyIncrementSizeToSourceValues = optimizer.applyIncrementSizeToSourceValues();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void registerExportables(Database database) {
		buildSequence( database );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void initialize(SqlStringGenerationContext context) {
		sql = context.getDialect().getSequenceSupport()
				.getSequenceNextValString( context.format( physicalSequenceName ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void registerExtraExportables(Table table, Optimizer optimizer) {
		table.addResyncCommand( (sqlContext, isolator) -> {
			final String sequenceName = sqlContext.format( physicalSequenceName );
			final String tableName = sqlContext.format( table.getQualifiedTableName() );
			final String primaryKeyColumnName = table.getPrimaryKey().getColumn( 0 ).getName();
			final int adjustment = optimizer.getAdjustment();
			final long max = getMaxPrimaryKey( isolator, primaryKeyColumnName, tableName );
			final long current = getNextSequenceValue( isolator, sequenceName);
			final long startWith = Math.max( max + adjustment, current );
			optimizer.reset();
			return new InitCommand( sqlContext.getDialect().getSequenceSupport()
					.getRestartSequenceString( sequenceName, startWith ) );
		} );
		table.addResetCommand( sqlContext -> {
			optimizer.reset();
			final String sequenceName = sqlContext.format( physicalSequenceName );
			return new InitCommand( sqlContext.getDialect().getSequenceSupport()
					.getRestartSequenceString( sequenceName, initialValue ) );
		} );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isPhysicalSequence() {
		return true;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected final int getSourceIncrementSize() {
		return applyIncrementSizeToSourceValues ? incrementSize : 1;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected QualifiedName getQualifiedName() {
		return logicalQualifiedSequenceName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void buildSequence(Database database) {
		final var sequence =
				locateOrCreateSequence( database.locateNamespace(
						logicalQualifiedSequenceName.getCatalogName(),
						logicalQualifiedSequenceName.getSchemaName()
				) );
		physicalSequenceName = sequence.getName();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private Sequence locateOrCreateSequence(Namespace namespace) {
		final int sourceIncrementSize = getSourceIncrementSize();
		final var objectName = logicalQualifiedSequenceName.getObjectName();
		final var existingSequence = namespace.locateSequence( objectName );
		if ( existingSequence != null ) {
			existingSequence.validate( initialValue, sourceIncrementSize );
			return existingSequence;
		}
		else {
			return namespace.createSequence(
					objectName,
					physicalName -> new Sequence(
							contributor,
							namespace.getPhysicalName().catalog(),
							namespace.getPhysicalName().schema(),
							physicalName,
							initialValue,
							sourceIncrementSize,
							options
					)
			);
		}
	}
}
