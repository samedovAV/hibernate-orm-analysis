/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import org.hibernate.cache.MutableCacheKeyBuilder;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import static org.hibernate.engine.internal.CacheHelper.addBasicValueToCacheKey;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Any basic-typed ValueMapping.  Generally this would be one of<ul>
 *     <li>a {@link jakarta.persistence.Basic} attribute</li>
 *     <li>a basic-valued collection part</li>
 *     <li>a {@link org.hibernate.type.BasicType}</li>
 * </ul>
 *
 * @author Steve Ebersole
 */
public interface BasicValuedMapping extends ValueMapping, SqlExpressible {

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default int getJdbcTypeCount() {
		return 1;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default JdbcMapping getJdbcMapping(int index) {
		if ( index != 0 ) {
			throw new IndexOutOfBoundsException( index );
		}
		return getJdbcMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default JdbcMapping getSingleJdbcMapping() {
		return getJdbcMapping();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcMapping getJdbcMapping();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Object disassemble(Object value, SharedSessionContractImplementor session) {
		return getJdbcMapping().convertToRelationalValue( value );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void addToCacheKey(
			MutableCacheKeyBuilder cacheKey,
			Object value,
			SharedSessionContractImplementor session) {
		addBasicValueToCacheKey( cacheKey, value, getJdbcMapping(), session );
	}
}
