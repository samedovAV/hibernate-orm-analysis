/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.internal;

import java.io.Serializable;
import java.util.Comparator;

import org.hibernate.SharedSessionContract;
import org.hibernate.annotations.Immutable;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.java.MutabilityPlan;
import org.hibernate.type.descriptor.java.MutabilityPlanExposer;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.JdbcTypeIndicators;
import org.hibernate.usertype.CompositeUserType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 *
 * @author Christian Beikov
 */
public class CompositeUserTypeJavaTypeWrapper<J> implements JavaType<J> {
	protected final CompositeUserType<J> userType;
	private final MutabilityPlan<J> mutabilityPlan;

	private final Comparator<J> comparator;

	public CompositeUserTypeJavaTypeWrapper(CompositeUserType<J> userType) {
		this.userType = userType;

		MutabilityPlan<J> resolvedMutabilityPlan = null;

		if ( userType instanceof MutabilityPlanExposer ) {
			//noinspection unchecked
			resolvedMutabilityPlan = ( (MutabilityPlanExposer<J>) userType ).getExposedMutabilityPlan();
		}

		if ( resolvedMutabilityPlan == null ) {
			final Class<J> jClass = userType.returnedClass();
			if ( jClass != null ) {
				if ( jClass.getAnnotation( Immutable.class ) != null ) {
					resolvedMutabilityPlan = ImmutableMutabilityPlan.instance();
				}
			}
		}

		if ( resolvedMutabilityPlan == null ) {
			resolvedMutabilityPlan = new MutabilityPlanWrapper<>( userType );
		}

		this.mutabilityPlan = resolvedMutabilityPlan;

		if ( userType instanceof Comparator ) {
			//noinspection unchecked
			this.comparator = ( (Comparator<J>) userType );
		}
		else {
			this.comparator = this::compare;
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private int compare(J first, J second) {
		if ( userType.equals( first, second ) ) {
			return 0;
		}
		return Comparator.<J, Integer>comparing( userType::hashCode ).compare( first, second );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MutabilityPlan<J> getMutabilityPlan() {
		return mutabilityPlan;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcType getRecommendedJdbcType(JdbcTypeIndicators context) {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Comparator<J> getComparator() {
		return comparator;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int extractHashCode(J value) {
		return userType.hashCode(value );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean areEqual(J one, J another) {
		return userType.equals( one, another );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public J fromString(CharSequence string) {
		throw new UnsupportedOperationException( "No support for parsing UserType values from String: " + userType );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> X unwrap(J value, Class<X> type, WrapperOptions options) {
		assert value == null || userType.returnedClass().isInstance( value );
		return type.cast( value );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> J wrap(X value, WrapperOptions options) {
//		assert value == null || userType.returnedClass().isInstance( value );
		//noinspection unchecked
		return (J) value;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<J> getJavaTypeClass() {
		return userType.returnedClass();
	}

	public static class MutabilityPlanWrapper<J> implements MutabilityPlan<J> {
		private final CompositeUserType<J> userType;

		public MutabilityPlanWrapper(CompositeUserType<J> userType) {
			this.userType = userType;
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public boolean isMutable() {
			return userType.isMutable();
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public J deepCopy(J value) {
			return userType.deepCopy( value );
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public Serializable disassemble(J value, SharedSessionContract session) {
			return userType.disassemble( value );
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public J assemble(Serializable cached, SharedSessionContract session) {
			return userType.assemble( cached, null );
		}
	}
}
