/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;

import java.util.IdentityHashMap;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A {@link PersistEvent} represents a {@linkplain org.hibernate.Session#persist(Object) persist operation}
 * applied to a single entity. A {@code PersistContext} is propagated across all cascaded persist operations,
 * and keeps track of all the entities we've already visited.
 *
 * @author Gavin King
 */
public interface PersistContext {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean add(Object entity);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static PersistContext create() {
		// use extension to avoid creating
		// a useless wrapper object
		class Impl extends IdentityHashMap<Object,Object>
				implements PersistContext {
			Impl() {
				super(10);
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public boolean add(Object entity) {
				return put(entity,entity)==null;
			}
		}
		return new Impl();
	}
}
