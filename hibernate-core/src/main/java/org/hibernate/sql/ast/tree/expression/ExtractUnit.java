/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import org.hibernate.metamodel.mapping.BasicValuedMapping;
import org.hibernate.query.common.TemporalUnit;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.SqlAstNode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A {@link TemporalUnit} passed as an argument to the
 * {@link org.hibernate.dialect.function.ExtractFunction}.
 * These are different to {@link DurationUnit}s because of
 * how the {@link TemporalUnit#WEEK} field is handled on
 * some platforms.
 *
 * @author Gavin King
 */
public class ExtractUnit implements Expression, SqlAstNode {
	private final TemporalUnit unit;
	private final BasicValuedMapping type;

	public ExtractUnit(TemporalUnit unit, BasicValuedMapping type) {
		this.unit = unit;
		this.type = type;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TemporalUnit getUnit() {
		return unit;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public BasicValuedMapping getExpressionType() {
		return type;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitExtractUnit( this );
	}
}
