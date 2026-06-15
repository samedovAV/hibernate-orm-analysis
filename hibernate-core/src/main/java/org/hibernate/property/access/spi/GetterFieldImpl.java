/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.property.access.spi;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;

import org.hibernate.Internal;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import jakarta.annotation.Nullable;

import static org.hibernate.internal.util.ReflectHelper.findGetterMethodForFieldAccess;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Field-based implementation of Getter
 *
 * @author Steve Ebersole
 */
@Internal
public class GetterFieldImpl implements Getter {
	private final Class<?> containerClass;
	private final String propertyName;
	private final Field field;
	private final @Nullable Method getterMethod;

	public GetterFieldImpl(Class<?> containerClass, String propertyName, Field field) {
		this ( containerClass, propertyName, field, findGetterMethodForFieldAccess( field, propertyName ) );
	}

	GetterFieldImpl(Class<?> containerClass, String propertyName, Field field, Method getterMethod) {
		this.containerClass = containerClass;
		this.propertyName = propertyName;
		this.field = field;
		this.getterMethod = getterMethod;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public @Nullable Object get(Object owner) {
		try {
			return field.get( owner );
		}
		catch (Exception e) {
			throw new PropertyAccessException(
					String.format(
							Locale.ROOT,
							"Error accessing field [%s] by reflection for persistent property [%s#%s] : %s",
							field.toGenericString(),
							containerClass.getName(),
							propertyName,
							owner
					),
					e
			);
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Object getForInsert(Object owner, Map<Object, Object> mergeMap, SharedSessionContractImplementor session) {
		return get( owner );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getReturnTypeClass() {
		return field.getType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Type getReturnType() {
		return field.getGenericType();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Field getField() {
		return field;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Member getMember() {
		return getField();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable String getMethodName() {
		return getterMethod != null ? getterMethod.getName() : null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Method getMethod() {
		return getterMethod;
	}

}
