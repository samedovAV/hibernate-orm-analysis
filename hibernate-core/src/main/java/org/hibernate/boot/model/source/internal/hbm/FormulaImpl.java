/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import org.hibernate.boot.model.source.spi.DerivedValueSource;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
class FormulaImpl
		extends AbstractHbmSourceNode
		implements DerivedValueSource {
	private final String tableName;
	private final String expression;

	FormulaImpl(MappingDocument mappingDocument, String tableName, String expression) {
		super( mappingDocument );
		this.tableName = tableName;
		this.expression = expression;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Nature getNature() {
		return Nature.DERIVED;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getExpression() {
		return expression;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getContainingTableName() {
		return tableName;
	}
}
