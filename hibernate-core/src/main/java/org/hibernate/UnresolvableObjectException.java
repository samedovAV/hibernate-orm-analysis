/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate;

import static org.hibernate.pretty.MessageHelper.infoString;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Thrown when Hibernate could not resolve an object by id, especially when
 * loading an association.
 *
 * @author Gavin King
 */
public class UnresolvableObjectException extends HibernateException {
	private final Object identifier;
	private final String entityName;

	/**
	 * Constructs an {@code UnresolvableObjectException} using the specified information.
	 *
	 *  @param identifier The identifier of the entity which could not be resolved
	 * @param entityName The name of the entity which could not be resolved
	 */
	public UnresolvableObjectException(Object identifier, String entityName) {
		this( "No row with the given identifier exists", identifier, entityName );
	}

	protected UnresolvableObjectException(String message, Object identifier, String clazz) {
		super( message );
		this.identifier = identifier;
		this.entityName = clazz;
	}

	/**
	 * Factory method for building and throwing an {@code UnresolvableObjectException} if the entity is null.
	 *
	 * @param entity The entity to check for nullness
	 * @param identifier The identifier of the entity
	 * @param entityName The name of the entity
	 *
	 * @throws UnresolvableObjectException Thrown if entity is null
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static void throwIfNull(Object entity, Object identifier, String entityName)
			throws UnresolvableObjectException {
		if ( entity == null ) {
			throw new UnresolvableObjectException( identifier, entityName );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getIdentifier() {
		return identifier;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getEntityName() {
		return entityName;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getMessage() {
		return super.getMessage() + " for entity " + infoString( entityName, identifier );
	}

}
