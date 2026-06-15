/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.convert.spi;

import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;

import jakarta.persistence.AttributeConverter;

import java.lang.reflect.Type;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Boot-time descriptor of a JPA {@linkplain AttributeConverter converter}.
 *
 * @author Steve Ebersole
 *
 * @param <X> The entity attribute type
 * @param <Y> The converted type
 *
 * @see AttributeConverter
 * @see ConverterRegistry
 */
public interface ConverterDescriptor<X,Y> {
	String TYPE_NAME_PREFIX = "converted::";

	/**
	 * The class of the JPA {@link AttributeConverter}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<? extends AttributeConverter<X,Y>> getAttributeConverterClass();

	/**
	 * The resolved Classmate type descriptor for the conversion's domain type
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Type getDomainValueResolvedType();

	/**
	 * The resolved Classmate type descriptor for the conversion's relational type
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Type getRelationalValueResolvedType();

	/**
	 * Get the auto-apply checker for this converter.
	 * <p>
	 * Should never return {@code null}. If the converter is not auto-applied, return
	 * {@link org.hibernate.boot.model.convert.internal.AutoApplicableConverterDescriptorBypassedImpl#INSTANCE}
	 * instead.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AutoApplicableConverterDescriptor getAutoApplyDescriptor();

	/**
	 * Factory for the runtime representation of the converter
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaAttributeConverter<X,Y> createJpaAttributeConverter(JpaAttributeConverterCreationContext context);

	/**
	 * Can this converter be overridden by other competing converters?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean overrideable() {
		return false;
	}
}
