/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.instantiation.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.hibernate.query.sqm.sql.internal.InstantiationException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
class BeanInjectorSetter<T> implements BeanInjector<T> {
	private final Method setter;

	public BeanInjectorSetter(Method setter) {
		this.setter = setter;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void inject(T target, Object value) {
		try {
			setter.invoke( target, value );
		}
		catch (InvocationTargetException e) {
			throw new InstantiationException( "Error performing the dynamic instantiation", e.getCause() );
		}
		catch (Exception e) {
			throw new InstantiationException( "Error performing the dynamic instantiation", e );
		}
	}
}
