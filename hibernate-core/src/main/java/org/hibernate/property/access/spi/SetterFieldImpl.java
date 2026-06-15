/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.property.access.spi;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

import org.hibernate.Internal;
import org.hibernate.PropertyAccessException;

import jakarta.annotation.Nullable;

import static org.hibernate.internal.util.ReflectHelper.setterMethodOrNull;
import static org.hibernate.proxy.HibernateProxy.extractLazyInitializer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Field-based implementation of Setter
 *
 * @author Steve Ebersole
 */
@Internal
public class SetterFieldImpl implements Setter {
	private final Class<?> containerClass;
	private final String propertyName;
	private final Field field;
	private final @Nullable Method setterMethod;

	public SetterFieldImpl(Class<?> containerClass, String propertyName, Field field) {
		this.containerClass = containerClass;
		this.propertyName = propertyName;
		this.field = field;
		this.setterMethod = setterMethodOrNull( containerClass, propertyName, field.getType() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getContainerClass() {
		return containerClass;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getPropertyName() {
		return propertyName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Field getField() {
		return field;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void set(Object target, @Nullable Object value) {
		try {
			field.set( target, value );
		}
		catch (Exception e) {
			if ( value == null && field.getType().isPrimitive() ) {
				throw new PropertyAccessException(
						e,
						String.format(
								Locale.ROOT,
								"Null value was assigned to a property [%s.%s] of primitive type",
								containerClass,
								propertyName
						),
						true,
						containerClass,
						propertyName
				);
			}
			else {
				throw new PropertyAccessException(
						e,
						String.format(
								Locale.ROOT,
								"Could not set value of type [%s]",
								typeName( value )
						),
						true,
						containerClass,
						propertyName
				);
			}
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static String typeName(@Nullable Object value) {
		final var lazyInitializer = extractLazyInitializer( value );
		if ( lazyInitializer != null ) {
			return lazyInitializer.getEntityName();
		}
		else if ( value != null ) {
			return value.getClass().getTypeName();
		}
		else {
			return "<unknown>";
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable String getMethodName() {
		return setterMethod != null ? setterMethod.getName() : null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Method getMethod() {
		return setterMethod;
	}

}
