/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.internal;


import org.hibernate.boot.Metadata;
import org.hibernate.boot.model.relational.QualifiedNameImpl;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.dialect.Dialect;
import org.hibernate.mapping.Index;
import org.hibernate.tool.schema.spi.Exporter;

import static org.hibernate.internal.util.StringHelper.isBlank;
import static org.hibernate.internal.util.StringHelper.isNotBlank;
import static org.hibernate.internal.util.StringHelper.qualify;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An {@link Exporter} for {@linkplain Index indexes}.
 *
 * @author Steve Ebersole
 */
public class StandardIndexExporter implements Exporter<Index> {

	private final Dialect dialect;

	public StandardIndexExporter(Dialect dialect) {
		this.dialect = dialect;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected Dialect getDialect() {
		return dialect;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String[] getSqlCreateStrings(Index index, Metadata metadata, SqlStringGenerationContext context) {
		final var createIndex = new StringBuilder()
				.append( createIndexString( index ) )
				.append( " " )
				.append( indexName( index, context, metadata ) )
				.append( " on " )
				.append( context.format( index.getTable().getQualifiedTableName() ) );
		final String using = index.getUsing();
		if ( isNotBlank( using ) ) {
			createIndex.append( " using " ).append( using );
		}
		createIndex.append( " (" );
		appendColumnList( index, createIndex );
		createIndex.append( ")" );
		String options = index.getOptions();
		if ( isNotBlank( options ) ) {
			createIndex.append( " " ).append( options );
		}
		return new String[] { createIndex.toString() };
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private String createIndexString(Index index) {
		final String createIndexString = dialect.getCreateIndexString( index.isUnique() );
		final String type = index.getType();
		return isBlank( type )
				? createIndexString
				: createIndexString.replaceFirst( " (?i:index)",
						' ' + type + " index" );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private String indexName(Index index, SqlStringGenerationContext context, Metadata metadata) {
		if ( dialect.qualifyIndexName() ) {
			final var qualifiedTableName = index.getTable().getQualifiedTableName();
			return context.format(
					new QualifiedNameImpl(
							qualifiedTableName.getCatalogName(),
							qualifiedTableName.getSchemaName(),
							metadata.getDatabase().getJdbcEnvironment().getIdentifierHelper()
									.toIdentifier( index.getQuotedName( dialect ) )
					)
			);
		}
		else {
			return index.getName();
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private void appendColumnList(Index index, StringBuilder createIndex) {
		boolean first = true;
		final var columnOrderMap = index.getSelectableOrderMap();
		for ( var column : index.getSelectables() ) {
			if ( first ) {
				first = false;
			}
			else {
				createIndex.append( ", " );
			}
			createIndex.append( column.getText( dialect ) );
			if ( columnOrderMap.containsKey( column ) ) {
				createIndex.append( " " ).append( columnOrderMap.get( column ) );
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String[] getSqlDropStrings(Index index, Metadata metadata, SqlStringGenerationContext context) {
		if ( !dialect.dropConstraints() ) {
			return NO_COMMANDS;
		}
		else {
			final String tableName = context.format( index.getTable().getQualifiedTableName() );
			final String indexNameForCreation = dialect.qualifyIndexName()
					? qualify( tableName, index.getName() )
					: index.getName();
			return new String[] {"drop index " + indexNameForCreation};
		}
	}
}
