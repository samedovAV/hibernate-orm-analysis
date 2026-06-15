/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.internal;

import jakarta.persistence.Tuple;

import org.hibernate.sql.results.spi.RowTransformer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * RowTransformer generating a JPA {@link Tuple}
 *
 * @author Steve Ebersole
 */
public class RowTransformerJpaTupleImpl implements RowTransformer<Tuple> {
	private final TupleMetadata tupleMetadata;

	public RowTransformerJpaTupleImpl(TupleMetadata tupleMetadata) {
		this.tupleMetadata = tupleMetadata;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Tuple transformRow(Object[] row) {
		return new TupleImpl( tupleMetadata, row );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int determineNumberOfResultElements(int rawElementCount) {
		return 1;
	}
}
