/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.action.internal;
import org.hibernate.HibernateException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An exception indicating that an {@link EntityAction} was vetoed.
 *
 * @author Vlad Mihalcea
 */
public class EntityActionVetoException extends HibernateException {

	private final EntityAction entityAction;

	/**
	 * Constructs a EntityActionVetoException
	 *
	 * @param message Message explaining the exception condition
	 * @param entityAction The {@link EntityAction} was vetoed that was vetoed.
	 */
	public EntityActionVetoException(String message, EntityAction entityAction) {
		super( message );
		this.entityAction = entityAction;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityAction getEntityAction() {
		return entityAction;
	}
}
