/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate;

import static org.hibernate.internal.util.StringHelper.qualify;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Thrown when the state of an entity cannot be made persistent
 * because the entity holds a reference to a transient entity.
 * <p>
 * An entity is considered <em>transient</em> if it is:
 * <ul>
 * <li>a newly-instantiated instance of an entity class which has
 *    never been {@linkplain Session#persist made persistent} in
 *    the database, or
 * <li>an entity instance previously associated with a persistence
 *     context which has been {@linkplain Session#remove removed}
 *     from the database.
 * </ul>
 *
 * @author Gail Badner
 */
public class TransientPropertyValueException extends TransientObjectException {
	private final String transientEntityName;
	private final String propertyOwnerEntityName;
	private final String propertyName;

	/**
	 * Constructs a {@code TransientPropertyValueException} instance.
	 *
	 * @param message - the exception message;
	 * @param transientEntityName - the entity name for the transient entity
	 * @param propertyOwnerEntityName - the entity name for entity that owns
	 * the association property.
	 * @param propertyName - the property name
	 */
	public TransientPropertyValueException(
			String message,
			String transientEntityName,
			String propertyOwnerEntityName,
			String propertyName) {
		super( message );
		this.transientEntityName = transientEntityName;
		this.propertyOwnerEntityName = propertyOwnerEntityName;
		this.propertyName = propertyName;
	}

	/**
	 * Returns the entity name for the transient entity.
	 * @return the entity name for the transient entity.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTransientEntityName() {
		return transientEntityName;
	}

	/**
	 * Returns the entity name for entity that owns the association
	 * property.
	 *
	 * @return the entity name for entity that owns the association
	 * property
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getPropertyOwnerEntityName() {
		return propertyOwnerEntityName;
	}

	/**
	 * Returns the property name.
	 *
	 * @return the property name.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getMessage() {
		return super.getMessage() + " ["
				+ qualify( propertyOwnerEntityName, propertyName )
				+ " -> " + transientEntityName + "]";
	}
}
