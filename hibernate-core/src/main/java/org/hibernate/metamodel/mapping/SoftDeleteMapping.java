/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import jakarta.annotation.Nullable;
import org.hibernate.Internal;
import org.hibernate.annotations.SoftDeleteType;
import org.hibernate.sql.ast.spi.SqlExpressionResolver;
import org.hibernate.sql.ast.tree.expression.ColumnReference;
import org.hibernate.sql.ast.tree.from.TableReference;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import org.hibernate.sql.ast.tree.update.Assignment;
import org.hibernate.sql.model.ast.ColumnValueBinding;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Metadata about the indicator column for entities and collections enabled
 * for soft delete
 *
 * @see org.hibernate.annotations.SoftDelete
 *
 * @author Steve Ebersole
 */
public interface SoftDeleteMapping extends AuxiliaryMapping, SelectableMapping, VirtualModelPart, SqlExpressible {
	String ROLE_NAME = "{soft-delete}";

	/**
	 * The soft-delete strategy - how to interpret indicator values
	 */
	@Internal // only used in tests!
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SoftDeleteType getSoftDeleteStrategy();

	/**
	 * The name of the soft-delete indicator column.
	 */
	@Internal // only used in tests!
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getColumnName();

	/**
	 * The name of the table which holds the {@linkplain #getColumnName() indicator column}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTableName();

	/**
	 * Create a SQL AST Assignment for setting the soft-delete column to its indicated "deleted" value
	 *
	 * @param tableReference Reference for the table containing the soft-delete column
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Assignment createSoftDeleteAssignment(TableReference tableReference);

	/**
	 * Create a SQL AST Predicate for restricting matches to non-deleted rows
	 *
	 * @param tableReference Reference for the table containing the soft-delete column
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Predicate createNonDeletedRestriction(TableReference tableReference);

	/**
	 * Create a SQL AST Predicate for restricting matches to non-deleted rows
	 *
	 * @param tableReference Reference for the table containing the soft-delete column
	 * @param expressionResolver Resolver for SQL AST Expressions
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Predicate createNonDeletedRestriction(TableReference tableReference, SqlExpressionResolver expressionResolver);

	/**
	 * Create a ColumnValueBinding for non-deleted indicator.
	 *
	 * @param softDeleteColumnReference Reference to the soft-delete column
	 *
	 * @apiNote Generally used as a restriction in a SQL AST
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ColumnValueBinding createNonDeletedValueBinding(ColumnReference softDeleteColumnReference);

	/**
	 * Create a ColumnValueBinding for deleted indicator.
	 *
	 * @param softDeleteColumnReference Reference to the soft-delete column
	 *
	 * @apiNote Generally used as an assignment in a SQL AST
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ColumnValueBinding createDeletedValueBinding(ColumnReference softDeleteColumnReference);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// SelectableMapping

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getSelectionExpression() {
		return getColumnName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getSelectableName() {
		return getColumnName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getContainingTableExpression() {
		return getTableName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable String getCustomReadExpression() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable String getCustomWriteExpression() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isFormula() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isNullable() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isInsertable() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isUpdateable() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isPartitioned() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable Long getLength() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable Integer getArrayLength() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable Integer getPrecision() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable Integer getScale() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable Integer getTemporalPrecision() {
		return null;
	}
}
