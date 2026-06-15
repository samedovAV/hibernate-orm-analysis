/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.internal;

import jakarta.annotation.Nonnull;
import org.hibernate.metamodel.mapping.DiscriminatorConverter;
import org.hibernate.metamodel.mapping.DiscriminatorType;
import org.hibernate.type.BasicType;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.internal.ConvertedBasicTypeImpl;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Specialization of ConvertedBasicTypeImpl to expose access to the
 * {@link #underlyingJdbcMapping} of this discriminator - the bit that handles
 * the relationship between the relational JavaType and the JdbcType
 *
 * @author Steve Ebersole
 */
public class DiscriminatorTypeImpl<O> extends ConvertedBasicTypeImpl<O> implements DiscriminatorType<O> {
	private final JavaType<O> domainJavaType;
	private final BasicType<?> underlyingJdbcMapping;

	public DiscriminatorTypeImpl(
			BasicType<?> underlyingJdbcMapping,
			DiscriminatorConverter<O,?> discriminatorValueConverter) {
		super(
				discriminatorValueConverter.getDiscriminatorName(),
				"Discriminator type " + discriminatorValueConverter.getDiscriminatorName(),
				underlyingJdbcMapping.getJdbcType(),
				discriminatorValueConverter
		);

		assert underlyingJdbcMapping.getJdbcJavaType() == discriminatorValueConverter.getRelationalJavaType();
		this.domainJavaType = discriminatorValueConverter.getDomainJavaType();
		this.underlyingJdbcMapping = underlyingJdbcMapping;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public BasicType<?> getUnderlyingJdbcMapping() {
		return underlyingJdbcMapping;
	}

	@Override @SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public DiscriminatorConverter<O,?> getValueConverter() {
		return (DiscriminatorConverter<O,?>) super.getValueConverter();
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<O> getJavaType() {
		return domainJavaType.getJavaTypeClass();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean canDoExtraction() {
		return underlyingJdbcMapping.canDoExtraction();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<O> getExpressibleJavaType() {
		return domainJavaType;
	}
}
