/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.enhance.internal.bytebuddy;

import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.pool.TypePool;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A simple cache provider that allows overriding the resolution for the class that is currently being enhanced.
 */
final class EnhancerCacheProvider extends TypePool.CacheProvider.Simple {

	private final ThreadLocal<EnhancementState> enhancementState = new ThreadLocal<>();

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TypePool.Resolution find(final String name) {
		final var enhancementState = getEnhancementState();
		return enhancementState != null && enhancementState.getClassName().equals( name )
				? enhancementState.getTypePoolResolution()
				: super.find( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EnhancementState getEnhancementState() {
		return enhancementState.get();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setEnhancementState(EnhancementState state) {
		enhancementState.set( state );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void removeEnhancementState() {
		enhancementState.remove();
	}

	static final class EnhancementState {
		private final String className;
		private final ClassFileLocator.Resolution classFileResolution;
		private TypePool.Resolution typePoolResolution;

		public EnhancementState(String className, ClassFileLocator.Resolution classFileResolution) {
			this.className = className;
			this.classFileResolution = classFileResolution;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getClassName() {
			return className;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ClassFileLocator.Resolution getClassFileResolution() {
			return classFileResolution;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public TypePool.Resolution getTypePoolResolution() {
			return typePoolResolution;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public void setTypePoolResolution(TypePool.Resolution typePoolResolution) {
			this.typePoolResolution = typePoolResolution;
		}
	}
}
