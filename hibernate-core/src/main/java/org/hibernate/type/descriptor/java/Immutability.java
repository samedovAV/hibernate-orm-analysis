/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.java;

import java.io.Serializable;

import org.hibernate.SharedSessionContract;
import org.hibernate.annotations.Mutability;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Object-typed form of {@link ImmutableMutabilityPlan} for easier use
 * with {@link Mutability} for users
 *
 * @see org.hibernate.annotations.Immutable
 *
 * @author Steve Ebersole
 */
public class Immutability implements MutabilityPlan<Object> {
	/**
	 * Singleton access
	 *
	 * @deprecated in favor of {@link #instance()}
	 */
	@Deprecated( forRemoval = true )
	public static final Immutability INSTANCE = new Immutability();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static <X> MutabilityPlan<X> instance() {
		//noinspection unchecked
		return (MutabilityPlan<X>) INSTANCE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isMutable() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object deepCopy(Object value) {
		return value;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Serializable disassemble(Object value, SharedSessionContract session) {
		return (Serializable) value;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object assemble(Serializable cached, SharedSessionContract session) {
		return cached;
	}
}
