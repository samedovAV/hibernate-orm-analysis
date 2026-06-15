/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.internal;

import java.util.List;

import org.hibernate.MappingException;
import org.hibernate.boot.model.relational.Database;
import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.engine.FetchStyle;
import org.hibernate.internal.util.collections.ArrayHelper;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Selectable;
import org.hibernate.mapping.Table;
import org.hibernate.mapping.Value;
import org.hibernate.mapping.ValueVisitor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.MappingContext;
import org.hibernate.type.Type;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class ColumnValue implements Value {

	private final Database database;
	private final Table table;
	private final Column column;
	private final Type type;

	public ColumnValue(Database database, Table table, Column column, Type type) {
		this.database = database;
		this.table = table;
		this.column = column;
		this.type = type;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Value copy() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getColumnSpan() {
		return 1;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<Selectable> getSelectables() {
		return List.of( column );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<Column> getColumns() {
		return List.of( column );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasColumns() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Type getType() {
		return type;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchStyle getFetchStyle() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Table getTable() {
		return table;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasFormula() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isAlternateUniqueKey() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isNullable() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean[] getColumnInsertability() {
		return ArrayHelper.TRUE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasAnyInsertableColumns() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean[] getColumnUpdateability() {
		return ArrayHelper.TRUE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasAnyUpdatableColumns() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void createForeignKey() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void createUniqueKey(MetadataBuildingContext context) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isSimpleValue() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isValid(MappingContext mappingContext) throws MappingException {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setTypeUsingReflection(String className, String propertyName) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object accept(ValueVisitor visitor) {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isSame(Value value) {
		return this == value;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public ServiceRegistry getServiceRegistry() {
		return database.getServiceRegistry();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isColumnInsertable(int index) {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isColumnUpdateable(int index) {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isPartitionKey() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setNonInsertable() {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setNonUpdatable() {
		throw new UnsupportedOperationException();
	}
}
