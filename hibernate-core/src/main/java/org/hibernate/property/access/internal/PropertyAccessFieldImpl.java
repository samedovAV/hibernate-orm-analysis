/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.property.access.internal;


import org.hibernate.property.access.spi.Getter;
import org.hibernate.property.access.spi.GetterFieldImpl;
import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.property.access.spi.PropertyAccessStrategy;
import org.hibernate.property.access.spi.Setter;
import org.hibernate.property.access.spi.SetterFieldImpl;

import static org.hibernate.internal.util.ReflectHelper.findField;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 * @author Gavin King
 */
public class PropertyAccessFieldImpl implements PropertyAccess {
	private final PropertyAccessStrategyFieldImpl strategy;
	private final Getter getter;
	private final Setter setter;

	public PropertyAccessFieldImpl(
			PropertyAccessStrategyFieldImpl strategy,
			Class<?> containerJavaType,
			final String propertyName) {
		this.strategy = strategy;

		final var field = findField( containerJavaType, propertyName );
		getter = new GetterFieldImpl( containerJavaType, propertyName, field );
		setter = new SetterFieldImpl( containerJavaType, propertyName, field );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PropertyAccessStrategy getPropertyAccessStrategy() {
		return strategy;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Getter getGetter() {
		return getter;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Setter getSetter() {
		return setter;
	}

}
