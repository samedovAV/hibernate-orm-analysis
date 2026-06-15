/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.spi;

import org.hibernate.MappingException;
import org.hibernate.models.spi.ClassDetails;
import org.hibernate.models.spi.MemberDetails;
import org.hibernate.models.spi.TypeDetails;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Details about an attribute as we process the {@linkplain org.hibernate.mapping boot model}.
 */
public interface PropertyData {

	/**
	 * @return default member access (whether field or property)
	 * @throws MappingException No getter or field found or wrong JavaBean spec usage
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AccessType getDefaultAccess();

	/**
	 * @return property name
	 * @throws MappingException No getter or field found or wrong JavaBean spec usage
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getPropertyName() throws MappingException;

	/**
	 * Returns the returned class itself or the element type if an array
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TypeDetails getClassOrElementType() throws MappingException;

	/**
	 * Returns the returned class itself or the element type if an array or collection
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ClassDetails getClassOrPluralElement() throws MappingException;

	/**
	 * Return the class itself
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TypeDetails getPropertyType() throws MappingException;

	/**
	 * Returns the returned class name itself or the element type if an array
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getClassOrElementName() throws MappingException;

	/**
	 * Returns the returned class name itself
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTypeName() throws MappingException;

	/**
	 * Return the Hibernate mapping property
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MemberDetails getAttributeMember();

	/**
	 * Return the Class the property is declared on
	 * If the property is declared on a @MappedSuperclass,
	 * this class will be different than the PersistentClass's class
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ClassDetails getDeclaringClass();
}
