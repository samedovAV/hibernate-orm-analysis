/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.spi;

import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Defines transformation of a raw row in the domain query result row.
 *
 * @see org.hibernate.query.TupleTransformer
 *
 * @author Steve Ebersole
 */
@Incubating
public interface RowTransformer<T> {
	/**
	 * Transform the "raw" row values into the ultimate query result (for a row)
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T transformRow(Object[] row);

	/**
	 * How many result elements will this transformation produce?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default int determineNumberOfResultElements(int rawElementCount) {
		return rawElementCount;
	}
}
