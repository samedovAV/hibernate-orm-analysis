/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.spi;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.query.Query;
import org.hibernate.sql.exec.spi.Callback;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Context for execution of {@link Query}"
 */
public interface DomainQueryExecutionContext {
	/**
	 * The options to use for execution of the query
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryOptions getQueryOptions();

	/**
	 * The domain parameter bindings
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryParameterBindings getQueryParameterBindings();

	/**
	 * The callback reference
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Callback getCallback();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean hasCallbackActions() {
		final Callback callback = getCallback();
		return callback != null && callback.hasAfterLoadActions();
	}

	/**
	 * The underlying session
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionContractImplementor getSession();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Class<?> getResultType() {
		return null;
	}
}
