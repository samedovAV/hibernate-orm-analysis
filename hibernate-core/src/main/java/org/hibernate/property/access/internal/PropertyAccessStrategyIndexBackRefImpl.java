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
 * @author Gavin King
 * @author Steve Ebersole
 */
public class PropertyAccessStrategyIndexBackRefImpl implements PropertyAccessStrategy {
	private final String entityName;
	private final String propertyName;

	public PropertyAccessStrategyIndexBackRefImpl(String collectionRole, String entityName) {
		this.entityName = entityName;
		this.propertyName = collectionRole.substring( entityName.length() + 1 );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PropertyAccess buildPropertyAccess(Class<?> containerJavaType, String propertyName, boolean setterRequired) {
		return new PropertyAccessIndexBackRefImpl( this );
	}

	private static class PropertyAccessIndexBackRefImpl implements PropertyAccess {
		private final PropertyAccessStrategyIndexBackRefImpl strategy;
		private final GetterImpl getter;

		public PropertyAccessIndexBackRefImpl(PropertyAccessStrategyIndexBackRefImpl strategy) {
			this.strategy = strategy;
			this.getter = new GetterImpl( strategy.entityName, strategy.propertyName );
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
	}

	private static class GetterImpl implements Getter {
		private final String entityName;
		private final String propertyName;

		public GetterImpl(String entityName, String propertyName) {
			this.entityName = entityName;
			this.propertyName = propertyName;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Object get(Object owner) {
			return PropertyAccessStrategyBackRefImpl.UNKNOWN;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Object getForInsert(Object owner, Map<Object, Object> mergeMap, SharedSessionContractImplementor session) {
			return session.getPersistenceContextInternal().getIndexInOwner( entityName, propertyName, owner, mergeMap );
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
		public static final Setter INSTANCE = new SetterImpl();

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public void set(Object target, @Nullable Object value) {
			// this page intentionally left blank :)
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
