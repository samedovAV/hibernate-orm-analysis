/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import jakarta.annotation.Nullable;
import org.hibernate.Incubating;
import org.hibernate.annotations.ColumnTransformer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Mapping of a selectable (column/formula)
 *
 * @author Christian Beikov
 */
@Incubating
public interface SelectableMapping extends SqlTypedMapping {
	/**
	 * The name of the table to which this selectable is mapped
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getContainingTableExpression();

	/**
	 * The selection's expression.  This is the column name or formula
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSelectionExpression();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getSelectableName() {
		return getSelectionExpression();
	}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SelectablePath getSelectablePath() {
		return new SelectablePath( getSelectableName() );
	}

	/**
	 * The selection's read expression accounting for formula treatment as well
	 * as {@link ColumnTransformer#read()}
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCustomReadExpression();

	/**
	 * The selection's write expression accounting {@link ColumnTransformer#write()}
	 *
	 * @apiNote Always null for formula mappings
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCustomWriteExpression();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getWriteExpression() {
		final String customWriteExpression = getCustomWriteExpression();
		return customWriteExpression != null
				? customWriteExpression
				: "?";
	}

	/**
	 * Is the mapping a formula instead of a physical column?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isFormula();

	/**
	 * Is the mapping considered nullable?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isNullable();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isInsertable();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isUpdateable();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isPartitioned();
}
