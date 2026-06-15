/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.MappingException;
import org.hibernate.boot.model.relational.Exportable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A mapping model object representing a constraint on a relational database table.
 *
 * @author Gavin King
 * @author Brett Meyer
 */
public abstract class Constraint implements Exportable, Serializable {

	private String name;
	private final ArrayList<Column> columns = new ArrayList<>();
	private final Table table;
	private String options = "";

	Constraint(Table table) {
		this.table = table;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getName() {
		return name;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setName(String name) {
		this.name = name;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getOptions() {
		return options;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setOptions(String options) {
		this.options = options;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addColumn(Column column) {
		if ( !columns.contains( column ) ) {
			columns.add( column );
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void addColumns(Value value) {
		for ( var selectable : value.getSelectables() ) {
			if ( selectable.isFormula() ) {
				throw new MappingException( "constraint involves a formula: " + name );
			}
			else {
				addColumn( (Column) selectable );
			}
		}
	}

	/**
	 * @return true if this constraint already contains a column with same name.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean containsColumn(Column column) {
		return columns.contains( column );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getColumnSpan() {
		return columns.size();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Column getColumn(int i) {
		return columns.get( i );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Table getTable() {
		return table;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<Column> getColumns() {
		return columns;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return getClass().getSimpleName() + '(' + getTable().getName() + getColumns() + ") as " + name;
	}
}
