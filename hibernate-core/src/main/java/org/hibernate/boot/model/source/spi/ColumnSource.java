/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import java.util.Set;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contract for source information pertaining to a physical column definition specific to a particular attribute
 * context.
 * <p>
 * Conceptual note: this really describes a column from the perspective of its binding to an attribute, not
 * necessarily the column itself.
 *
 * @author Steve Ebersole
 */
public interface ColumnSource extends RelationalValueSource {
	/**
	 * Obtain the name of the column.
	 *
	 * @return The name of the column.  Can be {@code null}, in which case a naming strategy is applied.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();

	/**
	 * A SQL fragment to apply to the column value on read.
	 *
	 * @return The SQL read fragment
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getReadFragment();

	/**
	 * A SQL fragment to apply to the column value on write.
	 *
	 * @return The SQL write fragment
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getWriteFragment();

	/**
	 * Is this column nullable?
	 *
	 * @return {@code true} indicates it is nullable; {@code false} non-nullable.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isNullable();

	/**
	 * Obtain a specified default value for the column
	 *
	 * @return THe column default
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getDefaultValue();

	/**
	 * Obtain the free-hand definition of the column's type.
	 *
	 * @return The free-hand column type
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSqlType();

	/**
	 * The deduced (and dialect convertible) type for this column
	 *
	 * @return The column's SQL data type.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcDataType getDatatype();

	/**
	 * Obtain the source for the specified column size.
	 *
	 * @return The source for the column size.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SizeSource getSizeSource();

	/**
	 * Is this column unique?
	 *
	 * @return {@code true} indicates it is unique; {@code false} non-unique.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isUnique();

	/**
	 * Obtain the specified check constraint condition
	 *
	 * @return Check constraint condition
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCheckCondition();

	/**
	 * Obtain the specified SQL comment
	 *
	 * @return SQL comment
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getComment();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<String> getIndexConstraintNames();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<String> getUniqueKeyConstraintNames();
}
