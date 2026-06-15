/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.id.enhanced;

import org.hibernate.boot.model.relational.Database;
import org.hibernate.tool.schema.internal.ColumnValue;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Table;
import org.hibernate.type.BasicType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

class ExportableColumnHelper {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static Column column(Database database, Table table, String segmentColumnName, BasicType<?> type, String typeName) {
		final var column = new Column( segmentColumnName );
		column.setSqlType( typeName );
		column.setValue( new ColumnValue( database, table, column, type ) );
		return column;
	}
}
