/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Event class for {@link org.hibernate.Session#merge}.
 *
 * @author Gavin King
 *
 * @see org.hibernate.Session#merge
 */
public class MergeEvent extends AbstractSessionEvent {

	private Object original;
	private Object requestedId;
	private String entityName;
	private Object entity;
	private Object result;

	public MergeEvent(String entityName, Object original, EventSource source) {
		this(original, source);
		this.entityName = entityName;
	}

	public MergeEvent(String entityName, Object original, Object id, EventSource source) {
		this(entityName, original, source);
		this.requestedId = id;
		if ( requestedId == null ) {
			throw new IllegalArgumentException( "Identifier may not be null" );
		}
	}

	public MergeEvent(Object object, EventSource source) {
		super(source);
		if ( object == null ) {
			throw new IllegalArgumentException( "Entity may not be null" );
		}
		this.original = object;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getOriginal() {
		return original;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setOriginal(Object object) {
		this.original = object;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getRequestedId() {
		return requestedId;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setRequestedId(Object requestedId) {
		this.requestedId = requestedId;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getEntityName() {
		return entityName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getEntity() {
		return entity;
	}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setEntity(Object entity) {
		this.entity = entity;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getResult() {
		return result;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setResult(Object result) {
		this.result = result;
	}
}
