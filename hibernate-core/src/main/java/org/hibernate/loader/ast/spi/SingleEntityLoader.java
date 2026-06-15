/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.loader.ast.spi;

import org.hibernate.LockOptions;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.metamodel.mapping.EntityMappingType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Loader for loading a single entity by primary or unique key
 *
 * @author Steve Ebersole
 */
public interface SingleEntityLoader<T> extends EntityLoader {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityMappingType getLoadable();

	/**
	 * Load an entity by a primary or unique key value.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T load(Object key, LockOptions lockOptions, Boolean readOnly, SharedSessionContractImplementor session);
}
