/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.enhance.spi;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

import org.hibernate.bytecode.enhance.spi.interceptor.BytecodeLazyAttributeInterceptor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contract for controlling how lazy properties get initialized.
 *
 * @author Gavin King
 */
public interface LazyPropertyInitializer {

	/**
	 * Marker value for uninitialized properties.
	 */
	Serializable UNFETCHED_PROPERTY = new Serializable() {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String toString() {
			return "<lazy>";
		}

		@Serial
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Object readResolve() {
			return UNFETCHED_PROPERTY;
		}
	};

	/**
	 * @deprecated Prefer the form of these methods defined on
	 * {@link BytecodeLazyAttributeInterceptor} instead
	 */
	@Deprecated
	interface InterceptorImplementor {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		default Set<String> getInitializedLazyAttributeNames() {
			return Collections.emptySet();
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		default void attributeInitialized(String name) {
		}
	}

	/**
	 * Initialize the property, and return its new value.
	 *
	 * @param fieldName The name of the field being initialized
	 * @param entity The entity on which the initialization is occurring
	 * @param session The session from which the initialization originated.
	 *
	 * @return ?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object initializeLazyProperty(String fieldName, Object entity, SharedSessionContractImplementor session);

}
