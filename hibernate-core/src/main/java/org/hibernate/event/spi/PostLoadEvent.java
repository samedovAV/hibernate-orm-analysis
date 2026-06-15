/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;

import org.hibernate.persister.entity.EntityPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Occurs after an entity instance is fully loaded.
 *
 * @author Kabir Khan, Gavin King
 */
public class PostLoadEvent extends AbstractSessionEvent {
	private Object entity;
	private Object id;
	private EntityPersister persister;

	public PostLoadEvent(EventSource session) {
		super(session);
	}

	public PostLoadEvent(Object id, EntityPersister persister, Object entity, EventSource session) {
		super(session);
		this.id = id;
		this.persister = persister;
		this.entity = entity;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void reset() {
		entity = null;
		id = null;
		persister = null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getEntity() {
		return entity;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityPersister getPersister() {
		return persister;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getId() {
		return id;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PostLoadEvent setEntity(Object entity) {
		this.entity = entity;
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PostLoadEvent setId(Object id) {
		this.id = id;
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PostLoadEvent setPersister(EntityPersister persister) {
		this.persister = persister;
		return this;
	}

}
