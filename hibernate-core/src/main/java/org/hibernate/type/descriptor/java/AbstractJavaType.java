/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.java;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.internal.util.compare.ComparableComparator;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Abstract adapter for Java type descriptors.
 *
 * @apiNote This abstract descriptor implements BasicJavaType
 * because we currently only categorize "basic" JavaTypes,
 * as in the {@link jakarta.persistence.metamodel.Type.PersistenceType#BASIC}
 * sense
 *
 * @author Steve Ebersole
 */
public abstract class AbstractJavaType<T> implements BasicJavaType<T>, Serializable {
	private final Type type;
	private final MutabilityPlan<T> mutabilityPlan;
	private final Comparator<T> comparator;

	/**
	 * Initialize a type descriptor for the given type.  Assumed immutable.
	 *
	 * @param type The Java type.
	 *
	 * @see #AbstractJavaType(Type, MutabilityPlan)
	 */
	protected AbstractJavaType(Type type) {
		this( type, ImmutableMutabilityPlan.instance() );
	}

	/**
	 * Initialize a type descriptor for the given type.  Assumed immutable.
	 *
	 * @param type The Java type.
	 * @param mutabilityPlan The plan for handling mutability aspects of the java type.
	 */
	@SuppressWarnings("unchecked")
	protected AbstractJavaType(Type type, MutabilityPlan<T> mutabilityPlan) {
		this.type = type;
		this.mutabilityPlan = mutabilityPlan;
		this.comparator =
				type != null && Comparable.class.isAssignableFrom( getJavaTypeClass() )
						? ComparableComparator.INSTANCE
						: null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MutabilityPlan<T> getMutabilityPlan() {
		return mutabilityPlan;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Type getJavaType() {
		return type;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int extractHashCode(T value) {
		return value.hashCode();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean areEqual(T one, T another) {
		return Objects.equals( one, another );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Comparator<T> getComparator() {
		return comparator;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String extractLoggableRepresentation(T value) {
		return (value == null) ? "null" : value.toString();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected HibernateException unknownUnwrap(Class<?> conversionType) {
		throw new HibernateException(
				"Unknown unwrap conversion requested: " + type.getTypeName() + " to " + conversionType.getName()
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected HibernateException unknownWrap(Class<?> conversionType) {
		throw new HibernateException(
				"Unknown wrap conversion requested: " + conversionType.getName() + " to " + type.getTypeName()
		);
	}
}
