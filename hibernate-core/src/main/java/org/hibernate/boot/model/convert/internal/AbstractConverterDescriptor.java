/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.convert.internal;

import org.hibernate.boot.model.convert.spi.AutoApplicableConverterDescriptor;
import org.hibernate.boot.model.convert.spi.ConverterDescriptor;
import org.hibernate.boot.model.convert.spi.JpaAttributeConverterCreationContext;
import org.hibernate.type.descriptor.converter.internal.AttributeConverterBean;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.hibernate.resource.beans.spi.ManagedBean;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.lang.reflect.Type;

import static org.hibernate.internal.util.GenericsHelper.erasedType;
import static org.hibernate.internal.util.GenericsHelper.typeArguments;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
abstract class AbstractConverterDescriptor<X,Y> implements ConverterDescriptor<X,Y> {
	private final Class<? extends AttributeConverter<X,Y>> converterClass;

	private final Type domainType;
	private final Type jdbcType;

	private final AutoApplicableConverterDescriptor autoApplicableDescriptor;

	AbstractConverterDescriptor(
			Class<? extends AttributeConverter<X, Y>> converterClass,
			Boolean forceAutoApply) {
		this.converterClass = converterClass;
		final var converterParamTypes = typeArguments( AttributeConverter.class, converterClass );
		domainType = converterParamTypes[0];
		jdbcType = converterParamTypes[1];
		autoApplicableDescriptor = resolveAutoApplicableDescriptor( converterClass, forceAutoApply );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private AutoApplicableConverterDescriptor resolveAutoApplicableDescriptor(
			Class<? extends AttributeConverter<?,?>> converterClass,
			Boolean forceAutoApply) {
		return isAutoApply( converterClass, forceAutoApply )
				? new AutoApplicableConverterDescriptorStandardImpl( this )
				: AutoApplicableConverterDescriptorBypassedImpl.INSTANCE;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static boolean isAutoApply(Class<? extends AttributeConverter<?, ?>> converterClass, Boolean forceAutoApply) {
		if ( forceAutoApply != null ) {
			// if the caller explicitly specified whether to auto-apply, honor that
			return forceAutoApply;
		}
		else {
			// otherwise, look at the converter's @Converter annotation
			final var annotation = converterClass.getAnnotation( Converter.class );
			return annotation != null && annotation.autoApply();
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends AttributeConverter<X,Y>> getAttributeConverterClass() {
		return converterClass;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Type getDomainValueResolvedType() {
		return domainType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Type getRelationalValueResolvedType() {
		return jdbcType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AutoApplicableConverterDescriptor getAutoApplyDescriptor() {
		return autoApplicableDescriptor;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JpaAttributeConverter<X,Y> createJpaAttributeConverter(JpaAttributeConverterCreationContext context) {
		return new AttributeConverterBean<>(
				createManagedBean( context ),
				context.getJavaTypeRegistry().resolveDescriptor( converterClass ),
				getDomainClass(),
				getRelationalClass(),
				context
		);
	}

	@SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private Class<Y> getRelationalClass() {
		return (Class<Y>) erasedType( getRelationalValueResolvedType() );
	}

	@SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private Class<X> getDomainClass() {
		return (Class<X>) erasedType( getDomainValueResolvedType() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract ManagedBean<? extends AttributeConverter<X,Y>>
	createManagedBean(JpaAttributeConverterCreationContext context);
}
