/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.java;

import java.io.Serializable;

import org.hibernate.SharedSessionContract;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Mutability plan for mutable objects
 *
 * @author Steve Ebersole
 */
public abstract class MutableMutabilityPlan<T> implements MutabilityPlan<T> {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static <T> MutableMutabilityPlan<T> instance() {
		//noinspection unchecked
		return INSTANCE;
	}

	public static final MutableMutabilityPlan INSTANCE = new MutableMutabilityPlan<>() {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		protected Object deepCopyNotNull(Object value) {
			return value;
		}
	};

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isMutable() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Serializable disassemble(T value, SharedSessionContract session) {
		return (Serializable) deepCopy( value );
	}

	@Override
	@SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public T assemble(Serializable cached, SharedSessionContract session) {
		return deepCopy( (T) cached );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final T deepCopy(T value) {
		return value == null ? null : deepCopyNotNull( value );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract T deepCopyNotNull(T value);
}
