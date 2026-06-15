/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.extract.spi;

import java.util.List;

import org.hibernate.boot.model.naming.Identifier;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Provides access to information about existing index in the database
 *
 * @author Christoph Sturm
 * @author Steve Ebersole
 */
public interface IndexInformation {
	/**
	 * Obtain the identifier for this index.
	 *
	 * @return The index identifier.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Identifier getIndexIdentifier();

	/**
	 * Obtain the columns indexed under this index.  Returned in sequential order.
	 *
	 * @return The columns
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<ColumnInformation> getIndexedColumns();
}
