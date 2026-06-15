/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.extract.spi;

import java.sql.Types;

import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Provides access to information about existing table columns
 *
 * @author Christoph Sturm
 * @author Steve Ebersole
 */
@Incubating
public interface ColumnTypeInformation {

	ColumnTypeInformation EMPTY = new ColumnTypeInformation() {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Boolean getNullable() {
			return null;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public int getTypeCode() {
			return Types.OTHER;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getTypeName() {
			return null;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public int getColumnSize() {
			return 0;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public int getDecimalDigits() {
			return 0;
		}
	};

	/**
	 * Is the column nullable?
	 * <p>
	 * The database is allowed to report unknown, hence the use of {@link Boolean}.
	 *
	 * @return nullability, if known
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean getNullable();

	/**
	 * The JDBC type-code.
	 *
	 * @return JDBC type-code
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getTypeCode();

	/**
	 * The database specific type name.
	 *
	 * @return Type name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTypeName();

	// todo : wrap these in org.hibernate.metamodel.spi.relational.Size

	/**
	 * The column size (length).
	 *
	 * @return The column length
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getColumnSize();

	/**
	 * The precision, for numeric types
	 *
	 * @return The numeric precision
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getDecimalDigits();
}
