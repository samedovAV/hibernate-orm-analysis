/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;

import java.util.HashMap;
import java.util.Map;

import static org.hibernate.internal.util.StringHelper.isNotEmpty;
import static org.hibernate.internal.util.StringHelper.qualify;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A mapping model object representing a {@linkplain jakarta.persistence.UniqueConstraint unique key}
 * constraint on a relational database table.
 *
 * @author Brett Meyer
 */
public class UniqueKey extends Constraint {

	private final Map<Column, String> columnOrderMap = new HashMap<>();
	private boolean nameExplicit; // true when the constraint name was explicitly specified by @UniqueConstraint annotation
	private boolean explicit; // true when the constraint was explicitly specified by @UniqueConstraint annotation

	public UniqueKey(Table table) {
		super( table );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void addColumn(Column column, String order) {
		addColumn( column );
		if ( isNotEmpty( order ) ) {
			columnOrderMap.put( column, order );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Map<Column, String> getColumnOrderMap() {
		return columnOrderMap;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getExportIdentifier() {
		return qualify( getTable().getExportIdentifier(), "UK-" + getName() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isNameExplicit() {
		return nameExplicit;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setNameExplicit(boolean nameExplicit) {
		this.nameExplicit = nameExplicit;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isExplicit() {
		return explicit;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setExplicit(boolean explicit) {
		this.explicit = explicit;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean hasNullableColumn() {
		for ( var column : getColumns() ) {
			final var tableColumn = getTable().getColumn( column );
			if ( tableColumn != null && tableColumn.isNullable() ) {
				return true;
			}
		}
		return false;
	}
}
