/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.java;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * A mutability plan for mutable arrays of immutable, non-primitive objects.
 * <p>
 * Since the elements themselves are immutable, the deep copy can be implemented with a shallow copy.
 *
 * @author Steve Ebersole
 */
public final class ImmutableObjectArrayMutabilityPlan<T> extends MutableMutabilityPlan<T[]> {
	@SuppressWarnings("rawtypes")
	private static final ImmutableObjectArrayMutabilityPlan INSTANCE = new ImmutableObjectArrayMutabilityPlan();

	@SuppressWarnings("unchecked") // Works for any T
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static <T> ImmutableObjectArrayMutabilityPlan<T> get() {
		return (ImmutableObjectArrayMutabilityPlan<T>) INSTANCE;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public T[] deepCopyNotNull(T[] value) {
		return value.clone();
	}
}
