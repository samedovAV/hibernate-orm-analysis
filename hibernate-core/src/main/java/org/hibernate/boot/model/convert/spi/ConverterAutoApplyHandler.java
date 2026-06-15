/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.convert.spi;

import org.hibernate.Incubating;
import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.models.spi.MemberDetails;

import jakarta.persistence.Converter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Manages resolution of auto-applied {@link jakarta.persistence.AttributeConverter}
 * references for specific mappings
 *
 * @see Converter#autoApply()
 *
 * @author Steve Ebersole
 */
@Incubating
public interface ConverterAutoApplyHandler {
	/**
	 * Resolve the auto-applied converter to be applied to a basic attribute described
	 * by the passed property descriptor.  {@code null} indicates that no auto-applied
	 * converter matched
	 *
	 * @param attributeMember The HCANN descriptor for the basic attribute
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ConverterDescriptor<?,?> findAutoApplyConverterForAttribute(MemberDetails attributeMember, MetadataBuildingContext context);

	/**
	 * Resolve the auto-applied converter to be applied to the elements of a plural attribute
	 * described by the passed property descriptor.  {@code null} indicates that no auto-applied
	 * converter matched
	 *
	 * @param attributeMember The HCANN descriptor for the plural attribute
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ConverterDescriptor<?,?> findAutoApplyConverterForCollectionElement(MemberDetails attributeMember, MetadataBuildingContext context);

	/**
	 * Resolve the auto-applied converter to be applied to the keys of a plural Map attribute
	 * described by the passed property descriptor.  {@code null} indicates that no auto-applied
	 * converter matched
	 *
	 * @param attributeMember The HCANN descriptor for the Map-typed plural attribute
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ConverterDescriptor<?,?> findAutoApplyConverterForMapKey(MemberDetails attributeMember, MetadataBuildingContext context);
}
