/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.internal;

import org.hibernate.sql.results.spi.RowTransformer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * RowTransformer used when an array is explicitly specified as the return type
 *
 * @author Steve Ebersole
 */
public class RowTransformerArrayImpl implements RowTransformer<Object[]> {
	/**
	 * Singleton access
	 */
	private static final RowTransformerArrayImpl INSTANCE = new RowTransformerArrayImpl();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static RowTransformerArrayImpl instance() {
		return INSTANCE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object[] transformRow(Object[] row) {
		return row;
	}
}
