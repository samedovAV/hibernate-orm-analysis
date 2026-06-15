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
import org.hibernate.property.access.spi.Getter;
import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.property.access.spi.PropertyAccessStrategy;
import org.hibernate.property.access.spi.Setter;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * {@link PropertyAccess} implementation that deals with an underlying {@code Map}
 * as the container, using {@link Map#get} and {@link Map#put}.
 *
 * @author Steve Ebersole
 * @author Gavin King
 */
public class PropertyAccessMapImpl implements PropertyAccess {
	private final Getter getter;
	private final Setter setter;
	private final PropertyAccessStrategyMapImpl strategy;

	public PropertyAccessMapImpl(PropertyAccessStrategyMapImpl strategy, final String propertyName) {
		this.strategy = strategy;
		this.getter = new GetterImpl( propertyName );
		this.setter = new SetterImpl( propertyName );
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

	public static class GetterImpl implements Getter {
		private final String propertyName;

		public GetterImpl(String propertyName) {
			this.propertyName = propertyName;
		}

		@Override
		@SuppressWarnings("rawtypes")
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public @Nullable Object get(Object owner) {
			return ( (Map) owner ).get( propertyName );
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public @Nullable Object getForInsert(Object owner, Map<Object, Object> mergeMap, SharedSessionContractImplementor session) {
			return get( owner );
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Class<?> getReturnTypeClass() {
			// we just don't know...
			return Object.class;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Type getReturnType() {
			return Object.class;
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

	public static class SetterImpl implements Setter {
		private final String propertyName;

		public SetterImpl(String propertyName) {
			this.propertyName = propertyName;
		}

		@Override
		@SuppressWarnings({"unchecked", "rawtypes"})
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public void set(Object target, @Nullable Object value) {
			( (Map) target ).put( propertyName, value );
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
}
