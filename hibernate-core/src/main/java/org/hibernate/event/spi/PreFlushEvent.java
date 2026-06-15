/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;

import org.hibernate.Incubating;
import org.hibernate.query.spi.QueryParameterBindings;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An event that occurs just before arguments are bound to JDBC
 * parameters during execution of HQL. Gives Hibernate a chance
 * to persist any transient entities used as query parameter
 * arguments.
 *
 * @author Gavin King
 * @since 7.2
 */
@Incubating
public class PreFlushEvent extends AbstractSessionEvent {

	private boolean preFlushRequired;
	private final QueryParameterBindings parameterBindings;

	public PreFlushEvent(QueryParameterBindings parameterBindings, EventSource source) {
		super( source );
		this.parameterBindings = parameterBindings;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QueryParameterBindings getParameterBindings() {
		return parameterBindings;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isPreFlushRequired() {
		return preFlushRequired;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setPreFlushRequired(boolean preFlushRequired) {
		this.preFlushRequired = preFlushRequired;
	}
}
