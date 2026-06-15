/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.internal;

import java.lang.reflect.Member;

import org.hibernate.mapping.Property;
import org.hibernate.metamodel.AttributeClassification;
import org.hibernate.metamodel.model.domain.ManagedDomainType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Basic contract for describing an attribute.
 *
 * @param <X> The attribute owner type
 * @param <Y> The attribute type.
 */
public interface AttributeMetadata<X, Y> {
	/**
	 * Retrieve the name of the attribute
	 *
	 * @return The attribute name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();

	/**
	 * Retrieve the member defining the attribute
	 *
	 * @return The attribute member
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Member getMember();

	/**
	 * Retrieve the attribute java type.
	 *
	 * @return The java type of the attribute.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<Y> getJavaType();

	/**
	 * Get the classification for this attribute
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AttributeClassification getAttributeClassification();

	/**
	 * Retrieve the attribute owner's metamodel information
	 *
	 * @return The metamodel information for the attribute owner
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ManagedDomainType<X> getOwnerType();

	/**
	 * Retrieve the Hibernate property mapping related to this attribute.
	 *
	 * @return The Hibernate property mapping
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Property getPropertyMapping();

	/**
	 * Is the attribute plural (a collection)?
	 *
	 * @return True if it is plural, false otherwise.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isPlural();
}
