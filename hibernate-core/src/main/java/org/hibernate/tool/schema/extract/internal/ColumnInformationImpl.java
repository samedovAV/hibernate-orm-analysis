/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.extract.internal;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.tool.schema.extract.spi.ColumnInformation;
import org.hibernate.tool.schema.extract.spi.TableInformation;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * JDBC column metadata
 *
 * @author Christoph Sturm
 * @author Steve Ebersole
 */
public class ColumnInformationImpl implements ColumnInformation {
	private final TableInformation containingTableInformation;
	private final Identifier columnIdentifier;

	private final int typeCode;
	private final String typeName;
	private final int columnSize;
	private final int decimalDigits;
	private final Boolean nullable;

	public ColumnInformationImpl(
			TableInformation containingTableInformation,
			Identifier columnIdentifier,
			int typeCode,
			String typeName,
			int columnSize,
			int decimalDigits,
			Boolean nullable) {
		this.containingTableInformation = containingTableInformation;
		this.columnIdentifier = columnIdentifier;
		this.typeCode = typeCode;
		this.typeName = typeName;
		this.columnSize = columnSize;
		this.decimalDigits = decimalDigits;
		this.nullable = nullable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableInformation getContainingTableInformation() {
		return containingTableInformation;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Identifier getColumnIdentifier() {
		return columnIdentifier;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getTypeCode() {
		return typeCode;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTypeName() {
		return typeName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getColumnSize() {
		return columnSize;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getDecimalDigits() {
		return decimalDigits;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean getNullable() {
		return nullable;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "ColumnInformation(" + columnIdentifier + ')';
	}
}
