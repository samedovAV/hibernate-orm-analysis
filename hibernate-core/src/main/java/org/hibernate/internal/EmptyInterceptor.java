/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal;

import java.io.Serializable;

import org.hibernate.Interceptor;
import org.hibernate.type.Type;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An interceptor that does nothing.
 * This is an internal class and should not be used as a base to implement a custom Interceptor;
 * it overrides the default methods for sake of efficiency.
 *
 * Implementors of Interceptor don't need a base class anymore since we now have default
 * implementations of the contract defined in the interface.
 */
public final class EmptyInterceptor implements Interceptor, Serializable {

	public static final Interceptor INSTANCE = new EmptyInterceptor();

	private EmptyInterceptor() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean onLoad(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean onFlushDirty(
			Object entity,
			Object id,
			Object[] currentState,
			Object[] previousState,
			String[] propertyNames,
			Type[] types) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean onSave(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void onDelete(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int[] findDirty(
			Object entity,
			Object id,
			Object[] currentState,
			Object[] previousState,
			String[] propertyNames,
			Type[] types) {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getEntity(String entityName, Object id) {
		return null;
	}

}
