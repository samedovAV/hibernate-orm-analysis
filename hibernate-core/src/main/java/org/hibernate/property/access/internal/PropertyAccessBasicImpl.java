/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.property.access.internal;

import org.hibernate.property.access.spi.Getter;
import org.hibernate.property.access.spi.GetterMethodImpl;
import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.property.access.spi.PropertyAccessStrategy;
import org.hibernate.property.access.spi.Setter;
import org.hibernate.property.access.spi.SetterMethodImpl;


import jakarta.annotation.Nullable;

import static org.hibernate.internal.util.ReflectHelper.findGetterMethod;
import static org.hibernate.internal.util.ReflectHelper.findSetterMethod;
import static org.hibernate.internal.util.ReflectHelper.setterMethodOrNull;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * {@link PropertyAccess} for accessing the wrapped property via get/set pair, which may be nonpublic.
 *
 * @author Steve Ebersole
 *
 * @see PropertyAccessStrategyBasicImpl
 */
public class PropertyAccessBasicImpl implements PropertyAccess {

	private final PropertyAccessStrategyBasicImpl strategy;
	private final GetterMethodImpl getter;
	private final @Nullable SetterMethodImpl setter;

	public PropertyAccessBasicImpl(
			PropertyAccessStrategyBasicImpl strategy,
			Class<?> containerJavaType,
			final String propertyName,
			boolean setterRequired) {
		this.strategy = strategy;

		final var getterMethod = findGetterMethod( containerJavaType, propertyName );
		getter = new GetterMethodImpl( containerJavaType, propertyName, getterMethod );

		final var setterMethod = setterRequired
				? findSetterMethod( containerJavaType, propertyName, getterMethod.getReturnType() )
				: setterMethodOrNull( containerJavaType, propertyName, getterMethod.getReturnType() );
		setter = setterMethod != null
				? new SetterMethodImpl( containerJavaType, propertyName, setterMethod )
				: null;
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
	public @Nullable Setter getSetter() {
		return setter;
	}
}
