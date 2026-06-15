/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.extract.internal;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.Nullable;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.relational.Namespace;
import org.hibernate.boot.model.relational.QualifiedSequenceName;
import org.hibernate.boot.model.relational.QualifiedTableName;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.resource.transaction.spi.DdlTransactionIsolator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.schema.extract.spi.DatabaseInformation;
import org.hibernate.tool.schema.extract.spi.ExtractionContext;
import org.hibernate.tool.schema.extract.spi.ForeignKeyInformation;
import org.hibernate.tool.schema.extract.spi.IndexInformation;
import org.hibernate.tool.schema.extract.spi.InformationExtractor;
import org.hibernate.tool.schema.extract.spi.NameSpaceTablesInformation;
import org.hibernate.tool.schema.extract.spi.PrimaryKeyInformation;
import org.hibernate.tool.schema.extract.spi.SchemaExtractionException;
import org.hibernate.tool.schema.extract.spi.SequenceInformation;
import org.hibernate.tool.schema.extract.spi.TableInformation;
import org.hibernate.tool.schema.spi.SchemaManagementTool;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class DatabaseInformationImpl
		implements DatabaseInformation, ExtractionContext.DatabaseObjectAccess {
	protected final JdbcEnvironment jdbcEnvironment;
	protected final SqlStringGenerationContext context;
	protected final ExtractionContext extractionContext;
	protected final InformationExtractor extractor;

	private final Map<QualifiedSequenceName, SequenceInformation> sequenceInformationMap = new HashMap<>();

	public DatabaseInformationImpl(
			ServiceRegistry serviceRegistry,
			JdbcEnvironment jdbcEnvironment,
			SqlStringGenerationContext context,
			DdlTransactionIsolator ddlTransactionIsolator,
			SchemaManagementTool tool) throws SQLException {
		this.jdbcEnvironment = jdbcEnvironment;
		this.context = context;
		final var extractionTool = tool.getExtractionTool();
		extractionContext =
				extractionTool.createExtractionContext(
						serviceRegistry,
						jdbcEnvironment,
						context,
						ddlTransactionIsolator,
						this
				);
		extractor = extractionTool.createInformationExtractor( extractionContext );
		// because we do not have defined a way to locate sequence info by name
		initializeSequences();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static QualifiedSequenceName unqualifiedSequenceName(QualifiedSequenceName sequenceName) {
		return new QualifiedSequenceName( null, null, sequenceName.getSequenceName() );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private void initializeSequences() throws SQLException {
		final var sequences =
				jdbcEnvironment.getDialect().getSequenceInformationExtractor()
						.extractMetadata( extractionContext );
		for ( var sequenceInformation : sequences ) {
			sequenceInformationMap.put(
					// for now, follow the legacy behavior of storing just the
					// unqualified sequence name.
					unqualifiedSequenceName( sequenceInformation.getSequenceName() ),
					sequenceInformation
			);
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean catalogExists(Identifier catalog) {
		return extractor.catalogExists( context.catalogWithDefault( catalog ) );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean schemaExists(Namespace.Name namespace) {
		return extractor.schemaExists( context.catalogWithDefault( namespace.catalog() ),
				context.schemaWithDefault( namespace.schema() ) );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TableInformation getTableInformation(
			Identifier catalogName,
			Identifier schemaName,
			Identifier tableName) {
		return getTableInformation( new QualifiedTableName( catalogName, schemaName, tableName ) );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TableInformation getTableInformation(
			Namespace.Name namespace,
			Identifier tableName) {
		return getTableInformation( new QualifiedTableName( namespace, tableName ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableInformation getTableInformation(QualifiedTableName tableName) {
		if ( tableName.getObjectName() == null ) {
			throw new IllegalArgumentException( "Passed table name cannot be null" );
		}
		return extractor.getTable(
				context.catalogWithDefault( tableName.getCatalogName() ),
				context.schemaWithDefault( tableName.getSchemaName() ),
				tableName.getTableName()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NameSpaceTablesInformation getTablesInformation(Namespace namespace) {
		return extractor.getTables( context.catalogWithDefault( namespace.getPhysicalName().catalog() ),
				context.schemaWithDefault( namespace.getPhysicalName().schema() ) );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SequenceInformation getSequenceInformation(
			Identifier catalogName,
			Identifier schemaName,
			Identifier sequenceName) {
		return getSequenceInformation( new QualifiedSequenceName( catalogName, schemaName, sequenceName ) );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SequenceInformation getSequenceInformation(Namespace.Name schemaName, Identifier sequenceName) {
		return getSequenceInformation( new QualifiedSequenceName( schemaName, sequenceName ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SequenceInformation getSequenceInformation(QualifiedSequenceName sequenceName) {
		return locateSequenceInformation( sequenceName );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void cleanup() {
		extractionContext.cleanup();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable TableInformation locateTableInformation(QualifiedTableName tableName) {
		return getTableInformation( tableName );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SequenceInformation locateSequenceInformation(QualifiedSequenceName sequenceName) {
		// again, follow legacy behavior
		if ( sequenceName.getCatalogName() != null || sequenceName.getSchemaName() != null ) {
			sequenceName = unqualifiedSequenceName( sequenceName );
		}
		return sequenceInformationMap.get( sequenceName );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PrimaryKeyInformation locatePrimaryKeyInformation(QualifiedTableName tableName) {
		return extractor.getPrimaryKey( locateNonNullTableInformation( tableName ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Iterable<ForeignKeyInformation> locateForeignKeyInformation(QualifiedTableName tableName) {
		return extractor.getForeignKeys( locateNonNullTableInformation( tableName ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Iterable<IndexInformation> locateIndexesInformation(QualifiedTableName tableName) {
		return extractor.getIndexes( locateNonNullTableInformation( tableName ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private TableInformation locateNonNullTableInformation(QualifiedTableName tableName) {
		final TableInformation tableInformation = locateTableInformation( tableName );
		if ( tableInformation == null ) {
			throw new SchemaExtractionException( "Could not locate table information for " + tableName );
		}
		return tableInformation;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCaching() {
		return false;
	}
}
