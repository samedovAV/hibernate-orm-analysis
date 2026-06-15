/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;

import java.util.Set;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Event class for {@link org.hibernate.FlushMode#AUTO automatic}
 * stateful session flush.
 *
 * @author Steve Ebersole
 */
public class AutoFlushEvent extends FlushEvent {

	private Set<String> querySpaces;
	private boolean flushRequired;
	private final boolean skipPreFlush;

	public AutoFlushEvent(Set<String> querySpaces, EventSource source) {
		this( querySpaces, false, source );
	}

	public AutoFlushEvent(Set<String> querySpaces, boolean skipPreFlush, EventSource source) {
		super( source );
		this.querySpaces = querySpaces;
		this.skipPreFlush = skipPreFlush;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<String> getQuerySpaces() {
		return querySpaces;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setQuerySpaces(Set<String> querySpaces) {
		this.querySpaces = querySpaces;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isFlushRequired() {
		return flushRequired;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setFlushRequired(boolean dirty) {
		this.flushRequired = dirty;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isSkipPreFlush() {
		return skipPreFlush;
	}
}
