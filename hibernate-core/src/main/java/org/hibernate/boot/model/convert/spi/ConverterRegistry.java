/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.convert.spi;

import org.hibernate.Incubating;

import jakarta.persistence.AttributeConverter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A registry for JPA {@linkplain AttributeConverter converters}.
 *
 * @author Gavin King
 * @see AttributeConverter
 * @since 6.2
 */
@Incubating
public interface ConverterRegistry {
	/**
	 * Apply the descriptor for an {@link AttributeConverter}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addAttributeConverter(ConverterDescriptor<?,?> descriptor);

	/**
	 * Apply an {@link AttributeConverter}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addAttributeConverter(Class<? extends AttributeConverter<?,?>> converterClass);

	/**
	 * Apply an {@link AttributeConverter} that may be overridden by competing converters
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addOverridableConverter(Class<? extends AttributeConverter<?,?>> converterClass);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addRegisteredConversion(RegisteredConversion conversion);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ConverterAutoApplyHandler getAttributeConverterAutoApplyHandler();
}
