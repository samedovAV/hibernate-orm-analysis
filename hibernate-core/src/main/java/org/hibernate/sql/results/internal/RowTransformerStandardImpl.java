/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.internal;

import org.hibernate.Incubating;
import org.hibernate.sql.results.spi.RowTransformer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Returns the first object in each row, if there
 * is exactly one item in the selection list, or
 * the whole row otherwise.
 *
 * @author Steve Ebersole
 */
@Incubating
public class RowTransformerStandardImpl<T> implements RowTransformer<T> {
	/**
	 * Singleton access
	 */
	@SuppressWarnings("rawtypes")
	private static final RowTransformerStandardImpl INSTANCE = new RowTransformerStandardImpl();

	@SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static <T> RowTransformer<T> instance() {
		return INSTANCE;
	}

	private RowTransformerStandardImpl() {
	}

	@Override
	@SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public T transformRow(Object[] row) {
		return row.length == 1 ? (T) row[0] : (T) row;
	}
}
