/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.exec.internal;

import org.hibernate.engine.spi.LoadQueryInfluencers;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.query.internal.QueryParameterBindingsImpl;
import org.hibernate.query.spi.QueryOptions;
import org.hibernate.query.spi.QueryParameterBindings;
import org.hibernate.sql.exec.spi.Callback;
import org.hibernate.sql.exec.spi.ExecutionContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class BaseExecutionContext implements ExecutionContext {

	private final SharedSessionContractImplementor session;
	private final boolean transactionActive;

	public BaseExecutionContext(SharedSessionContractImplementor session) {
		this.session = session;
		transactionActive = session.isTransactionInProgress();
	}

	// Optimization: mark this as final so to avoid a megamorphic call on this
	// as it will never need to be implemented differently.
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final SharedSessionContractImplementor getSession() {
		return session;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final boolean isTransactionActive() {
		return transactionActive;
	}

	// Also marked as final for the same reason
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public final LoadQueryInfluencers getLoadQueryInfluencers() {
		return session.getLoadQueryInfluencers();
	}

	// Unable to avoid the megamorphic calls in this case, but at least
	// let's reduce this to the most common case.
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QueryOptions getQueryOptions() {
		return QueryOptions.NONE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getQueryIdentifier(String sql) {
		return sql;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Callback getCallback() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QueryParameterBindings getQueryParameterBindings() {
		return QueryParameterBindingsImpl.EMPTY;
	}

}
