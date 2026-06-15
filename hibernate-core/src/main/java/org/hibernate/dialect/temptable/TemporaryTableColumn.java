/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.temptable;

import org.hibernate.engine.jdbc.Size;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.mapping.SqlTypedMapping;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A column in a IdTable.  As these columns mirror the entity id columns, we know a few things about it inherently,
 * such as being non-nullable
 *
 * @author Steve Ebersole
 */
public class TemporaryTableColumn implements SqlTypedMapping {
	private final TemporaryTable containingTable;
	private final String columnName;
	private final JdbcMapping jdbcMapping;
	private final String sqlTypeName;
	private final Size size;
	private final boolean nullable;
	private final boolean primaryKey;

	public TemporaryTableColumn(
			TemporaryTable containingTable,
			String columnName,
			JdbcMapping jdbcMapping,
			String sqlTypeName,
			Size size,
			boolean nullable) {
		this( containingTable, columnName, jdbcMapping, sqlTypeName, size, nullable, false );
	}

	public TemporaryTableColumn(
			TemporaryTable containingTable,
			String columnName,
			JdbcMapping jdbcMapping,
			String sqlTypeName,
			Size size,
			boolean nullable,
			boolean primaryKey) {
		this.containingTable = containingTable;
		this.columnName = columnName;
		this.jdbcMapping = jdbcMapping;
		this.sqlTypeName = sqlTypeName;
		this.size = size;
		this.nullable = nullable;
		this.primaryKey = primaryKey;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TemporaryTable getContainingTable() {
		return containingTable;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getColumnName() {
		return columnName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMapping getJdbcMapping() {
		return jdbcMapping;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDefaultValue() {
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSqlTypeDefinition() {
		return sqlTypeName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Size getSize() {
		return size;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isNullable() {
		return nullable;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isPrimaryKey() {
		return primaryKey;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable String getColumnDefinition() {
		return sqlTypeName;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public @Nullable Long getLength() {
		return size.getLength();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public @Nullable Integer getArrayLength() {
		return size.getArrayLength();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public @Nullable Integer getPrecision() {
		return size.getPrecision();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public @Nullable Integer getScale() {
		return size.getScale();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Integer getTemporalPrecision() {
		return getJdbcMapping().getJdbcType().isTemporal() ? size.getPrecision() : null;
	}
}
