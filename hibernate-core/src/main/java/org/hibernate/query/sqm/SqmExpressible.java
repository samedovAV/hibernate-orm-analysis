/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm;

import jakarta.annotation.Nullable;
import org.hibernate.query.sqm.tree.expression.SqmExpression;
import org.hibernate.query.sqm.tree.domain.SqmDomainType;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Anything in the application domain model that can be used in an
 * SQM query as an expression.
 *
 * @see SqmExpression#getNodeType
 *
 * @author Steve Ebersole
 */
public interface SqmExpressible<J> {
	/**
	 * The Java type descriptor for this expressible
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JavaType<J> getExpressibleJavaType();

	/**
	 * Usually the same as {@link #getExpressibleJavaType()}. But for types with
	 * {@linkplain org.hibernate.type.descriptor.converter.spi.BasicValueConverter
	 * value conversion}, the Java type of the converted value.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default JavaType<?> getRelationalJavaType() {
		return getExpressibleJavaType();
	}

	/**
	 * The name of the type. Usually, but not always, the name of a Java class.
	 *
	 * @see org.hibernate.metamodel.model.domain.DomainType#getTypeName()
	 * @see JavaType#getTypeName()
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default String getTypeName() {
		// default impl to handle the general case returning the Java type name
		final JavaType<J> expressibleJavaType = getExpressibleJavaType();
		return expressibleJavaType == null ? "unknown" : expressibleJavaType.getTypeName();
	}

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmDomainType<J> getSqmType();
}
