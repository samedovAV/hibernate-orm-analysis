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
 * {@link PropertyAccess} for handling non-aggregated composites.
 *
 * @implNote We actually use a singleton for the {@link Setter}; we cannot for the getter mainly
 *           because we need to differentiate {@link Getter#getReturnTypeClass()}. Ultimately I'd
 *           prefer to model that "common information" on {@link PropertyAccess} itself.
 *
 * @author Gavin King
 * @author Steve Ebersole
 */
public class PropertyAccessEmbeddedImpl implements PropertyAccess {
	private final PropertyAccessStrategyEmbeddedImpl strategy;
	private final GetterImpl getter;

	public PropertyAccessEmbeddedImpl(
			PropertyAccessStrategyEmbeddedImpl strategy,
			Class<?> containerType,
			@SuppressWarnings("UnusedParameters")
			String propertyName) {
		this.strategy = strategy;
		this.getter = new GetterImpl( containerType );
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
		return SetterImpl.INSTANCE;
	}

	private static class GetterImpl implements Getter {
		private final Class<?> containerType;

		public GetterImpl(Class<?> containerType) {
			this.containerType = containerType;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Object get(Object owner) {
			return owner;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Object getForInsert(Object owner, Map<Object, Object> mergeMap, SharedSessionContractImplementor session) {
			return owner;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Class<?> getReturnTypeClass() {
			return containerType;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Type getReturnType() {
			return containerType;
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
		 * Singleton access - we can actually use a singleton for the setter
		 */
		public static final SetterImpl INSTANCE = new SetterImpl();

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public void set(Object target, @Nullable Object value) {
			// nothing to do
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
