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
 * Occurs after inserting an item in the datastore
 *
 * @author Gavin King
 */
public class PostInsertEvent extends AbstractPostDatabaseOperationEvent {
	private final Object[] state;

	public PostInsertEvent(
			Object entity,
			Object id,
			Object[] state,
			EntityPersister persister,
			SharedSessionContractImplementor source) {
		super( source, entity, id, persister );
		this.state = state;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object[] getState() {
		return state;
	}
}
