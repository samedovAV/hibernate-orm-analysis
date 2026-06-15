/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import jakarta.annotation.Nullable;
import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.sql.ast.spi.SqlSelection;
import org.hibernate.sql.ast.spi.SqlSelectionProducer;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.results.internal.SqlSelectionImpl;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.spi.TypeConfiguration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models an expression at the SQL AST level.
 *
 * @author Steve Ebersole
 */
public interface Expression extends SqlAstNode, SqlSelectionProducer {
	/**
	 * The type for this expression
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcMappingContainer getExpressionType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable ColumnReference getColumnReference() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqlSelection createSqlSelection(
			int jdbcPosition,
			int valuesArrayPosition,
			JavaType javaType,
			boolean virtual,
			TypeConfiguration typeConfiguration) {
		return new SqlSelectionImpl(
				jdbcPosition,
				valuesArrayPosition,
				javaType,
				this,
				virtual
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqlSelection createDomainResultSqlSelection(
			int jdbcPosition,
			int valuesArrayPosition,
			JavaType javaType,
			boolean virtual,
			TypeConfiguration typeConfiguration) {
		// Apply possible jdbc type wrapping
		final Expression expression;
		final JdbcMappingContainer expressionType = getExpressionType();
		if ( expressionType == null ) {
			expression = this;
		}
		else {
			expression = expressionType.getJdbcMapping( 0 ).getJdbcType().wrapTopLevelSelectionExpression( this );
		}
		return expression == this
			? createSqlSelection( jdbcPosition, valuesArrayPosition, javaType, virtual, typeConfiguration )
			: new SqlSelectionImpl( jdbcPosition, valuesArrayPosition, expression, virtual );
	}
}
