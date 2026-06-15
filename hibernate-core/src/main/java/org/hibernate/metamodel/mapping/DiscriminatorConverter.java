/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import org.hibernate.metamodel.RepresentationMode;
import org.hibernate.type.descriptor.converter.spi.BasicValueConverter;
import org.hibernate.type.descriptor.java.JavaType;

import java.util.function.Consumer;
import java.util.function.Function;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 * @author Gavin King
 */
public abstract class DiscriminatorConverter<O,R> implements BasicValueConverter<O,R> {
	private final String discriminatorName;
	private final JavaType<O> domainJavaType;
	private final JavaType<R> relationalJavaType;

	public DiscriminatorConverter(
			String discriminatorName,
			JavaType<O> domainJavaType,
			JavaType<R> relationalJavaType) {
		this.discriminatorName = discriminatorName;
		this.domainJavaType = domainJavaType;
		this.relationalJavaType = relationalJavaType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDiscriminatorName() {
		return discriminatorName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<O> getDomainJavaType() {
		return domainJavaType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<R> getRelationalJavaType() {
		return relationalJavaType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiscriminatorValueDetails getDetailsForRelationalForm(R relationalForm) {
		return getDetailsForDiscriminatorValue( relationalForm );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public O toDomainValue(R relationalForm) {
		assert relationalForm == null || relationalJavaType.isInstance( relationalForm );
		final var matchingValueDetails = getDetailsForRelationalForm( relationalForm );
		if ( matchingValueDetails == null ) {
			throw new IllegalStateException( "Could not resolve discriminator value" );
		}

		final var indicatedEntity = matchingValueDetails.getIndicatedEntity();
		//noinspection unchecked
		return indicatedEntity.getRepresentationStrategy().getMode() == RepresentationMode.POJO
			&& indicatedEntity.getEntityName().equals( indicatedEntity.getJavaType().getJavaTypeClass().getName() )
				? (O) indicatedEntity.getJavaType().getJavaTypeClass()
				: (O) indicatedEntity.getEntityName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public R toRelationalValue(O domainForm) {
		final String entityName = getEntityName( domainForm );
		if ( entityName == null ) {
			return null;
		}
		else {
			final var discriminatorValueDetails = getDetailsForEntityName( entityName );
			//noinspection unchecked
			Object value = discriminatorValueDetails.getValue();
			return (R) value;
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract String getEntityName(O domainForm);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public abstract DiscriminatorValueDetails getDetailsForDiscriminatorValue(Object relationalValue);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public abstract DiscriminatorValueDetails getDetailsForEntityName(String entityName);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "DiscriminatorConverter(" + discriminatorName + ")";
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public abstract void forEachValueDetail(Consumer<DiscriminatorValueDetails> consumer);

	/**
	 * Find and return the first DiscriminatorValueDetails which matches the given {@code handler}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public abstract <X> X fromValueDetails(Function<DiscriminatorValueDetails,X> handler);
}
