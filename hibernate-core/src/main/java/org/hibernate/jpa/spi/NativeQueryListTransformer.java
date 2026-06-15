/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.jpa.spi;

import org.hibernate.query.TupleTransformer;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A {@link TupleTransformer} for handling {@link List} results from native queries.
 *
 * @since 6.3
 *
 * @author Gavin King
 */
public class NativeQueryListTransformer implements TupleTransformer<List<Object>> {

	public static final NativeQueryListTransformer INSTANCE = new NativeQueryListTransformer();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<Object> transformTuple(Object[] tuple, String[] aliases) {
		return List.of( tuple );
	}
}
