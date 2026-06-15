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
 * @author Michael Bartmann
 * @author Gavin King
 * @author Steve Ebersole
 */
public class PropertyAccessStrategyNoopImpl implements PropertyAccessStrategy {
	/**
	 * Singleton access
	 */
	public static final PropertyAccessStrategy INSTANCE = new PropertyAccessStrategyNoopImpl();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PropertyAccess buildPropertyAccess(Class<?> containerJavaType, String propertyName, boolean setterRequired) {
		return PropertyAccessNoopImpl.INSTANCE;
	}

	private static class PropertyAccessNoopImpl implements PropertyAccess {
		/**
		 * Singleton access
		 */
		public static final PropertyAccessNoopImpl INSTANCE = new PropertyAccessNoopImpl();

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public PropertyAccessStrategy getPropertyAccessStrategy() {
			return PropertyAccessStrategyNoopImpl.INSTANCE;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Getter getGetter() {
			return GetterImpl.INSTANCE;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Setter getSetter() {
			return SetterImpl.INSTANCE;
		}
	}

	private static class GetterImpl implements Getter {
		/**
		 * Singleton access
		 */
		public static final GetterImpl INSTANCE = new GetterImpl();

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public @Nullable Object get(Object owner) {
			return null;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public @Nullable Object getForInsert(Object owner, Map<Object, Object> mergeMap, SharedSessionContractImplementor session) {
			return null;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Class<?> getReturnTypeClass() {
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

	private static class SetterImpl implements Setter {
		/**
		 * Singleton access
		 */
		public static final SetterImpl INSTANCE = new SetterImpl();

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public void set(Object target, @Nullable Object value) {
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
