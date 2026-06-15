/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.converter.spi;

import jakarta.persistence.AttributeConverter;

import org.hibernate.Incubating;
import org.hibernate.resource.beans.spi.ManagedBean;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Adapts a JPA-standard {@link AttributeConverter} to the native
 * {@link BasicValueConverter}.
 *
 * @param <O> The entity attribute type
 * @param <R> The converted type
 *
 * @author Steve Ebersole
 */
@Incubating
public interface JpaAttributeConverter<O,R> extends BasicValueConverter<O,R> {
	/**
	 * A {@link JavaType} representing the JPA {@link AttributeConverter}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JavaType<? extends AttributeConverter<O,R>> getConverterJavaType();

	/**
	 * A {@link ManagedBean} representing the JPA {@link AttributeConverter},
	 * in the case that the converter is a managed bean, e.g., a CDI bean.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ManagedBean<? extends AttributeConverter<O,R>> getConverterBean();
}
