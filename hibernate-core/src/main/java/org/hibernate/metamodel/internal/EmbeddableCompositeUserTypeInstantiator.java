/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.internal;

import org.hibernate.metamodel.spi.EmbeddableInstantiator;
import org.hibernate.metamodel.spi.ValueAccess;
import org.hibernate.usertype.CompositeUserType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class EmbeddableCompositeUserTypeInstantiator implements EmbeddableInstantiator {

	private final CompositeUserType<Object> userType;

	public EmbeddableCompositeUserTypeInstantiator(CompositeUserType<Object> userType) {
		this.userType = userType;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object instantiate(ValueAccess valuesAccess) {
		return userType.instantiate( valuesAccess );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isInstance(Object object) {
		return userType.returnedClass().isInstance( object );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isSameClass(Object object) {
		return object.getClass().equals( userType.returnedClass() );
	}
}
