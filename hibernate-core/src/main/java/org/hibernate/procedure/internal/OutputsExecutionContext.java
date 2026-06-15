/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.procedure.internal;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.query.spi.QueryOptions;
import org.hibernate.sql.exec.internal.BaseExecutionContext;
import org.hibernate.sql.exec.internal.CallbackImpl;
import org.hibernate.sql.exec.spi.Callback;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class OutputsExecutionContext extends BaseExecutionContext {
	private final Callback callback = new CallbackImpl();

	public OutputsExecutionContext(SharedSessionContractImplementor session) {
		super( session );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QueryOptions getQueryOptions() {
		return QueryOptions.READ_WRITE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Callback getCallback() {
		return callback;
	}

}
