/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.extract.internal;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.tool.schema.extract.spi.ColumnInformation;
import org.hibernate.tool.schema.extract.spi.PrimaryKeyInformation;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class PrimaryKeyInformationImpl implements PrimaryKeyInformation {
	private final Identifier identifier;
	private final Iterable<ColumnInformation> columns;

	public PrimaryKeyInformationImpl(Identifier identifier, Iterable<ColumnInformation> columns) {
		this.identifier = identifier;
		this.columns = columns;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Identifier getPrimaryKeyIdentifier() {
		return identifier;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Iterable<ColumnInformation> getColumns() {
		return columns;
	}
}
