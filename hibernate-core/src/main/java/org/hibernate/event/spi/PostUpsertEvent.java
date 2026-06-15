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
 * Occurs after the datastore is updated via a SQL {@code merge}
 *
 * @author Gavin King
 */
public class PostUpsertEvent extends AbstractPostDatabaseOperationEvent {
	private final Object[] state;
	//list of dirty properties as computed by Hibernate during a FlushEntityEvent
	private final int[] dirtyProperties;

	public PostUpsertEvent(
			Object entity,
			Object id,
			Object[] state,
			int[] dirtyProperties,
			EntityPersister persister,
			SharedSessionContractImplementor source) {
		super( source, entity, id, persister );
		this.state = state;
		this.dirtyProperties = dirtyProperties;
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
