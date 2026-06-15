/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import org.hibernate.metamodel.model.domain.BasicDomainType;
import org.hibernate.type.BasicType;
import org.hibernate.type.ConvertedBasicType;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Union of {@link ConvertedBasicType} and {@link BasicDomainType} capabilities.
 *
 * @implNote We need the {@link BasicDomainType} aspect for handling in SQM trees.
 *
 * @param <O> The Java type of the domain form of the discriminator.
 *
 * @author Steve Ebersole
 */
public interface DiscriminatorType<O> extends ConvertedBasicType<O>, BasicDomainType<O> {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiscriminatorConverter<O, ?> getValueConverter();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	BasicType<?> getUnderlyingJdbcMapping();

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default JavaType<O> getJavaTypeDescriptor() {
		return ConvertedBasicType.super.getJavaTypeDescriptor();
	}
}
