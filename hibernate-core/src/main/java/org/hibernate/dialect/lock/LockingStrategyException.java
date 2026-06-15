/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.lock;

import org.hibernate.HibernateException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Represents an error trying to apply a {@link LockingStrategy} to an entity
 *
 * @author Steve Ebersole
 */
public abstract class LockingStrategyException extends HibernateException {
	private final Object entity;

	/**
	 * Constructs a LockingStrategyException
	 *
	 * @param entity The entity we were trying to lock
	 * @param message Message explaining the condition
	 */
	public LockingStrategyException(Object entity, String message) {
		super( message );
		this.entity = entity;
	}

	/**
	 * Constructs a LockingStrategyException
	 *
	 * @param entity The entity we were trying to lock
	 * @param message Message explaining the condition
	 * @param cause The underlying cause
	 */
	public LockingStrategyException(Object entity, String message, Throwable cause) {
		super( message, cause );
		this.entity = entity;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getEntity() {
		return entity;
	}
}
