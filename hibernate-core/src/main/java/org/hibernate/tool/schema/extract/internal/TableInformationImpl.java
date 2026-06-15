/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.extract.internal;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.relational.QualifiedTableName;
import org.hibernate.engine.jdbc.env.spi.IdentifierHelper;
import org.hibernate.tool.schema.extract.spi.ColumnInformation;
import org.hibernate.tool.schema.extract.spi.ForeignKeyInformation;
import org.hibernate.tool.schema.extract.spi.IndexInformation;
import org.hibernate.tool.schema.extract.spi.InformationExtractor;
import org.hibernate.tool.schema.extract.spi.PrimaryKeyInformation;
import org.hibernate.tool.schema.extract.spi.TableInformation;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Provides access to information about existing schema objects (tables, sequences etc) of existing database.
 *
 * @author Christoph Sturm
 * @author Max Rydahl Andersen
 * @author Steve Ebersole
 */
public class TableInformationImpl implements TableInformation {
	private final InformationExtractor extractor;
	private final IdentifierHelper identifierHelper;

	private final QualifiedTableName tableName;
	private final boolean physicalTable;
	private final String comment;

	private PrimaryKeyInformation primaryKey;
	private Map<Identifier, ForeignKeyInformation> foreignKeys;
	private Map<Identifier, IndexInformation> indexes;
	private final Map<Identifier, ColumnInformation> columns = new HashMap<>();

	private boolean wasPrimaryKeyLoaded = false; // to avoid multiple db reads since primary key can be null.

	public TableInformationImpl(
			InformationExtractor extractor,
			IdentifierHelper identifierHelper,
			QualifiedTableName tableName,
			boolean physicalTable,
			String comment ) {
		this.extractor = extractor;
		this.identifierHelper = identifierHelper;
		this.tableName = tableName;
		this.physicalTable = physicalTable;
		this.comment = comment;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QualifiedTableName getName() {
		return tableName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isPhysicalTable() {
		return physicalTable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getComment() {
		return comment;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ColumnInformation getColumn(Identifier columnIdentifier) {
		return columns.get( new Identifier(
				identifierHelper.toMetaDataObjectName( columnIdentifier ),
				false
		) );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public PrimaryKeyInformation getPrimaryKey() {
		if ( ! wasPrimaryKeyLoaded ) {
			primaryKey = extractor.getPrimaryKey( this );
			wasPrimaryKeyLoaded = true;
		}
		return primaryKey;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Iterable<ForeignKeyInformation> getForeignKeys() {
		return foreignKeys().values();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected Map<Identifier, ForeignKeyInformation> foreignKeys() {
		if ( foreignKeys == null ) {
			final Map<Identifier, ForeignKeyInformation> result = new HashMap<>();
			for ( var foreignKeyInformation : extractor.getForeignKeys( this ) ) {
				result.put( foreignKeyInformation.getForeignKeyIdentifier(), foreignKeyInformation );
			}
			foreignKeys = result;
		}
		return foreignKeys;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ForeignKeyInformation getForeignKey(Identifier fkIdentifier) {
		return foreignKeys().get( new Identifier(
				identifierHelper.toMetaDataObjectName( fkIdentifier ),
				false
		)  );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Iterable<IndexInformation> getIndexes() {
		return indexes().values();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected Map<Identifier, IndexInformation> indexes() {
		if ( indexes == null ) {
			final Map<Identifier, IndexInformation> indexMap = new HashMap<>();
			for ( var index : extractor.getIndexes( this ) ) {
				indexMap.put( index.getIndexIdentifier(), index );
			}
			this.indexes = indexMap;
		}
		return indexes;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addColumn(ColumnInformation columnIdentifier) {
		columns.put( columnIdentifier.getColumnIdentifier(), columnIdentifier );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public IndexInformation getIndex(Identifier indexName) {
		return indexes().get( new Identifier(
				identifierHelper.toMetaDataObjectName( indexName ),
				false
		) );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String toString() {
		return "TableInformationImpl(" + tableName.toString() + ')';
	}
}
