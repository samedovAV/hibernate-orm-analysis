/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.extract.spi;

import org.hibernate.boot.model.naming.Identifier;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Provides access to information about existing table columns
 *
 * @author Christoph Sturm
 * @author Steve Ebersole
 */
public interface ColumnInformation extends ColumnTypeInformation {
	/**
	 * Access to the containing table.
	 *
	 * @return The containing table information
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TableInformation getContainingTableInformation();

	/**
	 * The simple (not qualified) column name.
	 *
	 * @return The column simple identifier.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Identifier getColumnIdentifier();
}
