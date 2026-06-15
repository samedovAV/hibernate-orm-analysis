/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.internal;

import org.hibernate.LockOptions;
import org.hibernate.query.spi.QueryOptionsAdapter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class SimpleQueryOptions extends QueryOptionsAdapter {

	private final LockOptions lockOptions;
	private final Boolean readOnlyEnabled;

	public SimpleQueryOptions(LockOptions lockOptions, Boolean readOnlyEnabled) {
		this.lockOptions = lockOptions;
		this.readOnlyEnabled = readOnlyEnabled;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public LockOptions getLockOptions() {
		return lockOptions;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean isReadOnly() {
		return readOnlyEnabled;
	}
}
