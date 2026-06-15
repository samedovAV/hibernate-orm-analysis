/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.loader.ast.internal;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.sql.exec.internal.BaseExecutionContext;
import org.hibernate.sql.exec.spi.Callback;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class NoCallbackExecutionContext extends BaseExecutionContext {

	public NoCallbackExecutionContext(SharedSessionContractImplementor session) {
		super( session );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Callback getCallback() {
		return null;
//		throw new UnsupportedOperationException( "Follow-on locking not supported yet" );
	}

}
