/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.convert.internal;

import org.hibernate.boot.model.convert.spi.JpaAttributeConverterCreationContext;
import org.hibernate.internal.util.ReflectHelper;
import org.hibernate.resource.beans.spi.ManagedBean;
import org.hibernate.resource.beans.spi.ProvidedInstanceManagedBeanImpl;

import jakarta.persistence.AttributeConverter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * ConverterDescriptor implementation for cases where we are handed
 * the AttributeConverter instance to use.
 *
 * @author Steve Ebersole
 */
class InstanceBasedConverterDescriptor<X,Y> extends AbstractConverterDescriptor<X,Y> {
	private final AttributeConverter<X,Y> converterInstance;

	InstanceBasedConverterDescriptor(
			AttributeConverter<X,Y> converterInstance,
			Boolean forceAutoApply) {
		super( ReflectHelper.getClass( converterInstance ), forceAutoApply );
		this.converterInstance = converterInstance;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected ManagedBean<? extends AttributeConverter<X,Y>>
	createManagedBean(JpaAttributeConverterCreationContext context) {
		return new ProvidedInstanceManagedBeanImpl<>( converterInstance );
	}

}
