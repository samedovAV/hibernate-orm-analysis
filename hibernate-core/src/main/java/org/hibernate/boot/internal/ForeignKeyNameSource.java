/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.internal;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitForeignKeyNameSource;
import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.ForeignKey;
import org.hibernate.mapping.Table;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.hibernate.boot.model.naming.Identifier.toIdentifier;
import static org.hibernate.internal.util.collections.CollectionHelper.arrayList;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class ForeignKeyNameSource implements ImplicitForeignKeyNameSource {

	private final List<Identifier> columnNames;
	private final ForeignKey foreignKey;
	private final Table table;
	private final MetadataBuildingContext buildingContext;
	private List<Identifier> referencedColumnNames;

	public ForeignKeyNameSource(ForeignKey foreignKey, Table table, MetadataBuildingContext buildingContext) {
		this.foreignKey = foreignKey;
		this.table = table;
		this.buildingContext = buildingContext;
		columnNames = extractColumnNames( foreignKey.getColumns() );
		referencedColumnNames = null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Identifier getTableName() {
		return table.getNameIdentifier();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<Identifier> getColumnNames() {
		return columnNames;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Identifier getReferencedTableName() {
		return foreignKey.getReferencedTable().getNameIdentifier();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<Identifier> getReferencedColumnNames() {
		if ( referencedColumnNames == null ) {
			referencedColumnNames = extractColumnNames( foreignKey.getReferencedColumns() );
		}
		return referencedColumnNames;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Identifier getUserProvidedIdentifier() {
		String name = foreignKey.getName();
		return name != null ? toIdentifier(name) : null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MetadataBuildingContext getBuildingContext() {
		return buildingContext;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private List<Identifier> extractColumnNames(List<Column> columns) {
		if ( columns == null || columns.isEmpty() ) {
			return emptyList();
		}

		final List<Identifier> columnNames = arrayList( columns.size() );
		for ( Column column : columns ) {
			columnNames.add( column.getNameIdentifier( buildingContext ) );
		}
		return columnNames;
	}
}
