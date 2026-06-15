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
import org.hibernate.internal.util.NullnessUtil;
import org.hibernate.property.access.spi.Getter;
import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.property.access.spi.PropertyAccessException;
import org.hibernate.property.access.spi.PropertyAccessStrategy;
import org.hibernate.property.access.spi.Setter;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class ChainedPropertyAccessImpl implements PropertyAccess, Getter, Setter {

	private final PropertyAccess[] propertyAccesses;

	public ChainedPropertyAccessImpl(PropertyAccess... propertyAccesses) {
		this.propertyAccesses = propertyAccesses;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public PropertyAccessStrategy getPropertyAccessStrategy() {
		return propertyAccesses[0].getPropertyAccessStrategy();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Getter getGetter() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Setter getSetter() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public @Nullable Object get(Object owner) {
		@Nullable Object result = owner;
		for ( int i = 0; i < propertyAccesses.length; i++ ) {
			result = propertyAccesses[i].getGetter().get( NullnessUtil.castNonNull( result ) );
		}
		return result;
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public @Nullable Object getForInsert(Object owner, Map<Object, Object> mergeMap, SharedSessionContractImplementor session) {
		@Nullable Object result = owner;
		for ( int i = 0; i < propertyAccesses.length; i++ ) {
			if ( result == null ) {
				throw new PropertyAccessException( "Could not chain accessor because result of previous accessor was null" );
			}
			result = propertyAccesses[i].getGetter().getForInsert( result, mergeMap, session );
		}
		return result;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void set(Object target, @Nullable Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Class<?> getReturnTypeClass() {
		return propertyAccesses[propertyAccesses.length - 1].getGetter().getReturnTypeClass();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Type getReturnType() {
		return propertyAccesses[propertyAccesses.length - 1].getGetter().getReturnType();
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
