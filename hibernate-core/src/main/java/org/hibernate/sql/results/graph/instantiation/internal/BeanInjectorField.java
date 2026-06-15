/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.instantiation.internal;

import java.lang.reflect.Field;

import org.hibernate.query.sqm.sql.internal.InstantiationException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
class BeanInjectorField<T> implements BeanInjector<T> {
	private final Field field;

	public BeanInjectorField(Field field) {
		this.field = field;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void inject(T target, Object value) {
		try {
			field.set( target, value );
		}
		catch (Exception e) {
			throw new InstantiationException( "Error performing the dynamic instantiation", e );
		}
	}
}
