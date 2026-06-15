/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.loader.ast.internal;

import org.hibernate.LockOptions;
import org.hibernate.engine.spi.EntityHolder;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.engine.spi.SubselectFetch;
import org.hibernate.query.internal.SimpleQueryOptions;
import org.hibernate.query.spi.QueryOptions;
import org.hibernate.sql.exec.internal.BaseExecutionContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

class ExecutionContextWithSubselectFetchHandler extends BaseExecutionContext {

	private final SubselectFetch.RegistrationHandler subSelectFetchableKeysHandler;
	private final boolean readOnly;
	private final QueryOptions queryOptions;

	public ExecutionContextWithSubselectFetchHandler(
			SharedSessionContractImplementor session,
			SubselectFetch.RegistrationHandler subSelectFetchableKeysHandler) {
		super( session );
		this.subSelectFetchableKeysHandler = subSelectFetchableKeysHandler;
		this.readOnly = false;
		this.queryOptions = QueryOptions.NONE;
	}

	public ExecutionContextWithSubselectFetchHandler(
			SharedSessionContractImplementor session,
			SubselectFetch.RegistrationHandler subSelectFetchableKeysHandler,
			boolean readOnly,
			LockOptions lockOptions) {
		super( session );
		this.subSelectFetchableKeysHandler = subSelectFetchableKeysHandler;
		this.readOnly = readOnly;
		this.queryOptions = determineQueryOptions( readOnly, lockOptions );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private QueryOptions determineQueryOptions(boolean readOnly, LockOptions lockOptions) {
		return new SimpleQueryOptions( lockOptions, readOnly ? true : null );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void registerLoadingEntityHolder(EntityHolder holder) {
		if ( subSelectFetchableKeysHandler != null ) {
			subSelectFetchableKeysHandler.addKey( holder );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QueryOptions getQueryOptions() {
		return queryOptions;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean upgradeLocks() {
		return true;
	}
}
