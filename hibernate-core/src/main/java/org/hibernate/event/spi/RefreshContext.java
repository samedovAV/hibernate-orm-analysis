/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;

import java.util.IdentityHashMap;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A {@link RefreshEvent} represents a {@linkplain org.hibernate.Session#refresh(Object) refresh operation}
 * applied to a single entity. A {@code RefreshContext} is propagated across all cascaded refresh operations,
 * and keeps track of all the entities we've already visited.
 *
 * @author Gavin King
 */
public interface RefreshContext {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean add(Object entity);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isEmpty() {
		return false;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static RefreshContext create() {
		// use extension to avoid creating
		// a useless wrapper object
		class Impl extends IdentityHashMap<Object,Object>
				implements RefreshContext {
			Impl() {
				super(10);
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public boolean add(Object entity) {
				return put(entity,entity)==null;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public boolean isEmpty() {
				return size() == 0;
			}
		}
		return new Impl();
	}
}
