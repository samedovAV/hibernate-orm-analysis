/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;

import org.hibernate.engine.spi.SessionFactoryImplementor;

import java.io.Serializable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Base class for events which are generated from a {@linkplain org.hibernate.Session Session}
 * ({@linkplain EventSource}).
 *
 * @author Steve Ebersole
 */
public abstract class AbstractSessionEvent implements Serializable {
	protected final EventSource source;

	/**
	 * Constructs an event from the given event session.
	 *
	 * @param source The session event source.
	 */
	public AbstractSessionEvent(EventSource source) {
		this.source = source;
	}

	/**
	 * Returns the session event source for this event. This is the underlying
	 * session from which this event was generated.
	 *
	 * @return The session event source.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final EventSource getSession() {
		return getEventSource();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final EventSource getEventSource() {
		return source.asEventSource();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryImplementor getFactory() {
		return source.getFactory();
	}
}
