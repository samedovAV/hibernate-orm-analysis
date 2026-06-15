/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.Set;

import org.hibernate.boot.model.source.spi.ColumnSource;
import org.hibernate.boot.model.source.spi.JdbcDataType;
import org.hibernate.boot.model.source.spi.SizeSource;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Implementation of a {@link ColumnSource} when the column is declared as just the name via the column XML
 * attribute.  For example, {@code <property name="socialSecurityNumber" column="ssn"/>}.
 *
 * @author Steve Ebersole
 */
class ColumnAttributeSourceImpl
		extends AbstractHbmSourceNode
		implements ColumnSource {

	private final String tableName;
	private final String columnName;
	private final SizeSource sizeSource;
	private final Boolean nullable;
	private final Boolean unique;
	private final Set<String> indexConstraintNames;
	private final Set<String> ukConstraintNames;

	ColumnAttributeSourceImpl(
			MappingDocument mappingDocument,
			String tableName,
			String columnName,
			SizeSource sizeSource,
			Boolean nullable,
			Boolean unique,
			Set<String> indexConstraintNames,
			Set<String> ukConstraintNames) {
		super( mappingDocument );
		this.tableName = tableName;
		this.columnName = columnName;
		this.sizeSource = sizeSource;
		this.nullable = nullable;
		this.unique = unique;
		this.indexConstraintNames = indexConstraintNames;
		this.ukConstraintNames = ukConstraintNames;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Nature getNature() {
		return Nature.COLUMN;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getContainingTableName() {
		return tableName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getName() {
		return columnName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean isNullable() {
		return nullable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDefaultValue() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSqlType() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcDataType getDatatype() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SizeSource getSizeSource() {
		return sizeSource;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getReadFragment() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getWriteFragment() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isUnique() {
		return unique == Boolean.TRUE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getCheckCondition() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getComment() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<String> getIndexConstraintNames() {
		return indexConstraintNames;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<String> getUniqueKeyConstraintNames() {
		return ukConstraintNames;
	}
}
