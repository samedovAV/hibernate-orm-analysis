/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.internal;

import jakarta.persistence.TupleElement;
import org.hibernate.sql.results.spi.RowTransformer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * {@link RowTransformer} instantiating a {@link Map}
 *
 * @author Gavin King
 */
public class RowTransformerMapImpl implements RowTransformer<Map<String,Object>> {
	private final TupleMetadata tupleMetadata;

	public RowTransformerMapImpl(TupleMetadata tupleMetadata) {
		this.tupleMetadata = tupleMetadata;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Map<String,Object> transformRow(Object[] row) {
		Map<String,Object> map = new HashMap<>( row.length );
		List<TupleElement<?>> list = tupleMetadata.getList();
		for ( int i = 0; i < row.length; i++ ) {
			String alias = list.get(i).getAlias();
			if ( alias == null ) {
				alias = Integer.toString(i);
			}
			map.put( alias, row[i] );
		}
		return map;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int determineNumberOfResultElements(int rawElementCount) {
		return 1;
	}
}
