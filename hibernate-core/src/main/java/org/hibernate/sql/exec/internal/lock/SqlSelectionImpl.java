/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.exec.internal.lock;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.spi.SqlExpressionAccess;
import org.hibernate.sql.ast.spi.SqlSelection;
import org.hibernate.sql.ast.tree.expression.ColumnReference;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.results.jdbc.spi.JdbcValuesMetadata;
import org.hibernate.type.descriptor.ValueExtractor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * SqlSelection implementation used in follow-on locking.
 *
 * @author Steve Ebersole
 */
public class SqlSelectionImpl implements SqlSelection, SqlExpressionAccess {
	private final ColumnReference columnReference;
	private final int valuesArrayPosition;

	public SqlSelectionImpl(ColumnReference columnReference, int valuesArrayPosition) {
		this.columnReference = columnReference;
		this.valuesArrayPosition = valuesArrayPosition;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// SqlExpressionAccess

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getSqlExpression() {
		return columnReference;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public ValueExtractor getJdbcValueExtractor() {
		return columnReference.getJdbcMapping().getJdbcValueExtractor();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getValuesArrayPosition() {
		return valuesArrayPosition;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getExpression() {
		return columnReference;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JdbcMappingContainer getExpressionType() {
		return columnReference.getExpressionType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isVirtual() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlAstWalker) {
		sqlAstWalker.visitSqlSelection( this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqlSelection resolve(JdbcValuesMetadata jdbcResultsMetadata, SessionFactoryImplementor sessionFactory) {
		return null;
	}
}
