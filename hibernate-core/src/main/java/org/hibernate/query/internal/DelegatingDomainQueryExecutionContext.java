/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.internal;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.query.spi.DomainQueryExecutionContext;
import org.hibernate.query.spi.QueryOptions;
import org.hibernate.query.spi.QueryParameterBindings;
import org.hibernate.sql.exec.spi.Callback;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class DelegatingDomainQueryExecutionContext implements DomainQueryExecutionContext {
	private final DomainQueryExecutionContext delegate;

	public DelegatingDomainQueryExecutionContext(DomainQueryExecutionContext delegate) {
		this.delegate = delegate;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public QueryOptions getQueryOptions() {
		return delegate.getQueryOptions();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public QueryParameterBindings getQueryParameterBindings() {
		return delegate.getQueryParameterBindings();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Callback getCallback() {
		return delegate.getCallback();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean hasCallbackActions() {
		return delegate.hasCallbackActions();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedSessionContractImplementor getSession() {
		return delegate.getSession();
	}
}
