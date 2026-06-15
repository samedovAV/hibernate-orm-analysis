/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.internal;

import org.hibernate.sql.results.spi.RowTransformer;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * {@link RowTransformer} instantiating a {@link List}
 *
 * @author Gavin King
 */
public class RowTransformerListImpl<T> implements RowTransformer<List<Object>> {
	/**
	 * Singleton access
	 */
	@SuppressWarnings( "rawtypes" )
	private static final RowTransformerListImpl INSTANCE = new RowTransformerListImpl();

	@SuppressWarnings( "unchecked" )
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static <X> RowTransformerListImpl<X> instance() {
		return INSTANCE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<Object> transformRow(Object[] row) {
		return List.of( row );
	}
}
