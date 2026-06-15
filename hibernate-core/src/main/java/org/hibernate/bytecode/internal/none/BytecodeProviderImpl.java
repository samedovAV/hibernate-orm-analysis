/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.internal.none;

import java.util.Map;

import jakarta.annotation.Nonnull;
import org.hibernate.HibernateException;
import org.hibernate.bytecode.enhance.spi.EnhancementContext;
import org.hibernate.bytecode.enhance.spi.Enhancer;
import org.hibernate.bytecode.spi.BytecodeProvider;
import org.hibernate.bytecode.spi.ProxyFactoryFactory;
import org.hibernate.bytecode.spi.ReflectionOptimizer;
import org.hibernate.property.access.spi.PropertyAccess;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * This BytecodeProvider represents the "no-op" enhancer; mostly useful
 * as an optimisation when not needing any byte code optimisation applied,
 * for example when the entities have been enhanced at compile time.
 * Choosing this BytecodeProvider allows to exclude the bytecode enhancement
 * libraries from the runtime classpath.
 *
 * @since 5.4
 */
public final class BytecodeProviderImpl implements BytecodeProvider {

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ProxyFactoryFactory getProxyFactoryFactory() {
		return new NoProxyFactoryFactory();
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ReflectionOptimizer getReflectionOptimizer(
			@Nonnull Class<?> clazz,
			@Nonnull String[] getterNames,
			@Nonnull String[] setterNames,
			@Nonnull Class<?>[] types) {
		throw new HibernateException( "Using the ReflectionOptimizer is not possible when the configured BytecodeProvider is 'none'. Use a different BytecodeProvider" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable ReflectionOptimizer getReflectionOptimizer(@Nonnull Class<?> clazz, @Nonnull Map<String, PropertyAccess> propertyAccessMap) {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Enhancer getEnhancer(@Nonnull EnhancementContext enhancementContext) {
		return null;
	}
}
