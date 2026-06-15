/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.internal;

import org.hibernate.sql.results.spi.RowTransformer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Returns the first object in each row.
 * <p>
 * Should only be used when the query has exactly
 * one item in its selection list.
 *
 * @author Steve Ebersole
 */
public class RowTransformerSingularReturnImpl<R> implements RowTransformer<R> {
	/**
	 * Singleton access
	 */
	@SuppressWarnings("rawtypes")
	private static final RowTransformerSingularReturnImpl INSTANCE = new RowTransformerSingularReturnImpl();

	@SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static <R> RowTransformer<R> instance() {
		return INSTANCE;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public R transformRow(Object[] row) {
		assert row.length == 1;
		return (R) row[0];
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int determineNumberOfResultElements(int rawElementCount) {
		return 1;
	}
}
