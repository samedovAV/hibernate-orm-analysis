/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.property.access.internal;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.internal.util.ReflectHelper;
import org.hibernate.property.access.spi.Getter;
import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.property.access.spi.PropertyAccessStrategy;
import org.hibernate.property.access.spi.Setter;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * {@link PropertyAccess} for accessing the wrapped property via get/set pair, which may be nonpublic.
 *
 * @author Steve Ebersole
 *
 * @see PropertyAccessStrategyBasicImpl
 */
public class PropertyAccessCompositeUserTypeImpl implements PropertyAccess, Getter {

	private final PropertyAccessStrategyCompositeUserTypeImpl strategy;
	private final int propertyIndex;

	public PropertyAccessCompositeUserTypeImpl(PropertyAccessStrategyCompositeUserTypeImpl strategy, String property) {
		this.strategy = strategy;
		this.propertyIndex = strategy.sortedPropertyNames.indexOf( property );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PropertyAccessStrategy getPropertyAccessStrategy() {
		return strategy;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Getter getGetter() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Setter getSetter() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Object get(Object owner) {
		return strategy.compositeUserType.getPropertyValue( owner, propertyIndex );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Object getForInsert(Object owner, Map<Object, Object> mergeMap, SharedSessionContractImplementor session) {
		return get( owner );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getReturnTypeClass() {
		return ReflectHelper.getClass( strategy.sortedPropertyTypes.get(propertyIndex) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Type getReturnType() {
		return strategy.sortedPropertyTypes.get(propertyIndex);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Member getMember() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable String getMethodName() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Method getMethod() {
		return null;
	}
}
