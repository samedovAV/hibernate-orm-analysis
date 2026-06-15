/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.internal;

import org.hibernate.metamodel.spi.Instantiator;

import static org.hibernate.internal.util.ReflectHelper.isAbstractClass;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Base support for POJO-based instantiation
 *
 * @author Steve Ebersole
 */
public abstract class AbstractPojoInstantiator implements Instantiator {
	private final Class<?> mappedPojoClass;
	private final boolean isAbstract;

	public AbstractPojoInstantiator(Class<?> mappedPojoClass) {
		this.mappedPojoClass = mappedPojoClass;
		this.isAbstract = isAbstractClass( mappedPojoClass );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getMappedPojoClass() {
		return mappedPojoClass;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isAbstract() {
		return isAbstract;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isInstance(Object object) {
		return mappedPojoClass.isInstance( object );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isSameClass(Object object) {
		return object.getClass() == mappedPojoClass;
	}

}
