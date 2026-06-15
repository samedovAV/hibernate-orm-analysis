/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.java.spi;

import java.lang.reflect.Type;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractJavaType;
import org.hibernate.type.descriptor.java.MutabilityPlan;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.JdbcTypeIndicators;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * {@link AbstractJavaType} adapter for cases where we do not know a proper
 * {@link org.hibernate.type.descriptor.java.JavaType} for a given Java type.
 */
public final class UnknownBasicJavaType<T> extends AbstractJavaType<T> {
	private final String typeName;

	public UnknownBasicJavaType(Class<T> type) {
		this( type, type.getTypeName() );
	}

	public UnknownBasicJavaType(Class<T> type, String typeName) {
		super( type );
		this.typeName = typeName;
	}

	public UnknownBasicJavaType(Class<T> type, MutabilityPlan<T> mutabilityPlan) {
		super( type, mutabilityPlan );
		this.typeName = type.getTypeName();
	}

	public UnknownBasicJavaType(Type type, MutabilityPlan<T> mutabilityPlan) {
		super( type, mutabilityPlan );
		this.typeName = type.getTypeName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTypeName() {
		return typeName;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Type getJavaType() {
		final Type type = super.getJavaType();
		if ( type == null ) {
			throw new UnsupportedOperationException( "Unloadable Java type: " + typeName );
		}
		else {
			return type;
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcType getRecommendedJdbcType(JdbcTypeIndicators context) {
		throw new JdbcTypeRecommendationException(
				"Could not determine recommended JdbcType for Java type '" + getTypeName() + "'"
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> X unwrap(T value, Class<X> type, WrapperOptions options) {
		if ( type.isAssignableFrom( getJavaTypeClass() ) ) {
			return type.cast( value );
		}
		throw new UnsupportedOperationException(
				"Unwrap strategy not known for this Java type: " + getTypeName()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> T wrap(X value, WrapperOptions options) {
		final var javaTypeClass = getJavaTypeClass();
		if ( javaTypeClass.isInstance( value ) ) {
			return javaTypeClass.cast( value );
		}
		throw new UnsupportedOperationException(
				"Wrap strategy not known for this Java type: " + getTypeName()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "BasicJavaType(" + getTypeName() + ")";
	}
}
