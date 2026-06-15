/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Event class for {@link org.hibernate.Session#persist}.
 *
 * @author Gavin King
 *
 * @see org.hibernate.Session#persist
 */
public class PersistEvent extends AbstractSessionEvent {

	private Object object;
	private String entityName;

	public PersistEvent(String entityName, Object original, EventSource source) {
		this(original, source);
		this.entityName = entityName;
	}

	public PersistEvent(Object object, EventSource source) {
		super(source);
		if ( object == null ) {
			throw new IllegalArgumentException( "Entity may not be null" );
		}
		this.object = object;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getObject() {
		return object;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setObject(Object object) {
		this.object = object;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getEntityName() {
		return entityName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

}
