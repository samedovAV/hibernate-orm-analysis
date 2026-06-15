/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.persister.entity.EntityPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Occurs after the datastore is updated
 *
 * @author Gavin King
 */
public class PostUpdateEvent extends AbstractPostDatabaseOperationEvent {
	private final Object[] state;
	private final Object[] oldState;
	// list of dirty properties as computed during a FlushEntityEvent
	private final int[] dirtyProperties;

	public PostUpdateEvent(
			Object entity,
			Object id,
			Object[] state,
			Object[] oldState,
			int[] dirtyProperties,
			EntityPersister persister,
			SharedSessionContractImplementor source) {
		super( source, entity, id, persister );
		this.state = state;
		this.oldState = oldState;
		this.dirtyProperties = dirtyProperties;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object[] getOldState() {
		return oldState;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object[] getState() {
		return state;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int[] getDirtyProperties() {
		return dirtyProperties;
	}
}
