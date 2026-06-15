/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;

import org.hibernate.persister.entity.EntityPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Called before injecting property values into a newly loaded entity instance.
 *
 * @author Gavin King
 */
public class PreLoadEvent extends AbstractSessionEvent {
	private Object entity;
	private Object[] state;
	private Object id;
	private EntityPersister persister;

	public PreLoadEvent(EventSource session) {
		super(session);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void reset() {
		entity = null;
		state = null;
		id = null;
		persister = null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getEntity() {
		return entity;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getId() {
		return id;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityPersister getPersister() {
		return persister;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object[] getState() {
		return state;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PreLoadEvent setEntity(Object entity) {
		this.entity = entity;
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PreLoadEvent setId(Object id) {
		this.id = id;
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PreLoadEvent setPersister(EntityPersister persister) {
		this.persister = persister;
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PreLoadEvent setState(Object[] state) {
		this.state = state;
		return this;
	}
}
