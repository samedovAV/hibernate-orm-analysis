/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Event class for {@link org.hibernate.Session#evict}
 * and {@link org.hibernate.Session#detach}.
 *
 * @author Steve Ebersole
 *
 * @see org.hibernate.Session#evict
 * @see org.hibernate.Session#detach
 */
public class EvictEvent extends AbstractSessionEvent {

	private Object object;

	public EvictEvent(Object object, EventSource source) {
		super(source);
		if (object == null) {
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
}
