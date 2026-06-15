/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import org.hibernate.internal.util.IndexedConsumer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Container for one-or-more JdbcMappings
 */
public interface JdbcMappingContainer {
	/**
	 * The number of JDBC mappings
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default int getJdbcTypeCount() {
		return forEachJdbcType( (index, jdbcMapping) -> {} );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcMapping getJdbcMapping(int index);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default JdbcMapping getSingleJdbcMapping() {
		assert getJdbcTypeCount() == 1;
		return getJdbcMapping( 0 );
	}

	/**
	 * Visit each of JdbcMapping
	 *
	 * @apiNote Same as {@link #forEachJdbcType(int, IndexedConsumer)} starting from `0`
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default int forEachJdbcType(IndexedConsumer<JdbcMapping> action) {
		return forEachJdbcType( 0, action );
	}

	/**
	 * Visit each JdbcMapping starting from the given offset
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int forEachJdbcType(int offset, IndexedConsumer<JdbcMapping> action);
}
