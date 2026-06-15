/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.extract.spi;

import org.hibernate.boot.model.naming.Identifier;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Provides access to information about existing primary key for a table
 *
 * @author Steve Ebersole
 */
public interface PrimaryKeyInformation {
	/**
	 * Obtain the identifier for this PK.
	 *
	 * @return The PK identifier.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Identifier getPrimaryKeyIdentifier();

	/**
	 * Obtain the columns making up the primary key.  Returned in sequential order.
	 *
	 * @return The columns
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Iterable<ColumnInformation> getColumns();
}
