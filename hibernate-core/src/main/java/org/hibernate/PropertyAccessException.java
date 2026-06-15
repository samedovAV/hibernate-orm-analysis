/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate;

import static org.hibernate.internal.util.StringHelper.qualify;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A problem occurred accessing a property of an instance of a
 * persistent class by reflection, or via enhanced entities.
 * There are a number of possible underlying causes, including:
 * <ul>
 * <li>failure of a security check,
 * <li>an exception occurring inside the getter or setter method,
 * <li>a nullable database column was mapped to a primitive-type
 *     property, or
 * <li>the Hibernate type was not castable to the property type
 *     (or vice-versa)
 * </ul>
 *
 * @author Gavin King
 */
public class PropertyAccessException extends HibernateException {
	private final Class<?> persistentClass;
	private final String propertyName;
	private final boolean wasSetter;

	/**
	 * Constructs a {@code PropertyAccessException} using the specified information.
	 *
	 * @param cause The underlying cause
	 * @param message A message explaining the exception condition
	 * @param wasSetter Was the attempting to access the setter the cause of the exception?
	 * @param persistentClass The class which is supposed to contain the property in question
	 * @param propertyName The name of the property.
	 */
	public PropertyAccessException(
			@Nullable Throwable cause,
			String message,
			boolean wasSetter,
			Class<?> persistentClass,
			String propertyName) {
		super( message, cause );
		this.persistentClass = persistentClass;
		this.wasSetter = wasSetter;
		this.propertyName = propertyName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getPersistentClass() {
		return persistentClass;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getPropertyName() {
		return propertyName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String originalMessage() {
		return super.getMessage();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getMessage() {
		return originalMessage()
				+ ": '" + qualify( persistentClass.getName(), propertyName ) + "'"
				+ ( wasSetter ? " (setter)" : " (getter)" );
	}
}
