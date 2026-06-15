/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.jpa.spi;

import org.hibernate.query.TupleTransformer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A {@link TupleTransformer} for handling {@code Object[]} results from native queries.
 *
 * @since 7.0
 */
public class NativeQueryArrayTransformer implements TupleTransformer<Object[]> {

	public static final NativeQueryArrayTransformer INSTANCE = new NativeQueryArrayTransformer();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object[] transformTuple(Object[] tuple, String[] aliases) {
		return tuple;
	}
}
