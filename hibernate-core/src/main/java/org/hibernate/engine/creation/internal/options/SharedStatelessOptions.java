/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.creation.internal.options;

import org.hibernate.Transaction;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.engine.spi.TransactionCompletionCallbacksImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Mutable collector for creating a stateless child session from an existing
/// session.
///
/// The collector starts with the normal stateless defaults and then inherits the
/// values that are meaningful for child session creation, such as tenant
/// identifier and JDBC time zone. Calling [#shareTransactionContext()]
/// enables reuse of the original session's transaction/JDBC context through
/// [CommonSharedOptions].
///
/// @since 8.0
/// @author Steve Ebersole
public class SharedStatelessOptions extends StatelessOptions implements CommonSharedOptions {
	private final SharedSessionContractImplementor original;
	private boolean shareTransactionContext;

	public SharedStatelessOptions(SharedSessionContractImplementor original) {
		super( original.getSessionFactory() );
		this.original = original;
		tenantIdentifier( original.getTenantIdentifierValue() );
		jdbcTimeZone( original.getJdbcTimeZone() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SharedSessionContractImplementor getOriginalSession() {
		return original;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isTransactionCoordinatorShared() {
		return shareTransactionContext;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Transaction getTransaction() {
		return shareTransactionContext
				? original.getTransaction()
				: null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TransactionCompletionCallbacksImplementor getTransactionCompletionCallbacks() {
		return shareTransactionContext
				? original.getTransactionCompletionCallbacksImplementor()
				: null;
	}

	/// Enable sharing of the original session's transaction/JDBC context.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void shareTransactionContext() {
		shareTransactionContext = true;
	}
}
