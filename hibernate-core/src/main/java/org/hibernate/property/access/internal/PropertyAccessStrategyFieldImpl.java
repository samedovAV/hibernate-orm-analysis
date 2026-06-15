/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.property.access.internal;

import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.property.access.spi.PropertyAccessStrategy;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Defines a strategy for accessing property values directly via a field, which may be non-public.
 *
 * @author Gavin King
 * @author Steve Ebersole
 */
public class PropertyAccessStrategyFieldImpl implements PropertyAccessStrategy {
	/**
	 * Singleton access
	 */
	public static final PropertyAccessStrategy INSTANCE = new PropertyAccessStrategyFieldImpl();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PropertyAccess buildPropertyAccess(Class<?> containerJavaType, String propertyName, boolean setterRequired) {
		return new PropertyAccessFieldImpl( this, containerJavaType, propertyName );
	}
}
