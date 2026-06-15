/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.loader.ast.spi;

import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// EntityMultiLoader implementation based on [identifier][org.hibernate.KeyType#IDENTIFIER].
///
/// @see org.hibernate.Session#findMultiple
///
/// @author Steve Ebersole
public interface MultiIdEntityLoader<T> extends EntityMultiLoader<T> {
	/**
	 * Load multiple entities by id.  The exact result depends on the passed options.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<K> List<T> load(K[] ids, MultiIdLoadOptions options, SharedSessionContractImplementor session);
}
