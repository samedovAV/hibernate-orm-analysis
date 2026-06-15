/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate;

import static org.hibernate.pretty.MessageHelper.infoString;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * This exception is thrown when an operation would break session-scoped identity.
 * This occurs if the user tries to associate two different instances of the same
 * Java class with a particular identifier, in the scope of a single {@link Session}.
 *
 * @author Gavin King
 */
public class NonUniqueObjectException extends HibernateException {
	private final Object identifier;
	private final String entityName;

	/**
	 * Constructs a {@code NonUniqueObjectException} using the given information.
	 *
	 * @param message A message explaining the exception condition
	 * @param entityId The identifier of the entity
	 * @param entityName The name of the entity
	 */
	public NonUniqueObjectException(String message, Object entityId, String entityName) {
		super( message );
		this.entityName = entityName;
		this.identifier = entityId;
	}

	/**
	 * Constructs a {@code NonUniqueObjectException} using the given information,
	 * and using a standard message.
	 *
	 * @param entityId The identifier of the entity
	 * @param entityName The name of the entity
	 */
	public NonUniqueObjectException(Object entityId, String entityName) {
		this(
				"A different object with the same identifier value was already associated with this persistence context",
				entityId,
				entityName
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getEntityName() {
		return entityName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getIdentifier() {
		return identifier;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getMessage() {
		return super.getMessage() + " for entity " + infoString( entityName, identifier );
	}
}
